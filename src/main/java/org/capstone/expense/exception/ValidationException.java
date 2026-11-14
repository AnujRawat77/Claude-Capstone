package org.capstone.expense.exception;

/**
 * Unchecked exception for user input or business rule violations.
 * The message describes the correctable error and is displayed to the user.
 * Caught at the console boundary; the application returns to the main menu.
 */
public class ValidationException extends RuntimeException {

	/**
	 * Constructs a new ValidationException with the specified message.
	 *
	 * @param message the error message displayed to the user
	 */
	public ValidationException(String message) {
		super(message);
	}
}
