package org.capstone.expense.model;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.testng.Assert.*;

public class TransactionTest {
	private Transaction.TransactionBuilder builder() {
		return Transaction.builder().id(1).type(TransactionType.EXPENSE)
				.amount(new BigDecimal("12.505")).category(ExpenseCategory.FOOD)
				.date(LocalDate.of(2026, 11, 14)).description("  lunch  ");
	}

	@Test
	public void buildsValidTransactionAndPreservesAmountScale() {
		Transaction transaction = builder().build();
		assertEquals(transaction.getId(), 1);
		assertEquals(transaction.getAmount(), new BigDecimal("12.505"));
		assertEquals(transaction.getDescription(), "lunch");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void rejectsZeroAmount() {
		builder().amount(BigDecimal.ZERO).build();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void rejectsNegativeAmount() {
		builder().amount(new BigDecimal("-1")).build();
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void rejectsNullType() {
		builder().type(null).build();
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void rejectsNullCategory() {
		builder().category(null).build();
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void rejectsNullDate() {
		builder().date(null).build();
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void rejectsNullDescription() {
		builder().description(null).build();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void rejectsCategoryTypeMismatch() {
		builder().type(TransactionType.INCOME).build();
	}

	@Test
	public void toBuilderCreatesUpdatedImmutableValue() {
		Transaction original = builder().build();
		Transaction updated = original.toBuilder().amount(new BigDecimal("20.00")).build();
		assertNotSame(updated, original);
		assertEquals(updated.getId(), original.getId());
		assertEquals(updated.getAmount(), new BigDecimal("20.00"));
		assertEquals(original.getAmount(), new BigDecimal("12.505"));
	}
}
