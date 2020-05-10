package se.kth.iv1350.processSale.controller;

/**
 * A generic Exception that is thrown when an operation fails in the lower layers.
 */
public class OperationFailedException extends Exception{
	
	/**
	 * Creates a new instance
	 * 
	 * @param message Describes the exception.
	 * @param cause The cause of the exception.
	 */
	OperationFailedException(String message,Exception cause) {
		super(message,cause);
	}

}
