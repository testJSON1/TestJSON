package ro.hpe.objects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.hpe.ProcessMessage;

/**Calls that contain all CONSTANTS and counters 
 * @author popescra
 *
 */
public class Params {
	private static final Logger logger = LoggerFactory.getLogger(Params.class.getName());
	public static final String URL_PATH = "https://raw.githubusercontent.com/vas-test/test1/master/logs/";
	public static final String JSON_SCHEMA = "/schema/callInformationSchema.json";
	public static final String JSON_SCHEMA_NOT_REQUIRED = "/schema/callInformationNotRequiredSchema.json";
	public static final List <String> WORDS = Arrays.asList("HELLO", "GOOD");
	private static int missingFieldsCounter=0;
	private static int blankContentCounter=0;
	private static int fieldsErrorCounter=0;
	private static Map<Integer, Integer>  callsPerOriginCountryCounter = new HashMap<Integer, Integer>();		
	private static Map<Integer, Integer>  callsPerDestCountryCounter = new HashMap<Integer, Integer>();
	private static int okCounter=0;
	private static int koCounter=0;
	private static Map<Integer,Integer>  averageDurationPerCountryCounter = new HashMap<Integer, Integer>();
	private static Map<Integer, Integer>  totalDurationPerCountryCounter = new HashMap<Integer, Integer>();
	private static Map<String,Integer>  wordOccurrenceCounter = new HashMap<String,Integer>();
	private static int processedFilesCounter=0;
	private static int rowsCounter = 0;
	private static int callsCounter = 0;
	private static int messageCounter = 0;
	private static int differentOriginCountryCounter= 0;
	private static int differentDestinationCountryCounter = 0;
	private static int averageProcessTime = 0;
	private static long totalProcessTime = 0;
	
	
	
	public static  synchronized void  incMissingFieldsCounter() {
		missingFieldsCounter ++;
	}
	
	public static synchronized void  incFieldsErrorCounter() {
		fieldsErrorCounter ++;
	}

	
	public static synchronized void  incOkCounter() {
		okCounter ++;
	}
	
	public static synchronized void  incKoCounter() {
		koCounter ++;
	}

	
	/**Add Call Information
	 * @param countryCodeOrigin Country code of origin 
	 * @param countryCodeDest Country code of destination
	 * @param duration Duration of the call.
	 */
	public static synchronized void  addCall(int countryCodeOrigin, int countryCodeDest, int duration) {
		callsCounter ++;
		int count = callsPerOriginCountryCounter.containsKey(countryCodeOrigin) ? callsPerOriginCountryCounter.get(countryCodeOrigin) : 0;
		callsPerOriginCountryCounter.put(countryCodeOrigin, count + 1);
		
		count = callsPerDestCountryCounter.containsKey(countryCodeDest) ? callsPerDestCountryCounter.get(countryCodeDest) : 0;
		callsPerDestCountryCounter.put(countryCodeDest, count + 1);
		
		count = totalDurationPerCountryCounter.containsKey(countryCodeDest) ? totalDurationPerCountryCounter.get(countryCodeDest) : 0;
		totalDurationPerCountryCounter.put(countryCodeDest, count + duration);
		
		averageDurationPerCountryCounter.put(countryCodeDest, totalDurationPerCountryCounter.get(countryCodeDest)/callsPerDestCountryCounter.get(countryCodeOrigin));
		
		differentOriginCountryCounter = callsPerOriginCountryCounter.size();
		differentDestinationCountryCounter = callsPerDestCountryCounter.size(); 

	}

	/**Add Information regarding Words used in Messages.  The list of words that are checked are defined in Params.WORDS 
	 * @param content Content of the message
	 */
	public static synchronized void  addWordcounter(String content) {
		messageCounter ++;
		if (content.isEmpty() ) {
			blankContentCounter ++;			
		} else {
			for (String word : Params.WORDS) {
				if (content.toUpperCase().contains(word)) {
					int count = wordOccurrenceCounter.containsKey(word) ? wordOccurrenceCounter.get(word) : 0;
					wordOccurrenceCounter.put(word, count + 1);
				}
			}
			
		}
	}
	
	/**Add Informations related to processed file
	 * @param duration duration for the file to be processed
	 * @param nrRows number of processed json's contained in the file
	 */
	public static synchronized void  addFileInfo(long duration, int nrRows) {
		processedFilesCounter ++;		
		totalProcessTime += duration;
		averageProcessTime = (int) totalProcessTime/processedFilesCounter;
		rowsCounter += nrRows;
	}

	/**Get JSON containing Kpis
	 * @return Kpi JSON
	 */
	public static synchronized String getKpiJson() {
		Kpi kpi = new Kpi(processedFilesCounter, rowsCounter, callsCounter, messageCounter,differentOriginCountryCounter, 
				differentDestinationCountryCounter, averageProcessTime );
		ObjectMapper mapper = new ObjectMapper();
		String returnMessage="";
		try {
			returnMessage = mapper.writeValueAsString(kpi);
		} catch (Exception e) {
			returnMessage="An exception occurred getting Kpi Json\n" + e.getMessage();
			logger.error(returnMessage, e);
			return returnMessage;
		}
		return returnMessage;
	}
	/**Get JSON containing Metrics 
	 * @return Metrics JSON
	 */
	public static synchronized String getMetricsJson() {
		float okCallsPerc = (int)(((float) okCounter / (okCounter + koCounter)) * 10000) /100F;
		Metrics Metrics = new Metrics(missingFieldsCounter, blankContentCounter, fieldsErrorCounter, callsPerOriginCountryCounter, 
				callsPerDestCountryCounter, okCallsPerc, averageDurationPerCountryCounter, wordOccurrenceCounter);
		ObjectMapper mapper = new ObjectMapper();
		String returnMessage="";
		try {
			returnMessage = mapper.writeValueAsString(Metrics);
		} catch (Exception e) {
			returnMessage="An exception occurred getting Metrics Json\n" + e.getMessage();
			logger.error(returnMessage, e);
			return returnMessage;
		}
		return returnMessage;
	}
	
}
