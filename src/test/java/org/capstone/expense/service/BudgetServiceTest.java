package org.capstone.expense.service;

import org.capstone.expense.exception.ValidationException;
import org.capstone.expense.model.*;
import org.capstone.expense.storage.BudgetStore;
import org.capstone.expense.storage.TransactionStore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.testng.Assert.*;

public class BudgetServiceTest {
	private BudgetService service;
	private TransactionStore transactions;

	@BeforeMethod
	public void setUp() {
		transactions = new TransactionStore();
		service = new BudgetService(new BudgetStore(), transactions);
	}

	private Transaction expense(int id, String amount, ExpenseCategory category, LocalDate date) {
		return Transaction.builder().id(id).type(TransactionType.EXPENSE).amount(new BigDecimal(amount))
				.category(category).date(date).description("").build();
	}

	@Test
	public void setBudgetAndGetBudget() {
		service.setBudget(ExpenseCategory.FOOD, 2024, 11, new BigDecimal("500"));
		assertEquals(service.getBudget(ExpenseCategory.FOOD, 2024, 11).orElseThrow(), new BigDecimal("500"));
	}

	@Test
	public void setBudgetOverwritesExistingBudget() {
		service.setBudget(ExpenseCategory.FOOD, 2024, 11, new BigDecimal("500"));
		service.setBudget(ExpenseCategory.FOOD, 2024, 11, new BigDecimal("700"));
		assertEquals(service.getBudget(ExpenseCategory.FOOD, 2024, 11).orElseThrow(), new BigDecimal("700"));
	}

	@Test(expectedExceptions = ValidationException.class)
	public void setBudgetRejectsZero() {
		service.setBudget(ExpenseCategory.FOOD, 2024, 11, BigDecimal.ZERO);
	}

	@Test(expectedExceptions = ValidationException.class)
	public void setBudgetRejectsNegative() {
		service.setBudget(ExpenseCategory.FOOD, 2024, 11, new BigDecimal("-1"));
	}

	@Test(expectedExceptions = ValidationException.class)
	public void setBudgetRejectsNullAmount() {
		service.setBudget(ExpenseCategory.FOOD, 2024, 11, null);
	}

	@Test(expectedExceptions = ValidationException.class)
	public void setBudgetRejectsNullCategory() {
		service.setBudget(null, 2024, 11, BigDecimal.ONE);
	}

	@Test(expectedExceptions = ValidationException.class)
	public void setBudgetRejectsYearBelowFourDigits() {
		service.setBudget(ExpenseCategory.FOOD, 999, 11, BigDecimal.ONE);
	}

	@Test(expectedExceptions = ValidationException.class)
	public void setBudgetRejectsYearAboveFourDigits() {
		service.setBudget(ExpenseCategory.FOOD, 10000, 11, BigDecimal.ONE);
	}

	@Test(expectedExceptions = ValidationException.class)
	public void setBudgetRejectsMonthZero() {
		service.setBudget(ExpenseCategory.FOOD, 2024, 0, BigDecimal.ONE);
	}

	@Test(expectedExceptions = ValidationException.class)
	public void setBudgetRejectsMonthThirteen() {
		service.setBudget(ExpenseCategory.FOOD, 2024, 13, BigDecimal.ONE);
	}

	@Test
	public void getBudgetReturnsEmptyWhenMissing() {
		assertEquals(service.getBudget(ExpenseCategory.FOOD, 2024, 11), Optional.empty());
	}

	@Test(expectedExceptions = ValidationException.class)
	public void getBudgetRejectsInvalidMonth() {
		service.getBudget(ExpenseCategory.FOOD, 2024, 13);
	}

	@Test
	public void evaluateAlertReturnsEmptyWithoutBudgetOrForIncome() {
		Transaction expense = expense(1, "1000", ExpenseCategory.FOOD, LocalDate.of(2024, 11, 1));
		transactions.save(expense);
		assertFalse(service.evaluateAlert(expense).isPresent());
		Transaction income = Transaction.builder().id(2).type(TransactionType.INCOME).amount(BigDecimal.ONE)
				.category(IncomeCategory.SALARY).date(expense.getDate()).description("").build();
		assertFalse(service.evaluateAlert(income).isPresent());
	}

	@Test
	public void alertFiresAtExactly100PercentUsingNumericComparison() {
		LocalDate date = LocalDate.of(2024, 11, 1);
		Transaction expense = expense(1, "500.00", ExpenseCategory.FOOD, date);
		transactions.save(expense);
		service.setBudget(ExpenseCategory.FOOD, 2024, 11, new BigDecimal("500"));
		BudgetAlert alert = service.evaluateAlert(expense).orElseThrow();
		assertEquals(alert.getCategory(), ExpenseCategory.FOOD);
		assertEquals(alert.getBudget(), new BigDecimal("500"));
		assertEquals(alert.getMonthlySpend(), new BigDecimal("500.00"));
		assertEquals(alert.getYear(), 2024);
		assertEquals(alert.getMonth(), 11);
	}

	@Test
	public void alertRequiresMatchingMonthAndCategoryAndSpendAtLeastBudget() {
		LocalDate date = LocalDate.of(2024, 11, 10);
		Transaction food = expense(1, "40", ExpenseCategory.FOOD, date);
		Transaction transport = expense(2, "100", ExpenseCategory.TRANSPORT, date);
		Transaction nextMonth = expense(3, "100", ExpenseCategory.FOOD, date.plusMonths(1));
		transactions.save(food);
		transactions.save(transport);
		transactions.save(nextMonth);
		service.setBudget(ExpenseCategory.FOOD, 2024, 11, new BigDecimal("100"));
		assertFalse(service.evaluateAlert(food).isPresent());
		Transaction reaches = expense(4, "60", ExpenseCategory.FOOD, date);
		transactions.save(reaches);
		assertEquals(service.evaluateAlert(reaches).orElseThrow().getMonthlySpend(), new BigDecimal("100"));
		assertFalse(service.evaluateAlert(nextMonth).isPresent());
	}
}
