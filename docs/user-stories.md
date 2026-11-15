# User Stories — Personal Expense Tracker

## Baseline and Provenance

This document records the derived user-story baseline for the Personal Expense Tracker capstone project.

The stories are derived from the approved functional requirements in `docs/requirements.md` and the approved project baseline.

These stories provide a user-oriented view of the application's expected behaviour. They supplement the requirements document and improve traceability between user goals, functional requirements, acceptance criteria, and implementation tasks.

The approved technical baseline remains:

- Java 21
- Maven
- TestNG 7.11.0 exclusively
- Lombok 1.18.46 for the approved model classes
- Console-based user interface
- Layered application architecture
- In-memory transaction and budget storage
- BigDecimal for monetary values
- LocalDate for dates

**Baseline status:** APPROVED by the human stakeholder on 2026-11-15.
**Created:** 2026-11-12  
**Source:** `docs/requirements.md`  
**Implementation authorization:** Not granted by this document.

The approved requirements remain authoritative. If any wording in a user story conflicts with the requirements or their acceptance criteria, the approved requirements take precedence.

---

## Stories

### US-001: Add an expense

As a user, I want to record an expense with its amount, category, date, and description so that I can track where my money is spent.

**Acceptance criteria**

- A valid expense is recorded with a unique automatically generated transaction ID.
- The expense amount must be positive.
- The selected category must be one of the supported expense categories.
- The expense date must be a valid date in `yyyy-MM-dd` format.
- If the date is left blank, the application defaults it to the current date.
- Malformed or impossible dates are rejected, and no transaction is stored.
- A successfully added expense is available for subsequent listing and reporting.
- Invalid input is handled through the application's validation behaviour.

**Traceability:** FR-001

### US-002: Add income

As a user, I want to record income with its amount, category, date, and description so that I can track the money I receive.

**Acceptance criteria**

- A valid income transaction is recorded with a unique automatically generated transaction ID.
- The income amount must be positive.
- The selected category must be one of the supported income categories.
- The income date must follow the supported date format.
- The transaction receives an automatically generated ID from the shared transaction ID sequence.
- Income transactions are available for subsequent listing and financial summaries.
- Invalid input must not result in an invalid transaction being stored.

**Traceability:** FR-002

### US-003: View and filter transactions

As a user, I want to view my recorded transactions and filter them using the supported criteria so that I can find and review relevant financial records.

**Acceptance criteria**

- The application displays recorded transactions matching the selected filter criteria.
- Both expense and income transactions can be listed.
- Filtering must not modify stored transaction data.
- Date-range filtering uses inclusive start and end dates.
- A reversed date range, where the start date is after the end date, is rejected through a `ValidationException`.
- The console displays the validation error and no transactions are returned for an invalid date range.
- The transaction store remains unchanged after an invalid filtering request.

**Traceability:** FR-003

### US-004: Edit a transaction

As a user, I want to edit an existing transaction so that I can correct or update financial information that I previously recorded.

**Acceptance criteria**

- An existing transaction can be selected using its transaction ID.
- The user can update the supported transaction details.
- The transaction type is always prompted during editing.
- Pressing Enter retains the existing transaction type.
- The user can change an expense into income or income into expense.
- When the transaction type changes, the category must be selected from the category options for the new type.
- An unsuccessful edit must not leave the transaction in a partially updated state.
- The transaction ID remains associated with the edited transaction.

**Traceability:** FR-004

### US-005: Delete a transaction

As a user, I want to delete an existing transaction after confirming the action so that I can remove records that should no longer be included in my financial records.

**Acceptance criteria**

- The user can select a transaction using its ID.
- The application requests confirmation before deletion.
- A confirmed deletion removes the selected transaction.
- A deletion that is not confirmed must not remove the transaction.
- A deleted transaction ID must never be reused.
- The application handles invalid transaction references through its validation behaviour.

**Traceability:** FR-005

### US-006: View category-wise spending summary

As a user, I want to see my expenses grouped by category so that I can understand how my spending is distributed.

**Acceptance criteria**

- The application calculates expense totals separately for each displayed category.
- Only expense transactions contribute to the spending summary.
- Category totals use accurate monetary calculations.
- Category percentages are calculated independently.
- Each category percentage is rounded to two decimal places using HALF_UP rounding.
- The sum of displayed percentages may differ from 100.00 because of independent rounding, within the approved tolerance.
- Generating a summary must not modify transaction data.

