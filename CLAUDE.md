# Claude Code Project Instructions

## 1. Project Identity

**Project:** Personal Expense Tracker
**Purpose:** Demonstrate an evidence-based, eight-stage Agentic Software Development Life Cycle (SDLC) using Claude Code.

## 2. Technology Constraints

- Java 21
- Apache Maven
- TestNG 7.11.0
- Lombok 1.18.46
- IntelliJ IDEA
- Git and GitHub
- Console-based interaction using Scanner and System.out
- In-memory storage only
- BigDecimal for all monetary calculations
- LocalDate for all transaction dates

Do not introduce:
- Spring Boot or other application frameworks
- Databases or persistent file storage
- REST APIs or HTTP services
- Web or GUI interfaces
- Cloud services or external runtime dependencies
- JUnit or another test framework

## 3. Primary Objective

Demonstrate genuine specialist-agent collaboration, controlled execution, human approvals, traceability and verifiable engineering outcomes.

The eight SDLC stages are:

1. Requirements Analysis
2. Architecture Design
3. Design Review
4. Implementation Planning
5. Application Implementation
6. Independent Code Review
7. Verification and Testing
8. Pull Request Preparation

## 4. Current Project Status

**Stage 1 — Requirements: APPROVED** (2026-11-11, clarified 2026-11-12)

Source of truth: `docs/requirements.md`

The approved requirements include:
- 10 functional requirements
- 9 non-functional requirements
- 37 acceptance criteria (28 FR + 9 NFR)
- Requirements-to-acceptance-criteria traceability
- Fixed expense and income category enums
- Monthly category budgets
- Non-blocking budget alerts at or above 100%
- Editable transaction types with category re-selection
- No clear-all or reset-session option

Do not silently modify approved requirements.

If a genuine inconsistency or missing requirement is discovered, document it and request stakeholder clarification.

**Stage 2 — Architecture: APPROVED** (2026-11-12)

Source of truth: `docs/architecture.md` Revision 2

**Stage 3 — Design Review: APPROVED** (2026-11-12, targeted re-review)

Source of truth: `docs/design-review.md` (Final Status Addendum)

**Stage 4 — Implementation Planning: APPROVED** (2026-11-12)

Source of truth: `docs/implementation-plan.md` (20 tasks, 6 milestones, full traceability)

**Stage 5 — Application Implementation: TASKS APPROVED** (2026-11-15)

Source of truth: `src/main/java/org/capstone/expense/`, `src/test/java/org/capstone/expense/`, `docs/implementation-plan.md`, and `docs/changelog.md` (IMP-001 through IMP-020 implemented and approved by the human stakeholder on 2026-11-15; 110 TestNG tests pass; independent Stage 6 review and Stage 7 verification remain pending)

**Next stage: Stage 6 — Independent Code Review.**

Stage 5 task completion is not approval of Stage 6 or Stage 7. Commits require explicit human approval per §9, §10.

## 5. Agent Collaboration

Specialist agents are defined under `.claude/agents/`.

Skills are defined under `.claude/skills/`.

The Orchestrator coordinates the SDLC and delegates specialist work using Claude Code's supported subagent mechanisms.

Use agents only when their specialist contribution is valuable. Avoid unnecessary delegation and repeated analysis.

Each specialist must:
- Read its required source documents.
- Work within its assigned responsibility.
- Produce the agreed deliverables.
- Report findings and evidence.
- Identify unresolved issues.
- Hand off results clearly.

An agent must not claim that another agent performed work unless delegation and its result can be verified.

## 6. Human Approval Gates

The stakeholder owns all stage approvals.

The following deliverables require explicit human approval before progression:

- Requirements
- Architecture
- Design Review
- Implementation Plan
- Implementation milestones where significant scope or design changes occur
- Independent Code Review findings
- Final Verification
- Pull Request creation

Never self-approve a stage.

Never interpret silence as approval.

Do not begin a subsequent stage when its mandatory approval is missing.

## 7. Traceability and Documentation

Maintain traceability across:

Requirements → Architecture → Design Review → Implementation Tasks → Source Code → Tests → Review Findings → PR.

Use these documents:

- `docs/requirements.md`
- `docs/architecture.md`
- `docs/design-review.md`
- `docs/implementation-plan.md`
- `docs/code-review.md`
- `docs/test-report.md`
- `docs/pr-description.md`
- `docs/reviewer-checklist.md`
- `docs/changelog.md`

Maintain two distinct changelogs:

- Root `CHANGELOG.md`: application and release changes.
- `docs/changelog.md`: SDLC activity, agent work, evidence and human approvals.

Update existing documentation instead of unnecessarily regenerating it.

## 8. Quality and Verification

- Run Maven commands from the project root.
- Use `mvn test` as the primary test verification command.
- Use TestNG exclusively.
- Verify Java 21 compatibility.
- Verify monetary calculations use BigDecimal.
- Verify dates use LocalDate.
- Verify the application remains console-only and in-memory.
- Report actual command results.
- Never invent test results, coverage figures or successful build claims.

## 9. Git and Pull Request Policy

Support both PR workflows.

**Mode A — Automated:**
- Prepare and validate PR materials.
- Obtain explicit human approval.
- Use GitHub CLI (`gh`) to create the PR.
- Verify the resulting PR URL and number.

**Mode B — Semi-automated:**
- Prepare the PR description and reviewer checklist.
- Provide the suggested GitHub CLI command or manual GitHub instructions.
- Allow the human to create the PR.

Neither mode may push commits or create a PR without explicit approval.

If GitHub CLI is unavailable or unauthenticated, use semi-automated mode.

Do not implement a custom token-based GitHub API client.

Never expose credentials or tokens.

## 10. Security and Command Execution

- Inspect files before modifying them.
- Avoid destructive commands.
- Do not delete or overwrite unrelated project files.
- Do not execute Git push, PR creation or other externally visible actions without explicit approval.
- Do not expose secrets in logs or reports.
- Use the minimum permissions necessary.

## 11. Context and Usage Efficiency

The project has a limited Claude usage budget.

Therefore:
- Read only relevant documents for each task.
- Avoid repeatedly reading the entire repository.
- Do not regenerate approved documents.
- Do not repeat completed analysis.
- Prefer focused specialist delegation.
- Batch related verification commands when appropriate.
- Avoid unnecessary model-intensive operations.
- Report usage information when available.

## 12. Execution Protocol

At the beginning of each stage:

1. Confirm the current approved stage.
2. Read the relevant source documents.
3. Identify the required specialist agent and skill.
4. Explain the intended work briefly.
5. Execute only the authorized stage.
6. Validate the deliverables.
7. Update the SDLC activity log.
8. Present evidence and unresolved issues.
9. Stop and request human approval where required.

**Current instruction: Stage 1 is complete and approved. Wait for explicit stakeholder authorization before starting Stage 2.**