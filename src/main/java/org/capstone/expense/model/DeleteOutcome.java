package org.capstone.expense.model;

/**
 * Enumeration of possible outcomes when attempting to delete a transaction.
 * DELETED: transaction was successfully removed.
 * CANCELLED: deletion was not confirmed and no change was made.
 */
public enum DeleteOutcome {
	DELETED,
	CANCELLED
}
