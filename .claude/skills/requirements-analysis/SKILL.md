---
name: requirements-analysis
description: Analyse, refine, validate, and trace software requirements for the Personal Expense Tracker. Use during Stage 1 and when explicitly reviewing approved requirement changes.
---

# Requirements Analysis Skill

## Purpose

Provide a consistent, evidence-based process for analysing and documenting requirements for the Personal Expense Tracker.

This skill supports the Requirements Analyst agent and the Orchestrator.

It defines the procedure for requirements work; it does not grant approval authority.

## 1. Inputs

Read the relevant project context before starting:

- `CLAUDE.md`
- Existing `docs/requirements.md`, if present.
- Existing `docs/changelog.md`, if present.
- User-provided clarifications and decisions.
- Existing project constraints.

Treat explicit user decisions as authoritative.

Do not invent requirements to fill gaps.

## 2. Analysis Procedure

### Step 1: Establish scope

Identify:

- Project objective.
- Intended user.
- Application boundaries.
- Included functionality.
- Explicit exclusions.
- Technical constraints.
- Assumptions requiring confirmation.

Separate confirmed facts from assumptions.

### Step 2: Identify functional requirements

Write each functional requirement so that it describes one coherent capability.

Each requirement must have:

- Unique identifier, such as `FR-001`.
- Descriptive title.
- Clear requirement statement.
- Relevant business rules.
- Preconditions, where applicable.
- Expected outcomes.
- Validation and error behaviour.

Avoid vague terms such as "user-friendly", "fast", or "appropriate" unless they are made measurable.

### Step 3: Identify non-functional requirements

Consider:

- Technology and runtime constraints.
- Reliability and correctness.
- Data handling.
- Monetary precision.
- Date handling.
- Maintainability.
- Testability.
- Performance, where relevant.
- Security and privacy boundaries.

Only include requirements relevant to the approved project scope.

### Step 4: Define business rules

Record domain rules explicitly.

For monetary values, consider:

- Numeric representation.
- Valid amount ranges.
- Precision and rounding.
- Comparison behaviour.
- Aggregation behaviour.

For dates, consider:

- Accepted format.
- Default behaviour.
- Invalid dates.
- Date ranges.
- Boundary inclusivity.

For transaction operations, consider:

- Identifier generation.
- Uniqueness.
- Editing.
- Deletion.
- Type changes.
- Category compatibility.

### Step 5: Define acceptance criteria

Give each functional requirement one or more uniquely identified acceptance criteria.

Use identifiers such as `AC-001`.

Acceptance criteria must be:

- Observable.
- Testable.
- Specific.
- Unambiguous.
- Consistent with the requirement.

Include normal, boundary, and invalid-input scenarios where applicable.

### Step 6: Build traceability

Maintain a traceability relationship between:

- Functional requirements.
- Acceptance criteria.
- Business rules.
- Later architecture components.
- Implementation tasks.
- Test cases.

Do not claim implementation or test coverage before evidence exists.

## 3. Ambiguity and Conflict Handling

When information is incomplete:

1. Identify the ambiguity.
2. Explain why it matters.
3. Present a small number of reasonable interpretations if useful.
4. Ask the user to decide.
5. Record the decision.

When requirements conflict:

1. Identify both conflicting statements.
2. Explain the impact.
3. Do not silently choose one.
4. Request human clarification.
5. Record the resolution.

Do not proceed past unresolved critical ambiguities.

## 4. Project-Specific Constraints

The approved project uses:

- Java 21.
- Maven.
- TestNG 7.11.0 exclusively.
- Lombok 1.18.46.
- Console-based interaction using Scanner and System.out.
- In-memory storage only.
- BigDecimal for monetary values.
- LocalDate for dates.

Do not introduce databases, persistence, Spring Boot, APIs, web interfaces, GUI frameworks, cloud services, or external runtime services.

Do not change the approved scope without explicit user authorization.

## 5. Requirements Quality Checklist

Before recommending approval, verify:

- [ ] Every requirement has a unique identifier.
- [ ] Functional requirements are specific and testable.
- [ ] Non-functional requirements are measurable where practical.
- [ ] Business rules are explicitly documented.
- [ ] Acceptance criteria cover relevant scenarios.
- [ ] Boundary and invalid-input behaviour is addressed.
- [ ] Scope exclusions are explicit.
- [ ] Technical constraints are consistent.
- [ ] No unresolved critical ambiguity remains.
- [ ] Traceability is established.
- [ ] User decisions are recorded.
- [ ] Requirements do not contradict one another.

## 6. Deliverables

The primary deliverable is:

`docs/requirements.md`

It should contain:

1. Project overview.
2. Scope and exclusions.
3. Functional requirements.
4. Non-functional requirements.
5. Business rules.
6. Data and validation rules.
7. Acceptance criteria.
8. Traceability matrix.
9. Assumptions and decisions.
10. Open questions, if any.

Record significant decisions and approval events in:

`docs/changelog.md`

Preserve existing historical entries.

## 7. Approval Boundary

The Requirements Analyst may recommend that requirements are ready for approval.

Only the human user can approve the requirements baseline.

After approval:

- Treat the requirements as the authoritative Stage 1 baseline.
- Do not silently modify approved requirements.
- Record proposed changes and obtain authorization.
- Notify the Orchestrator that Stage 1 is ready for the next explicitly authorized stage.

## 8. Prohibited Actions

- Do not design the application architecture as part of requirements approval.
- Do not create application source code.
- Do not invent user decisions.
- Do not mark requirements approved on the user's behalf.
- Do not remove existing audit history.
- Do not silently relax project constraints.
- Do not claim traceability to implementation or tests before evidence exists.