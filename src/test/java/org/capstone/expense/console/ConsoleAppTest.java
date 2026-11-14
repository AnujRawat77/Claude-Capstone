package org.capstone.expense.console;

import org.capstone.expense.service.BudgetService;
import org.capstone.expense.service.SummaryService;
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

public class ConsoleAppTest {
	private String runSession(String script) {
		TransactionStore transactions = new TransactionStore();
		BudgetStore budgets = new BudgetStore();
		BudgetService budgetService = new BudgetService(budgets, transactions);
		TransactionService transactionService = new TransactionService(transactions, budgetService);
		SummaryService summaryService = new SummaryService(transactions);
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bytes);
		InputReader input = new InputReader(new Scanner(script), out,
				Clock.fixed(Instant.parse("2024-11-14T12:00:00Z"), ZoneOffset.UTC));
		ConsoleFormatter formatter = new ConsoleFormatter();
		TransactionConsole transactionConsole = new TransactionConsole(transactionService, input, formatter, out);
		ReportConsole reportConsole = new ReportConsole(summaryService, budgetService, input, formatter, out);
		new ConsoleApp(transactionConsole, reportConsole, input, out).run();
		return bytes.toString();
	}

	@Test
	public void addListDeleteEndToEnd() {
		String output = runSession("1\n10\n1\n2024-11-14\nlunch\n3\n1\n5\n1\ny\n3\n1\n9\n");
		assertTrue(output.contains("Expense added:"));
		assertTrue(output.contains("Transaction deleted."));
		assertTrue(output.contains("No transactions found."));
	}

	@Test
	public void settingBudgetThenAddingExpensePrintsAlert() {
		String output = runSession("8\n1\n2024\n11\n500\n1\n500.00\n1\n2024-11-14\nrent\n9\n");
		assertTrue(output.contains("WARNING: FOOD budget of 500.00 reached for 2024-11"));
	}

	@Test
	public void editingExpenseIntoIncomeChangesBalance() {
		String output = runSession("1\n20\n1\n2024-11-14\n\n4\n1\n2\n\n1\n\n\n7\n9\n");
		assertTrue(output.contains("Transaction updated:"));
		assertTrue(output.contains("Total income: 20.00"));
		assertTrue(output.contains("Total expenses: 0.00"));
	}

	@Test
	public void invalidMenuInputShowsErrorAndReturnsToMenu() {
		String output = runSession("abc\n9\n");
		assertTrue(output.contains("Error: Invalid choice 'abc'"));
		assertEquals(count(output, "Personal Expense Tracker"), 2);
	}

	@Test
	public void invalidExpenseAmountShowsErrorAndStoresNothing() {
		String output = runSession("1\n0\n1\n2024-11-14\nbad amount\n3\n1\n9\n");
		assertTrue(output.contains("Error: Amount must be greater than zero."));
		assertTrue(output.contains("No transactions found."));
	}

	@Test
	public void eofDuringFlowExitsWithoutReenteringMenu() {
		String output = runSession("1\n");
		assertTrue(output.contains("Input closed. Exiting."));
		assertEquals(count(output, "Personal Expense Tracker"), 1);
	}

	private static int count(String text, String value) {
		return (text.length() - text.replace(value, "").length()) / value.length();
	}
}
