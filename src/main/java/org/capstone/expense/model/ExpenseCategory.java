package org.capstone.expense.model;

/**
 * Enumeration of expense categories.
 * Each value represents a category of expense and belongs to the EXPENSE transaction type.
 * Order matters: list numbering in the menu depends on enum order.
 */
public enum ExpenseCategory implements Category {
	FOOD,
	TRANSPORT,
	SHOPPING,
	BILLS,
	HEALTH,
	ENTERTAINMENT,
	OTHER;

	@Override
	public TransactionType transactionType() {
		return TransactionType.EXPENSE;
	}
}
