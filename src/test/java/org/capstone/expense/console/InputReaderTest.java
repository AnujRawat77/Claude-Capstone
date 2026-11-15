package org.capstone.expense.console;

import org.capstone.expense.exception.ValidationException;
import org.capstone.expense.model.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.Scanner;

import static org.testng.Assert.*;

public class InputReaderTest {
	private static final Clock CLOCK = Clock.fixed(Instant.parse("2026-11-14T12:00:00Z"), ZoneOffset.UTC);

	private InputReader reader(String input) {
		return new InputReader(new Scanner(input), new PrintStream(new ByteArrayOutputStream()), CLOCK);
	}

	@Test
	public void parsesAmountsAndTrimsInputs() {
		InputReader input = reader(" 12.505 \n");
		assertEquals(input.readAmount("Amount:"), new BigDecimal("12.505"));
	}

	@DataProvider(name = "invalidAmounts")
	public Object[][] invalidAmounts() {
		return new Object[][]{{""}, {"abc"}, {"1e3"}, {"1,000"}, {".5"}, {"5."}, {"12 50"}};
	}

	@Test(dataProvider = "invalidAmounts", expectedExceptions = ValidationException.class)
	public void rejectsInvalidAmountSyntax(String amount) {
		reader(amount + "\n").readAmount("Amount:");
	}

	@Test
	public void optionalAmountCanBeLeftBlank() {
		assertEquals(reader("\n").readOptionalAmount("Amount:"), Optional.empty());
	}

	@Test
	public void blankDateDefaultsToClockDate() {
		assertEquals(reader("\n").readDate("Date:"), LocalDate.of(2026, 11, 14));
	}

	@Test
	public void acceptsLeapDayAndRequiredDate() {
		assertEquals(reader("2024-02-29\n").readRequiredDate("Date:"), LocalDate.of(2024, 2, 29));
	}

	@Test(expectedExceptions = ValidationException.class)
	public void rejectsImpossibleCalendarDate() {
		reader("2026-02-30\n").readDate("Date:");
	}

	@Test(expectedExceptions = ValidationException.class)
	public void rejectsMalformedDate() {
		reader("2026-1-5\n").readDate("Date:");
	}

	@Test(expectedExceptions = ValidationException.class)
	public void requiredDateRejectsBlank() {
		reader("\n").readRequiredDate("Date:");
	}

	@Test
	public void optionalDateAndLineRetainBlankAsEmpty() {
		InputReader input = reader("\n  \n");
		assertEquals(input.readOptionalDate("Date:"), Optional.empty());
		assertEquals(input.readOptionalLine("Description:"), Optional.empty());
	}

	@Test
	public void parsesIntegerAndCategoryOptions() {
		InputReader input = reader("12\n1\n4\n");
		assertEquals(input.readInt("Year:", 1, 9999), 12);
		assertEquals(input.readCategory(TransactionType.EXPENSE), ExpenseCategory.FOOD);
		assertEquals(input.readCategory(TransactionType.INCOME), IncomeCategory.OTHER_INCOME);
	}

	@Test
	public void menuChoiceProvidesSpecificErrors() {
		ValidationException nonNumeric = expectValidation(() -> reader("abc\n").readMenuChoice(1, 9));
		assertTrue(nonNumeric.getMessage().contains("Invalid choice 'abc'"));
		ValidationException outOfRange = expectValidation(() -> reader("12\n").readMenuChoice(1, 9));
		assertTrue(outOfRange.getMessage().contains("out of range"));
	}

	@Test
	public void confirmationAcceptsEitherCase() {
		assertTrue(reader("Y\n").readConfirmation("Confirm?"));
		assertFalse(reader("n\n").readConfirmation("Confirm?"));
		assertThrows(ValidationException.class, () -> reader("maybe\n").readConfirmation("Confirm?"));
	}

	@Test(expectedExceptions = java.util.NoSuchElementException.class)
	public void eofPropagatesToConsoleBoundary() {
		reader("").readLine("Input:");
	}

	private static ValidationException expectValidation(org.testng.Assert.ThrowingRunnable action) {
		try {
			action.run();
			fail("Expected ValidationException.");
		} catch (ValidationException exception) {
			return exception;
		} catch (Throwable throwable) {
			throw new AssertionError("Expected ValidationException.", throwable);
		}
		throw new AssertionError("Expected ValidationException.");
	}
}
