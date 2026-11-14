package org.capstone.expense.storage;

import org.capstone.expense.model.BudgetKey;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BudgetStore {
	private final Map<BudgetKey, BigDecimal> budgets = new HashMap<>();

	public void put(BudgetKey key, BigDecimal amount) {
		budgets.put(key, amount);
	}

	public Optional<BigDecimal> find(BudgetKey key) {
		return Optional.ofNullable(budgets.get(key));
	}
}
