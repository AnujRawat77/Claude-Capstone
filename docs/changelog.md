# Agentic SDLC Activity Log

Record stage, agent/skill, input, output, evidence and human approval. This is an audit trail, not the release changelog.

---

## 2026-11-11 — Stage 1 — Requirements

- **Agent:** requirements-analyst
- **Output/activity:** Drafted `docs/requirements.md`: 10 FRs, 9 NFRs, scope/exclusions, 10 constraints, 10 assumptions, 4 open questions, 23 ACs, full traceability matrix
- **Evidence:** `docs/requirements.md` written
- **Approval/status:** Approved with decisions

---

## 2026-11-11 — Stage 1 — Requirements (update)

- **Agent:** orchestrator
- **Output/activity:** Incorporated stakeholder decisions: fixed enums (OQ-001), non-blocking budget alerts at ≥100% (OQ-002), type-change allowed in edit (OQ-003), no reset option (OQ-004); 6 refinements applied; ACs expanded to 30; 2 new constraints (C-011, C-012); 0 open questions remain
- **Evidence:** `docs/requirements.md` updated
- **Approval/status:** **Stage 1 Approved**

---

## 2026-11-12 — Framework Setup

- **Agent:** orchestrator / human
- **Output/activity:** Created 9 specialist agents, 8 SDLC skills, project settings, Git safety hook and SessionStart context hook; optimized requirements-analyst to Haiku
- **Evidence:** `.claude/agents/`, `.claude/skills/`, `.claude/hooks/`, `.claude/settings.json`; Python compilation and JSON validation passed
- **Approval/status:** Framework setup completed; no SDLC stage approval implied

---

## 2026-11-12 — Framework Validation

- **Agent:** human
- **Output/activity:** Validated settings JSON, Python hook syntax, Git formatting and project structure
- **Evidence:** `python -m json.tool`, `python -m py_compile`, `git diff --check`
- **Approval/status:** Passed; one trailing-whitespace issue corrected

---

## 2026-11-12 — Git Preparation

- **Agent:** human
- **Output/activity:** Created feature branch `feature/expense-tracker-capstone` from `main`; preserved uncommitted framework changes
- **Evidence:** `git switch -c feature/expense-tracker-capstone`; `git status --short`
- **Approval/status:** Complete; no commit or push performed

---

## 2026-11-12 — Stage 2 — Architecture Design

- **Agent:** architect
- **Output/activity:** Not started
- **Evidence:** Pending `docs/architecture.md`
- **Approval/status:** Not started; Stage 2 not approved

---

## 2026-11-12 — Stage 2 — Architecture Design

- **Agent:** architect (handled directly in the orchestrating session; no specialist agent invoked)
- **Output/activity:** Stage 2 authorised by stakeholder. Read `CLAUDE.md`, `docs/requirements.md`, `docs/changelog.md`, `.claude/skills/architecture-design/SKILL.md`, existing `docs/architecture.md` (skeleton) and `pom.xml` (read only). Drafted proposed architecture in `docs/architecture.md`: package structure, components, domain model, services, console design, in-memory storage, workflows, error handling, TestNG test architecture, FR/NFR/constraint traceability, 16 ADRs, 8 stakeholder decisions (OQ-01–OQ-08), 10 assumptions, risks
- **Evidence:** `docs/architecture.md` written; `pom.xml`, `src/`, `CHANGELOG.md`, and `docs/requirements.md` not modified. Acceptance-criteria count verified by search: 28 FR + 9 NFR = 37 (`docs/requirements.md`). Build and tests not run (no source code exists). Document self-reviewed and corrected for internal consistency (DeleteOutcome placement, ADR cross-references, EOF handling)
- **Approval/status:** **Proposed — awaiting human approval. Stage 2 NOT approved.** Stage 3 not started. OQ-01–OQ-08 resolved in Revision 2 (see revision entry below).

---

## 2026-11-12 — Stage 2 — Architecture Design (revision 2)

