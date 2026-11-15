package org.capstone.expense.storage;

import org.capstone.expense.model.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TransactionStore {
	private final LinkedHashMap<Integer, Transaction> transactions = new LinkedHashMap<>();
	private int nextId = 1;

	public int nextId() {
		int allocated = nextId;
		nextId = Math.incrementExact(nextId);
		return allocated;
	}

	public void save(Transaction transaction) {
		if (transactions.containsKey(transaction.getId())) {
			throw new IllegalArgumentException("Transaction ID already exists: " + transaction.getId());
		}
		transactions.put(transaction.getId(), transaction);
	}

	public void replace(Transaction transaction) {
		if (!transactions.containsKey(transaction.getId())) {
			throw new IllegalArgumentException("Cannot replace missing transaction ID: " + transaction.getId());
		}
		transactions.put(transaction.getId(), transaction);
	}

	public Optional<Transaction> findById(int id) {
		return Optional.ofNullable(transactions.get(id));
	}

	public boolean remove(int id) {
		return transactions.remove(id) != null;
	}

	public List<Transaction> findAll() {
		return Collections.unmodifiableList(new ArrayList<>(transactions.values()));
	}

	public List<Transaction> findMatching(Predicate<Transaction> predicate) {
		return Collections.unmodifiableList(
				transactions.values().stream().filter(predicate).toList());
	}
}
