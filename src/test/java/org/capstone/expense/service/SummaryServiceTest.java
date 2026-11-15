package org.capstone.expense.service;

import org.capstone.expense.model.*;
import org.capstone.expense.storage.BudgetStore;
import org.capstone.expense.storage.TransactionStore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.*;

public class SummaryServiceTest {
	private TransactionStore store;
	private TransactionService transactions;
	private SummaryService summaries;
	private final LocalDate date = LocalDate.of(2024, 11, 15);

	@BeforeMethod
	public void setUp() {
		store = new TransactionStore();
		transactions = new TransactionService(store, new BudgetService(new BudgetStore(), store));
		summaries = new SummaryService(store);
	}

	@Test
	public void spendingSummarySortsDescendingAndOmitsIncome() {
		transactions.addExpense(new BigDecimal("10"), ExpenseCategory.FOOD, date, "");
		transactions.addExpense(new BigDecimal("20"), ExpenseCategory.TRANSPORT, date, "");
		transactions.addIncome(new BigDecimal("1000"), IncomeCategory.SALARY, date, "");
		List<CategorySpending> result = summaries.spendingByCategory();
		assertEquals(result.get(0).getCategory(), ExpenseCategory.TRANSPORT);
		assertEquals(result.get(0).getTotal(), new BigDecimal("20"));
		assertEquals(result.get(1).getCategory(), ExpenseCategory.FOOD);
		assertEquals(result.get(1).getPercentage(), new BigDecimal("33.33"));
	}

	@Test
	public void percentageUsesHalfUpAndSevenRowTolerance() {
		for (ExpenseCategory category : ExpenseCategory.values()) {
			transactions.addExpense(new BigDecimal(category == ExpenseCategory.FOOD ? "2858" : "2857"),
					category, date, "");
		}
		List<CategorySpending> result = summaries.spendingByCategory();
		assertEquals(result.size(), 7);
		assertEquals(result.stream().map(CategorySpending::getPercentage)
				.reduce(BigDecimal.ZERO, BigDecimal::add), new BigDecimal("100.03"));
		assertEquals(result.get(0).getPercentage(), new BigDecimal("14.29"));
	}

	@Test
	public void tiesUseEnumOrderAndEmptyExpensesReturnNoRows() {
		assertTrue(summaries.spendingByCategory().isEmpty());
		transactions.addExpense(BigDecimal.ONE, ExpenseCategory.TRANSPORT, date, "");
		transactions.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, date, "");
		assertEquals(summaries.spendingByCategory().get(0).getCategory(), ExpenseCategory.FOOD);
	}

	@Test
	public void balanceSummaryCalculatesExactTotalsAndNegativeNet() {
		transactions.addIncome(new BigDecimal("0.3"), IncomeCategory.SALARY, date, "");
		transactions.addExpense(new BigDecimal("0.1"), ExpenseCategory.FOOD, date, "");
		transactions.addExpense(new BigDecimal("0.3"), ExpenseCategory.FOOD, date, "");
		BalanceSummary result = summaries.balanceSummary();
		assertEquals(result.getTotalIncome(), new BigDecimal("0.3"));
		assertEquals(result.getTotalExpenses(), new BigDecimal("0.4"));
		assertEquals(result.getNetBalance(), new BigDecimal("-0.1"));
	}

	@Test
	public void balanceSummaryEmptyIsZero() {
		BalanceSummary result = summaries.balanceSummary();
		assertEquals(result.getTotalIncome(), BigDecimal.ZERO);
		assertEquals(result.getTotalExpenses(), BigDecimal.ZERO);
		assertEquals(result.getNetBalance(), BigDecimal.ZERO);
	}
}
