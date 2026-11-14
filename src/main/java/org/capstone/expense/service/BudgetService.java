package org.capstone.expense.service;

import org.capstone.expense.exception.ValidationException;
import org.capstone.expense.model.BudgetAlert;
import org.capstone.expense.model.BudgetKey;
import org.capstone.expense.model.ExpenseCategory;
import org.capstone.expense.model.Transaction;
import org.capstone.expense.model.TransactionType;
import org.capstone.expense.storage.BudgetStore;
import org.capstone.expense.storage.TransactionStore;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class BudgetService {
	private final BudgetStore budgetStore;
	private final TransactionStore transactionStore;

	public BudgetService(BudgetStore budgetStore, TransactionStore transactionStore) {
		this.budgetStore = Objects.requireNonNull(budgetStore);
		this.transactionStore = Objects.requireNonNull(transactionStore);
	}

	public void setBudget(ExpenseCategory category, int year, int month, BigDecimal amount) {
		validateKey(category, year, month);
		validateAmount(amount);
		budgetStore.put(new BudgetKey(category, year, month), amount);
	}

	public Optional<BigDecimal> getBudget(ExpenseCategory category, int year, int month) {
		validateKey(category, year, month);
		return budgetStore.find(new BudgetKey(category, year, month));
	}

	public Optional<BudgetAlert> evaluateAlert(Transaction transaction) {
		Objects.requireNonNull(transaction, "Transaction is required.");
		if (transaction.getType() != TransactionType.EXPENSE) {
			return Optional.empty();
		}
		ExpenseCategory category = (ExpenseCategory) transaction.getCategory();
		int year = transaction.getDate().getYear();
		int month = transaction.getDate().getMonthValue();
		Optional<BigDecimal> budget = budgetStore.find(new BudgetKey(category, year, month));
		if (budget.isEmpty()) {
			return Optional.empty();
		}
		BigDecimal spend = transactionStore.findMatching(candidate ->
				candidate.getType() == TransactionType.EXPENSE
						&& candidate.getCategory() == category
						&& candidate.getDate().getYear() == year
						&& candidate.getDate().getMonthValue() == month)
				.stream()
				.map(Transaction::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		if (spend.compareTo(budget.get()) < 0) {
			return Optional.empty();
		}
		return Optional.of(new BudgetAlert(category, budget.get(), spend, year, month));
	}

	private static void validateKey(ExpenseCategory category, int year, int month) {
		if (category == null) {
			throw new ValidationException("Category is required.");
		}
		if (year < 1000 || year > 9999) {
			throw new ValidationException("Year must be between 1000 and 9999.");
		}
		if (month < 1 || month > 12) {
			throw new ValidationException("Month must be between 1 and 12.");
		}
	}

	private static void validateAmount(BigDecimal amount) {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidationException("Amount must be greater than zero.");
		}
	}
}
