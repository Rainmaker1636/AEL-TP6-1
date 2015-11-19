package automata;

public class StateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StateException() {
	}

	public StateException(String message) {
		super(message);
	}

	public StateException(Throwable cause) {
		super(cause);
	}

	public StateException(String message, Throwable cause) {
		super(message, cause);
	}

	public StateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
