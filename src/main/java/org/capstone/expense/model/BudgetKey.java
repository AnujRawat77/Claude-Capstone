package org.capstone.expense.model;

import lombok.Value;

@Value
public class BudgetKey {
	ExpenseCategory category;
	int year;
	int month;
}
