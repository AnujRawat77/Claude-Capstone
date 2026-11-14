# Requirements

Status: **Approved** — 2026-11-11. **Baseline clarifications C-4 and C-5 approved and incorporated** — 2026-11-12.

---

## Goal

Build a single-user, console-based Personal Expense Tracker in Java 21. The application allows a user to record income and expense transactions, categorise them using fixed enum categories, view summaries, generate simple reports, and track spending against monthly category budgets — entirely in memory, with no persistence between runs. The primary purpose is to demonstrate an evidence-based, eight-stage Agentic SDLC capstone.

---

## Users and Use Cases

**Single user type:** An individual end-user who runs the application from the command line.

| Use Case | Actor | Trigger | Goal |
|---|---|---|---|
| UC-01 | User | Launches app | Navigate a numbered console menu |
| UC-02 | User | Chooses "Add Expense" | Record a new expense transaction |
| UC-03 | User | Chooses "Add Income" | Record a new income transaction |
| UC-04 | User | Chooses "List Transactions" | View all or filtered transactions |
| UC-05 | User | Chooses "Edit Transaction" | Correct a previously entered transaction |
| UC-06 | User | Chooses "Delete Transaction" | Remove an erroneous transaction |
| UC-07 | User | Chooses "Spending Summary" | See totals and percentages per expense category |
| UC-08 | User | Chooses "Balance Summary" | See total income vs. total expenses and net balance |
| UC-09 | User | Chooses "Set Budget" | Assign a monthly spending limit to an expense category |
| UC-10 | User | Adds or edits a transaction | Receive a non-blocking alert if a category budget is reached or exceeded |

---

## Functional Requirements

### FR-001 — Add Expense

The application shall allow the user to add an expense transaction by providing:
- Amount (positive `BigDecimal`, strictly greater than zero; mandatory)
- Category (one of the `ExpenseCategory` enum values; mandatory — user selects by number from a displayed list)
- Date (`LocalDate` in `yyyy-MM-dd` format; defaults to `LocalDate.now()` if the user presses Enter without input)
- Description (free-text string; optional; may be blank)

Each expense is assigned a unique, auto-generated integer ID (starting at 1, never reused). The transaction type is recorded as `EXPENSE`.

**ExpenseCategory enum values:** `FOOD`, `TRANSPORT`, `SHOPPING`, `BILLS`, `HEALTH`, `ENTERTAINMENT`, `OTHER`

### FR-002 — Add Income

The application shall allow the user to add an income transaction by providing:
- Amount (positive `BigDecimal`, strictly greater than zero; mandatory)
- Category (one of the `IncomeCategory` enum values; mandatory — user selects by number from a displayed list)
- Date (`LocalDate` in `yyyy-MM-dd` format; defaults to `LocalDate.now()` if the user presses Enter without input)
- Description (free-text string; optional; may be blank)

Each income record is assigned a unique, auto-generated integer ID (same sequence as expenses). The transaction type is recorded as `INCOME`.

**IncomeCategory enum values:** `SALARY`, `BUSINESS`, `INVESTMENT`, `OTHER_INCOME`

### FR-003 — List Transactions

The application shall allow the user to list transactions with the following filter options:
1. All transactions (no filter) — returned in insertion order
2. Filtered by category — exact match against the enum name, case-insensitive; the user may enter either an expense or income category name
3. Filtered by date range — user supplies a start date and end date (`yyyy-MM-dd`); results include all transactions whose date satisfies `start <= date <= end` (both bounds inclusive); if start > end, the application displays an error and returns to the menu

Each row in the output shall display: ID, type, date, category, amount (formatted to 2 decimal places), description.

### FR-004 — Edit a Transaction

The application shall allow the user to edit any field of an existing transaction identified by its ID:
- Amount: re-validated (must be strictly positive `BigDecimal`)
- Category: when changed, must be valid for the **current or newly chosen** transaction type; presented as a numbered enum list appropriate to the type
- Date: re-validated (`yyyy-MM-dd`); blank retains current value
- Description: blank retains current value
- **Transaction type** (EXPENSE ↔ INCOME): may be changed; if changed, the application shall prompt the user to re-select the category from the enum list appropriate to the new type, since the previous category enum may be incompatible

Fields left blank (except type and category which always prompt) retain their current values. After editing, budget alerts are re-evaluated for the transaction's category and month (FR-009).

