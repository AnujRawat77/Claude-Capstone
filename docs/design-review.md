# Design Review — Stage 3

## Review Summary

| Item | Details |
|---|---|
| Review Date | 2026-11-12 |
| Reviewer | design-reviewer agent |
| Scope | Requirements (docs/requirements.md, including approved baseline clarifications C-1, C-2, and C-3) and Architecture Revision 2 (docs/architecture.md) |
| Verdict | **PASS WITH FINDINGS** |
| Status | **APPROVED** (2026-11-12, targeted re-review; all findings resolved) |

---

## Major Findings

### DR-001: Architecture Correction (Input validation boundary)

**Severity:** Major

**Description:** Review of the validation boundary described in Section 10.1 of docs/architecture.md identifies a gap in the architecture specification regarding input validation responsibilities across console and service layers for date and amount fields.

**Required Action:** Architect to review and formalize the validation boundary (syntax in console, business rules in service) with specific examples for all major input types. This is a design correction, not a requirements change. Clarification to be addressed before implementation begins.

---

### DR-002: Date Handling Specification Clarification

**Severity:** Major

**Description:** Section 7.5 and Section 10.3 specify that blank dates default to today in add flows and retain the current value in edit flows. The specification does not explicitly address how "blank = today" is implemented (e.g., does the console read and apply the default, or does the service accept null and apply it?). Additionally, the round-trip validation for yyyy-MM-dd format rejects malformed dates (e.g., 2026-1-5, 2026-02-30) as documented in ADR-010. The exact behavior when impossible dates are entered requires clarification.

**Required Action:** Formal baseline clarification needed: confirm that blank date input defaults to today (using LocalDate.now(clock) in the console), and that malformed or impossible dates are rejected with a ValidationException. This is a non-functional clarification. Once approved, it may be applied to docs/requirements.md or retained as an accepted interpretation in the architecture.

**Human Decision (2026-11-12):** Blank dates default to today; malformed and impossible dates are rejected. Formal baseline clarification **required** for consistency with NFR-002 and AC-FR-001c. The architectural specification in ADR-010 aligns with this decision.

---

### DR-003: In-Memory Storage and Console I/O Constraint Clarification

**Severity:** Major

**Description:** AC-NFR-003 and NFR-003 state that the application shall be "in-memory only", with the architectural consequence (Section 1.2) that "Storage uses java.util collections; no java.io / java.nio in src/main." However, the same constraint section explicitly permits console I/O: "All I/O confined to console; Scanner and System.out only." The design allows java.io.PrintStream and java.util.Scanner to be imported and used in the console layer, which technically involves the java.io package. This boundary requires explicit clarification: whether java.io is prohibited in src/main in all contexts, or whether console I/O (file-descriptor-based Scanner and System.out) is an exception.

**Required Action:** Formal baseline clarification needed: confirm that AC-NFR-003 prohibits file, network, and database I/O in src/main, while explicitly permitting standard stream I/O (java.io.PrintStream, java.util.Scanner over System.in/System.out) in the console layer only. This affects the scope of the Stage 7 verification compliance check for NFR-003.

**Human Decision (2026-11-12):** AC-NFR-003 prohibits file, network, and database I/O, while permitting console I/O (standard streams). Formal baseline clarification **required** to align the requirements with the architectural design. Stage 7 compliance check shall verify that java.io, java.nio, and java.sql imports are confined to console layer or explicitly tied to standard streams, not file handles.

---

### DR-004: Edit Flow Type Prompt Behavior

**Severity:** Major

**Description:** Section 7.4 of the architecture specifies the Edit Transaction flow as: "Type (Enter = keep, 1 = Expense, 2 = Income)". The natural interpretation of this syntax is that pressing Enter retains the existing type. However, in Section 14.2 (Architect Assumptions), assumption A-ARCH-09 states: "In the edit flow, the category prompt is always shown and must be answered with a number. Enter does not keep the current category." This assumption is explicit about category behavior but silent on type behavior. The consistency of "Enter = keep" for type versus the mandatory re-entry pattern elsewhere requires clarification.

**Required Action:** Confirm whether the type prompt in the edit flow accepts Enter to retain the current type, or whether it requires the user to re-enter the type (1 or 2) without a "keep" shorthand.

**Human Decision (2026-11-12):** The type prompt always appears in the edit flow. **Enter retains the existing type** (interpretation approved). This aligns with "Enter = keep" in Section 7.4. If the user enters a different type (1 or 2), the category list is re-populated for that type (FR-004, A-010). This is an approved interpretation and does not require a requirements change.

