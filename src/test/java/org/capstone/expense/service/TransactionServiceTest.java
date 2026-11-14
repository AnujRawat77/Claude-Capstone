package org.capstone.expense.service;

import org.capstone.expense.exception.TransactionNotFoundException;
import org.capstone.expense.exception.ValidationException;
import org.capstone.expense.model.*;
import org.capstone.expense.storage.BudgetStore;
import org.capstone.expense.storage.TransactionStore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.*;

public class TransactionServiceTest {
	private TransactionStore store;
	private BudgetService budgets;
	private TransactionService service;
	private final LocalDate date = LocalDate.of(2024, 11, 15);

	@BeforeMethod
	public void setUp() {
		store = new TransactionStore();
		budgets = new BudgetService(new BudgetStore(), store);
		service = new TransactionService(store, budgets);
	}

	@Test
	public void addsExpenseAndIncomeWithSharedIds() {
		Transaction expense = service.addExpense(new BigDecimal("12.505"), ExpenseCategory.FOOD,
				date, " meal ").getTransaction();
		Transaction income = service.addIncome(new BigDecimal("100"), IncomeCategory.SALARY,
				date, null).getTransaction();
		assertEquals(expense.getId(), 1);
		assertEquals(expense.getDescription(), "meal");
		assertEquals(expense.getAmount(), new BigDecimal("12.505"));
		assertEquals(income.getId(), 2);
		assertEquals(income.getDescription(), "");
		assertEquals(income.getType(), TransactionType.INCOME);
	}

	@Test(expectedExceptions = ValidationException.class)
	public void addExpenseRejectsZeroAmount() {
		service.addExpense(BigDecimal.ZERO, ExpenseCategory.FOOD, date, "");
	}

	@Test(expectedExceptions = ValidationException.class)
	public void addExpenseRejectsNegativeAmount() {
		service.addExpense(new BigDecimal("-1"), ExpenseCategory.FOOD, date, "");
	}

	@Test(expectedExceptions = ValidationException.class)
	public void addIncomeRejectsZeroAmount() {
		service.addIncome(BigDecimal.ZERO, IncomeCategory.SALARY, date, "");
	}

	@Test(expectedExceptions = ValidationException.class)
	public void addIncomeRejectsNegativeAmount() {
		service.addIncome(new BigDecimal("-1"), IncomeCategory.SALARY, date, "");
	}

	@Test
	public void rejectedAddDoesNotStoreOrConsumeId() {
		assertThrows(ValidationException.class,
				() -> service.addExpense(BigDecimal.ONE, null, date, ""));
		assertTrue(service.listAll().isEmpty());
		assertEquals(service.addIncome(BigDecimal.ONE, IncomeCategory.SALARY, date, "").getTransaction().getId(), 1);
	}

