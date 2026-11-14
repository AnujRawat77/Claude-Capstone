package org.capstone.expense.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class BalanceSummary {
	BigDecimal totalIncome;
	BigDecimal totalExpenses;
	BigDecimal netBalance;
}