---

## Minor Findings

(None identified at this time.)

---

## Informational Findings

(None identified at this time.)

---

## Overall Assessment

The architecture document (Revision 2) demonstrates comprehensive coverage of the approved requirements. The design is sound and supports the planned implementation. However, three major findings require attention before implementation begins:

1. **DR-001** requires an architectural clarification on validation boundaries (console/service split).
2. **DR-002** and **DR-003** require formal baseline clarifications to align the requirements text with the design.
3. **DR-004** requires an approved interpretation of user-facing behavior (type prompt with Enter).

Findings DR-002, DR-003, and DR-004 have received human decisions (above). DR-001 remains an open architecture correction.

---

## Review Evidence

| Item | Status |
|---|---|
| Requirements baseline reviewed | docs/requirements.md (with approved clarifications C-1, C-2, C-3) |
| Architecture document reviewed | docs/architecture.md Revision 2 (Sections 1–15) |
| Consistency check (requirements ↔ architecture) | Complete; 3 points of clarification identified |
| Cross-reference check (FR/NFR/constraint traceability) | Verified against Section 12 |
| Test architecture alignment | Verified against Section 11 |
| Build configuration review | Verified; pom.xml already in place |
| Application code review | Not applicable (no source code exists at Stage 3) |

---

## Stage 3 Approval Status

| Item | Status |
|---|---|
| Design Review | **Complete** |
| Verdict | **PASS WITH FINDINGS** |
| Major Findings | 4 (DR-001 to DR-004) |
| Minor Findings | 0 |
| Informational Findings | 0 |
| Human Decisions Recorded | Yes (DR-002, DR-003, DR-004) |
| Corrections Pending | Yes (DR-001 architecture correction; DR-002/DR-003 baseline clarifications) |
| **Stage 3 Approval** | **APPROVED BY HUMAN — 2026-11-12 (Targeted Re-review)** |
| **Next Step** | All corrections applied. Ready for Stage 4 (Implementation Planning). |

---

## Final Status Addendum (2026-11-12)

**Targeted Independent Re-Review:** Completed 2026-11-12.

**Re-Review Verdict:** **PASS**

**Findings Resolution:**
- **DR-001 (Architecture Correction — Input Validation Boundary):** RESOLVED. EOF handling specified: `NoSuchElementException` caught separately at `ConsoleApp` top-level loop; causes clean application termination without menu re-entry (distinct from generic `RuntimeException` handling). Architecture Section 10.2 and 7.5 updated.
- **DR-002 (Date Handling Clarification):** RESOLVED. Baseline clarification C-4 approved by human. Blank date input defaults to current date (`LocalDate.now(clock)`) in add flows; malformed and impossible dates rejected with `ValidationException`. Architecture Sections 7.3, 7.5, and 10.3 updated.
- **DR-003 (In-Memory Storage and Console I/O Clarification):** RESOLVED. Baseline clarification C-5 approved by human. File, network, and database I/O prohibited in `src/main`; console I/O via standard streams (`java.util.Scanner`, `System.in`, `System.out`) explicitly permitted in console layer. Architecture Sections 1.2, 10.1, 10.2, and 11.5 updated.
- **DR-004 (Edit Flow Type Prompt Behavior):** RESOLVED. Type prompt always displayed in edit flow; Enter retains existing type (interpretation approved). Architecture Section 7.4 and 10.2 updated.

**Additional Clarifications Applied:**
- `Transaction` constructor validation enforces all invariants despite Lombok `@Value` and `@Builder` (Section 5.4).
- `ConsoleFormatterTest` added to test traceability matrix (Section 11.3).
- Display-rounding conventions documented (Section 7.6).
- AC-NFR-003 compliance scope clarified: console I/O via standard streams is an exception (Section 1.2, §C-5).

**Scope:** No requirements changed. No additional findings. Architecture Revision 2 consistent with approved requirements baseline.

**Status:** **FORMALLY APPROVED FOR STAGE 4.** All Major findings (DR-001–DR-004) resolved and documented. Targeted re-review identified no blocking issues.

---

## Sign-off

**Initial review completed:** 2026-11-12  
**Reviewer:** design-reviewer agent  
**Initial approval status:** PASS WITH FINDINGS — pending corrections

**Targeted re-review completed:** 2026-11-12  
**Reviewer:** design-reviewer agent (human-directed targeted re-review)  
**Final approval status:** **APPROVED** by human decision, 2026-11-12  
**Cleared for Stage 4 (Implementation Planning):** **Yes** — all findings resolved; Stage 3 Design Review formally approved.
