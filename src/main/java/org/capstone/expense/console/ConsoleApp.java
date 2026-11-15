package org.capstone.expense.console;

import org.capstone.expense.exception.TransactionNotFoundException;
import org.capstone.expense.exception.ValidationException;

import java.io.PrintStream;
import java.util.NoSuchElementException;

public class ConsoleApp {
	private final TransactionConsole transactionConsole;
	private final ReportConsole reportConsole;
	private final InputReader input;
	private final PrintStream out;

	public ConsoleApp(TransactionConsole transactionConsole, ReportConsole reportConsole,
	                  InputReader input, PrintStream out) {
		this.transactionConsole = transactionConsole;
		this.reportConsole = reportConsole;
		this.input = input;
		this.out = out;
	}

	public void run() {
		while (true) {
			out.println("========== Personal Expense Tracker ==========");
			out.println("1. Add Expense");
			out.println("2. Add Income");
			out.println("3. List Transactions");
			out.println("4. Edit Transaction");
			out.println("5. Delete Transaction");
			out.println("6. Spending Summary");
			out.println("7. Balance Summary");
			out.println("8. Set Monthly Budget");
			out.println("9. Exit");
			try {
				int choice = input.readMenuChoice(1, 9);
				switch (choice) {
					case 1 -> transactionConsole.addExpenseFlow();
					case 2 -> transactionConsole.addIncomeFlow();
					case 3 -> transactionConsole.listFlow();
					case 4 -> transactionConsole.editFlow();
					case 5 -> transactionConsole.deleteFlow();
					case 6 -> reportConsole.spendingSummaryFlow();
					case 7 -> reportConsole.balanceSummaryFlow();
					case 8 -> reportConsole.setBudgetFlow();
					case 9 -> {
						return;
					}
					default -> throw new IllegalStateException("Unsupported menu choice.");
				}
			} catch (NoSuchElementException exception) {
				out.println("Input closed. Exiting.");
				return;
			} catch (ValidationException | TransactionNotFoundException exception) {
				out.println("Error: " + exception.getMessage());
			} catch (RuntimeException exception) {
				out.println("Unexpected error. Returning to the menu.");
			}
		}
	}
}
