package wurmod.mod;

public class ModException extends Exception {

	private final Throwable cause;
	
	public ModException(Throwable cause) {
		this.cause = cause;
	}
	
	public ModException() {
		this.cause = null;
	}
	
	@Override
	public synchronized Throwable getCause() {
		return cause;
	}
}
