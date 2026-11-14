package org.capstone.expense.model;

import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

/**
 * Unit tests for Category interface and implementing enums.
 */
public class CategoryTest {

	// ===== ExpenseCategory tests =====

	@Test
	public void testExpenseCategoryValues() {
		ExpenseCategory[] values = ExpenseCategory.values();
		assertEquals(values.length, 7, "ExpenseCategory should have 7 values");
		assertEquals(values[0], ExpenseCategory.FOOD);
		assertEquals(values[1], ExpenseCategory.TRANSPORT);
		assertEquals(values[2], ExpenseCategory.SHOPPING);
		assertEquals(values[3], ExpenseCategory.BILLS);
		assertEquals(values[4], ExpenseCategory.HEALTH);
		assertEquals(values[5], ExpenseCategory.ENTERTAINMENT);
		assertEquals(values[6], ExpenseCategory.OTHER);
	}

	@Test
	public void testExpenseCategoryTransactionType() {
		for (ExpenseCategory ec : ExpenseCategory.values()) {
			assertEquals(ec.transactionType(), TransactionType.EXPENSE,
					ec.name() + " should return EXPENSE");
		}
	}

	// ===== IncomeCategory tests =====

	@Test
	public void testIncomeCategoryValues() {
		IncomeCategory[] values = IncomeCategory.values();
		assertEquals(values.length, 4, "IncomeCategory should have 4 values");
		assertEquals(values[0], IncomeCategory.SALARY);
		assertEquals(values[1], IncomeCategory.BUSINESS);
		assertEquals(values[2], IncomeCategory.INVESTMENT);
		assertEquals(values[3], IncomeCategory.OTHER_INCOME);
	}

	@Test
	public void testIncomeCategoryTransactionType() {
		for (IncomeCategory ic : IncomeCategory.values()) {
			assertEquals(ic.transactionType(), TransactionType.INCOME,
					ic.name() + " should return INCOME");
		}
	}

	// ===== fromName() tests: case-insensitive lookup =====

	@Test
	public void testFromNameLowercase() {
		Optional<Category> result = Category.fromName("food");
		assertTrue(result.isPresent(), "fromName(\"food\") should find FOOD");
		assertEquals(result.get(), ExpenseCategory.FOOD);
	}

	@Test
	public void testFromNameUppercase() {
		Optional<Category> result = Category.fromName("FOOD");
		assertTrue(result.isPresent(), "fromName(\"FOOD\") should find FOOD");
		assertEquals(result.get(), ExpenseCategory.FOOD);
	}

	@Test
	public void testFromNameMixedCase() {
		Optional<Category> result = Category.fromName("Food");
		assertTrue(result.isPresent(), "fromName(\"Food\") should find FOOD");
		assertEquals(result.get(), ExpenseCategory.FOOD);
	}

	@Test
	public void testFromNameWithLeadingWhitespace() {
		Optional<Category> result = Category.fromName("  food");
		assertTrue(result.isPresent(), "fromName(\"  food\") should find FOOD after trim");
		assertEquals(result.get(), ExpenseCategory.FOOD);
	}

	@Test
	public void testFromNameWithTrailingWhitespace() {
		Optional<Category> result = Category.fromName("food  ");
		assertTrue(result.isPresent(), "fromName(\"food  \") should find FOOD after trim");
		assertEquals(result.get(), ExpenseCategory.FOOD);
	}

	@Test
	public void testFromNameWithBothSidesWhitespace() {
		Optional<Category> result = Category.fromName("  Food  ");
		assertTrue(result.isPresent(), "fromName(\"  Food  \") should find FOOD after trim");
		assertEquals(result.get(), ExpenseCategory.FOOD);
	}

	@Test
	public void testFromNameNotFound() {
		Optional<Category> result = Category.fromName("CAR");
		assertFalse(result.isPresent(), "fromName(\"CAR\") should return empty");
	}

	@Test
	public void testFromNameNull() {
		Optional<Category> result = Category.fromName(null);
		assertFalse(result.isPresent(), "fromName(null) should return empty");
	}

	@Test
	public void testFromNameEmptyString() {
		Optional<Category> result = Category.fromName("");
		assertFalse(result.isPresent(), "fromName(\"\") should return empty");
	}

	@Test
	public void testFromNameWhitespaceOnly() {
		Optional<Category> result = Category.fromName("   ");
		assertFalse(result.isPresent(), "fromName(\"   \") should return empty after trim");
	}

	@Test
	public void testFromNameAllExpenseCategories() {
		for (ExpenseCategory ec : ExpenseCategory.values()) {
			Optional<Category> result = Category.fromName(ec.name());
			assertTrue(result.isPresent(), "fromName(\"" + ec.name() + "\") should find " + ec);
			assertEquals(result.get(), ec);
		}
	}

	@Test
	public void testFromNameAllIncomeCategoriesCaseInsensitive() {
		for (IncomeCategory ic : IncomeCategory.values()) {
			String lowerName = ic.name().toLowerCase();
			Optional<Category> result = Category.fromName(lowerName);
			assertTrue(result.isPresent(), "fromName(\"" + lowerName + "\") should find " + ic);
			assertEquals(result.get(), ic);
		}
	}

	// ===== Enum name uniqueness =====

	@Test
	public void testNoNameCollision() {
		java.util.Set<String> names = new java.util.HashSet<>();
		for (ExpenseCategory ec : ExpenseCategory.values()) {
			assertTrue(names.add(ec.name()), ec.name() + " already exists in another enum");
		}
		for (IncomeCategory ic : IncomeCategory.values()) {
			assertTrue(names.add(ic.name()), ic.name() + " already exists in another enum");
		}
	}

	// ===== TransactionType tests =====

	@Test
	public void testTransactionTypeValues() {
		TransactionType[] values = TransactionType.values();
		assertEquals(values.length, 2);
		assertEquals(values[0], TransactionType.EXPENSE);
		assertEquals(values[1], TransactionType.INCOME);
	}

	// ===== DeleteOutcome tests =====

	@Test
	public void testDeleteOutcomeValues() {
		DeleteOutcome[] values = DeleteOutcome.values();
		assertEquals(values.length, 2);
		assertEquals(values[0], DeleteOutcome.DELETED);
		assertEquals(values[1], DeleteOutcome.CANCELLED);
	}
}