### FR-005 — Delete a Transaction

The application shall allow the user to delete a transaction by its ID. The application shall display the full transaction details and prompt for explicit confirmation ("y" / "n") before deletion. A deleted transaction's ID shall not be reused. Entering a non-existent ID displays an error and returns to the menu.

### FR-006 — Spending Summary by Category

The application shall compute and display, for all `EXPENSE` transactions currently in memory:
- Total amount spent per `ExpenseCategory`
- Each category's percentage of overall total expense spend, rounded to 2 decimal places using `RoundingMode.HALF_UP`

The summary shall be sorted by total spend descending. Categories with zero spend shall be omitted. If no expense transactions exist, the application displays an appropriate message.

### FR-007 — Income vs. Expense Balance Summary

The application shall display:
- Total income: sum of all `INCOME` transaction amounts
- Total expenses: sum of all `EXPENSE` transaction amounts
- Net balance: total income − total expenses (may be negative)

All arithmetic uses `BigDecimal`. Values are displayed formatted to 2 decimal places.

### FR-008 — Set Monthly Budget per Category

The application shall allow the user to set a monthly spending budget for an `ExpenseCategory`. The user provides:
- Category (selected from `ExpenseCategory` enum list)
- Year (4-digit integer)
- Month (1–12 integer)
- Budget amount (positive `BigDecimal`, strictly greater than zero)

If a budget already exists for that (category, year, month) triple, it is overwritten. Budgets apply only to `EXPENSE` transactions.

### FR-009 — Budget Alert

Whenever an `EXPENSE` transaction is added (FR-001) or edited (FR-004), the application shall:
1. Sum all `EXPENSE` transactions for the same `ExpenseCategory` and calendar month (year + month) as the transaction
2. Compare that total against the budget set for that (category, year, month), if one exists
3. If the total **equals or exceeds** the budget (i.e., ≥ 100% utilisation), display a non-blocking warning immediately after the operation completes; the user is not required to acknowledge it and may continue using the application normally

The warning message shall clearly state: the category name, the configured budget amount, and the actual total spend for that month.

If no budget is set for the (category, year, month) triple, no warning is displayed.

### FR-010 — Console Menu Navigation

The application shall present a numbered main menu. The user selects options by entering the corresponding number. The application shall:
- Display a clear, numbered prompt for every choice point
- Validate all user inputs and display a specific, human-readable error message for invalid input (e.g., non-numeric amount, unrecognised date format, out-of-range menu number, non-existent transaction ID, zero or negative amount)
- Never propagate an unhandled exception to the console
- Return to the main menu after each operation completes or after an error
- Provide an "Exit" option that terminates the application cleanly (System.exit(0) or equivalent)

No "clear all data" or "reset session" option shall be implemented.

---

## Non-Functional Requirements

### NFR-001 — Monetary Precision

All monetary values (amounts, totals, budgets, balances) shall be represented and computed using `java.math.BigDecimal`. Use of `double`, `float`, `Double`, or `Float` for monetary arithmetic is prohibited. Division for percentage calculation shall use an explicit `MathContext` or `scale` with `RoundingMode.HALF_UP`.

### NFR-002 — Date Handling

All date values shall use `java.time.LocalDate`. Use of `java.util.Date`, `java.util.Calendar`, or any legacy date API is prohibited. Date parsing shall use `DateTimeFormatter.ofPattern("yyyy-MM-dd")` with explicit error handling for `DateTimeParseException`.

### NFR-003 — In-Memory Storage Only

All data shall reside in JVM heap collections (e.g., `List`, `Map`). No file I/O (`java.io.*`, `java.nio.*`), no database access (JDBC, JPA, etc.), and no network calls shall be made.

### NFR-004 — Console I/O

All user interaction shall use `java.util.Scanner` for input and `System.out` for output. No GUI framework, logging framework, or external I/O library shall be used.

### NFR-005 — Build Tool

The project shall build and all tests shall pass with the single command `mvn test` executed from the project root. No IDE-specific build steps are required.

### NFR-006 — Test Framework

All automated tests shall use TestNG 7.11.0 exclusively. JUnit and other test frameworks shall not be introduced.

### NFR-007 — Code Generation via Lombok

