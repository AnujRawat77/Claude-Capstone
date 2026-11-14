package org.capstone.expense;

import org.capstone.expense.console.ConsoleApp;
import org.capstone.expense.console.ConsoleFormatter;
import org.capstone.expense.console.InputReader;
import org.capstone.expense.console.ReportConsole;
import org.capstone.expense.console.TransactionConsole;
import org.capstone.expense.service.BudgetService;
import org.capstone.expense.service.SummaryService;
import org.capstone.expense.service.TransactionService;
import org.capstone.expense.storage.BudgetStore;
import org.capstone.expense.storage.TransactionStore;

import java.time.Clock;
import java.util.Scanner;

public final class Main {
	private Main() {
	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			var out = System.out;
			TransactionStore transactionStore = new TransactionStore();
			BudgetStore budgetStore = new BudgetStore();
			BudgetService budgetService = new BudgetService(budgetStore, transactionStore);
			TransactionService transactionService = new TransactionService(transactionStore, budgetService);
			SummaryService summaryService = new SummaryService(transactionStore);
			InputReader input = new InputReader(scanner, out, Clock.systemDefaultZone());
			ConsoleFormatter formatter = new ConsoleFormatter();
			TransactionConsole transactionConsole = new TransactionConsole(transactionService, input, formatter, out);
			ReportConsole reportConsole = new ReportConsole(summaryService, budgetService, input, formatter, out);
			new ConsoleApp(transactionConsole, reportConsole, input, out).run();
		}
	}
}