	@Test
	public void addValidatesNullAmountAndDate() {
		assertThrows(ValidationException.class, () -> service.addExpense(null, ExpenseCategory.FOOD, date, ""));
		assertThrows(ValidationException.class, () -> service.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, null, ""));
	}

	@Test
	public void findByIdAndListAllPreserveInsertionOrder() {
		Transaction first = service.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, date, "").getTransaction();
		Transaction second = service.addIncome(BigDecimal.ONE, IncomeCategory.SALARY, date, "").getTransaction();
		assertEquals(service.findById(first.getId()), first);
		assertEquals(service.listAll(), List.of(first, second));
	}

	@Test(expectedExceptions = TransactionNotFoundException.class)
	public void findByIdNonExistentThrows() {
		service.findById(99);
	}

	@Test
	public void listByCategoryMatchesExpenseAndIncomeNamesCaseInsensitively() {
		service.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, date, "");
		Transaction salary = service.addIncome(BigDecimal.ONE, IncomeCategory.SALARY, date, "").getTransaction();
		assertEquals(service.listByCategory("food").size(), 1);
		assertEquals(service.listByCategory("SALARY"), List.of(salary));
	}

	@Test(expectedExceptions = ValidationException.class)
	public void listByCategoryRejectsUnknownName() {
		service.listByCategory("CAR");
	}

	@Test
	public void listByDateRangeIncludesBothBounds() {
		Transaction start = service.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, date, "").getTransaction();
		Transaction middle = service.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, date.plusDays(1), "").getTransaction();
		Transaction end = service.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, date.plusDays(2), "").getTransaction();
		assertEquals(service.listByDateRange(date, date.plusDays(2)), List.of(start, middle, end));
		assertEquals(service.listByDateRange(date, date).size(), 1);
		assertTrue(service.listByDateRange(date.plusDays(3), date.plusDays(4)).isEmpty());
	}

	@Test
	public void listByDateRangeStartAfterEndThrowsAndStoreIsUnchanged() {
		service.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, date, "");
		int countBefore = service.listAll().size();
		assertThrows(ValidationException.class, () -> service.listByDateRange(date.plusDays(1), date));
		assertEquals(service.listAll().size(), countBefore);
	}

	@Test
	public void editUpdatesAmountAndKeepsOtherFields() {
		Transaction original = service.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, date, "lunch").getTransaction();
		Transaction updated = service.editTransaction(original.getId(), TransactionType.EXPENSE,
				new BigDecimal("3.50"), ExpenseCategory.FOOD, date, "lunch").getTransaction();
		assertEquals(updated.getAmount(), new BigDecimal("3.50"));
		assertEquals(updated.getDescription(), "lunch");
		assertEquals(updated.getDate(), date);
		assertEquals(updated.getId(), original.getId());
	}

	@Test
	public void editTypeChangeExpenseToIncomeRevalidatesCategory() {
		Transaction original = service.addExpense(new BigDecimal("20"), ExpenseCategory.FOOD, date, "").getTransaction();
		Transaction updated = service.editTransaction(original.getId(), TransactionType.INCOME,
				new BigDecimal("20"), IncomeCategory.SALARY, date, "").getTransaction();
		assertEquals(updated.getType(), TransactionType.INCOME);
		assertEquals(updated.getCategory(), IncomeCategory.SALARY);
		assertTrue(new SummaryService(store).spendingByCategory().isEmpty());
		assertEquals(new SummaryService(store).balanceSummary().getTotalIncome(), new BigDecimal("20"));
	}

	@Test
	public void invalidEditIsAtomic() {
		Transaction original = service.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, date, "").getTransaction();
		assertThrows(ValidationException.class, () -> service.editTransaction(original.getId(),
				TransactionType.INCOME, BigDecimal.ONE, ExpenseCategory.FOOD, date, ""));
		assertEquals(service.findById(original.getId()), original);
	}

	@Test(expectedExceptions = TransactionNotFoundException.class)
	public void editNonExistentIdThrows() {
		service.editTransaction(99, TransactionType.EXPENSE, BigDecimal.ONE, ExpenseCategory.FOOD, date, "");
	}

	@Test
	public void editReturnsBudgetAlertAfterSavingUpdatedTransaction() {
		Transaction original = service.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, date, "").getTransaction();
		budgets.setBudget(ExpenseCategory.FOOD, 2024, 11, new BigDecimal("2"));
		TransactionResult result = service.editTransaction(original.getId(), TransactionType.EXPENSE,
				new BigDecimal("2"), ExpenseCategory.FOOD, date, "");
		assertTrue(result.getBudgetAlert().isPresent());
		assertEquals(result.getBudgetAlert().orElseThrow().getMonthlySpend(), new BigDecimal("2"));
	}

	@Test
	public void deleteRequiresConfirmationAndDoesNotReuseId() {
		Transaction first = service.addExpense(BigDecimal.ONE, ExpenseCategory.FOOD, date, "").getTransaction();
		assertEquals(service.deleteTransaction(first.getId(), false), DeleteOutcome.CANCELLED);
		assertTrue(service.findById(first.getId()) != null);
		assertEquals(service.deleteTransaction(first.getId(), true), DeleteOutcome.DELETED);
		assertEquals(service.addIncome(BigDecimal.ONE, IncomeCategory.SALARY, date, "").getTransaction().getId(), 2);
	}

	@Test(expectedExceptions = TransactionNotFoundException.class)
	public void deleteNonExistentIdThrows() {
		service.deleteTransaction(99, true);
	}
}