**Traceability:** FR-006

### US-007: View income, expense, and net balance summary

As a user, I want to see my total income, total expenses, and net balance so that I can understand my overall financial position.

**Acceptance criteria**

- The application calculates total income from recorded income transactions.
- The application calculates total expenses from recorded expense transactions.
- Net balance is calculated as total income minus total expenses.
- Monetary calculations use BigDecimal.
- The summary reflects the currently stored transactions.
- Generating the summary must not modify stored transaction data.

**Traceability:** FR-007

### US-008: Manage monthly category budgets

As a user, I want to set or update a monthly budget for an expense category so that I can establish spending limits for different categories.

**Acceptance criteria**

- The user can set a budget for a supported expense category, year, and month.
- The budget amount must be positive.
- A budget is associated with its expense category and specified year and month.
- Setting a budget for an existing category/year/month combination overwrites the previous budget.
- Different category/year/month combinations maintain separate budgets.
- Setting or updating a budget does not itself trigger a budget alert.

**Traceability:** FR-008

### US-009: Receive monthly budget alerts

As a user, I want to receive an alert when spending in an expense category reaches or exceeds its monthly budget so that I can become aware of potential overspending.

**Acceptance criteria**

- The application evaluates spending against the corresponding monthly category budget.
- An alert is generated when spending reaches or exceeds 100% of the budget.
- Spending below the budget does not trigger an alert.
- Spending exactly equal to the budget triggers an alert.
- Spending above the budget also triggers an alert.
- Budget alerts are non-blocking and do not prevent transaction operations.
- Alerts are evaluated when an expense is added or edited.
- Setting a budget alone does not trigger an alert.

**Traceability:** FR-009

### US-010: Navigate the application and handle invalid input

As a user, I want clear console navigation and understandable validation messages so that I can operate the application and recover from invalid input.

**Acceptance criteria**

- The application provides the approved console menu and its supported operations.
- Menu selections route to their corresponding operations.
- Invalid menu selections are handled without unexpectedly terminating the application.
- Invalid user input produces an understandable validation message.
- The console uses the approved Scanner and System.out interaction approach.
- After a validation error, the application follows the approved recovery behaviour and returns to the menu.
- End-of-input (EOF) is handled cleanly, allowing the console to terminate normally.
- Selecting Exit terminates the application normally.

**Traceability:** FR-010

---

## Traceability Summary

| User Story | Functional Requirement | User Goal |
|---|---|---|
| US-001 | FR-001 | Record expenses |
| US-002 | FR-002 | Record income |
| US-003 | FR-003 | View and filter transactions |
| US-004 | FR-004 | Edit transactions |
| US-005 | FR-005 | Delete transactions |
| US-006 | FR-006 | Understand category-wise spending |
| US-007 | FR-007 | Understand overall financial position |
| US-008 | FR-008 | Manage monthly category budgets |
| US-009 | FR-009 | Receive budget alerts |
| US-010 | FR-010 | Navigate the application and handle invalid input |

---

## Relationship with Other Project Documents

| Document | Responsibility |
|---|---|
| `docs/user-stories.md` | Describes functionality from the user's perspective |
| `docs/requirements.md` | Authoritative functional and non-functional requirements and acceptance criteria |
| `docs/architecture.md` | Defines the approved technical architecture and design decisions |
| `docs/design-review.md` | Records the independent design review and its findings |
| `docs/implementation-plan.md` | Defines implementation tasks, milestones, and verification mapping |
| `docs/changelog.md` | Records SDLC activities, decisions, evidence, and approvals |

## Scope and Change Control

1. These user stories are derived from the approved requirements.
2. They do not introduce additional application features.
3. They do not replace or override the acceptance criteria in `docs/requirements.md`.
4. Implementation must remain within the approved requirements and architecture.
5. Any proposed functional change must follow the project's change-control and human-approval process.
6. The implementation plan should reference the relevant user-story IDs alongside functional requirements and acceptance criteria.
7. Any ambiguity must be resolved against the authoritative requirements before implementation.

---

## Human Review and Approval

**Document status:** APPROVED
**Reviewed by:** Human stakeholder
**Approved by:** Human stakeholder
**Approval date:** 2026-11-15
**Implementation authorization:** Not granted

This document becomes an approved supplementary project artifact only after explicit human approval.