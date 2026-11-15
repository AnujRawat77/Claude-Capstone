package org.capstone.expense.service;

import org.capstone.expense.exception.TransactionNotFoundException;
import org.capstone.expense.exception.ValidationException;
import org.capstone.expense.model.*;
import org.capstone.expense.storage.TransactionStore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionService {
	private final TransactionStore transactionStore;
	private final BudgetService budgetService;

	public TransactionService(TransactionStore transactionStore, BudgetService budgetService) {
		this.transactionStore = Objects.requireNonNull(transactionStore);
		this.budgetService = Objects.requireNonNull(budgetService);
	}

	public TransactionResult addExpense(BigDecimal amount, ExpenseCategory category, LocalDate date, String description) {
		validateAmount(amount);
		if (category == null) {
			throw new ValidationException("Category is required.");
		}
		if (date == null) {
			throw new ValidationException("Date is required.");
		}
		Transaction transaction = new Transaction(transactionStore.nextId(), TransactionType.EXPENSE,
				amount, category, date, normalizeDescription(description));
		transactionStore.save(transaction);
		return new TransactionResult(transaction, budgetService.evaluateAlert(transaction));
	}

	public TransactionResult addIncome(BigDecimal amount, IncomeCategory category, LocalDate date, String description) {
		validateAmount(amount);
		if (category == null) {
			throw new ValidationException("Category is required.");
		}
		if (date == null) {
			throw new ValidationException("Date is required.");
		}
		Transaction transaction = new Transaction(transactionStore.nextId(), TransactionType.INCOME,
				amount, category, date, normalizeDescription(description));
		transactionStore.save(transaction);
		return new TransactionResult(transaction, Optional.empty());
	}

	public TransactionResult editTransaction(int id, TransactionType type, BigDecimal amount, Category category,
	                                        LocalDate date, String description) {
		Transaction existing = findById(id);
		validateAmount(amount);
		if (type == null) {
			throw new ValidationException("Transaction type is required.");
		}
		if (category == null || category.transactionType() != type) {
			throw new ValidationException("Category is not valid for " + type + ".");
		}
		if (date == null) {
			throw new ValidationException("Date is required.");
		}
		Transaction updated = existing.toBuilder()
				.type(type)
				.amount(amount)
				.category(category)
				.date(date)
				.description(normalizeDescription(description))
				.build();
		transactionStore.replace(updated);
		return new TransactionResult(updated, budgetService.evaluateAlert(updated));
	}

	public DeleteOutcome deleteTransaction(int id, boolean confirmed) {
		findById(id);
		if (!confirmed) {
			return DeleteOutcome.CANCELLED;
		}
		transactionStore.remove(id);
		return DeleteOutcome.DELETED;
	}

	public Transaction findById(int id) {
		return transactionStore.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
	}

	public List<Transaction> listAll() {
		return transactionStore.findAll();
	}

	public List<Transaction> listByCategory(String categoryName) {
		Category category = Category.fromName(categoryName)
				.orElseThrow(() -> new ValidationException("Unknown category: " + categoryName));
		return transactionStore.findMatching(transaction -> transaction.getCategory().equals(category));
	}

	public List<Transaction> listByDateRange(LocalDate start, LocalDate end) {
		if (start == null || end == null) {
			throw new ValidationException("Start date and end date are required.");
		}
		if (start.isAfter(end)) {
			throw new ValidationException("Start date must not be after end date.");
		}
		return transactionStore.findMatching(transaction ->
				!transaction.getDate().isBefore(start) && !transaction.getDate().isAfter(end));
	}

	private static void validateAmount(BigDecimal amount) {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidationException("Amount must be greater than zero.");
		}
	}

	private static String normalizeDescription(String description) {
		return description == null ? "" : description.trim();
	}
}
