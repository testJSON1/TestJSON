package ro.hpe.objects;

/**CallInformation POJO object
 * @author popescra
 *
 */
public class CallInformation {
	private String message_type;
	private long timestamp;
	private long origin;
	private long destination;
	private int duration;
	private String status_code;
	private String status_description;
	private String message_content;
	private String message_status;
	public String getMessage_type() {
		return message_type;
	}
	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getOrigin() {
		return origin;
	}
	public void setOrigin(long origin) {
		this.origin = origin;
	}
	public long getDestination() {
		return destination;
	}
	public void setDestination(long destination) {
		this.destination = destination;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getStatus_description() {
		return status_description;
	}
	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public String getMessage_status() {
		return message_status;
	}
	public void setMessage_status(String message_status) {
		this.message_status = message_status;
	}

}