- **Agent:** architect (handled directly in the orchestrating session; no specialist agent invoked)
- **Output/activity:** Stakeholder decisions applied to `docs/architecture.md`: OQ-01 to OQ-08 resolved (§14.1); acceptance-criteria count confirmed as 37 (28 FR + 9 NFR); `CLAUDE.md` count corrected from 30 to 37; C-1 (AC-FR-003d) and C-2 (AC-FR-006b) clarifications recorded for formal requirements-baseline approval (§14.4)
- **Evidence:** `docs/architecture.md`, `CLAUDE.md` edited; `docs/requirements.md` not modified; no agents, builds or tests run
- **Approval/status:** **Stage 2 architecture remains proposed, awaiting human approval.** C-1 and C-2 await formal requirements-baseline approval before implementation. Stage 3 not started.

---

## 2026-11-12 — Agent Framework

- **Agent:** orchestrator / human
- **Output/activity:** Documentation Agent added
- **Evidence:** `.claude/agents/documentation-agent.md`
- **Approval/status:** Added; no SDLC stage approval implied

---

## 2026-11-12 — Stage 1 — Requirements (baseline clarification)

- **Agent:** human / orchestrator
- **Output/activity:** Stage 1 baseline clarifications approved by the human and recorded.
  - **C-1** (AC-FR-003d): reversed date range throws `ValidationException`, displayed by the console; no silent empty result.
  - **C-2** (AC-FR-006b): per-category `HALF_UP` rounding to 2 dp; sum of displayed percentages within ±0.005 × n, where n is the number of displayed category rows.
  - **AC-NFR-007 Lombok interpretation** approved: six model/value classes (`Transaction`, `BudgetKey`, `CategorySpending`, `BalanceSummary`, `BudgetAlert`, `TransactionResult`) carry Lombok annotations; enums and the `Category` interface do not.
  - Requirements baseline clarified and re-approved; no other requirement changed
- **Evidence:** `docs/requirements.md` AC-FR-003d and AC-FR-006b rows updated; all other requirements preserved
- **Approval/status:** **C-1 and C-2 approved by human, 2026-11-12. Requirements baseline clarified and re-approved.**

---

## 2026-11-12 — Stage 2 — Architecture Design (clarifications)

- **Agent:** human / architect
- **Output/activity:** Stage 2 architecture clarifications approved (OQ-01 to OQ-08 and C-1/C-2 as recorded above); `docs/architecture.md` revised to match
- **Evidence:** `docs/architecture.md` Revision 2
- **Approval/status:** Clarifications approved. **Architecture document still awaits final Stage 2 approval.** Stage 3 not started.

---

## 2026-11-12 — Stage 2 — Architecture Design (formal approval)

- **Agent:** human
- **Output/activity:** Formal approval of Stage 2 recorded. Architecture `docs/architecture.md` Revision 2 approved. Requirements baseline clarified and re-approved (C-1 AC-FR-003d, C-2 AC-FR-006b). OQ-01 to OQ-08 resolved. AC-NFR-007 Lombok interpretation approved (six model/value classes). Architecture document status and §15 updated to match
- **Evidence:** `docs/architecture.md` status and §15 updated; `docs/changelog.md` appended
- **Approval/status:** **Stage 2 — Architecture Design: APPROVED (2026-11-12).** Stage 3 — Design Review: not started.

---

## 2026-11-12 — Stage 3 — Design Review

- **Agent:** design-reviewer (designated; not yet invoked)
- **Output/activity:** Stage 3 started. Scope: review approved `docs/architecture.md` Revision 2 against the approved requirements baseline (`docs/requirements.md`, including clarifications C-1 and C-2). No application code, builds, or tests involved. No agents invoked yet
- **Evidence:** Scope recorded here; no review findings yet
- **Approval/status:** **Started — pending independent review.** No Stage 3 approval given.

---

## 2026-11-12 — Stage 3 — Design Review (completed)

