# Claude Code Instructions

## Project constraints
Personal Expense Tracker; Java 21, Maven, TestNG 7.11.0, Lombok 1.18.46, console UI, in-memory storage, IntelliJ IDEA, Git/GitHub. Use BigDecimal for money. No database, Spring Boot, REST API, web UI, cloud service, or external runtime service.

## Primary objective
Demonstrate an evidence-based eight-stage Agentic SDLC:
1. Requirements (`docs/requirements.md`)
2. Architecture (`docs/architecture.md`)
3. Design review (`docs/design-review.md`)
4. Implementation planning (`docs/impl-plan.md`)
5. Implementation
6. Independent code review (`docs/code-review.md`)
7. Verification (`docs/test-report.md`)
8. PR preparation (`docs/pr-description.md`, changelogs, reviewer checklist)

## Rules
- Inspect the repository before changing it.
- Use specialist agents and skills under `.claude/`; report delegation and outputs.
- Maintain traceability from requirements to design, tasks, code and tests.
- Do not implement application code until requirements, architecture, design review and implementation plan have human approval.
- Never self-approve a human approval gate.
- Do not claim a command succeeded without verifiable output.
- Do not silently change constraints or invent stakeholder decisions.
- Never expose secrets or tokens.
- Keep changes small and reviewable.

## PR policy — both modes
A. Automated: after explicit human approval, validate readiness and create the PR with GitHub CLI (`gh`).
B. Semi-automated: prepare PR materials and provide the command/content for the human to create the PR manually.
Neither mode may push or create a PR without explicit human approval. If `gh` is unavailable or unauthenticated, use semi-automated mode. Do not implement a custom token-based GitHub API client.

## Changelogs
- Root `CHANGELOG.md`: application/release changes.
- `docs/changelog.md`: SDLC stages, agent activity, outputs, evidence and approvals.

## Initial boundary
This scaffold contains no application implementation. First inspect it, explain the planned workflow and begin requirements discovery. Wait for human approval at every defined gate.