Lombok 1.18.46 shall be used for boilerplate reduction on model/entity classes. Permitted annotations include but are not limited to: `@Getter`, `@Setter`, `@Builder`, `@AllArgsConstructor`, `@NoArgsConstructor`, `@EqualsAndHashCode`, `@ToString`.

### NFR-008 — Compilation Quality

The project shall compile cleanly under Java 21 (`maven.compiler.release=21`) with zero compiler warnings on target application classes.

### NFR-009 — Test Coverage Minimum

Every public method on every service class shall be exercised by at least one TestNG test. Edge cases (zero amount, negative amount, non-existent ID, start > end date range, type change during edit, budget overwrite, budget alert at exactly 100%) shall each have their own test method.

---

## Scope and Exclusions

The following are explicitly outside scope and must not be implemented:

| Excluded Item | Rationale |
|---|---|
| Data persistence (file, DB) | In-memory only; no state survives restart |
| REST or any HTTP API | Console application only |
| Web UI (HTML, JSP, Thymeleaf, etc.) | Console application only |
| Spring Boot or any DI framework | Plain Java; no framework |
| Authentication or authorisation | Single-user; no login |
| Multi-user or concurrent access | Single-threaded; one session at a time |
| Cloud services or external APIs | No network calls |
| Internationalisation / localisation | English locale only |
| Recurring transactions | Out of scope for this capstone |
| Data export (CSV, PDF, etc.) | Out of scope for this capstone |
| Clear-all / reset-session menu option | Decided out of scope (OQ-004) |

---

## Constraints

| ID | Constraint |
|---|---|
| C-001 | Language: Java 21 (LTS); `maven.compiler.release=21` |
| C-002 | Build: Apache Maven; groupId `org.capstone`, artifactId `expense-tracker`, version `0.1.0-SNAPSHOT` |
| C-003 | Test framework: TestNG 7.11.0 (`<scope>test</scope>`) |
| C-004 | Code generation: Lombok 1.18.46 (`<scope>provided</scope>`) |
| C-005 | Monetary type: `java.math.BigDecimal` exclusively |
| C-006 | Date type: `java.time.LocalDate` exclusively; format `yyyy-MM-dd` |
| C-007 | UI: console only — `Scanner` / `System.out` |
| C-008 | Storage: JVM heap (in-memory); no persistence |
| C-009 | IDE: IntelliJ IDEA (no IDE-specific files committed) |
| C-010 | Version control: Git / GitHub |
| C-011 | Expense categories: fixed enum `ExpenseCategory` — `FOOD`, `TRANSPORT`, `SHOPPING`, `BILLS`, `HEALTH`, `ENTERTAINMENT`, `OTHER` |
| C-012 | Income categories: fixed enum `IncomeCategory` — `SALARY`, `BUSINESS`, `INVESTMENT`, `OTHER_INCOME` |

---

## Assumptions

| ID | Assumption |
|---|---|
| A-001 | Categories are fixed enums (C-011, C-012); users select by number from a displayed list; free-text category entry is not supported. |
| A-002 | If the user does not supply a date when adding a transaction, the application defaults to `LocalDate.now()`. |
| A-003 | Amounts must be strictly positive (`> 0`). Zero and negative values are rejected with an error message; no transaction is stored. |
| A-004 | Transaction IDs are auto-incremented integers starting at 1 and are never reused after deletion. Expenses and incomes share a single ID sequence. |
| A-005 | The application runs as a single-threaded interactive loop; no concurrency is required. |
| A-006 | Date input format is `yyyy-MM-dd` (ISO-8601). The application states this format when prompting. |
| A-007 | Percentage values in the spending summary are rounded to 2 decimal places using `RoundingMode.HALF_UP`. |
| A-008 | Budgets are monthly: keyed by (`ExpenseCategory`, year, month); they apply only to `EXPENSE` transactions. |
| A-009 | Budget alert fires when monthly category spend ≥ budget (i.e., at exactly 100% or more); alerts are non-blocking. |
| A-010 | When editing changes the transaction type, the user must re-select a category from the enum list for the new type; the old category value is discarded. |
| A-011 | Net balance = total income − total expenses; this value may be negative. |
| A-012 | Date-range filtering is inclusive on both bounds: transactions where `start <= date <= end` are returned. If `start > end` the application reports an error. |
| A-013 | The application is single-locale English; no internationalisation is planned. |
| A-014 | The in-memory store is not shared across JVM instances; restarting the application resets all data. |

---

## Remaining Ambiguities