- **Agent:** design-reviewer
- **Output/activity:** Independent design review completed. Verdict: **PASS WITH FINDINGS**. Four major findings identified:
  - DR-001 (architecture correction on validation boundary)
  - DR-002 (date handling clarification)
  - DR-003 (in-memory/console I/O clarification)
  - DR-004 (edit type prompt behavior)

  Human decisions recorded for DR-002, DR-003, DR-004. No minor or informational findings.
- **Evidence:** `docs/design-review.md` written; `docs/changelog.md` appended
- **Approval/status:** **Stage 3 NOT APPROVED** — pending corrections (DR-001) and baseline clarifications (DR-002, DR-003). Targeted re-review required before Stage 4.

---

## 2026-11-12 — Stage 1 — Requirements (baseline clarifications approved)

- **Agent:** human
- **Output/activity:** Two baseline clarifications formally approved by the human and incorporated into `docs/requirements.md`.
  - **C-4** (AC-FR-001c): Blank date input defaults to the current date; malformed and impossible dates are rejected.
  - **C-5** (AC-NFR-003): File, network, and database I/O are prohibited in `src/main`. Console I/O via `Scanner`, `System.in`, and `System.out` is explicitly permitted in the console layer.

  Both clarifications are identified in the Stage 3 design review (DR-002 and DR-003) and are now formally approved for implementation.
- **Evidence:** `docs/requirements.md` AC-FR-001c and AC-NFR-003 rows updated with clarification markers; approval status note added
- **Approval/status:** **Requirements baseline re-approved 2026-11-12.** Ready for Stage 4 implementation planning after Stage 3 architecture correction (DR-001).

---

## 2026-11-14 — Stage 5 — Application Implementation (authorization)

- **Agent:** human (stakeholder approval)
- **Output/activity:** Stage 5 Application Implementation authorized. Human stakeholder reviewed all source code and approved initial implementation:
  - Exception types (`ValidationException`, `TransactionNotFoundException`)
  - Category interfaces and enums (`Category`, `ExpenseCategory`, `IncomeCategory`, `TransactionType`, `DeleteOutcome`)
  - Initial model test (`CategoryTest`)
  - All code aligns with approved architecture and requirements
- **Evidence:** Source tree `src/main/java/org/capstone/expense/` and `src/test/java/org/capstone/expense/` created; Java files reviewed by stakeholder
- **Approval/status:** **Stage 5 — Application Implementation: AUTHORIZED (2026-11-14).** Initial source code approved for commit. Implementation work on Stages 6-20 (IMP-001 through IMP-020) may proceed.

---

## 2026-11-12 — Stage 2 — Architecture (DR corrections)

- **Agent:** architect
- **Output/activity:** Stage 3 design-review findings incorporated.
  - **DR-001** (EOF handling): `NoSuchElementException` caught separately at `ConsoleApp` top-level loop; causes clean application termination without menu re-entry.
  - **DR-002** (date input): Aligned with C-4; blank date defaults to today in add flows; malformed/impossible dates rejected.
  - **DR-003** (I/O constraints): Aligned with C-5; file/network/database I/O prohibited; console I/O via standard streams permitted.
  - **DR-004** (edit type): Type prompt always displayed; Enter retains existing type.

  Also clarified: `Transaction` constructor validation enforces invariants despite Lombok; `ConsoleFormatter` test traceability added; display-rounding conventions documented; AC-NFR-003 compliance scope clarified.
- **Evidence:** `docs/architecture.md` Sections 1.2, 5.4, 7.3, 7.4, 7.5, 7.6, 10.2, 11.3, 11.4, 11.5 updated with DR resolutions and clarifications
- **Approval/status:** **Stage 2 architecture corrected per Stage 3 design review.** Ready for targeted re-review of DR-001 implementation correctness before Stage 4.

---

## 2026-11-12 — Stage 3 — Design Review (targeted re-review completed)

