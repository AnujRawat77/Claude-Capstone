package org.capstone.expense.exception;

/**
 * Unchecked exception thrown when a requested transaction ID does not exist.
 * The message indicates which transaction was not found.
 * Caught at the console boundary; the application returns to the main menu.
 */
public class TransactionNotFoundException extends RuntimeException {

	/**
	 * Constructs a new TransactionNotFoundException with a message derived from the transaction ID.
	 *
	 * @param id the transaction ID that was not found
	 */
	public TransactionNotFoundException(int id) {
		super("No transaction with ID " + id + ".");
	}

	/**
	 * Constructs a new TransactionNotFoundException with the specified message.
	 *
	 * @param message the error message
	 */
	public TransactionNotFoundException(String message) {
		super(message);
	}
}
