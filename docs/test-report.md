# Test Report — Personal Expense Tracker

**Verification date:** 2026-11-14
**Runtime:** Java 21
**Build tool:** Apache Maven
**Test framework:** TestNG 7.11.0
**Status:** Implementation-level automated verification completed and Stage 5 tasks IMP-001–IMP-020 approved by the human stakeholder on 2026-11-15; independent Stage 7 verification and approval remain pending.
**Document review:** Reviewed by the human stakeholder on 2026-11-15. This does not constitute Stage 7 verification or approval.

## 1. Scope and Evidence

This report records the implementation tests and checks actually run after completing IMP-001 through IMP-020. It is not a claim that the independent Stage 7 verification gate has been approved.

The original IMP-003 checkpoint results are retained in the 2026-11-14 history in `docs/changelog.md`. This report supersedes the earlier checkpoint-only status, which predated implementation of IMP-004 through IMP-020.

## 2. Test Suite

**Command:** `mvn clean test`
**JDK:** Java 21 (`C:\Program Files\Java\jdk-21.0.12.1`)
**Result:** **BUILD SUCCESS**

| Result | Count |
|---|---:|
| Tests run | 110 |
| Failures | 0 |
| Errors | 0 |
| Skipped | 0 |

Coverage includes:

- Category enums and category lookup.
- Transaction validation and immutable update behavior.
- Transaction and budget stores.
- Budget validation, overwrite, and the exact-100% alert boundary.
- Transaction creation, lookup, filtering, editing, deletion, and identifier behavior.
- Category-spending and balance summaries.
- Input parsing, date validation, formatting, and error messages.
- Transaction/report console flows and scripted end-to-end sessions, including EOF and invalid input.

The dedicated required edge-case tests include zero and negative amount rejection, missing transaction IDs, reversed date ranges, type changes during editing, budget overwrite, and an alert at exactly 100%.

## 3. Compilation and Runtime Smoke Check

**Command:** `mvn clean compile -Dmaven.compiler.showWarnings=true`
**Result:** **BUILD SUCCESS**; 24 production source files compiled for Java release 21; no compiler warnings were reported.

A scripted console smoke check supplied menu option `9` to `org.capstone.expense.Main`. The app displayed the menu once and returned normally with exit status 0.

## 4. Compliance Verification

**Command:** `powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\scripts\verify.ps1`
**Result:** **PASS**. The script runs `mvn clean test` and checks:

- No prohibited primitive floating-point monetary types in production source.
- No legacy `Date`/`Calendar` APIs.
- No persistence, database, or network APIs.
- No `System.exit`.
- No GUI or logging-framework imports.
- No JUnit references in test sources.
- Lombok usage in the six approved model value classes.
- Presence of the seven required edge-case test methods.

PowerShell required a process-scoped execution-policy override on the environment used for this run. The verification script itself does not change the machine-wide policy.

## 5. Notices and Limitations

- TestNG execution emitted SLF4J's missing-provider notice and NOP fallback notice. The notices did not fail the build or tests.
- These are implementation-level test and compliance results. They do not replace independent Stage 6 code review or Stage 7 verification.
- No coverage percentage or claim that every acceptance criterion has been independently verified is made in this report.

## 6. Gate Status

| Gate | Status |
|---|---|
| Stage 5 implementation tasks IMP-001–IMP-020 | Complete and human-approved on 2026-11-15 |
| TestNG implementation suite | PASS — 110 tests, no failures/errors/skips |
| Warning-enabled Java 21 production compilation | PASS |
| Scripted application startup/exit | PASS |
| Automated source-compliance checks | PASS |
| Stage 6 independent whole-project code review | Pending |
| Stage 7 independent verification and human approval | Pending |