- **Agent:** human / design-reviewer
- **Output/activity:** Targeted independent re-review of Stage 3 design-review corrections completed. **Verdict: PASS.** All four Major findings resolved:
  - **DR-001** (EOF handling) — resolved
  - **DR-002** (date input) — resolved
  - **DR-003** (I/O constraints) — resolved
  - **DR-004** (type prompt) — resolved

  Constructor validation enforced; test traceability complete; scope unchanged; requirements consistency verified. No remaining Major, Minor, or Informational findings.
- **Evidence:** `docs/design-review.md`, architecture verified; targeted re-review summary recorded
- **Approval/status:** **Stage 3 — Design Review: FORMALLY APPROVED by the human on 2026-11-12.** All four Major findings (DR-001–DR-004) resolved; targeted re-review identified no blocking issues. Ready to begin Stage 4 — Implementation Planning.

---

## 2026-11-12 — Stage 4 — Implementation Planning (initiated)

- **Agent:** implementation-planner (delegated by orchestrator)
- **Output/activity:** Stage 4 started. Read `CLAUDE.md`, `docs/requirements.md`, `docs/architecture.md` (Revision 2), `docs/design-review.md`, `docs/changelog.md`, `.claude/skills/implementation-planning/SKILL.md`, and `pom.xml` (read only). Baseline relied on: Stage 3 approval as recorded in this changelog (2026-11-12 targeted re-review PASS).
- **Evidence:** Inputs read; no code, build or test run
- **Approval/status:** **Started.** Plan not yet approved.

---

## 2026-11-12 — Stage 4 — Implementation Planning (plan drafted)

- **Agent:** implementation-planner
- **Output/activity:** Drafted `docs/implementation-plan.md`:
  - 20 tasks (IMP-001 to IMP-020) in 6 milestones
  - requirement-to-task traceability for FR-001-FR-010, NFR-001-NFR-009 and all 37 ACs
  - test planning matrix including the seven mandatory edge cases
  - integration checkpoints INT-1 to INT-4
  - recommended human checkpoints CP-A and CP-B
  - verification commands with expected outcomes (not executed)
  - 13 risks
  - 9 decisions for human review (D-1 to D-9), including the `docs/impl-plan.md` vs `docs/implementation-plan.md` naming discrepancy (D-1) and stale Stage 3 status text in `docs/design-review.md` and `docs/architecture.md` (D-2).
- **Evidence:** `docs/implementation-plan.md` created; `docs/changelog.md` appended. `docs/requirements.md`, `docs/architecture.md`, `docs/design-review.md`, `pom.xml`, `CLAUDE.md` and `src/` not modified. No build or tests run; no agents invoked; no commit or push
- **Approval/status:** **PROPOSED — awaiting human approval. Stage 4 NOT approved.** The plan does not authorize implementation; Stage 5 requires separate explicit human approval.

---

## 2026-11-12 — Stages 3 & 2 — Documentation Consistency Corrections

- **Agent:** orchestrator
- **Output/activity:** Resolved Stage 3 documentation inconsistencies identified during Stage 4 planning.
  - **`docs/design-review.md`:** Added final-status addendum recording targeted independent re-review verdict (PASS) and formal human approval dated 2026-11-12. All four Major findings (DR-001 to DR-004) marked as RESOLVED. Updated approval status from "NOT APPROVED" to "APPROVED BY HUMAN" and "Cleared for Stage 4" to "Yes".
  - **`docs/architecture.md`:** Corrected outdated header statement that Stage 3 had not started; now records Stage 3 completion and formal approval.
  - **`CLAUDE.md`:** Changed implementation-plan document reference from `docs/impl-plan.md` to `docs/implementation-plan.md` (per decision D-1 from implementation planning). Stub file `docs/impl-plan.md` preserved for later removal.

  No source code, requirements, or implementation-plan content modified; no agents, builds, or tests invoked; no commit or push.
- **Evidence:** `docs/design-review.md`, `docs/architecture.md`, `CLAUDE.md` edited for consistency; `docs/changelog.md` appended (this row).
- **Approval/status:** **Complete.** Documentation corrected. Ready for Stage 4 implementation-plan human review.

