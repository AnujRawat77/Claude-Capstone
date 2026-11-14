package org.capstone.expense.storage;

import org.capstone.expense.model.BudgetKey;
import org.capstone.expense.model.ExpenseCategory;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

public class BudgetStoreTest {
	@Test
	public void savesOverwritesAndFindsBudgetByValueKey() {
		BudgetStore store = new BudgetStore();
		BudgetKey first = new BudgetKey(ExpenseCategory.FOOD, 2024, 11);
		BudgetKey equivalent = new BudgetKey(ExpenseCategory.FOOD, 2024, 11);
		store.put(first, new BigDecimal("500"));
		assertEquals(store.find(equivalent).orElseThrow(), new BigDecimal("500"));
		store.put(equivalent, new BigDecimal("600"));
		assertEquals(store.find(first).orElseThrow(), new BigDecimal("600"));
		assertFalse(store.find(new BudgetKey(ExpenseCategory.FOOD, 2024, 12)).isPresent());
	}
}