No unresolved open questions remain. All four original open questions (OQ-001 through OQ-004) have been decided by the stakeholder:

| OQ | Decision |
|---|---|
| OQ-001 | Fixed enums (C-011, C-012) |
| OQ-002 | Non-blocking budget alert at ≥ 100% utilisation |
| OQ-003 | Type change allowed during edit; category must be re-selected for the new type |
| OQ-004 | No clear-all / reset-session option |

---

## Acceptance Criteria

### Functional

| ID | Criterion |
|---|---|
| AC-FR-001a | Given valid inputs (positive amount, valid `ExpenseCategory`, optional date/description), `addExpense()` stores a new `EXPENSE` transaction; retrieving it by ID returns all fields intact including assigned category enum. |
| AC-FR-001b | Given a zero or negative amount, `addExpense()` rejects the input with an error message; no transaction is stored; total transaction count is unchanged. |
| AC-FR-001c | A blank date defaults to the current date (`LocalDate.now()`). Malformed or impossible date values (e.g., `2026-1-5`, `2026-02-30`, or non-`yyyy-MM-dd` format) are rejected with an error message; no transaction is stored. **[C-4 — Clarified 2026-11-12: blank date defaults to today; malformed/impossible dates rejected.]** |
| AC-FR-002a | Given valid inputs (positive amount, valid `IncomeCategory`, optional date/description), `addIncome()` stores a new `INCOME` transaction; retrieving it by ID returns all fields intact. |
| AC-FR-002b | Given a zero or negative amount, `addIncome()` rejects the input with an error message; no transaction is stored. |
| AC-FR-003a | `listAll()` returns every transaction in the store in insertion order; count matches total adds minus deletes. |
| AC-FR-003b | `listByCategory("FOOD")` (case-insensitive) returns only transactions whose category name equals `FOOD`; transactions of other categories are excluded. |
| AC-FR-003c | `listByDateRange(start, end)` returns only transactions where `start <= date <= end` inclusive; a transaction dated exactly on `start` and one dated exactly on `end` are both included. |
| AC-FR-003d | `listByDateRange(start, end)` where `start > end` throws `ValidationException`; the console catches it and displays the error message. No transactions are returned and the store is unchanged. |
| AC-FR-004a | Given an existing transaction ID and a new valid amount, `editTransaction()` updates the amount; all other fields remain unchanged. |
| AC-FR-004b | Given an existing transaction ID and a type change from `EXPENSE` to `INCOME`, `editTransaction()` updates the type; the category is updated to a valid `IncomeCategory`; the transaction is no longer counted in expense summaries. |
| AC-FR-004c | Given a non-existent ID, `editTransaction()` displays an error; the store is unchanged. |
| AC-FR-005a | Given an existing ID and confirmation "y", `deleteTransaction()` removes the transaction; `listAll()` no longer contains that ID. |
| AC-FR-005b | Given an existing ID and confirmation "n", `deleteTransaction()` aborts; the transaction remains in the store. |
| AC-FR-005c | Given a non-existent ID, `deleteTransaction()` displays an error; the store is unchanged. |
| AC-FR-006a | `getCategorySpendingSummary()` returns entries sorted by total spend descending; each entry contains the category name, total `BigDecimal`, and percentage rounded to 2 decimal places; categories with zero spend are absent. |
| AC-FR-006b | Each category percentage is rounded to 2 decimal places using `RoundingMode.HALF_UP`, calculated independently per category. The sum of all displayed percentages differs from 100.00 by no more than ±0.005 multiplied by the number of displayed category rows (n). |
| AC-FR-007a | `getBalanceSummary()` returns totalIncome, totalExpenses, and netBalance where netBalance = totalIncome − totalExpenses, all computed with `BigDecimal` arithmetic. |
| AC-FR-007b | With no transactions, all three values are `BigDecimal.ZERO`. |
| AC-FR-008a | `setBudget(FOOD, 2024, 11, new BigDecimal("500"))` stores the budget; `getBudget(FOOD, 2024, 11)` returns `500`. |
| AC-FR-008b | Calling `setBudget` again for the same (category, year, month) with a new value replaces the previous value; the old value is no longer retrievable. |
| AC-FR-009a | After adding an `EXPENSE` that causes the `FOOD` monthly total to equal or exceed the set budget, the application prints a warning containing the category name, budget limit, and actual monthly total. |
| AC-FR-009b | After adding an `EXPENSE` where monthly total is below the budget, no warning is printed. |
| AC-FR-009c | If no budget is set for the (category, year, month) triple, no warning is printed regardless of spend amount. |
| AC-FR-009d | Budget alert fires at exactly 100% utilisation (spend = budget), not only above it. |
| AC-FR-010a | Entering a non-numeric character at the main menu displays a specific error message and re-displays the menu; no exception reaches the console. |
| AC-FR-010b | Entering a valid menu option number navigates to the corresponding sub-flow. |
| AC-FR-010c | Selecting "Exit" terminates the application cleanly. |