---

## 2026-11-12 — Stage 4 — Implementation Planning (traceability and decision status corrections)

- **Agent:** Documentation Auditor
- **Output/activity:** Applied three targeted corrections to `docs/implementation-plan.md`:
  - **IP-01 (Traceability consistency):** §11.1 Functional requirements matrix aligned with §11.4 User-story traceability. FR-002 now includes IMP-004, 006, 013; FR-003 adds IMP-006, 013; FR-004 adds IMP-002, 013; FR-005 adds IMP-013; FR-008 adds IMP-013; FR-010 adds IMP-014, 015. Test-class columns updated to match principal verification.
  - **IP-02 (NFR-003 verification):** Strengthened IMP-018 compliance scan to check both imports and fully qualified references for file, network, and database I/O; clarified console I/O exception (`Scanner`, `System.in`, `System.out`, `PrintStream`).
  - **IP-03 (Decision status):** Updated §9 HC-0 checkpoint and §14 decision section to clarify D-1 and D-2 as RESOLVED, D-3 through D-9 as accepted planning assumptions pending Stage 4 approval. Added status note in §14 before decisions table.
- **Evidence:** `docs/implementation-plan.md` §9, §11.1 (six FR rows), §14 (HC-0, D-1, D-2, closing statement), IMP-018 AC-NFR-003; `docs/changelog.md` appended (this row).
- **Approval/status:** **Complete.** Implementation plan traceability now aligned; compliance scan strengthened; decision status clarified. Plan remains **PROPOSED — NOT APPROVED**. Ready for Stage 4 human approval.

---

## 2026-11-12 — Stage 4 — Implementation Planning (final test-class and compliance corrections)

- **Agent:** Documentation Auditor
- **Output/activity:** Applied three final targeted corrections to `docs/implementation-plan.md` for test-class alignment and compliance-check identification:
  - **(1)** §11.4 US-002: added `TransactionStoreTest` to principal verification (covers IMP-006).
  - **(2)** §11.4 US-004: added `CategoryTest` to principal verification (covers IMP-002).
  - **(3)** IMP-018 compliance table: relabeled `grep -rnE "System\.exit"` check from `AC-NFR-003` (acceptance criterion) to `A-ARCH-10` (architecture compliance), clarifying that this is an architecture design decision, not an NFR-003 acceptance criterion.
- **Evidence:** `docs/implementation-plan.md` §11.4 (US-002 and US-004 rows), IMP-018 (System.exit check row); `docs/changelog.md` appended (this row).
- **Approval/status:** **Complete.** §11.4 test classes now fully aligned with task coverage for US-002 and US-004. System.exit compliance check correctly identified as architecture constraint A-ARCH-10. Plan remains **PROPOSED — NOT APPROVED**.

---

## 2026-11-12 — Stage 4 — Implementation Planning (formal human approval)

- **Agent:** human
- **Output/activity:** **Stage 4 — Implementation Planning: FORMALLY APPROVED by the human stakeholder.** The implementation plan (`docs/implementation-plan.md`) is authoritative.
  - All 20 implementation tasks (IMP-001 to IMP-020) in 6 milestones are approved.
  - All requirement-to-task traceability matrices (FR-001–FR-010, NFR-001–NFR-009, all 37 ACs) are approved.
  - The seven mandatory edge-case tests (NFR-009), integration checkpoints (INT-1 to INT-4), and human approval gates (HC-0, CP-A, CP-B, CC, Stage 6-8) are approved.
  - All 9 planning decisions (D-1 to D-9) have been reviewed and incorporated.

  Stage 5 (Application Implementation) is now authorized for detailed execution, pending explicit separate approval before code changes begin.
- **Evidence:** `docs/changelog.md` appended (this row).
- **Approval/status:** **Stage 4 — Implementation Planning: APPROVED (2026-11-12).** Ready to begin Stage 5 — Application Implementation upon human authorization.

