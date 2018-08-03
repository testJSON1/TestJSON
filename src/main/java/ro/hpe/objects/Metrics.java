package ro.hpe.objects;

import java.util.HashMap;
import java.util.Map;

/**Metrics POJO object
 * @author popescra
 *
 */
public class Metrics {
	private int missingFieldsCounter=0;
	private int blankContentCounter=0;
	private int fieldsErrorCounter=0;
	private Map<Integer, Integer>  callsPerOriginCountryCounter = new HashMap<Integer, Integer>();		
	private Map<Integer, Integer>  callsPerDestCountryCounter = new HashMap<Integer, Integer>();
	private float successfulCallCounter=0;
	private Map<Integer,Integer>  averageDurationPerCountryCounter = new HashMap<Integer, Integer>();
	private Map<String,Integer>  wordOccurrenceCounter = new HashMap<String,Integer>();
	
	
	public Metrics(int missingFieldsCounter, int blankContentCounter, int fieldsErrorCounter, Map<Integer, Integer> callsPerOriginCountryCounter, 
				Map<Integer, Integer> callsPerDestCountryCounter, float successfulCallCounter,  Map<Integer , Integer> averageDurationPerCountryCounter, Map<String, Integer> wordOccurrenceCounter) {
		this.missingFieldsCounter = missingFieldsCounter;
		this.blankContentCounter = blankContentCounter;
		this.fieldsErrorCounter = fieldsErrorCounter;
		this.callsPerOriginCountryCounter = new HashMap<Integer, Integer>(callsPerOriginCountryCounter);
		this.callsPerDestCountryCounter= new HashMap<Integer, Integer>(callsPerDestCountryCounter);
		this.successfulCallCounter = successfulCallCounter;
		this.averageDurationPerCountryCounter =  new HashMap<Integer, Integer>(averageDurationPerCountryCounter);
		this.wordOccurrenceCounter = new HashMap<String, Integer>(wordOccurrenceCounter);
	}
	
	public int getMissingFieldsCounter() {
		return missingFieldsCounter;
	}
	public void setMissingFieldsCounter(int missingFieldsCounter) {
		this.missingFieldsCounter = missingFieldsCounter;
	}
	public int getBlankContentCounter() {
		return blankContentCounter;
	}
	public void setBlankContentCounter(int blankContentCounter) {
		this.blankContentCounter = blankContentCounter;
	}
	public int getFieldsErrorCounter() {
		return fieldsErrorCounter;
	}
	public void setFieldsErrorCounter(int fieldsErrorCounter) {
		this.fieldsErrorCounter = fieldsErrorCounter;
	}
	public Map<Integer, Integer> getCallsPerOriginCountryCounter() {
		return callsPerOriginCountryCounter;
	}
	public void setCallsPerOriginCountryCounter(Map<Integer, Integer> callsPerOriginCountryCounter) {
		this.callsPerOriginCountryCounter = callsPerOriginCountryCounter;
	}
	public Map<Integer, Integer> getCallsPerDestCountryCounter() {
		return callsPerDestCountryCounter;
	}
	public void setCallsPerDestCountryCounter(Map<Integer, Integer> callsPerDestCountryCounter) {
		this.callsPerDestCountryCounter = callsPerDestCountryCounter;
	}
	public float getSuccessfulCallCounter() {
		return successfulCallCounter;
	}
	public void setSuccessfulCallCounter(float successfulCallCounter) {
		this.successfulCallCounter = successfulCallCounter;
	}
	public Map<Integer, Integer> getAverageDurationPerCountryCounter() {
		return averageDurationPerCountryCounter;
	}
	public void setAverageDurationPerCountryCounter(Map<Integer, Integer> averageDurationPerCountryCounter) {
		this.averageDurationPerCountryCounter = averageDurationPerCountryCounter;
	}
	public Map<String, Integer> getWordOccurrenceCounter() {
		return wordOccurrenceCounter;
	}
	public void setWordOccurrenceCounter(Map<String, Integer> wordOccurrenceCounter) {
		this.wordOccurrenceCounter = wordOccurrenceCounter;
	}	
}
