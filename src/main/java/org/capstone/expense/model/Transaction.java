package org.capstone.expense.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Value
public class Transaction {
	int id;
	TransactionType type;
	BigDecimal amount;
	Category category;
	LocalDate date;
	String description;

	@Builder(toBuilder = true)
	public Transaction(int id, TransactionType type, BigDecimal amount, Category category,
	                  LocalDate date, String description) {
		if (id < 1) {
			throw new IllegalArgumentException("Transaction ID must be positive.");
		}
		this.type = Objects.requireNonNull(type, "Transaction type is required.");
		this.amount = Objects.requireNonNull(amount, "Amount is required.");
		this.category = Objects.requireNonNull(category, "Category is required.");
		this.date = Objects.requireNonNull(date, "Date is required.");
		this.description = Objects.requireNonNull(description, "Description is required.").trim();
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero.");
		}
		if (category.transactionType() != type) {
			throw new IllegalArgumentException("Category does not match transaction type.");
		}
		this.id = id;
	}
}