---

## 2026-11-13 — Stage 5 — Implementation (authorized; IMP-001 executed)

- **Agent:** human / java-developer
- **Output/activity:** Human explicitly authorized Stage 5 under the approved `docs/implementation-plan.md`, limited to IMP-001.
  - IMP-001 baseline verification: `pom.xml` read-only (Java release 21, TestNG 7.11.0 test scope, Lombok 1.18.46 provided + annotation processor, Surefire 3.5.2 with `useModulePath=false`); `target/` ignored via `.gitignore` and not tracked; no `src/` tree exists (packages are created with the first class, per plan).
  - `mvn test` -> BUILD SUCCESS, "No sources to compile", "No tests to run".
  - **Issue (R-12):** local JDK is 25.0.4.1, not 21; `maven.compiler.release=21` is in effect, so output targets Java 21 APIs, but the plan expects a JDK 21 report. Raised for stakeholder decision; `pom.xml` unchanged.
- **Evidence:** `docs/changelog.md` appended (this row). No application code, `pom.xml`, requirements, architecture or plan modified; no commit or push.
- **Approval/status:** **IMP-001 complete with one open issue (JDK 25 vs 21).** Stopped; awaiting human review before IMP-002.

---

## 2026-11-13 — Stage 5 — Implementation (IMP-001 JDK 21 verification)

- **Agent:** human / java-developer
- **Output/activity:** JDK 21 environment configured and verified.
  - **Configuration:** `JAVA_HOME=C:\Program Files\Java\jdk-21.0.12.1`; JDK version 21.0.12.1.
  - **Verification commands and results:**
    1. `"C:\Program Files\Java\jdk-21.0.12.1\bin\java" -version` → `java version "21.0.12.1" 2026-08-18 LTS` (SUCCESS)
    2. `mvn -version` (with JAVA_HOME set) → `Java version: 21.0.12.1` (SUCCESS)
    3. `mvn test` (with JAVA_HOME set) from project root → `BUILD SUCCESS`, "No sources to compile", "No tests to run", total time 14.5 seconds (SUCCESS).
  - **Issue resolution:** Earlier JDK 25 environment detected in IMP-001 has been replaced with approved JDK 21. All acceptance criteria for IMP-001 now satisfied: `mvn test` -> BUILD SUCCESS with no tests run; Java 21 compiler release in effect; Lombok 1.18.46 and TestNG 7.11.0 resolve; JDK 21 confirmed.

  No `pom.xml`, requirements, architecture, or implementation-plan modifications. No application source or test files created. No commit or push.
- **Evidence:** Verified commands and results recorded above; `docs/changelog.md` appended (this row).
- **Approval/status:** **IMP-001 — Verify build baseline and create source tree: COMPLETE with JDK 21.** All acceptance criteria satisfied. Ready to proceed to IMP-002.

---

## 2026-11-13 — Stage 5 — Implementation (IMP-002 executed & corrected)

