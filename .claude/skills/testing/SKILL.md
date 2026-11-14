---
name: testing
description: Design, execute, and report traceable automated tests for the Personal Expense Tracker using TestNG 7.11.0 exclusively during Stage 7.
---

# Testing Skill

## Purpose

Provide a systematic verification process for the Personal Expense Tracker.

This skill supports the Test Engineer agent during Stage 7.

The objective is to establish, using actual evidence, whether the implemented application satisfies its approved requirements and acceptance criteria.

## 1. Mandatory Preconditions

Before beginning Stage 7, verify:

- Stage 1 Requirements Analysis is approved.
- Stage 2 Architecture Design is approved.
- Stage 3 Design Review is approved.
- Stage 4 Implementation Planning is approved.
- Stage 5 Implementation is completed and accepted.
- Stage 6 Independent Code Review is completed.
- The Orchestrator explicitly authorizes Stage 7.

If any prerequisite is missing, report the blocker.

Do not treat a successful compilation as proof that all requirements are satisfied.

## 2. Authoritative Inputs

Read:

- `CLAUDE.md`
- `docs/requirements.md`
- `docs/architecture.md`
- `docs/implementation-plan.md`
- `docs/code-review.md`
- Existing source code.
- Existing test code.
- `pom.xml`
- `docs/changelog.md`

Inspect actual implementation and tests.

Do not rely exclusively on summaries from the Java Developer.

## 3. Testing Framework Constraints

Use:

- TestNG 7.11.0 exclusively.
- Maven.
- Java 21.

Run the complete test suite using:

`mvn test`

Do not introduce JUnit or another testing framework.

Do not silently change dependency versions or test configuration.

## 4. Test Design Procedure

### Step 1: Build the test traceability matrix

Map:

`Requirement → Acceptance Criterion → Test Case → Execution Result`

Use stable identifiers such as:

- `TC-001`
- `TC-002`

Every applicable acceptance criterion must have corresponding verification coverage.

Identify untested criteria explicitly.

### Step 2: Design positive tests

Verify expected behaviour for valid inputs and normal workflows.

Include relevant scenarios for:

- Adding expenses.
- Adding income.
- Listing transactions.
- Filtering transactions.
- Editing transactions.
- Deleting transactions.
- Category summaries.
- Income and expense totals.
- Net balance.
- Monthly budgets.
- Budget alerts.
- Console navigation.

### Step 3: Design negative tests

Verify that invalid operations are handled correctly.

Consider:

- Zero or negative amounts.
- Invalid date formats.
- Invalid category values.
- Missing transaction IDs.
- Invalid menu selections.
- Invalid date ranges.
- Incorrect category for transaction type.
- Invalid budget amounts.
- Empty or malformed input.

Verify that recoverable errors do not corrupt existing application state.

### Step 4: Design boundary tests

Include important boundary conditions:

- Budget spending just below 100%.
- Budget spending exactly at 100%.
- Budget spending above 100%.
- Start and end dates included in filtering.
- Same-day date ranges.
- Empty transaction collections.
- Editing the only transaction.
- Deleting a transaction and adding another.
- Transaction type changes.
- Large monetary values and decimal precision.

Use the exact approved requirements when determining expected results.

### Step 5: Verify business rules

Pay particular attention to:

- BigDecimal monetary calculations.
- LocalDate date handling.
- Unique transaction identifiers.
- No reuse of deleted identifiers.
- Shared identifier sequence across income and expenses.
- Category compatibility.
- Transaction type changes.
- Inclusive date filtering.
- Net balance calculation.
- Monthly category budget calculations.
- Non-blocking budget alerts.

### Step 6: Verify non-functional constraints

Check:

- Java 21 compatibility.
- Maven build configuration.
- TestNG 7.11.0 usage.
- Lombok 1.18.46 configuration.
- Absence of prohibited dependencies.
- Absence of database or file persistence.
- Absence of network or external runtime service usage.
- Compilation warnings, where the build provides reliable evidence.

Do not claim that a property is verified without checking it.

## 5. Test Execution Procedure

For each test execution:

1. Identify the test scope.
2. Execute the relevant command.
3. Capture the actual result.
4. Record passed tests.
5. Record failed tests.
6. Record skipped tests.
7. Inspect failures and their causes.
8. Re-run affected tests after corrections.
9. Run the complete suite when appropriate.

Use actual Maven and TestNG output.

Do not fabricate execution counts or success percentages.

Do not change tests merely to make an incorrect implementation pass.

If a test expectation conflicts with the approved requirements, raise the conflict for review.

## 6. Defect Handling

For each confirmed defect, document:

- Unique identifier, such as `BUG-001`.
- Severity.
- Affected requirement.
- Reproduction steps.
- Expected behaviour.
- Actual behaviour.
- Relevant test case.
- Available evidence.

Coordinate implementation corrections with the Java Developer through the Orchestrator.

After a correction:

1. Re-run the failing test.
2. Run relevant regression tests.
3. Record the actual result.
4. Verify that no unrelated behaviour regressed.

Do not silently modify production code as part of independent testing.

## 7. Test Quality Checklist

Before completing verification, check:

- [ ] TestNG 7.11.0 is used exclusively.
- [ ] All applicable acceptance criteria have test coverage.
- [ ] Positive scenarios are covered.
- [ ] Negative scenarios are covered.
- [ ] Important boundary conditions are covered.
- [ ] Monetary precision is tested.
- [ ] Date handling is tested.
- [ ] Transaction editing and deletion are tested.
- [ ] Transaction identifier behaviour is tested.
- [ ] Budget calculations and alerts are tested.
- [ ] Regression tests are included.
- [ ] Actual Maven execution results are recorded.
- [ ] Failed and skipped tests are disclosed.
- [ ] Remaining defects are documented.
- [ ] Test traceability is maintained.

## 8. Deliverables

Create or update:

- `docs/test-plan.md`
- `docs/test-report.md`

The test report must include:

1. Testing objective and scope.
2. Environment and versions.
3. Test framework and configuration.
4. Test traceability matrix.
5. Test execution commands.
6. Actual execution results.
7. Passed, failed, and skipped counts.
8. Defects identified.
9. Regression testing results.
10. Requirement coverage assessment.
11. Known limitations.
12. Final verification recommendation.

Record significant testing activity in `docs/changelog.md`.

Preserve previous audit history.

## 9. Verification Recommendation

Choose one outcome:

- **PASS** — Required verification completed successfully.
- **PASS WITH OBSERVATIONS** — Verification passed with documented non-blocking limitations.
- **FAIL** — One or more required tests failed.
- **BLOCKED** — Required testing could not be completed because of missing prerequisites or unavailable evidence.

A recommendation is not human approval.

## 10. Approval Boundary

The Test Engineer reports actual verification evidence and recommends an outcome.

The human user retains final approval authority for Stage 7.

If required verification fails, report the failure and coordinate corrections.

Do not recommend progression to PR preparation while blocking failures remain unresolved.

## 11. Prohibited Actions

- Do not use JUnit or another testing framework.
- Do not fabricate test results.
- Do not change approved requirements.
- Do not conceal failures or skipped tests.
- Do not weaken assertions to hide defects.
- Do not modify application code during independent verification without explicit assignment and authorization.
- Do not claim complete coverage without a traceability assessment.
- Do not mark Stage 7 approved on the user's behalf.
- Do not bypass human approval gates.