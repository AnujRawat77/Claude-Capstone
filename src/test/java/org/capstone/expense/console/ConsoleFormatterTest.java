package org.capstone.expense.console;

import org.capstone.expense.model.*;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.testng.Assert.*;

public class ConsoleFormatterTest {
	private final ConsoleFormatter formatter = new ConsoleFormatter();

	@Test
	public void formatsMoneyWithHalfUpAndNoGrouping() {
		assertEquals(formatter.money(new BigDecimal("12.505")), "12.51");
		assertEquals(formatter.money(new BigDecimal("12.5")), "12.50");
		assertEquals(formatter.money(BigDecimal.ZERO), "0.00");
		assertEquals(formatter.money(new BigDecimal("-12.505")), "-12.51");
		assertEquals(formatter.money(new BigDecimal("1234567.891")), "1234567.89");
	}

	@Test
	public void formatsPercentAndTransactionRow() {
		Transaction transaction = Transaction.builder().id(2).type(TransactionType.EXPENSE)
				.amount(new BigDecimal("12.5")).category(ExpenseCategory.FOOD)
				.date(LocalDate.of(2024, 2, 29)).description("Lunch").build();
		assertEquals(formatter.percent(new BigDecimal("33.335")), "33.34");
		assertEquals(formatter.transactionRow(transaction),
				"2 | EXPENSE | 2024-02-29 | FOOD | 12.50 | Lunch");
	}

	@Test
	public void warningIncludesBudgetAndActualTotal() {
		BudgetAlert alert = new BudgetAlert(ExpenseCategory.FOOD, new BigDecimal("500"),
				new BigDecimal("512.3"), 2024, 11);
		assertEquals(formatter.budgetWarning(alert),
				"WARNING: FOOD budget of 500.00 reached for 2024-11. Total spend this month: 512.30.");
	}
}
