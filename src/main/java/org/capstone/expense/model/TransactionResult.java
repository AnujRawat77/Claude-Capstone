package org.capstone.expense.model;

import lombok.Value;

import java.util.Optional;

@Value
public class TransactionResult {
	Transaction transaction;
	Optional<BudgetAlert> budgetAlert;
}
