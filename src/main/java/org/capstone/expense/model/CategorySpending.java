package org.capstone.expense.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CategorySpending {
	ExpenseCategory category;
	BigDecimal total;
	BigDecimal percentage;
}
