package be.abc.bank.adapter.Exception;

/**
 * ClassNotFoundException for account id not found in the consumed services
 */
public class CustomerNotFoundException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String message;

	public CustomerNotFoundException(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public CustomerNotFoundException(String message) {
		this.message = message;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
