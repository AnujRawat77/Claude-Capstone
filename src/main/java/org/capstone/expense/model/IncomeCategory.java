package org.capstone.expense.model;

/**
 * Enumeration of income categories.
 * Each value represents a category of income and belongs to the INCOME transaction type.
 */
public enum IncomeCategory implements Category {
	SALARY,
	BUSINESS,
	INVESTMENT,
	OTHER_INCOME;

	@Override
	public TransactionType transactionType() {
		return TransactionType.INCOME;
	}
}
