package ro.hpe.objects;

/**Kpi POJO object
 * @author popescra
 *
 */
public class Kpi {
	private  int processedFilesCounter=0;
	private  int rowsCounter = 0;
	private  int callsCounter = 0;
	private  int messageCounter = 0;
	private  int differentOriginCountryCounter= 0;
	private  int differentDestinationCountryCounter = 0;
	private  int averageProcessTime = 0;
	
	public Kpi(int processedFilesCounter, int rowsCounter, int callsCounter, int messageCounter, 
			int  differentOriginCountryCounter, int differentDestinationCountryCounter, int averageProcessTime ) {
		this.processedFilesCounter=processedFilesCounter;
		this.rowsCounter = rowsCounter;
		this.callsCounter = callsCounter;
		this.messageCounter = messageCounter;
		this.differentOriginCountryCounter= differentOriginCountryCounter;
		this.differentDestinationCountryCounter = differentDestinationCountryCounter;
		this.averageProcessTime = averageProcessTime;
	}
	
	public int getProcessedFilesCounter() {
		return processedFilesCounter;
	}
	public void setProcessedFilesCounter(int processedFilesCounter) {
		this.processedFilesCounter = processedFilesCounter;
	}
	public int getRowsCounter() {
		return rowsCounter;
	}
	public void setRowsCounter(int rowsCounter) {
		this.rowsCounter = rowsCounter;
	}
	public int getCallsCounter() {
		return callsCounter;
	}
	public void setCallsCounter(int callsCounter) {
		this.callsCounter = callsCounter;
	}
	public int getMessageCounter() {
		return messageCounter;
	}
	public void setMessageCounter(int messageCounter) {
		this.messageCounter = messageCounter;
	}
	public int getDifferentOriginCountryCounter() {
		return differentOriginCountryCounter;
	}
	public void setDifferentOriginCountryCounter(int differentOriginCountryCounter) {
		this.differentOriginCountryCounter = differentOriginCountryCounter;
	}
	public int getDifferentDestinationCountryCounter() {
		return differentDestinationCountryCounter;
	}
	public void setDifferentDestinationCountryCounter(int differentDestinationCountryCounter) {
		this.differentDestinationCountryCounter = differentDestinationCountryCounter;
	}
	public int getAverageProcessTime() {
		return averageProcessTime;
	}
	public void setAverageProcessTime(int averageProcessTime) {
		this.averageProcessTime = averageProcessTime;
	}

}
