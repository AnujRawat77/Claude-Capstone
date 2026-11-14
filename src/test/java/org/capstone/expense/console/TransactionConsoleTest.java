package org.capstone.expense.console;

import org.capstone.expense.model.*;
import org.capstone.expense.service.BudgetService;
import org.capstone.expense.service.TransactionService;
import org.capstone.expense.storage.BudgetStore;
import org.capstone.expense.storage.TransactionStore;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Scanner;

import static org.testng.Assert.*;

public class TransactionConsoleTest {
	private static final Clock CLOCK =
			Clock.fixed(Instant.parse("2024-11-14T12:00:00Z"), ZoneOffset.UTC);

	private record Fixture(TransactionConsole console, TransactionService service, ByteArrayOutputStream output) {
	}

	private Fixture fixture(String input) {
		TransactionStore store = new TransactionStore();
		BudgetService budgets = new BudgetService(new BudgetStore(), store);
		TransactionService service = new TransactionService(store, budgets);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(output);
		InputReader reader = new InputReader(new Scanner(input), out, CLOCK);
		return new Fixture(new TransactionConsole(service, reader, new ConsoleFormatter(), out), service, output);
	}

	@Test
	public void addExpenseDisplaysSuccessAndUsesCurrentDateWhenBlank() {
		Fixture fixture = fixture("12.50\n1\n\nLunch\n");
		fixture.console().addExpenseFlow();
		assertTrue(fixture.output().toString().contains("2024-11-14 | FOOD | 12.50 | Lunch"));
		assertEquals(fixture.service().listAll().get(0).getDate().toString(), "2024-11-14");
	}

	@Test
	public void addIncomeDoesNotDisplayBudgetWarning() {
		Fixture fixture = fixture("100\n1\n2024-11-14\nSalary\n");
		fixture.console().addIncomeFlow();
		assertTrue(fixture.output().toString().contains("Income added:"));
		assertFalse(fixture.output().toString().contains("WARNING"));
	}

	@Test
	public void listFlowDisplaysRowsAndCategoryFilter() {
		Fixture fixture = fixture("1\nFOOD\n");
		fixture.service().addExpense(java.math.BigDecimal.ONE, ExpenseCategory.FOOD,
				java.time.LocalDate.of(2024, 11, 14), "meal");
		fixture.console().listFlow();
		assertTrue(fixture.output().toString().contains("FOOD | 1.00 | meal"));
	}

	@Test
	public void deleteFlowHonorsNoConfirmation() {
		Fixture fixture = fixture("1\nn\n");
		fixture.service().addExpense(java.math.BigDecimal.ONE, ExpenseCategory.FOOD,
				java.time.LocalDate.of(2024, 11, 14), "");
		fixture.console().deleteFlow();
		assertEquals(fixture.service().listAll().size(), 1);
		assertTrue(fixture.output().toString().contains("Deletion cancelled."));
	}
}
