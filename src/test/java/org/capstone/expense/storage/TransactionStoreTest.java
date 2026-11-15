package org.capstone.expense.storage;

import org.capstone.expense.model.*;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.*;

public class TransactionStoreTest {
	private Transaction transaction(int id, String amount) {
		return Transaction.builder().id(id).type(TransactionType.EXPENSE)
				.amount(new BigDecimal(amount)).category(ExpenseCategory.FOOD)
				.date(LocalDate.of(2026, 11, 14)).description("").build();
	}

	@Test
	public void assignsIncreasingIdsAndDoesNotReuseRemovedIds() {
		TransactionStore store = new TransactionStore();
		assertEquals(store.nextId(), 1);
		assertEquals(store.nextId(), 2);
		store.save(transaction(1, "1"));
		store.remove(1);
		assertEquals(store.nextId(), 3);
	}

	@Test
	public void savesAndFindsById() {
		TransactionStore store = new TransactionStore();
		store.save(transaction(1, "1"));
		assertTrue(store.findById(1).isPresent());
		assertFalse(store.findById(2).isPresent());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void rejectsDuplicateId() {
		TransactionStore store = new TransactionStore();
		store.save(transaction(1, "1"));
		store.save(transaction(1, "2"));
	}

	@Test
	public void preservesInsertionOrderWhenReplaced() {
		TransactionStore store = new TransactionStore();
		store.save(transaction(1, "1"));
		store.save(transaction(2, "2"));
		store.replace(transaction(1, "3"));
		assertEquals(store.findAll().stream().map(Transaction::getId).toList(), List.of(1, 2));
		assertEquals(store.findById(1).orElseThrow().getAmount(), new BigDecimal("3"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void rejectsReplacingUnknownId() {
		new TransactionStore().replace(transaction(1, "1"));
	}

	@Test
	public void removeReturnsWhetherRecordExisted() {
		TransactionStore store = new TransactionStore();
		store.save(transaction(1, "1"));
		assertTrue(store.remove(1));
		assertFalse(store.remove(1));
	}

	@Test
	public void returnsImmutableCopiesAndFilteredResults() {
		TransactionStore store = new TransactionStore();
		store.save(transaction(1, "1"));
		assertEquals(store.findMatching(tx -> tx.getAmount().compareTo(BigDecimal.ZERO) > 0).size(), 1);
		assertThrows(UnsupportedOperationException.class, () -> store.findAll().clear());
		assertThrows(UnsupportedOperationException.class, () -> store.findMatching(tx -> true).clear());
	}
}
