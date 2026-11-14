package org.capstone.expense.console;

import org.capstone.expense.model.BudgetAlert;
import org.capstone.expense.model.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConsoleFormatter {
	public String money(BigDecimal amount) {
		return amount.setScale(2, RoundingMode.HALF_UP).toPlainString();
	}

	public String percent(BigDecimal percentage) {
		return percentage.setScale(2, RoundingMode.HALF_UP).toPlainString();
	}

	public String transactionRow(Transaction transaction) {
		return transaction.getId() + " | " + transaction.getType() + " | " + transaction.getDate()
				+ " | " + transaction.getCategory().name() + " | " + money(transaction.getAmount())
				+ " | " + transaction.getDescription();
	}

	public String budgetWarning(BudgetAlert alert) {
		return "WARNING: " + alert.getCategory().name() + " budget of " + money(alert.getBudget())
				+ " reached for " + alert.getYear() + "-"
				+ (alert.getMonth() < 10 ? "0" : "") + alert.getMonth()
				+ ". Total spend this month: " + money(alert.getMonthlySpend()) + ".";
	}
}
