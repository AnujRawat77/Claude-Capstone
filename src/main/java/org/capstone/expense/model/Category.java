package org.capstone.expense.model;

import java.util.Optional;

/**
 * Common interface for expense and income category enums.
 * Links each category value to its transaction type and provides case-insensitive lookup.
 */
public interface Category {

	/**
	 * Returns the transaction type this category belongs to.
	 *
	 * @return EXPENSE or INCOME
	 */
	TransactionType transactionType();

	/**
	 * Returns the enum constant name.
	 *
	 * @return name of this enum constant
	 */
	String name();

	/**
	 * Looks up a category by name, case-insensitively.
	 * Searches across both ExpenseCategory and IncomeCategory values.
	 *
	 * @param name the category name (trimmed and matched case-insensitively)
	 * @return Optional containing the matching category, or empty if not found or input is null
	 */
	static Optional<Category> fromName(String name) {
		if (name == null) {
			return Optional.empty();
		}
		String trimmed = name.trim().toUpperCase();
		// Search ExpenseCategory first
		for (ExpenseCategory ec : ExpenseCategory.values()) {
			if (ec.name().equals(trimmed)) {
				return Optional.of(ec);
			}
		}
		// Then search IncomeCategory
		for (IncomeCategory ic : IncomeCategory.values()) {
			if (ic.name().equals(trimmed)) {
				return Optional.of(ic);
			}
		}
		return Optional.empty();
	}
}
