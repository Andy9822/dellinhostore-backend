package engsoft.dellinhostore.util;

public class ReturnMessage {

	private boolean success;
	private Object message;

	public ReturnMessage() {
		super();
	}

	public ReturnMessage(boolean success, Object message) {
		super();
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
