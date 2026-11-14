---
name: implementation
description: Implement approved Personal Expense Tracker tasks in small, traceable increments while preserving requirements, architecture, and project constraints during Stage 5.
---

# Implementation Skill

## Purpose

Guide controlled implementation of the Personal Expense Tracker using the approved requirements, architecture, and implementation plan.

This skill supports the Java Developer agent during Stage 5.

Implementation must be incremental, testable, traceable, and reviewable.

## 1. Mandatory Preconditions

Before writing application code, verify:

- Stage 1 Requirements Analysis is approved.
- Stage 2 Architecture Design is approved.
- Stage 3 Design Review is approved.
- Stage 4 Implementation Planning is approved.
- The Orchestrator explicitly authorizes Stage 5.
- The assigned implementation task exists in `docs/implementation-plan.md`.

If any prerequisite is missing, stop and report the blocker.

## 2. Authoritative Inputs

Read:

- `CLAUDE.md`
- `docs/requirements.md`
- `docs/architecture.md`
- `docs/design-review.md`
- `docs/implementation-plan.md`
- Relevant acceptance criteria.
- Existing source code and tests.
- `docs/changelog.md`.

Treat approved documentation as authoritative.

Do not rely solely on conversational summaries.

## 3. Implementation Procedure

### Step 1: Understand the assigned task

Before modifying files:

1. Identify the task ID.
2. Read its objective and completion conditions.
3. Identify relevant requirements and acceptance criteria.
4. Identify affected architecture components.
5. Check dependencies and existing implementation.
6. Identify expected tests.

Do not expand the task scope without authorization.

### Step 2: Inspect existing code

Inspect the relevant project structure and source files.

Preserve existing working functionality.

Do not overwrite unrelated changes.

Prefer focused changes over broad refactoring.

### Step 3: Implement in small increments

Implement one coherent task at a time.

For each increment:

1. Follow the approved architecture.
2. Use clear names and appropriate Java types.
3. Keep responsibilities focused.
4. Handle invalid inputs and expected errors.
5. Avoid unnecessary abstractions.
6. Add or update relevant tests.
7. Review the resulting diff.

Do not implement unrelated tasks merely because they appear convenient.

### Step 4: Follow domain rules

Apply the approved business rules consistently.

Important project requirements include:

- Use `BigDecimal` for every monetary value.
- Never use `double` or `float` for money.
- Use `LocalDate` for transaction dates.
- Preserve positive-amount validation.
- Preserve unique, auto-incrementing transaction IDs.
- Never reuse deleted transaction IDs.
- Use the approved expense and income categories.
- Enforce category compatibility with transaction type.
- Preserve correct transaction type-change behaviour.
- Apply inclusive date-range filtering.
- Calculate net balance as income minus expenses.
- Apply monthly category budgets correctly.
- Trigger budget alerts at or above 100% of the budget.
- Keep alerts non-blocking.

Refer to `docs/requirements.md` for exact rules.

### Step 5: Follow project conventions

Use:

- Java 21.
- Maven.
- TestNG 7.11.0 exclusively.
- Lombok 1.18.46 for model classes.
- Scanner and System.out for console I/O.
- In-memory data structures.

Respect the package structure defined in `docs/architecture.md`.

Do not introduce:

- Databases.
- File-based persistence.
- Spring Boot.
- REST APIs.
- Web interfaces.
- GUI frameworks.
- Cloud services.
- Unapproved runtime dependencies.
- Additional testing frameworks.

### Step 6: Handle errors safely

Validate user input before passing it to business operations.

Provide understandable error messages.

Handle recoverable input errors without unnecessarily terminating the application.

Do not hide unexpected programming errors with broad, empty catch blocks.

Do not silently convert invalid values into defaults.

### Step 7: Verify changes

After each coherent implementation increment:

- Inspect the changed files.
- Compile the relevant code where practical.
- Run relevant tests.
- Check the diff for unintended modifications.
- Confirm requirement and task traceability.

Run the full Maven verification command when appropriate:

`mvn test`

Report actual command results.

Never claim a successful build or test run without executing it.

### Step 8: Maintain implementation traceability

Record:

- Task ID.
- Files changed.
- Requirements addressed.
- Acceptance criteria addressed.
- Tests added or updated.
- Commands executed.
- Actual verification results.
- Known limitations.

Update `docs/changelog.md` with significant implementation activity.

Do not claim that a requirement is fully verified merely because code was written.

## 4. Code Quality Expectations

Code should be:

- Readable.
- Cohesive.
- Consistent with the architecture.
- Appropriately encapsulated.
- Testable.
- Free from unnecessary duplication.
- Free from unrelated changes.

Avoid:

- Overengineering.
- Premature abstraction.
- Unnecessary design patterns.
- Hard-coded business rules scattered across classes.
- Silent exception handling.
- Unjustified dependency additions.
- Suppressing compiler warnings instead of fixing their causes.

## 5. Implementation Completion Report

For every completed task, report:

1. Task ID and title.
2. Implementation summary.
3. Files changed.
4. Requirements and acceptance criteria addressed.
5. Tests added or updated.
6. Verification commands executed.
7. Actual results.
8. Known limitations.
9. Remaining tasks or dependencies.

Update `docs/implementation-plan.md` task status only when supported by actual evidence.

## 6. Escalation Rules

Stop and consult the Orchestrator if:

- An approved requirement is ambiguous.
- Architecture does not support the assigned task.
- A business rule appears contradictory.
- A change would affect approved scope.
- A dependency requires an unapproved technology.
- A significant implementation risk is discovered.

Do not independently change the requirements or architecture to resolve these issues.

## 7. Approval Boundary

The Java Developer implements only authorized tasks.

Completion of implementation does not constitute independent code review or final acceptance.

Stage 6 must independently review the implementation.

Stage 7 must verify the application and its tests.

The human approval gates remain mandatory.

## 8. Prohibited Actions

- Do not start implementation before Stage 4 approval.
- Do not invent new requirements.
- Do not redesign approved architecture.
- Do not modify unrelated modules.
- Do not use another testing framework.
- Do not introduce prohibited technologies.
- Do not fabricate test results.
- Do not conceal known defects.
- Do not approve your own implementation.
- Do not push commits or create a PR without explicit authorization.