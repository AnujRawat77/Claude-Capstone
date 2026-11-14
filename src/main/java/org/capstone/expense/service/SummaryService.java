package org.capstone.expense.service;

import org.capstone.expense.model.*;
import org.capstone.expense.storage.TransactionStore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SummaryService {
	private final TransactionStore transactionStore;

	public SummaryService(TransactionStore transactionStore) {
		this.transactionStore = Objects.requireNonNull(transactionStore);
	}

	public List<CategorySpending> spendingByCategory() {
		Map<ExpenseCategory, BigDecimal> totals = new EnumMap<>(ExpenseCategory.class);
		for (Transaction transaction : transactionStore.findAll()) {
			if (transaction.getType() == TransactionType.EXPENSE) {
				ExpenseCategory category = (ExpenseCategory) transaction.getCategory();
				totals.merge(category, transaction.getAmount(), BigDecimal::add);
			}
		}
		BigDecimal grandTotal = totals.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		if (grandTotal.compareTo(BigDecimal.ZERO) == 0) {
			return List.of();
		}
		List<CategorySpending> result = new ArrayList<>();
		totals.forEach((category, total) -> {
			if (total.compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal percentage = total.multiply(BigDecimal.valueOf(100))
						.divide(grandTotal, 2, RoundingMode.HALF_UP);
				result.add(new CategorySpending(category, total, percentage));
			}
		});
		result.sort(Comparator.comparing(CategorySpending::getTotal).reversed()
				.thenComparing(entry -> entry.getCategory().ordinal()));
		return List.copyOf(result);
	}

	public BalanceSummary balanceSummary() {
		BigDecimal income = BigDecimal.ZERO;
		BigDecimal expenses = BigDecimal.ZERO;
		for (Transaction transaction : transactionStore.findAll()) {
			if (transaction.getType() == TransactionType.INCOME) {
				income = income.add(transaction.getAmount());
			} else {
				expenses = expenses.add(transaction.getAmount());
			}
		}
		return new BalanceSummary(income, expenses, income.subtract(expenses));
	}
}
