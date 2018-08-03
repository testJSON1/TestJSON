package ro.hpe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.util.JsonLoader;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import ro.hpe.objects.CallInformation;
import ro.hpe.objects.Params;



/**Message processing class
 * @author popescra
 *
 */
public class ProcessMessage {
	private static final Logger logger = LoggerFactory.getLogger(ProcessMessage.class.getName());
	private static JsonNode schemaJSON = null;
	private static JsonNode schemaNotRequiredJSON = null;
	private static JsonSchema schema = null;
	private static JsonSchema schemaNotRequired = null;


	/**Process JSON file
	 * @param date
	 * @return Information regarded processed file.
	 */
	public  String processJson(String date) {
		long startTime = System.currentTimeMillis();
		String locationFile = Params.URL_PATH + "MCP_" + date + ".json";
		String line="";
		String returnMessage="";
		BufferedReader br = null;
		int nrRows = 0;
		try {
			URL url = new URL(locationFile);		
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((line = br.readLine()) != null) {
	        	parseJson(line);
	        	nrRows ++;
			}

		} catch (Exception e) {
			returnMessage="An exception occurred processing json file: " + locationFile + ", Last line processed was: " + line + "\n Exception : \n" + e.getMessage();
			logger.error(returnMessage, e);			
			return returnMessage;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("Cannot close Buffered Stream", e);
			}
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
    	Params.addFileInfo(duration, nrRows);
		returnMessage = "The  json file: " + locationFile + " was succesfully processed in: " + duration + "mSec;";
		return returnMessage; 
	}
	/**JSON parser according to business logic
	 * @param line JSON to be parsed
	 */
	private void parseJson(String line) {
		boolean error = false;
		boolean errorRequired = false;
		boolean errorNotRequired = false;
		JsonNode data = null;
		if (ProcessMessage.schemaJSON == null) {
			try {
				   ProcessMessage.schemaJSON = JsonLoader.fromURL(this.getClass().getClassLoader().getResource(Params.JSON_SCHEMA));
				   JsonSchemaFactory factory = JsonSchemaFactory.byDefault(); 
			       schema = factory.getJsonSchema(ProcessMessage.schemaJSON);
			} catch (Exception e) {
					logger.error("JSON_Schema cannot be loaded.  Abort processing JSON", e);
					return;
			}	
		}
		try {
			   data = JsonLoader.fromString(line);
			   ProcessingReport report = schema.validate(data);
			   if(report == null || !report.isSuccess()) {
				   error = true;
				   errorRequired = true;
				   logger.info("Validation against Schema whith required fields failed for following JSON: " + line);
			   }
		} catch (JsonParseException e1) {
				logger.warn("Following JSON cannot be parsed: " + line);
				error = true;
		} catch (ProcessingException e2) {
				logger.error("Following JSON cannot be validated: " + line, e2);
		    	error=true;
		    	errorRequired = true;
		} catch (IOException e3) {
				logger.error("Following JSON cannot be loaded: " + line, e3);
				error = true;
		}
		if (errorRequired) {		
			if (ProcessMessage.schemaNotRequiredJSON == null) {
				try {
					   ProcessMessage.schemaNotRequiredJSON = JsonLoader.fromURL(this.getClass().getClassLoader().getResource(Params.JSON_SCHEMA_NOT_REQUIRED));
					   JsonSchemaFactory factory = JsonSchemaFactory.byDefault(); 
				       schemaNotRequired = factory.getJsonSchema(ProcessMessage.schemaNotRequiredJSON);
				} catch (Exception e) {
						logger.error("JSON_Schema_Not_Required cannot be loaded.  Abort processing JSON", e);
						return;
				}	
			}
			try {
			   ProcessingReport report = schemaNotRequired.validate(data);
			   if(report == null || !report.isSuccess()) {
				   errorNotRequired = true;
				   logger.info("Validation against Schema whithout required fields failed for following JSON: " + line);
			   }
			} catch (ProcessingException e2) {
				logger.error("Following JSON cannot be validated: " + line, e2);
				errorNotRequired =true;
			}
		}
		if (error && !errorNotRequired) {
			Params.incFieldsErrorCounter();		
		} else if (errorNotRequired) {
			Params.incMissingFieldsCounter();		
		} else {
			logger.info("Following JSON was parsed and validated properly:: " + line);
			CallInformation callInformation;
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				callInformation = objectMapper.treeToValue(data, CallInformation.class);
			} catch (Exception e) {
				logger.error("Extracting data from JSON gave an exception. Abort processing this JSON: " + line, e);
				return;			
				
			}
			if (callInformation.getMessage_type().equals("MSG")) {
				Params.addWordcounter(callInformation.getMessage_content());
			} else {
				Params.addCall(getCountryCode(callInformation.getOrigin()), getCountryCode(callInformation.getDestination()),  callInformation.getDuration());
				if (callInformation.getStatus_code().equals("OK") ) {
					Params.incOkCounter();
				} else {
					Params.incKoCounter();
				}
			}
		}
		
	}
	
	/**Get country code from a number
	 * @param number Number from which country code should be extracted
	 * @return COUNTRY CODE
	 */
	private int getCountryCode(long number) {
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			String  phone = "+" + Long.toString(number);
		    PhoneNumber numberProto = phoneUtil.parse(phone, "");
		    return numberProto.getCountryCode();
		} catch (NumberParseException e) {
		    logger.error("NumberParseException was thrown: ", e);
		    return 0;
		}
	}

}
