---
name: implementation-planning
description: Convert approved requirements and architecture into an ordered, traceable, testable implementation plan during Stage 4, without writing application code.
---

# Implementation Planning Skill

## Purpose

Translate the approved requirements and architecture into a practical implementation roadmap.

This skill supports the Implementation Planner agent during Stage 4.

The plan must be sufficiently detailed for implementation agents to work in small, controlled increments without making unapproved design decisions.

This skill does not authorize implementation.

## 1. Inputs

Read:

- `CLAUDE.md`
- `docs/requirements.md`
- `docs/architecture.md`
- `docs/design-review.md`
- `docs/changelog.md`
- Relevant existing project files.

Verify that:

- Stage 1 requirements are approved.
- Stage 2 architecture is approved.
- Stage 3 design review is approved.
- Blocking review findings are resolved or explicitly accepted by the user.

Do not proceed if required approvals are missing.

## 2. Planning Procedure

### Step 1: Establish the implementation baseline

Identify the exact approved versions of:

- Requirements.
- Architecture.
- Design review.
- Project technology constraints.

Record any accepted observations or limitations.

Do not silently change the approved baseline.

### Step 2: Decompose the work

Break the implementation into small, logically ordered tasks.

Each task must have:

- Unique identifier, such as `TASK-001`.
- Title.
- Objective.
- Description.
- Relevant requirement IDs.
- Relevant acceptance criteria.
- Architecture components affected.
- Dependencies.
- Expected files or modules.
- Completion conditions.

Avoid oversized tasks that combine unrelated responsibilities.

### Step 3: Define implementation order

Choose an order that respects dependencies.

A typical sequence may include:

1. Maven and Java project configuration.
2. Domain models and enums.
3. In-memory repositories or storage components.
4. Core business services.
5. Validation and error handling.
6. Console interaction.
7. Application entry point.
8. Integration and regression testing.

Adapt the sequence to the approved architecture rather than treating this example as mandatory.

### Step 4: Define test tasks

Map implementation tasks to verification work.

For each relevant task, identify:

- Expected unit tests.
- Boundary conditions.
- Invalid-input scenarios.
- Relevant acceptance criteria.
- Regression risks.

Use TestNG 7.11.0 exclusively.

Do not introduce another test framework.

### Step 5: Define integration checkpoints

Identify where separately implemented components must be integrated.

For each checkpoint, define:

- Components involved.
- Expected interactions.
- Preconditions.
- Verification steps.
- Acceptance conditions.

### Step 6: Identify risks and dependencies

Document:

- Technical dependencies.
- Potential design misunderstandings.
- High-risk business rules.
- Areas requiring careful monetary precision.
- Transaction type-change risks.
- Budget calculation risks.
- Input validation risks.
- Integration risks.

Provide practical mitigation actions.

### Step 7: Define traceability

Maintain mappings across:

`Requirement → Acceptance Criterion → Architecture Component → Implementation Task → Test Case`

Use stable identifiers.

Do not invent test execution evidence or mark tests as passed before execution.

### Step 8: Define task ownership

Identify which specialist agent is responsible for each task.

Typical responsibilities:

- Java Developer: application implementation.
- Test Engineer: test design and execution.
- Code Reviewer: independent code review.

The Orchestrator coordinates assignments and human approval gates.

Do not assign an agent responsibilities outside its defined role.

## 3. Task Quality Rules

Every implementation task must be:

- Small enough to review.
- Clearly scoped.
- Traceable.
- Testable.
- Consistent with the architecture.
- Free of unresolved critical dependencies.

A task must not require an implementation agent to invent new business rules.

If a missing decision is discovered, raise it to the Orchestrator.

## 4. Project Constraints

Preserve the approved stack:

- Java 21.
- Maven.
- TestNG 7.11.0 exclusively.
- Lombok 1.18.46.
- BigDecimal for monetary values.
- LocalDate for dates.
- Scanner and System.out for console interaction.
- In-memory storage only.

Do not introduce databases, file persistence, Spring Boot, APIs, web interfaces, GUI frameworks, cloud services, or unapproved runtime dependencies.

## 5. Planning Quality Checklist

Before submitting the plan, verify:

- [ ] All tasks derive from approved requirements and architecture.
- [ ] Every functional requirement has implementation coverage.
- [ ] Tasks have unique identifiers.
- [ ] Task dependencies are explicit.
- [ ] Implementation order is logical.
- [ ] Expected file/module changes are identified.
- [ ] Test tasks are included.
- [ ] Acceptance criteria are traceable.
- [ ] Integration checkpoints are defined.
- [ ] Risks and mitigations are documented.
- [ ] Agent ownership is clear.
- [ ] No task changes approved scope.
- [ ] No unresolved critical design issue remains.

## 6. Deliverables

Create:

`docs/implementation-plan.md`

Include:

1. Approved baseline references.
2. Implementation strategy.
3. Task breakdown.
4. Task dependencies and execution order.
5. Agent ownership.
6. Test and verification tasks.
7. Integration checkpoints.
8. Requirement traceability matrix.
9. Risks and mitigations.
10. Completion criteria.

Record significant planning decisions in `docs/changelog.md`.

Preserve existing audit history.

## 7. Approval Boundary

The Implementation Planner may recommend the plan for approval.

Only the human user can approve Stage 4.

No application source code may be created until the user explicitly approves the implementation plan.

After approval, the Orchestrator may coordinate implementation in controlled increments.

## 8. Prohibited Actions

- Do not create application source code.
- Do not execute implementation tasks.
- Do not change approved requirements.
- Do not redesign the architecture.
- Do not ignore unresolved review findings.
- Do not fabricate test evidence.
- Do not mark the plan approved.
- Do not authorize implementation without human approval.