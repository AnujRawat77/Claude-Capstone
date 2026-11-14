package org.capstone.expense.console;

import org.capstone.expense.model.*;
import org.capstone.expense.service.TransactionService;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TransactionConsole {
	private final TransactionService transactionService;
	private final InputReader input;
	private final ConsoleFormatter formatter;
	private final PrintStream out;

	public TransactionConsole(TransactionService transactionService, InputReader input,
	                          ConsoleFormatter formatter, PrintStream out) {
		this.transactionService = transactionService;
		this.input = input;
		this.formatter = formatter;
		this.out = out;
	}

	public void addExpenseFlow() {
		BigDecimal amount = input.readAmount("Amount (e.g. 12.50):");
		ExpenseCategory category = (ExpenseCategory) input.readCategory(TransactionType.EXPENSE);
		LocalDate date = input.readDate("Date (yyyy-MM-dd, Enter = today):");
		String description = input.readLine("Description (optional):");
		TransactionResult result = transactionService.addExpense(amount, category, date, description);
		out.println("Expense added: " + formatter.transactionRow(result.getTransaction()));
		printAlert(result);
	}

	public void addIncomeFlow() {
		BigDecimal amount = input.readAmount("Amount (e.g. 12.50):");
		IncomeCategory category = (IncomeCategory) input.readCategory(TransactionType.INCOME);
		LocalDate date = input.readDate("Date (yyyy-MM-dd, Enter = today):");
		String description = input.readLine("Description (optional):");
		TransactionResult result = transactionService.addIncome(amount, category, date, description);
		out.println("Income added: " + formatter.transactionRow(result.getTransaction()));
	}

	public void listFlow() {
		out.println("1. All  2. By category  3. By date range");
		int filter = input.readMenuChoice(1, 3);
		List<Transaction> transactions;
		switch (filter) {
			case 1 -> transactions = transactionService.listAll();
			case 2 -> transactions = transactionService.listByCategory(input.readLine("Category name:"));
			case 3 -> {
				LocalDate start = input.readRequiredDate("Start date (yyyy-MM-dd):");
				LocalDate end = input.readRequiredDate("End date (yyyy-MM-dd):");
				transactions = transactionService.listByDateRange(start, end);
			}
			default -> throw new IllegalStateException("Unsupported filter.");
		}
		if (transactions.isEmpty()) {
			out.println("No transactions found.");
			return;
		}
		out.println("ID | Type | Date | Category | Amount | Description");
		transactions.forEach(transaction -> out.println(formatter.transactionRow(transaction)));
	}

	public void editFlow() {
		int id = input.readInt("Transaction ID:", 1, Integer.MAX_VALUE);
		Transaction current = transactionService.findById(id);
		out.println("Current: " + formatter.transactionRow(current));
		String typeInput = input.readLine("Type (Enter = keep, 1 = Expense, 2 = Income):");
		TransactionType type = switch (typeInput) {
			case "" -> current.getType();
			case "1" -> TransactionType.EXPENSE;
			case "2" -> TransactionType.INCOME;
			default -> throw new org.capstone.expense.exception.ValidationException("Choose 1, 2, or press Enter.");
		};
		Optional<BigDecimal> amountInput = input.readOptionalAmount("Amount (Enter = keep):");
		Category category = input.readCategory(type);
		Optional<LocalDate> dateInput = input.readOptionalDate("Date (yyyy-MM-dd, Enter = keep):");
		Optional<String> descriptionInput = input.readOptionalLine("Description (Enter = keep):");
		TransactionResult result = transactionService.editTransaction(id, type,
				amountInput.orElse(current.getAmount()), category,
				dateInput.orElse(current.getDate()), descriptionInput.orElse(current.getDescription()));
		out.println("Transaction updated: " + formatter.transactionRow(result.getTransaction()));
		printAlert(result);
	}

	public void deleteFlow() {
		int id = input.readInt("Transaction ID:", 1, Integer.MAX_VALUE);
		Transaction transaction = transactionService.findById(id);
		out.println(formatter.transactionRow(transaction));
		if (!input.readConfirmation("Delete this transaction? (y/n):")) {
			out.println("Deletion cancelled.");
			return;
		}
		transactionService.deleteTransaction(id, true);
		out.println("Transaction deleted.");
	}

	private void printAlert(TransactionResult result) {
		result.getBudgetAlert().ifPresent(alert -> out.println(formatter.budgetWarning(alert)));
	}
}