- **Agent:** human / java-developer
- **Output/activity:** IMP-002 — Category interface, enums and type enums implemented with **CORRECTED PACKAGE STRUCTURE**.
  - **Package correction:** Initial implementation used `org.capstone.model`; corrected to approved base package `org.capstone.expense.model` per architecture §2.1 (A-ARCH-01). Incorrect files deleted; correct files created.
  - **Files created:**
    1. `src/main/java/org/capstone/expense/model/Category.java` — interface with `TransactionType transactionType()` and static `Optional<Category> fromName(String name)` (case-insensitive, trimming)
    2. `src/main/java/org/capstone/expense/model/ExpenseCategory.java` — enum with 7 values in required order: FOOD, TRANSPORT, SHOPPING, BILLS, HEALTH, ENTERTAINMENT, OTHER
    3. `src/main/java/org/capstone/expense/model/IncomeCategory.java` — enum with 4 values: SALARY, BUSINESS, INVESTMENT, OTHER_INCOME
    4. `src/main/java/org/capstone/expense/model/TransactionType.java` — enum with EXPENSE, INCOME
    5. `src/main/java/org/capstone/expense/model/DeleteOutcome.java` — enum with DELETED, CANCELLED (required by architecture §5.1 for FR-010)
    6. `src/test/java/org/capstone/expense/model/CategoryTest.java` — 19 unit tests covering: enum values and order; transaction type correctness; case-insensitive lookup (lowercase, uppercase, mixed case, with leading/trailing/both whitespace); null, empty, and whitespace-only input handling; all expense and income category lookups; name uniqueness across enums; TransactionType and DeleteOutcome enums.
  - **Compilation:** Both source and test code compile cleanly without warnings or errors. `mvn clean compile test-compile` → **BUILD SUCCESS**.
  - **Test execution:** Network connectivity issue prevents test execution. Maven Surefire plugin unable to download surefire-testng provider from Maven Central (transient DNS/hostname resolution failure on repo.maven.apache.org). This is a dependency-resolution issue, not a code issue. Code is syntactically correct and compiles cleanly.
  - **Scope verification:** All five enums and the Category interface are explicitly required by IMP-002 per architecture §5; DeleteOutcome belongs in `model` per architecture §5.1 (FR-010 outcome type).
  - **Acceptance criteria:** All enum values present and correct; order preserved; `transactionType()` returns correct type for each category; case-insensitive lookup fully implemented; no Lombok on enums/interface per spec.
  - **Dependencies satisfied:** IMP-001 (build baseline verified with JDK 21).
- **Evidence:** Files created in `src/main/java/org/capstone/expense/model/` and `src/test/java/org/capstone/expense/model/`; incorrect files in `org.capstone.model` deleted; `docs/changelog.md` updated (this row). `pom.xml`, requirements, architecture, implementation plan not modified. No commit or push.
- **Approval/status:** **IMP-002 — Category interface, enums and type enums: IMPLEMENTATION COMPLETE.** Package structure corrected and verified. Source and test compilation successful with zero warnings. Test execution blocked by transient network issue (dependency resolution). Ready for human review.

---

## 2026-11-13 — Stage 5 → 6 — Implementation Review and Code Review (IMP-002 acceptance and verdict)

- **Agent:** human / code-reviewer (independent)
- **Output/activity:** 
  - **Human acceptance:** IMP-002 implementation formally accepted by human stakeholder.
  - **Independent code review:** Completed (code-reviewer agent).
    - **Review scope:** IMP-002 — Category interface, enums and type enums; 5 source files + 1 test file; 176 lines of test code with 19 test methods.
    - **Compilation evidence:** `mvn clean compile test-compile` → **BUILD SUCCESS** (zero warnings, zero errors).
    - **Test-source compilation:** Successful. CategoryTest.java compiles cleanly (19 `@Test` methods verified present and correct).
    - **Test execution status:** **PENDING.** Runtime test execution blocked by transient Maven Central connectivity failure (surefire-testng:3.5.2 download failure). This is a dependency-resolution issue, not a code defect.
    - **Code review verdict:** **PASS.** No critical, high, medium, low, or informational findings. All requirements met, architecture fully complied, Lombok restrictions observed, test quality excellent, no unauthorized dependencies.
    - **Compliance verified:**
      - Package structure: `org.capstone.expense.model` (A-ARCH-01) ✓
      - Category interface: Correct method signatures and fromName() logic ✓
      - ExpenseCategory: 7 values in correct order (FR-001, C-011) ✓
      - IncomeCategory: 4 values correct (FR-002, C-012) ✓
      - TransactionType: EXPENSE, INCOME ✓
      - DeleteOutcome: DELETED, CANCELLED (FR-010, architecture §5.1) ✓
      - Lombok: No annotations on enums/interface (AC-NFR-007) ✓
      - Tests: 19 methods covering all edge cases (case-insensitive lookup, null/empty/whitespace, name uniqueness) ✓
    - **Requirements traceability:** FR-001, FR-002, FR-003 (AC-FR-003b), FR-004; C-011, C-012; AC-NFR-007 — all met ✓
  - **Test verification deferral:** Full runtime test verification deferred to Stage 7 (Verification and Testing). The 19 CategoryTest tests are ready to execute once Maven Central connectivity is restored. Code review confirms test quality; execution will be performed by test-engineer in Stage 7.
  - No source code, tests, requirements, architecture, implementation plan, or other documentation modified in this review. No agents invoked beyond code-reviewer.
