package se.kth.iv1350.processSale.integration;

/**
 * Thrown when a failure to connect to the database occurs.
 */
public class DBConnectionFailureException extends RuntimeException {
	
	/**
	 * Creates a new instance.
	 */
	DBConnectionFailureException() {
		super("Failed to connect to database");
	}
}
