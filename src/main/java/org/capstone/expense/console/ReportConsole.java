package org.capstone.expense.console;

import org.capstone.expense.model.BalanceSummary;
import org.capstone.expense.model.CategorySpending;
import org.capstone.expense.model.ExpenseCategory;
import org.capstone.expense.model.TransactionType;
import org.capstone.expense.service.BudgetService;
import org.capstone.expense.service.SummaryService;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ReportConsole {
	private final SummaryService summaryService;
	private final BudgetService budgetService;
	private final InputReader input;
	private final ConsoleFormatter formatter;
	private final PrintStream out;

	public ReportConsole(SummaryService summaryService, BudgetService budgetService, InputReader input,
	                     ConsoleFormatter formatter, PrintStream out) {
		this.summaryService = summaryService;
		this.budgetService = budgetService;
		this.input = input;
		this.formatter = formatter;
		this.out = out;
	}

	public void spendingSummaryFlow() {
		List<CategorySpending> rows = summaryService.spendingByCategory();
		if (rows.isEmpty()) {
			out.println("No expense transactions recorded.");
			return;
		}
		out.println("Category | Total | Percentage");
		for (CategorySpending row : rows) {
			out.println(row.getCategory().name() + " | " + formatter.money(row.getTotal())
					+ " | " + formatter.percent(row.getPercentage()) + "%");
		}
	}

	public void balanceSummaryFlow() {
		BalanceSummary summary = summaryService.balanceSummary();
		out.println("Total income: " + formatter.money(summary.getTotalIncome()));
		out.println("Total expenses: " + formatter.money(summary.getTotalExpenses()));
		out.println("Net balance: " + formatter.money(summary.getNetBalance()));
	}

	public void setBudgetFlow() {
		ExpenseCategory category = (ExpenseCategory) input.readCategory(TransactionType.EXPENSE);
		int year = input.readInt("Year (1000-9999):", 1000, 9999);
		int month = input.readInt("Month (1-12):", 1, 12);
		BigDecimal amount = input.readAmount("Budget amount:");
		Optional<BigDecimal> previous = budgetService.getBudget(category, year, month);
		budgetService.setBudget(category, year, month, amount);
		if (previous.isPresent()) {
			out.println("Budget saved (overwrites any previous value).");
		} else {
			out.println("Budget saved.");
		}
	}
}
