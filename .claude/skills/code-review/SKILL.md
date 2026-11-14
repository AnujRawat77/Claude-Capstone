---
name: code-review
description: Independently inspect implementation changes for correctness, maintainability, security, requirement compliance, and architectural consistency during Stage 6.
---

# Code Review Skill

## Purpose

Perform an independent, evidence-based review of the Personal Expense Tracker implementation.

This skill supports the Code Reviewer agent during Stage 6.

The reviewer evaluates the actual implementation and identifies defects before final verification and PR preparation.

The review must be independent of the Java Developer's implementation claims.

## 1. Mandatory Preconditions

Before beginning Stage 6, verify:

- Stage 1 Requirements Analysis is approved.
- Stage 2 Architecture Design is approved.
- Stage 3 Design Review is approved.
- Stage 4 Implementation Planning is approved.
- Stage 5 Implementation is completed.
- The Orchestrator explicitly authorizes Stage 6.

If any prerequisite is missing, report the blocker.

## 2. Authoritative Inputs

Read:

- `CLAUDE.md`
- `docs/requirements.md`
- `docs/architecture.md`
- `docs/design-review.md`
- `docs/implementation-plan.md`
- Relevant source code.
- Existing tests.
- `pom.xml`
- `docs/changelog.md`

Inspect the actual Git diff and relevant source files.

Do not rely solely on implementation summaries.

## 3. Independence Requirements

The Code Reviewer must:

- Review implementation produced by another agent.
- Avoid reviewing its own modifications.
- Inspect actual code rather than accepting claims.
- Challenge questionable design decisions.
- Verify requirement compliance.
- Identify correctness and maintainability risks.
- Provide actionable findings.

During the initial review, do not modify application source code.

## 4. Review Procedure

### Step 1: Inspect the change set

Use appropriate Git commands to understand:

- Changed files.
- Added files.
- Deleted files.
- Modified files.
- Untracked files.
- Relevant diffs.

Check for unrelated modifications and unexpected generated files.

Never discard or overwrite user changes.

### Step 2: Verify requirements compliance

Compare the implementation against approved functional requirements and acceptance criteria.

Verify actual behaviour for:

- Expense creation.
- Income creation.
- Transaction listing and filtering.
- Transaction editing.
- Transaction deletion.
- Category summaries.
- Financial summaries.
- Monthly budgets.
- Budget alerts.
- Console navigation and validation.

Identify missing, partial, or incorrect behaviour.

### Step 3: Verify monetary correctness

Inspect all monetary calculations.

Verify:

- `BigDecimal` is used consistently.
- No `double` or `float` is used for money.
- Comparisons use appropriate BigDecimal comparison methods.
- Arithmetic does not lose precision.
- Equality and scale behaviour are handled correctly.
- Aggregations are accurate.

### Step 4: Verify date correctness

Inspect:

- `LocalDate` usage.
- Date parsing.
- Invalid date handling.
- Inclusive date-range filtering.
- Same-day ranges.
- Start-date and end-date validation.
- Default date behaviour.

### Step 5: Verify transaction integrity

Check:

- IDs are generated automatically.
- IDs are unique.
- Deleted IDs are not reused.
- Expenses and income share the required identifier sequence.
- Editing preserves transaction identity.
- Type changes enforce the correct category.
- Deletion does not corrupt other records.

### Step 6: Verify budget behaviour

Check:

- Budget association with category, year, and month.
- Budget overwrite behaviour.
- Positive budget validation.
- Expense aggregation for the correct month.
- Threshold comparison at 100%.
- Alerts are non-blocking.

### Step 7: Inspect architecture and code quality

Evaluate:

- Separation of concerns.
- Class responsibilities.
- Encapsulation.
- Cohesion.
- Coupling.
- Error handling.
- Naming and readability.
- Duplication.
- Unnecessary complexity.
- Dependency management.

Check that business logic is not improperly placed in console presentation classes.

### Step 8: Inspect test quality

Review the tests for:

- Meaningful assertions.
- Correct expected results.
- Positive scenarios.
- Negative scenarios.
- Boundary scenarios.
- Regression coverage.
- Appropriate isolation.
- TestNG 7.11.0 compliance.

A test that executes successfully is not necessarily a correct test.

### Step 9: Inspect technology and scope compliance

Verify:

- Java 21.
- Maven.
- TestNG 7.11.0 exclusively.
- Lombok 1.18.46.
- Approved console interaction.
- In-memory-only storage.
- No prohibited dependencies or technologies.

### Step 10: Inspect security and reliability

Look for:

- Exposed secrets or credentials.
- Unsafe command execution.
- Unexpected file or network access.
- Broad exception swallowing.
- Null-related defects.
- State corruption.
- Unhandled user input.
- Unintended destructive operations.

Do not claim a formal security audit unless one was actually performed.

## 5. Finding Severity

Classify each finding:

- **CRITICAL** — Severe correctness, integrity, or safety issue that blocks acceptance.
- **HIGH** — Major functional defect or serious requirement violation.
- **MEDIUM** — Meaningful defect, maintainability concern, or incomplete behaviour.
- **LOW** — Minor improvement or limited concern.
- **INFO** — Observation or non-blocking recommendation.

Every finding must include:

- Unique identifier, such as `CR-001`.
- Severity.
- Affected file and relevant location.
- Description.
- Evidence.
- Requirement or architecture reference.
- Potential impact.
- Recommended correction.

Avoid speculative findings without supporting evidence.

## 6. Review Outcome

Choose exactly one recommendation:

- **APPROVE** — No blocking findings remain.
- **APPROVE WITH OBSERVATIONS** — No blocking findings remain; non-blocking observations are documented.
- **CHANGES REQUESTED** — One or more defects require correction.
- **REJECT** — Fundamental implementation problems require substantial rework.

A review recommendation is not human approval.

## 7. Deliverable

Create:

`docs/code-review.md`

Include:

1. Review objective and scope.
2. Documents and files inspected.
3. Git change-set summary.
4. Requirements compliance assessment.
5. Architecture compliance assessment.
6. Monetary and date correctness.
7. Transaction integrity.
8. Budget behaviour.
9. Code quality.
10. Test quality.
11. Security and reliability observations.
12. Findings with severity and evidence.
13. Review recommendation.
14. Required corrections.

Record significant review activity in `docs/changelog.md`.

Preserve previous audit history.

## 8. Re-review Procedure

After corrections:

1. Inspect the updated diff.
2. Verify each previous finding.
3. Confirm that corrections address the root cause.
4. Inspect for regressions.
5. Record finding status.
6. Add new findings where necessary.
7. Update the review recommendation.

Do not mark a finding resolved without evidence.

Do not conceal unresolved findings.

## 9. Approval Boundary

The Code Reviewer provides an independent recommendation.

The human user retains approval authority for Stage 6.

The Java Developer must address requested changes through the Orchestrator.

Stage 7 verification must follow the required independent review and approval gate.

## 10. Prohibited Actions

- Do not implement application features.
- Do not modify application code during initial review.
- Do not review your own implementation.
- Do not change approved requirements.
- Do not fabricate evidence.
- Do not ignore unresolved findings.
- Do not approve your own review.
- Do not bypass human approval.
- Do not recommend PR preparation while blocking defects remain unresolved.