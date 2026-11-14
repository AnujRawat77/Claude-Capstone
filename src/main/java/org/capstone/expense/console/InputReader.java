package org.capstone.expense.console;

import org.capstone.expense.exception.ValidationException;
import org.capstone.expense.model.Category;
import org.capstone.expense.model.ExpenseCategory;
import org.capstone.expense.model.IncomeCategory;
import org.capstone.expense.model.TransactionType;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputReader {
	private static final Pattern AMOUNT_PATTERN = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private final Scanner scanner;
	private final PrintStream out;
	private final Clock clock;

	public InputReader(Scanner scanner, PrintStream out, Clock clock) {
		this.scanner = scanner;
		this.out = out;
		this.clock = clock;
	}

	public String readLine(String prompt) {
		out.print(prompt);
		return scanner.nextLine().trim();
	}

	public Optional<String> readOptionalLine(String prompt) {
		String value = readLine(prompt);
		return value.isEmpty() ? Optional.empty() : Optional.of(value);
	}

	public int readMenuChoice(int minimum, int maximum) {
		String value = readLine("Choose an option (" + minimum + "-" + maximum + "):");
		int choice;
		try {
			choice = Integer.parseInt(value);
		} catch (NumberFormatException exception) {
			throw new ValidationException("Invalid choice '" + value + "'. Please enter a number from "
					+ minimum + " to " + maximum + ".");
		}
		if (choice < minimum || choice > maximum) {
			throw new ValidationException("Choice " + choice + " is out of range. Please enter a number from "
					+ minimum + " to " + maximum + ".");
		}
		return choice;
	}

	public BigDecimal readAmount(String prompt) {
		return parseAmount(readLine(prompt));
	}

	public Optional<BigDecimal> readOptionalAmount(String prompt) {
		String value = readLine(prompt);
		return value.isEmpty() ? Optional.empty() : Optional.of(parseAmount(value));
	}

	public LocalDate readDate(String prompt) {
		String value = readLine(prompt);
		return value.isEmpty() ? LocalDate.now(clock) : parseDate(value);
	}

	public LocalDate readRequiredDate(String prompt) {
		String value = readLine(prompt);
		if (value.isEmpty()) {
			throw new ValidationException("Date is required in yyyy-MM-dd format.");
		}
		return parseDate(value);
	}

	public Optional<LocalDate> readOptionalDate(String prompt) {
		String value = readLine(prompt);
		return value.isEmpty() ? Optional.empty() : Optional.of(parseDate(value));
	}

	public int readInt(String prompt, int minimum, int maximum) {
		String value = readLine(prompt);
		int parsed;
		try {
			parsed = Integer.parseInt(value);
		} catch (NumberFormatException exception) {
			throw new ValidationException("Enter a whole number.");
		}
		if (parsed < minimum || parsed > maximum) {
			throw new ValidationException("Value must be between " + minimum + " and " + maximum + ".");
		}
		return parsed;
	}

	public Category readCategory(TransactionType type) {
		if (type == TransactionType.EXPENSE) {
			ExpenseCategory[] categories = ExpenseCategory.values();
			for (int index = 0; index < categories.length; index++) {
				out.println((index + 1) + ". " + categories[index].name());
			}
			return categories[readIndex(categories.length) - 1];
		}
		IncomeCategory[] categories = IncomeCategory.values();
		for (int index = 0; index < categories.length; index++) {
			out.println((index + 1) + ". " + categories[index].name());
		}
		return categories[readIndex(categories.length) - 1];
	}

	public boolean readConfirmation(String prompt) {
		String value = readLine(prompt);
		if (value.equalsIgnoreCase("y")) {
			return true;
		}
		if (value.equalsIgnoreCase("n")) {
			return false;
		}
		throw new ValidationException("Please enter y or n.");
	}

	private int readIndex(int length) {
		String value = readLine("Choose a category (1-" + length + "):");
		int choice;
		try {
			choice = Integer.parseInt(value);
		} catch (NumberFormatException exception) {
			throw new ValidationException("Enter a category number from 1 to " + length + ".");
		}
		if (choice < 1 || choice > length) {
			throw new ValidationException("Category choice must be between 1 and " + length + ".");
		}
		return choice;
	}

	private static BigDecimal parseAmount(String value) {
		if (!AMOUNT_PATTERN.matcher(value).matches()) {
			throw new ValidationException("Enter an amount using digits and an optional decimal point.");
		}
		try {
			return new BigDecimal(value);
		} catch (NumberFormatException exception) {
			throw new ValidationException("Enter a valid amount.");
		}
	}

	private static LocalDate parseDate(String value) {
		try {
			LocalDate parsed = LocalDate.parse(value, DATE_FORMAT);
			if (!DATE_FORMAT.format(parsed).equals(value)) {
				throw new ValidationException("Enter a valid date in yyyy-MM-dd format.");
			}
			return parsed;
		} catch (DateTimeParseException exception) {
			throw new ValidationException("Enter a valid date in yyyy-MM-dd format.");
		}
	}
}
