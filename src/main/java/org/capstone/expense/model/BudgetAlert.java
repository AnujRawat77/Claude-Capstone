package org.capstone.expense.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class BudgetAlert {
	ExpenseCategory category;
	BigDecimal budget;
	BigDecimal monthlySpend;
	int year;
	int month;
}
