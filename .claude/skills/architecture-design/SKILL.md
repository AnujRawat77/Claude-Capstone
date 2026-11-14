---
name: architecture-design
description: Define and document the software architecture, component responsibilities, data model, interactions, and technical decisions for the Personal Expense Tracker during Stage 2.
---

# Architecture Design Skill

## Purpose

Define a maintainable, testable architecture that satisfies the approved requirements baseline.

This skill guides the Architect agent during Stage 2.

It does not authorize implementation or architecture approval.

## 1. Inputs

Read:

- `CLAUDE.md`
- `docs/requirements.md`
- `docs/changelog.md`
- Relevant existing project files.
- Approved user decisions and constraints.

Treat `docs/requirements.md` as the authoritative requirements baseline.

Do not change requirements to make the architecture easier to implement.

## 2. Architecture Design Procedure

### Step 1: Confirm requirements coverage

Identify the major capabilities and constraints that the architecture must support.

Map each functional requirement to one or more proposed components.

Identify any requirements that cannot be mapped clearly and raise them for clarification.

### Step 2: Define architectural style

Choose a simple architecture appropriate for a small, single-user, console-based Java application.

Prefer clear separation of responsibilities over unnecessary architectural complexity.

Explain why the selected architecture is suitable.

Avoid introducing frameworks or infrastructure that are outside the approved scope.

### Step 3: Identify components and responsibilities

Define the major application components.

For each component, document:

- Name.
- Responsibility.
- Important operations.
- Inputs and outputs.
- Dependencies.
- Relevant requirements.

Keep responsibilities cohesive.

Avoid circular dependencies and unnecessary coupling.

### Step 4: Design the domain model

Define the principal domain concepts and their relationships.

Consider:

- Transaction.
- Expense.
- Income.
- ExpenseCategory.
- IncomeCategory.
- MonthlyBudget.

Specify:

- Fields.
- Types.
- Identifier behaviour.
- Validation responsibilities.
- Relationships.
- Important invariants.

Use `BigDecimal` for all monetary values and `LocalDate` for dates.

Ensure the model supports the approved transaction editing and category rules.

### Step 5: Design service responsibilities

Define the services needed to implement the requirements.

For each service, describe:

- Its purpose.
- Public operations.
- Input validation.
- Business rules enforced.
- Collaborating components.
- Expected errors or outcomes.

Keep business logic out of console presentation classes.

Avoid creating services without a clear requirement-driven responsibility.

### Step 6: Design console interaction

Describe:

- Application entry point.
- Menu navigation.
- Input collection.
- Input validation.
- User feedback.
- Error handling.
- Exit behaviour.

Use `Scanner` and `System.out` as specified by the requirements.

Keep console interaction separate from domain and business logic.

### Step 7: Design in-memory storage

Specify how transactions and budgets are stored during application execution.

Respect the in-memory-only requirement.

Define how identifiers are generated and how transaction lookup, editing, deletion, filtering, and aggregation work.

Do not introduce file persistence, databases, or external services.

### Step 8: Define dependency and interaction flow

Describe how components collaborate for important use cases.

Include flows for:

- Adding a transaction.
- Editing a transaction.
- Deleting a transaction.
- Listing and filtering transactions.
- Calculating summaries.
- Setting monthly budgets.
- Checking budget alerts.

Use diagrams or concise text flows where they improve clarity.

### Step 9: Define error-handling strategy

Document how the application handles:

- Invalid console input.
- Invalid amounts.
- Invalid dates.
- Missing transaction IDs.
- Invalid categories.
- Invalid date ranges.
- Budget validation failures.

Distinguish user input errors from programming or system errors.

### Step 10: Define testing considerations

Explain how the architecture supports isolated unit testing.

Identify important test boundaries and dependencies.

Use TestNG 7.11.0 exclusively, as required.

Do not claim tests have been implemented or executed during architecture design.

## 3. Project Technology Constraints

The architecture must use:

- Java 21.
- Maven.
- TestNG 7.11.0 exclusively.
- Lombok 1.18.46 for model classes.
- BigDecimal for money.
- LocalDate for dates.
- Scanner and System.out for console interaction.
- In-memory data structures.

Do not introduce:

- Spring Boot.
- Databases.
- File-based persistence.
- REST APIs.
- Web interfaces.
- GUI frameworks.
- Cloud services.
- External runtime dependencies outside the approved scope.

## 4. Architecture Quality Checklist

Before submitting the design, verify:

- [ ] Every functional requirement has an architectural mapping.
- [ ] Component responsibilities are clearly separated.
- [ ] Domain types and relationships are defined.
- [ ] Monetary precision is preserved.
- [ ] Date behaviour matches the requirements.
- [ ] Transaction identifiers remain unique.
- [ ] Transaction type changes are supported.
- [ ] Budget calculations and alerts are supported.
- [ ] Console interaction is separated from business logic.
- [ ] Storage remains in memory.
- [ ] Dependencies are understandable and non-circular.
- [ ] Error handling is defined.
- [ ] Unit testing is feasible.
- [ ] No prohibited technology has been introduced.
- [ ] Architectural decisions have clear justifications.

## 5. Architecture Decision Records

Document important decisions in the architecture document.

For each significant decision, include:

- Decision identifier.
- Context.
- Decision.
- Reasoning.
- Consequences.
- Relevant requirements.

Record unresolved alternatives where human input is required.

Do not silently make scope-changing decisions.

## 6. Deliverables

The primary deliverable is:

`docs/architecture.md`

It should contain:

1. Architecture overview.
2. Architectural style and justification.
3. Component responsibilities.
4. Domain model.
5. Service design.
6. Console interaction design.
7. In-memory storage design.
8. Component interactions.
9. Error-handling strategy.
10. Technology decisions.
11. Requirement-to-component mapping.
12. Testing considerations.
13. Architectural risks and open questions.

Record significant decisions in `docs/changelog.md`.

Preserve existing history.

## 7. Approval Boundary

The Architect may recommend the architecture for review.

The Architect cannot approve its own design.

Stage 2 requires explicit human approval.

Stage 3 Design Review must independently evaluate the architecture before implementation planning can proceed.

Do not create application source code during architecture design.

## 8. Prohibited Actions

- Do not modify the approved requirements baseline.
- Do not implement application code.
- Do not introduce unapproved technologies.
- Do not add unnecessary abstractions or frameworks.
- Do not claim implementation or test evidence.
- Do not mark the architecture approved.
- Do not bypass independent design review.
- Do not remove or rewrite historical approval records.