### Non-Functional

| ID | Criterion |
|---|---|
| AC-NFR-001 | A source scan of all `src/main` Java files finds zero occurrences of `double`, `float`, `Double`, or `Float` used for monetary values or arithmetic. |
| AC-NFR-002 | A source scan of all `src/main` Java files finds zero occurrences of `java.util.Date` or `java.util.Calendar`. |
| AC-NFR-003 | All data resides in JVM heap collections; file I/O (`java.io.*`, `java.nio.*`), database access (`java.sql.*`), and network calls are prohibited in `src/main`. Console I/O using `java.util.Scanner`, `System.in`, and `System.out` (standard streams) is explicitly permitted in the console layer. **[C-5 — Clarified 2026-11-12: prohibit file, network, and database I/O; explicitly permit console I/O via standard streams.]** |
| AC-NFR-004 | A source scan of all `src/main` Java files finds zero imports of GUI, logging, or external I/O libraries. |
| AC-NFR-005 | Running `mvn test` from the project root completes with `BUILD SUCCESS` and zero test failures. |
| AC-NFR-006 | The test source tree contains only TestNG annotations (`@Test`, `@BeforeMethod`, etc.); no JUnit imports are present. |
| AC-NFR-007 | At least one Lombok annotation (`@Getter`, `@Builder`, etc.) is present on each domain model class; `mvn compile` succeeds without annotation-processor errors. |
| AC-NFR-008 | `mvn compile` completes with zero warnings on `src/main` classes under Java 21. |
| AC-NFR-009 | Every public method in every class under `src/main/java/.../service/` has a corresponding TestNG `@Test` method; `mvn test` reports no failures. |

---

## Traceability Matrix

| Requirement ID | Description | Acceptance Criteria ID(s) |
|---|---|---|
| FR-001 | Add expense transaction | AC-FR-001a, AC-FR-001b, AC-FR-001c |
| FR-002 | Add income transaction | AC-FR-002a, AC-FR-002b |
| FR-003 | List transactions (all / by category / by date range) | AC-FR-003a, AC-FR-003b, AC-FR-003c, AC-FR-003d |
| FR-004 | Edit a transaction (including type change with category re-validation) | AC-FR-004a, AC-FR-004b, AC-FR-004c |
| FR-005 | Delete a transaction with confirmation | AC-FR-005a, AC-FR-005b, AC-FR-005c |
| FR-006 | Spending summary by category | AC-FR-006a, AC-FR-006b |
| FR-007 | Income vs. expense balance summary | AC-FR-007a, AC-FR-007b |
| FR-008 | Set monthly budget per expense category | AC-FR-008a, AC-FR-008b |
| FR-009 | Non-blocking budget alert at ≥ 100% utilisation | AC-FR-009a, AC-FR-009b, AC-FR-009c, AC-FR-009d |
| FR-010 | Console menu navigation and input validation | AC-FR-010a, AC-FR-010b, AC-FR-010c |
| NFR-001 | BigDecimal for all monetary values | AC-NFR-001 |
| NFR-002 | java.time.LocalDate for all dates | AC-NFR-002 |
| NFR-003 | In-memory storage only | AC-NFR-003 |
| NFR-004 | Console I/O via Scanner / System.out | AC-NFR-004 |
| NFR-005 | Build and test via `mvn test` | AC-NFR-005 |
| NFR-006 | TestNG 7.11.0 as the sole test framework | AC-NFR-006 |
| NFR-007 | Lombok 1.18.46 for boilerplate on model classes | AC-NFR-007 |
| NFR-008 | Clean Java 21 compilation, zero warnings | AC-NFR-008 |
| NFR-009 | All public service methods covered by at least one TestNG test | AC-NFR-009 |