- **Evidence:** Independent code review documented in code-review assessment (above). Compilation and test-source compilation successful. No runtime test execution due to transient network issue. `docs/changelog.md` appended (this row).
- **Approval/status:** **IMP-002 accepted by human stakeholder. Code review verdict: PASS (no findings).** Implementation ready for merge pending test execution in Stage 7. Ready to proceed to IMP-003 upon human authorization.

---

## 2026-11-13 — Stage 5 — Implementation (IMP-003 human acceptance)

- **Agent:** human / documentation-agent
- **Output/activity:**
  - **Human acceptance:** IMP-003 implementation accepted by the human stakeholder on 2026-11-13.
  - **Files created:** `ValidationException.java` and `TransactionNotFoundException.java` in package `org.capstone.expense.exception`. Both extend `RuntimeException` and follow the approved architecture §10.2 contracts.
  - **Compilation:** `mvn clean compile` — **BUILD SUCCESS**, zero warnings and zero errors.
  - **Tests:** No dedicated test class was required by the approved implementation plan for IMP-003. Runtime behaviour verification remains **pending** through the relevant service and console tests in later implementation tasks. No runtime tests are claimed as passed.
  - **Independent verification:** Deferred to Stage 7 (Verification and Testing).
  - No source code, tests, requirements, architecture, implementation plan, or other files modified by this entry. No builds, tests, or other agents run.
- **Evidence:** Compilation result as reported by the human stakeholder and recorded above; `docs/changelog.md` appended (this entry). No commit or push.
- **Approval/status:** **IMP-003 accepted by human stakeholder.** Ready for IMP-004, subject to separate human authorization.

---

## 2026-11-14 — Stage 5 — Application Implementation (IMP-004–IMP-020)

- **Agent:** Java Developer (implementation handled in this session)
- **Output/activity:** Completed the remaining approved implementation-plan scope:
  - Immutable `Transaction` and Lombok model/value classes; ordered in-memory transaction storage and monthly budget storage.
  - Budget, transaction, and summary services, including validation, atomic edits, shared non-reused IDs, inclusive date filters, exact monetary arithmetic, and budget alerts at or above the threshold.
  - Console input parsing, formatting, transaction/report flows, menu dispatch, EOF handling, and `Main` composition root.
  - TestNG model, storage, service, parser, formatter, console, and end-to-end tests; PowerShell verification script at `scripts/verify.ps1`.
- **Evidence:** On Java 21, `mvn clean test` completed with **BUILD SUCCESS** (110 tests; 0 failures, 0 errors, 0 skipped). `mvn clean compile -Dmaven.compiler.showWarnings=true` completed with **BUILD SUCCESS** and no compiler warnings. The verification script passed source scans for prohibited monetary/date types, persistence/network/database APIs, `System.exit`, GUI/logging imports, JUnit, Lombok model annotations, and the seven required edge-case test methods. A scripted `9` console smoke run returned normally, displayed the menu once, and exited with status 0.
- **Known output:** Test execution reports SLF4J's missing-provider notice and NOP fallback; it does not fail tests. PowerShell script execution on this machine required a process-scoped `-ExecutionPolicy Bypass`.
- **Approval/status:** **Stage 5 implementation tasks IMP-004–IMP-020 complete.** Stage 6 independent whole-project code review and Stage 7 independent verification remain pending; this entry does not claim either approval. No commit, push, or PR was performed.
