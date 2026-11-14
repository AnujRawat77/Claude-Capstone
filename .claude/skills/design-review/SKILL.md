---
name: design-review
description: Independently evaluate the proposed architecture for correctness, completeness, consistency, maintainability, testability, and compliance with approved requirements during Stage 3.
---

# Design Review Skill

## Purpose

Provide an independent, evidence-based review of the proposed software architecture.

This skill supports the Design Reviewer agent during Stage 3.

The objective is to identify architectural defects, omissions, risks, and requirement mismatches before implementation planning begins.

The reviewer must not simply endorse the Architect's recommendations.

## 1. Inputs

Read:

- `CLAUDE.md`
- `docs/requirements.md`
- `docs/architecture.md`
- `docs/changelog.md`
- Relevant user-approved decisions.

Inspect the actual architecture document.

Do not rely exclusively on the Architect's summary.

The requirements document is the authoritative baseline.

## 2. Independence Requirements

The reviewer must:

- Evaluate the architecture independently.
- Compare design decisions against approved requirements.
- Challenge unsupported assumptions.
- Identify missing responsibilities and interactions.
- Examine failure and boundary scenarios.
- Avoid implementing or redesigning the system during the initial review.

The reviewer must not approve its own work or treat the Architect's recommendation as approval.

## 3. Review Procedure

### Step 1: Requirements compliance

Check that every functional and non-functional requirement is addressed.

Verify that the architecture does not contradict:

- Approved functionality.
- Business rules.
- Acceptance criteria.
- Technology constraints.
- Scope exclusions.

Identify requirements with missing or incomplete architectural support.

### Step 2: Component responsibilities

Evaluate whether:

- Components have clear responsibilities.
- Responsibilities are not unnecessarily duplicated.
- Business logic is separated from presentation.
- Dependencies are justified.
- Circular dependencies are avoided.
- Components are appropriately cohesive.

### Step 3: Domain model correctness

Inspect:

- Domain entities and value types.
- Field types.
- Relationships.
- Identifier generation.
- Validation rules.
- Domain invariants.

Pay particular attention to:

- BigDecimal monetary calculations.
- LocalDate date handling.
- Expense and income category compatibility.
- Transaction type changes.
- Unique transaction identifiers.
- Monthly budget associations.

### Step 4: Behaviour and interaction coverage

Evaluate the architecture's support for:

- Adding expenses and income.
- Listing and filtering transactions.
- Editing transactions.
- Deleting transactions.
- Category summaries.
- Income, expense, and net balance summaries.
- Monthly budgets.
- Budget threshold alerts.
- Console navigation and validation.

Check both successful and failure flows.

### Step 5: Data handling

Verify:

- Storage is in memory only.
- No database or persistence mechanism is introduced.
- Data structures support required operations.
- Transaction identifiers are not reused.
- Budget keys and monthly associations are unambiguous.
- Aggregations preserve monetary precision.

### Step 6: Error handling

Evaluate how the design handles:

- Invalid input.
- Missing records.
- Invalid dates.
- Invalid amounts.
- Invalid category selection.
- Invalid date ranges.
- Budget validation failures.

Check that recoverable user errors do not unnecessarily terminate the application.

### Step 7: Testability

Evaluate whether:

- Business services can be tested independently.
- Domain logic has clear boundaries.
- Console input can be tested without excessive complexity.
- Dependencies are manageable.
- TestNG 7.11.0 can cover the required behaviour.

Do not claim that tests have been executed.

### Step 8: Technology and scope compliance

Verify the design uses the approved stack:

- Java 21.
- Maven.
- TestNG 7.11.0 exclusively.
- Lombok 1.18.46.
- Scanner and System.out.
- BigDecimal.
- LocalDate.

Flag prohibited or unapproved additions.

### Step 9: Record findings

Every finding must contain:

- Unique identifier, such as `DR-001`.
- Severity.
- Affected architecture section.
- Description of the issue.
- Requirement or design concern involved.
- Practical impact.
- Recommended corrective action.

## 4. Finding Severity

Use the following severity levels:

- **CRITICAL** — Fundamental design defect or major violation that prevents safe progression.
- **HIGH** — Significant requirement gap, correctness risk, or architectural weakness.
- **MEDIUM** — Meaningful maintainability, testability, or completeness concern.
- **LOW** — Minor improvement or documentation issue.
- **INFO** — Observation that does not require correction.

Do not inflate severity to make the review appear more rigorous.

## 5. Review Outcome

Choose exactly one recommendation:

- **PASS** — No blocking findings; architecture is suitable for the next stage.
- **PASS WITH OBSERVATIONS** — No blocking findings; non-blocking findings are documented.
- **REVISE AND RESUBMIT** — One or more material issues require correction.
- **REJECT** — Fundamental architectural problems require substantial reconsideration.

A recommendation is not human approval.

## 6. Deliverable

Create:

`docs/design-review.md`

Include:

1. Review objective and scope.
2. Documents reviewed.
3. Review approach.
4. Requirements compliance assessment.
5. Component and responsibility assessment.
6. Domain model assessment.
7. Behaviour and interaction assessment.
8. Data handling assessment.
9. Error-handling assessment.
10. Testability assessment.
11. Technology and scope compliance.
12. Findings with severity and identifiers.
13. Review recommendation.
14. Conditions for resubmission, if applicable.

Record significant review activity in `docs/changelog.md`.

Preserve previous review history and approval records.

## 7. Re-review Procedure

When the Architect revises the design:

1. Inspect the revised architecture.
2. Compare it with the previous reviewed version.
3. Verify each finding's resolution.
4. Identify regressions introduced by the changes.
5. Update finding statuses.
6. Record new findings where necessary.
7. Issue a new review recommendation.

Do not mark a finding resolved without supporting evidence.

Do not silently remove unresolved findings.

## 8. Approval Boundary

Only the human user can approve the design-review gate.

The Design Reviewer provides an independent recommendation.

Implementation planning must not begin until the user explicitly approves the design review and the required architecture baseline.

If the outcome is `REVISE AND RESUBMIT` or `REJECT`, report the blocker and stop.

## 9. Prohibited Actions

- Do not implement application code.
- Do not modify the architecture during the initial review.
- Do not change approved requirements.
- Do not approve your own review.
- Do not fabricate evidence.
- Do not conceal findings.
- Do not bypass human approval.
- Do not proceed to implementation planning without authorization.