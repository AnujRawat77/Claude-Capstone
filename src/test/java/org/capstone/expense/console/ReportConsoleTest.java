package org.capstone.expense.console;

import org.capstone.expense.model.ExpenseCategory;
import org.capstone.expense.service.BudgetService;
import org.capstone.expense.service.SummaryService;
import org.capstone.expense.service.TransactionService;
import org.capstone.expense.storage.BudgetStore;
import org.capstone.expense.storage.TransactionStore;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Scanner;

import static org.testng.Assert.*;

public class ReportConsoleTest {
	private static final Clock CLOCK =
			Clock.fixed(Instant.parse("2024-11-14T12:00:00Z"), ZoneOffset.UTC);

	private record Fixture(ReportConsole console, TransactionService transactions,
	                       BudgetService budgets, ByteArrayOutputStream output) {
	}

	private Fixture fixture(String input) {
		TransactionStore store = new TransactionStore();
		BudgetService budgets = new BudgetService(new BudgetStore(), store);
		TransactionService transactions = new TransactionService(store, budgets);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(output);
		InputReader reader = new InputReader(new Scanner(input), out, CLOCK);
		ReportConsole console = new ReportConsole(new SummaryService(store), budgets, reader,
				new ConsoleFormatter(), out);
		return new Fixture(console, transactions, budgets, output);
	}

	@Test
	public void spendingAndBalanceSummariesAreDisplayed() {
		Fixture fixture = fixture("");
		fixture.transactions().addExpense(new BigDecimal("25.50"), ExpenseCategory.FOOD,
				LocalDate.of(2024, 11, 1), "");
		fixture.console().spendingSummaryFlow();
		fixture.console().balanceSummaryFlow();
		String output = fixture.output().toString();
		assertTrue(output.contains("FOOD | 25.50 | 100.00%"));
		assertTrue(output.contains("Total expenses: 25.50"));
		assertTrue(output.contains("Net balance: -25.50"));
	}

	@Test
	public void setBudgetOverwritesAndDoesNotTriggerExistingSpendAlert() {
		Fixture fixture = fixture("1\n2024\n11\n10\n1\n2024\n11\n20\n");
		fixture.transactions().addExpense(new BigDecimal("50"), ExpenseCategory.FOOD,
				LocalDate.of(2024, 11, 1), "");
		fixture.console().setBudgetFlow();
		fixture.console().setBudgetFlow();
		assertEquals(fixture.budgets().getBudget(ExpenseCategory.FOOD, 2024, 11).orElseThrow(),
				new BigDecimal("20"));
		assertTrue(fixture.output().toString().contains("Budget saved (overwrites any previous value)."));
		assertFalse(fixture.output().toString().contains("WARNING"));
	}
}
