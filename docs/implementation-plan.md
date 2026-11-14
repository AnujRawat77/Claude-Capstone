# Implementation Plan

Status: **APPROVED by the human stakeholder on 2026-11-12 (Stage 4 — Implementation Planning).** Prepared by the implementation-planner on 2026-11-12.

> **This plan authorizes the implementation approach and task structure.** No application source code, `pom.xml` change, or test code may be created until the human stakeholder explicitly authorizes Stage 5 (Application Implementation). This Stage 4 approval authorizes the planning and methodology; Stage 5 requires separate explicit approval before code changes begin.

---

## 1. Planning Objective and Source Documents

**Objective.** Convert the approved requirements and architecture into a small, ordered, traceable, testable set of tasks that the Java Developer can execute in controlled increments, without inventing business behaviour, features, dependencies or architecture. The supplementary user-story document provides a stakeholder-readable view of the same approved functional scope; it does not introduce requirements or replace the requirements baseline.

### 1.1 Approved baseline

| Document | Version / status used |
|---|---|
| `docs/requirements.md` | Stage 1 approved 2026-11-11; clarifications C-1, C-2, C-3 approved 2026-11-12; C-4 (AC-FR-001c) and C-5 (AC-NFR-003) approved 2026-11-12. 10 FR, 9 NFR, 37 acceptance criteria (28 FR + 9 NFR). |
| `docs/architecture.md` | Revision 2, approved 2026-11-12; Stage 3 corrections DR-001 to DR-004 incorporated (see `docs/changelog.md`, 2026-11-12 rows). |
| `docs/design-review.md` | Stage 3 targeted re-review verdict PASS; Stage 3 formally approved by the human on 2026-11-12. The corrected review record and architecture status are authoritative; see `docs/changelog.md` entries dated 2026-11-12. Decision D-2 is resolved. |
| `docs/user-stories.md` | Created 2026-11-12 as a supplementary draft containing US-001 to US-010, mapped one-to-one to FR-001 to FR-010. **DRAFT — awaiting separate human review/approval.** Derived from approved requirements; it does not add or supersede acceptance criteria. |
| `pom.xml` | Existing and unchanged: groupId `org.capstone`, artifactId `expense-tracker`, 0.1.0-SNAPSHOT, `maven.compiler.release=21`, TestNG 7.11.0 (test), Lombok 1.18.46 (provided, with `annotationProcessorPaths`), compiler plugin 3.13.0, Surefire 3.5.2 (`useModulePath=false`). No `pom.xml` change is planned. |

Base package: `org.capstone.expense` (A-ARCH-01). Source root: `src/main/java/org/capstone/expense/`. Test root: `src/test/java/org/capstone/expense/`. No `src/` tree exists yet.

### 1.2 Baseline observations carried into the plan

| ID | Observation | Plan treatment |
|---|---|---|
| OBS-1 | Architecture uses `tx.type()`, `budgetAlert()` style accessor names in prose. Lombok `@Value` with no `lombok.config` generates `getType()`, `getBudgetAlert()` etc. | Developer uses Lombok default `getX()` accessors. No `lombok.config` file is created (not in approved architecture). Decision D-5. |
| OBS-2 | Architecture §3 test directory listing omits `ConsoleFormatterTest`, but §11.3 and DR corrections require it. | `ConsoleFormatterTest` is planned (IMP-012). Decision D-6. |
| OBS-3 | Architecture does not fix constructor signatures for console classes or exact `InputReader` method signatures, only names and wiring. | Tasks give *recommended* signatures marked as such. These are not business rules; developer may adjust while keeping approved names and responsibilities. |
| OBS-4 | `TransactionStore.replace` behaviour for an unknown ID is not specified. | Recommended: `IllegalArgumentException` (programming error; services always check existence first). Decision D-8, low impact. |
| OBS-5 | `docs/user-stories.md` is a new supplementary artifact and remains a draft until separately approved. | Use its IDs for traceability only; approved requirements and their acceptance criteria remain authoritative. Do not treat draft story wording as permission to change scope. |

---

## 2. Implementation Scope

**In scope:** everything in `docs/architecture.md` §3 — `Main`; `console` (`ConsoleApp`, `TransactionConsole`, `ReportConsole`, `InputReader`, `ConsoleFormatter`); `service` (`TransactionService`, `BudgetService`, `SummaryService`); `model` (`Category`, `ExpenseCategory`, `IncomeCategory`, `TransactionType`, `DeleteOutcome`, `Transaction`, `BudgetKey`, `CategorySpending`, `BalanceSummary`, `BudgetAlert`, `TransactionResult`); `storage` (`TransactionStore`, `BudgetStore`); `exception` (`ValidationException`, `TransactionNotFoundException`); the TestNG tests listed in §11.3; documentation, user-story traceability and changelog updates.

**Constraints preserved:** Java 21; Maven; TestNG 7.11.0 only; Lombok 1.18.46 on the six value classes of architecture §5.7 only; `BigDecimal` for money; `LocalDate` for dates; `Scanner` + `PrintStream`/`System.out` for console; in-memory only; single-user, single-threaded; no new dependencies, plugins, frameworks, utility packages or features.

**User-story relationship:** `docs/user-stories.md` is a supplementary draft traceability artifact. Its ten stories do not add scope beyond FR-001 to FR-010. No separate story-specific code, story-specific acceptance criteria, or additional implementation tasks are authorized by its creation.

**Out of scope:** see Section 10.

---

## 3. Architecture-to-Task Decomposition

| Architecture component (package) | Implementation task | Test task (same task unless noted) |
|---|---|---|
| Maven baseline, source tree | IMP-001 | `mvn test` baseline |
| `Category`, `ExpenseCategory`, `IncomeCategory`, `TransactionType`, `DeleteOutcome` (model) | IMP-002 | `CategoryTest` |
| `ValidationException`, `TransactionNotFoundException` (exception) | IMP-003 | exercised by service tests |
| `Transaction` (model) | IMP-004 | `TransactionTest` |
| `BudgetKey`, `CategorySpending`, `BalanceSummary`, `BudgetAlert`, `TransactionResult` (model) | IMP-005 | covered by store/service tests; Lombok scan in IMP-018 |
| `TransactionStore` (storage) | IMP-006 | `TransactionStoreTest` |
| `BudgetStore` (storage) | IMP-007 | `BudgetStoreTest` |
| `BudgetService` (service) | IMP-008 | `BudgetServiceTest` |
| `TransactionService`: add, find, list | IMP-009 | `TransactionServiceTest` (part 1) |
| `TransactionService`: edit, delete | IMP-010 | `TransactionServiceTest` (part 2) |
| `SummaryService` (service) | IMP-011 | `SummaryServiceTest` |
| `ConsoleFormatter` (console) | IMP-012 | `ConsoleFormatterTest` |
| `InputReader` (console) | IMP-013 | `InputReaderTest` |
| `TransactionConsole` (console) | IMP-014 | `TransactionConsoleTest` |
| `ReportConsole` (console) | IMP-015 | `ReportConsoleTest` |
| `ConsoleApp` (console) | IMP-016 | `ConsoleAppTest` |
| `Main` + end-to-end scenarios | IMP-017 | `ConsoleAppTest` end-to-end methods; scripted smoke run |
| Compliance scans (NFR-001..004, 006, 007) | IMP-018 | grep scans |
| Full build, warnings, NFR-009 inventory | IMP-019 | `mvn clean test` |
| Documentation, user-story traceability and changelogs | IMP-020 | document review |

Dependency direction follows architecture §4.1: `Main -> console -> service -> storage -> model`; `TransactionService -> BudgetService`; `BudgetService` never depends on `TransactionService`.

---

## 4. Ordered Implementation Tasks

**Conventions for all tasks**

- Owner: **Java Developer** implements production code *and* the unit tests listed in the same task (tests alongside implementation). **Test Engineer** performs independent verification in Stage 7. **Orchestrator** coordinates and holds approval gates. (Decision D-3.)
- Run Maven from the project root `expense-tracker-capstone/`. Primary command: `mvn test`. Single class: `mvn test -Dtest=ClassName -Dsurefire.failIfNoSpecifiedTests=false`.
- Verification outcomes state *expected* results only. Actual results must be reported from real command output; no result may be claimed before execution.
- No task commits or pushes. Commits require explicit human approval (CLAUDE.md §9, §10).
- A task is complete only per the Definition of Done (Section 13), not when files merely exist.
- Public methods of services must stay limited to those in architecture §6; helpers are `private` (NFR-009).
- Avoid the words `double`, `float`, `Double`, `Float` anywhere in `src/main`, including comments, so the AC-NFR-001 scan stays at zero hits.

### M1 — Project foundation

#### IMP-001 — Verify build baseline and create source tree
- **Objective:** Confirm the existing `pom.xml` supports the plan and the empty project builds.
- **Requirements / ACs:** NFR-005, NFR-006, NFR-007, NFR-008; AC-NFR-005 (baseline).
- **Architecture components:** `pom.xml` (read-only), directory layout §3.
- **Dependencies:** Human approval of this plan.
- **Files:** Create empty package directories only when the first class is added (Git ignores empty dirs, so no placeholder files). No `pom.xml` edit. Confirm `target/` is not tracked (report if `.gitignore` is missing; do not add unrelated files without asking).
- **Guidance:** Do not modify `pom.xml`. If `mvn test` fails for a reason attributable to `pom.xml` (e.g., Lombok processing, Surefire/TestNG discovery), stop and escalate to the Orchestrator (change control).
- **Acceptance criteria:** `mvn test` from project root ends `BUILD SUCCESS` with no tests run; Java 21 compiler release in effect; Lombok and TestNG resolve.
- **Verification:** `java -version`; `mvn -version`; `mvn test`. Expected: JDK 21 reported; `BUILD SUCCESS`.
- **Risks:** Local JDK not 21; offline dependency resolution. Escalate rather than change the pom.

### M2 — Domain model and exceptions

#### IMP-002 — Category interface, enums and type enums
- **Objective:** Implement the fixed category and type vocabulary.
- **Requirements / ACs:** FR-001, FR-002, FR-003, FR-004, C-011, C-012; AC-FR-003b (case-insensitive lookup).
- **Components:** `Category`, `ExpenseCategory`, `IncomeCategory`, `TransactionType`, `DeleteOutcome` in `model`.
- **Dependencies:** IMP-001.
- **Files:** `model/Category.java`, `ExpenseCategory.java`, `IncomeCategory.java`, `TransactionType.java`, `DeleteOutcome.java`; test `model/CategoryTest.java`.
- **Guidance:**
  - `TransactionType`: `EXPENSE`, `INCOME`. `DeleteOutcome`: `DELETED`, `CANCELLED`.
  - `ExpenseCategory`: `FOOD, TRANSPORT, SHOPPING, BILLS, HEALTH, ENTERTAINMENT, OTHER` (this order; list numbering `1..7` depends on it). `IncomeCategory`: `SALARY, BUSINESS, INVESTMENT, OTHER_INCOME`.
  - `Category` declares `TransactionType transactionType()`; static `Optional<Category> fromName(String)` trims and matches case-insensitively across both enums; null returns empty. No Lombok on enums or the interface (architecture §5.7).
- **Acceptance criteria:** Exact enum values and order; every expense value returns `EXPENSE`, every income value `INCOME`; `fromName("food")`, `"FOOD"`, `" Food "` all resolve; `fromName("CAR")` is empty; names unique across both enums.
- **Verification:** `mvn test -Dtest=CategoryTest`. Expected: all tests pass.
- **Risks:** Name collision between enums (`OTHER` vs `OTHER_INCOME` is already distinct); enum order change breaks numbered menus.

#### IMP-003 — Exception types
- **Objective:** Provide the two unchecked exceptions.
- **Requirements / ACs:** FR-010; AC-FR-003d, AC-FR-004c, AC-FR-005c, AC-FR-010a.
- **Components:** `exception.ValidationException`, `exception.TransactionNotFoundException`.
- **Dependencies:** IMP-001.
- **Files:** `exception/ValidationException.java`, `exception/TransactionNotFoundException.java`. No dedicated test class (architecture lists none); exercised by service and console tests.
- **Guidance:** Both extend `RuntimeException`, take a `String message`. `TransactionNotFoundException` may offer a constructor taking the `int id` that builds `"No transaction with ID <id>."` (message shown by console as `Error: No transaction with ID 42.`).
- **Acceptance criteria:** Both unchecked; messages retrievable via `getMessage()`; `TransactionNotFoundException` message matches architecture §10.2.
- **Verification:** `mvn -q compile`. Expected: success, no warnings on these files.

#### IMP-004 — `Transaction` entity with invariants
- **Objective:** Immutable transaction value with enforced invariants.
- **Requirements / ACs:** FR-001, FR-002, FR-004, A-003, A-004; AC-FR-001a, AC-FR-002a, AC-NFR-007; NFR-001, NFR-002.
- **Components:** `model.Transaction`.
- **Dependencies:** IMP-002.
- **Files:** `model/Transaction.java`; test `model/TransactionTest.java`.
- **Guidance:** Lombok `@Value` and `@Builder(toBuilder = true)`. Fields: `int id`, `TransactionType type`, `BigDecimal amount`, `Category category`, `LocalDate date`, `String description`. Because invariants must be enforced (architecture §5.4), write an explicit constructor that validates and place `@Builder(toBuilder = true)` on that constructor (with `@Value` on the class); throw `IllegalArgumentException` when: any field null (description null is rejected; callers pass `""`), `amount.compareTo(BigDecimal.ZERO) <= 0`, or `category.transactionType() != type`. Description is stored trimmed. Amount scale is preserved exactly as supplied.
- **Acceptance criteria:** Valid build succeeds with fields intact; zero/negative amount, null fields and type/category mismatch each throw `IllegalArgumentException`; `toBuilder()` yields a new instance with same ID and a changed field; amount `12.505` unchanged.
- **Verification:** `mvn test -Dtest=TransactionTest`. Expected: pass; `mvn -q compile` shows no Lombok warnings.
- **Risks:** Lombok `@Value` + explicit constructor + `@Builder` interplay (verify `toBuilder` works); do not use `double`.

#### IMP-005 — Remaining value types
- **Objective:** Create the five result/key value classes.
- **Requirements / ACs:** FR-006, FR-007, FR-008, FR-009; AC-NFR-007.
- **Components:** `BudgetKey`, `CategorySpending`, `BalanceSummary`, `BudgetAlert`, `TransactionResult`.
- **Dependencies:** IMP-002, IMP-004.
- **Files:** the five classes in `model/`.
- **Guidance:** Each is Lombok `@Value` (no records). Fields per architecture §5.5-5.6: `BudgetKey(ExpenseCategory category, int year, int month)`; `CategorySpending(ExpenseCategory category, BigDecimal total, BigDecimal percentage)`; `BalanceSummary(BigDecimal totalIncome, totalExpenses, netBalance)`; `BudgetAlert(ExpenseCategory category, BigDecimal budget, BigDecimal monthlySpend, int year, int month)`; `TransactionResult(Transaction transaction, Optional<BudgetAlert> budgetAlert)`. Do not add `MonthlyBudget` or other types.
- **Acceptance criteria:** All six value classes (with IMP-004) carry a Lombok annotation; `BudgetKey` equal when all three components equal.
- **Verification:** `mvn -q compile`; `grep -L "lombok" <the six files>` returns no file (run at IMP-018 too). `BudgetKey` equality is asserted in `BudgetStoreTest` (IMP-007).

### M3 — In-memory storage

#### IMP-006 — `TransactionStore`
- **Objective:** Ordered in-memory transaction storage with a monotonic ID sequence.
- **Requirements / ACs:** FR-001, FR-002, FR-005, A-004, NFR-003; AC-FR-003a, AC-FR-005a.
- **Components:** `storage.TransactionStore`.
- **Dependencies:** IMP-004.
- **Files:** `storage/TransactionStore.java`; test `storage/TransactionStoreTest.java`.
- **Guidance:** `LinkedHashMap<Integer, Transaction>`; `int nextId` starts at 1, never decremented. Operations: `nextId()`, `save(Transaction)` (reject existing ID), `replace(Transaction)` (keeps map position), `findById(int)` -> `Optional`, `remove(int)` -> `boolean`, `findAll()` and `findMatching(Predicate<Transaction>)` return unmodifiable copies in insertion order. No static state.
- **Acceptance criteria:** First ID is 1; IDs strictly increase and are not reused after `remove`; insertion order preserved; `replace` keeps position; returned lists cannot modify the store.
- **Verification:** `mvn test -Dtest=TransactionStoreTest`. Expected: pass.
- **Risks:** ID reuse regression (A-004); `replace` altering order.

#### IMP-007 — `BudgetStore`
- **Objective:** Monthly budget map with overwrite semantics.
- **Requirements / ACs:** FR-008; AC-FR-008a, AC-FR-008b.
- **Components:** `storage.BudgetStore`, `BudgetKey`.
- **Dependencies:** IMP-005.
- **Files:** `storage/BudgetStore.java`; test `storage/BudgetStoreTest.java`.
- **Guidance:** `HashMap<BudgetKey, BigDecimal>`; `put(BudgetKey, BigDecimal)` plain overwrite; `Optional<BigDecimal> find(BudgetKey)`.
- **Acceptance criteria:** `put` then `find` returns the value; second `put` replaces the first and the old value is not retrievable; absent key returns empty; two `BudgetKey` instances with the same components find the same entry.
- **Verification:** `mvn test -Dtest=BudgetStoreTest`. Expected: pass.

### M4 — Services

#### IMP-008 — `BudgetService`
- **Objective:** Budget validation, upsert and alert evaluation.
- **Requirements / ACs:** FR-008, FR-009, NFR-001; AC-FR-008a, 008b, AC-FR-009a-d.
- **Components:** `service.BudgetService`, `BudgetStore`, `TransactionStore`, `BudgetAlert`.
- **Dependencies:** IMP-006, IMP-007.
- **Files:** `service/BudgetService.java`; test `service/BudgetServiceTest.java`.
- **Guidance:** Constructor `(BudgetStore, TransactionStore)`. Public methods only: `setBudget(ExpenseCategory, int year, int month, BigDecimal amount)` (void), `getBudget(...)` -> `Optional<BigDecimal>`, `evaluateAlert(Transaction)` -> `Optional<BudgetAlert>`. Validation: year 1000-9999; month 1-12; amount non-null and `> 0` (messages must be specific, user-readable `ValidationException`s, e.g. "Amount must be greater than zero."). `getBudget` applies the same year/month check. `evaluateAlert` exactly per architecture §6.2: empty if type is not `EXPENSE`; empty if no budget for (category, year, month of date); sum same-category same-year-month `EXPENSE` transactions from `findMatching` starting at `BigDecimal.ZERO`; alert when `monthlySpend.compareTo(budget) >= 0` (never `equals`). `setBudget` never evaluates alerts (OQ-08).
- **Acceptance criteria:** All edge cases of Section 7 for `BudgetServiceTest` pass; alert at spend `500.00` vs budget `500` fires (AC-FR-009d); the alert object contains category, budget, monthly spend, year, month.
- **Verification:** `mvn test -Dtest=BudgetServiceTest`. Expected: pass.
- **Risks (high care):** `BigDecimal.equals` scale trap; month boundary (`getYear`/`getMonthValue`); summing the just-saved transaction exactly once (it is already in the store when evaluated).

#### IMP-009 — `TransactionService`: add, find, list
- **Objective:** Add expense/income, lookup and the three list operations.
- **Requirements / ACs:** FR-001, FR-002, FR-003, FR-009 (trigger); AC-FR-001a, 001b, 002a, 002b, 003a-d, 009a-c (via result).
- **Components:** `service.TransactionService`, `TransactionStore`, `BudgetService`, `TransactionResult`, `Category.fromName`.
- **Dependencies:** IMP-008, IMP-005, IMP-003.
- **Files:** `service/TransactionService.java` (add/find/list now; edit/delete in IMP-010); test `service/TransactionServiceTest.java`.
- **Guidance:** Constructor `(TransactionStore, BudgetService)`. `addExpense(BigDecimal, ExpenseCategory, LocalDate, String)` and `addIncome(BigDecimal, IncomeCategory, LocalDate, String)`: validate amount non-null and `> 0` (`ValidationException("Amount must be greater than zero.")`), category and date non-null, description null -> `""` and trimmed; validate *before* allocating an ID so a rejected add consumes no ID and stores nothing; then allocate ID, build `Transaction`, `save`, return `TransactionResult` (alert from `evaluateAlert` for expense; `Optional.empty()` for income). `findById(int)` throws `TransactionNotFoundException`. `listAll()`, `listByCategory(String)` (unknown name -> `ValidationException`), `listByDateRange(start, end)` (null -> `ValidationException`; `start.isAfter(end)` -> `ValidationException`; inclusive bounds). All lists in insertion order.
- **Acceptance criteria:** AC-FR-001a/b, 002a/b, 003a-d satisfied at service level; shared ID sequence for expenses and incomes; rejected add leaves store count unchanged.
- **Verification:** `mvn test -Dtest=TransactionServiceTest`. Expected: pass.
- **Risks:** Consuming an ID on a failed add (validate first); category filter must match income names too.

#### IMP-010 — `TransactionService`: edit and delete
- **Objective:** Atomic edit (including type change) and confirmed delete.
- **Requirements / ACs:** FR-004, FR-005, FR-009; AC-FR-004a-c, AC-FR-005a-c.
- **Components:** `TransactionService`, `TransactionStore.replace/remove`, `Transaction.toBuilder`, `BudgetService.evaluateAlert`, `DeleteOutcome`.
- **Dependencies:** IMP-009.
- **Files:** `service/TransactionService.java` (add methods); tests in `TransactionServiceTest`.
- **Guidance:** `editTransaction(int id, TransactionType type, BigDecimal amount, Category category, LocalDate date, String description)`: find existing (else `TransactionNotFoundException`); validate everything (amount `> 0`, `category.transactionType() == type` else `ValidationException("Category X is not valid for INCOME.")`, date non-null) **before** touching the store; build new `Transaction` via `toBuilder()` (same ID), `replace`, evaluate alert for the new category/month (empty if the new type is `INCOME`). `deleteTransaction(int id, boolean confirmed)`: not found -> `TransactionNotFoundException` (even if `confirmed` is false); `confirmed` -> remove and return `DELETED`; else `CANCELLED` and no change. The service never prompts or prints.
- **Acceptance criteria:** Edit of amount only leaves other fields identical; EXPENSE->INCOME edit with valid income category succeeds and the record no longer counts in expense summaries; mismatched category rejected and stored record unchanged; non-existent ID throws for edit and delete and leaves store unchanged; deleted ID never reused by a later add.
- **Verification:** `mvn test -Dtest=TransactionServiceTest`. Expected: pass.
- **Risks (high care):** Type-change correctness; partial application on invalid input (atomicity, ADR-003); alert evaluated for the *new* month only.

#### IMP-011 — `SummaryService`
- **Objective:** Category spending summary and balance summary.
- **Requirements / ACs:** FR-006, FR-007, NFR-001; AC-FR-006a, 006b, 007a, 007b.
- **Components:** `service.SummaryService`, `CategorySpending`, `BalanceSummary`.
- **Dependencies:** IMP-006, IMP-005.
- **Files:** `service/SummaryService.java`; test `service/SummaryServiceTest.java`.
- **Guidance:** Constructor `(TransactionStore)`. Public: `spendingByCategory()` and `balanceSummary()`. Spending: filter `EXPENSE`, sum per category with `BigDecimal`, omit categories without spend, per-category `percentage = total.multiply(100).divide(grandTotal, 2, RoundingMode.HALF_UP)` computed independently (no largest-remainder fix-up), sort by total descending, ties by enum ordinal ascending; empty list if no expenses. Balance: income and expense sums starting at `BigDecimal.ZERO`; `net = income.subtract(expenses)`.
- **Acceptance criteria:** Counterexample (six categories at 2857.00, one at 2858.00) produces seven rows at 14.29 each, sum 100.03, within `0.005 x 7 = 0.035` of 100.00; empty store gives `BigDecimal.ZERO` for all three balance values; negative net supported; income never appears in spending summary.
- **Verification:** `mvn test -Dtest=SummaryServiceTest`. Expected: pass.
- **Risks (high care):** Rounding mode/scale; `compareTo` for comparisons; tolerance test must use `n` = displayed rows.

**Checkpoint CP-A (end of M4):** `mvn test` passes for all model, storage and service tests; every public service method has a test (NFR-009 interim check). See Section 8.

### M5 — Console layer

#### IMP-012 — `ConsoleFormatter`
- **Objective:** Stable, locale-independent output formatting.
- **Requirements / ACs:** FR-003, FR-006, FR-007, FR-009, NFR-001; AC-FR-009a (message content).
- **Components:** `console.ConsoleFormatter`.
- **Dependencies:** IMP-005.
- **Files:** `console/ConsoleFormatter.java`; test `console/ConsoleFormatterTest.java`.
- **Guidance:** `money(BigDecimal)` = `setScale(2, RoundingMode.HALF_UP).toPlainString()`; `percent(BigDecimal)` same; `transactionRow(Transaction)` renders `ID | Type | Date | Category | Amount | Description` on a single line, date `yyyy-MM-dd` (a header line helper is allowed as long as it is not public API of a service); `budgetWarning(BudgetAlert)` = `WARNING: FOOD budget of 500.00 reached for 2024-11. Total spend this month: 512.30.` (month zero-padded). No `Locale`-dependent calls, no grouping separators.
- **Acceptance criteria:** `12.505 -> 12.51`; `12.5 -> 12.50`; `0 -> 0.00`; negative values keep minus sign; `1234567.891 -> 1234567.89`; warning text contains category name, budget and spend at 2 dp.
- **Verification:** `mvn test -Dtest=ConsoleFormatterTest`. Expected: pass.

#### IMP-013 — `InputReader`
- **Objective:** All prompting and syntactic parsing, using one injected `Scanner`, `PrintStream` and `Clock`.
- **Requirements / ACs:** FR-001, FR-002, FR-010, NFR-002, A-006; AC-FR-001c, AC-FR-010a.
- **Components:** `console.InputReader`.
- **Dependencies:** IMP-002, IMP-003.
- **Files:** `console/InputReader.java`; test `console/InputReaderTest.java`.
- **Guidance:**
  - Constructor `(Scanner, PrintStream, Clock)`. No other `Scanner` is created anywhere.
  - Methods named in architecture §4: `readLine`, `readOptionalLine`, `readMenuChoice`, `readAmount`, `readDate`, `readOptionalDate`, `readInt`, `readCategory`, `readConfirmation`. Recommended behaviour: `readDate` (add flows) returns today via `LocalDate.now(clock)` on blank; `readOptionalDate` (edit flows) returns an empty `Optional` on blank; `readOptionalLine` returns empty on blank.
  - Every line is trimmed. A syntax or range error throws `ValidationException` with a specific message (flows abort to menu; no re-prompt, ADR-012).
  - Amount: trimmed text must match `^[+-]?\d+(\.\d+)?$` before `new BigDecimal(text)`; signs accepted (so the service rejects `-5` with its own message); no decimal-place limit; `NumberFormatException` still caught as a safety net.
  - Date: `DateTimeFormatter.ofPattern("yyyy-MM-dd")`, `LocalDate.parse`, then require `formatter.format(parsed).equals(text)`; `DateTimeParseException` becomes a `ValidationException` naming the expected format (ADR-010).
  - Menu choice messages: `Invalid choice 'abc'. Please enter a number from 1 to 9.` and `Choice 12 is out of range. Please enter a number from 1 to 9.`
  - `readCategory` prints a numbered list for the supplied category type and returns the chosen value; out of range/non-numeric -> `ValidationException`.
  - `readConfirmation`: `y`/`n` case-insensitive; anything else -> `ValidationException`.
  - **Do not catch `NoSuchElementException`**; EOF must propagate to `ConsoleApp` (DR-001).
- **Acceptance criteria:** All Section 7 `InputReaderTest` cases pass, including the four dedicated date tests (blank = today with a fixed `Clock`; `2026-02-30` rejected; `2026-1-5` rejected; `2024-02-29` accepted).
- **Verification:** `mvn test -Dtest=InputReaderTest`. Expected: pass.
- **Risks (high care):** The assumption that the default resolver clips `2026-02-30` is unverified (ADR-010); the dedicated test is the evidence. Time-dependent tests must use a fixed `Clock` (e.g., `Clock.fixed(Instant.parse("2026-11-12T00:00:00Z"), ZoneOffset.UTC)`).

#### IMP-014 — `TransactionConsole`
- **Objective:** Flows for add expense, add income, list, edit, delete.
- **Requirements / ACs:** FR-001-FR-005, FR-009 (display), FR-010; AC-FR-001a-c, 002a-b, 003a-d, 004a-c, 005a-c, 009a-d (display).
- **Components:** `console.TransactionConsole`, `TransactionService`, `InputReader`, `ConsoleFormatter`.
- **Dependencies:** IMP-009, IMP-010, IMP-012, IMP-013.
- **Files:** `console/TransactionConsole.java`; test `console/TransactionConsoleTest.java`.
- **Guidance:** Public methods `addExpenseFlow()`, `addIncomeFlow()`, `listFlow()`, `editFlow()`, `deleteFlow()` (constructor recommended `(TransactionService, InputReader, ConsoleFormatter, PrintStream)`). Follow architecture §7.3-7.4 exactly:
  - Add: prompts Amount (`Amount (e.g. 12.50):`) -> Category (numbered list) -> Date (`Date (yyyy-MM-dd, Enter = today):`) -> Description (optional); collect everything, make one service call; print confirmation then the budget warning (if present) via `ConsoleFormatter.budgetWarning`.
  - List: `1. All  2. By category  3. By date range`; empty result prints `No transactions found.`
  - Edit: ID -> `findById` (missing ID aborts) -> display current values -> Type (always prompted; Enter = keep, `1` Expense, `2` Income; DR-004) -> Amount (Enter = keep) -> Category (**always** asked, numbered list for the *resulting* type; Enter does not keep) -> Date (Enter = keep) -> Description (Enter = keep) -> one `editTransaction` call with the full target state; print confirmation and any warning.
  - Delete: ID -> display full transaction -> `Delete this transaction? (y/n):` -> `y` calls `deleteTransaction(id, true)`; `n` prints `Deletion cancelled.` (and may call `deleteTransaction(id, false)`); anything else is an error and nothing is deleted.
  - Flows do not catch `ValidationException`/`TransactionNotFoundException`; they propagate to the `ConsoleApp` boundary (architecture §10.2). Flows never call each other.
- **Acceptance criteria:** Scripted-input tests show expected messages for each flow (Section 7); budget warning printed after confirmation; invalid input aborts with no stored change.
- **Verification:** `mvn test -Dtest=TransactionConsoleTest`. Expected: pass.
- **Risks:** Edit-flow "blank = keep" resolution; the type-change category prompt; ensuring no partial state after an aborted flow.

#### IMP-015 — `ReportConsole`
- **Objective:** Flows for spending summary, balance summary and set budget.
- **Requirements / ACs:** FR-006, FR-007, FR-008; AC-FR-006a, 007a-b, 008a-b.
- **Components:** `console.ReportConsole`, `SummaryService`, `BudgetService`, `InputReader`, `ConsoleFormatter`.
- **Dependencies:** IMP-008, IMP-011, IMP-012, IMP-013.
- **Files:** `console/ReportConsole.java`; test `console/ReportConsoleTest.java`.
- **Guidance:** `spendingSummaryFlow()` prints a table (category, total, percentage at 2 dp) or `No expense transactions recorded.`; `balanceSummaryFlow()` prints three labelled lines at 2 dp (negative net shown with minus sign); `setBudgetFlow()` prompts Category (numbered `ExpenseCategory` list) -> Year -> Month -> Amount, calls `getBudget` beforehand to decide whether to print `Budget saved (overwrites any previous value)` or a plain saved message, then `setBudget`. Setting a budget never prints an alert (OQ-08).
- **Acceptance criteria:** Output at 2 dp; empty-summary message; overwrite message shown on second set; no `WARNING` text after `setBudgetFlow` even when spend already exceeds the budget.
- **Verification:** `mvn test -Dtest=ReportConsoleTest`. Expected: pass.

#### IMP-016 — `ConsoleApp`
- **Objective:** Main menu loop, dispatch, error boundary and EOF handling.
- **Requirements / ACs:** FR-010, UC-01; AC-FR-010a, 010b, 010c.
- **Components:** `console.ConsoleApp`.
- **Dependencies:** IMP-014, IMP-015.
- **Files:** `console/ConsoleApp.java`; test `console/ConsoleAppTest.java`.
- **Guidance:** `run()` loops: print the menu exactly as architecture §7.2 (options 1-8 per A-ARCH-02, `9. Exit`), read choice via `readMenuChoice(1, 9)`, dispatch (1 add expense, 2 add income, 3 list, 4 edit, 5 delete, 6 spending summary, 7 balance summary, 8 set budget), return on 9. Catch, per iteration and in this order: `NoSuchElementException` -> print `Input closed. Exiting.` and **return** (no menu re-entry; DR-001); `ValidationException` and `TransactionNotFoundException` -> print `Error: <message>` and continue; any other `RuntimeException` -> print `Unexpected error. Returning to the menu.` (stack trace, if any, only to `System.err`) and continue. No `System.exit` anywhere (A-ARCH-10). Catch order matters: `NoSuchElementException` is a `RuntimeException`, so handle it before the generic catch.
- **Acceptance criteria:** Non-numeric menu input prints the specific error and redisplays the menu with no exception escaping; valid choice reaches the corresponding flow; `9` returns normally; EOF anywhere prints `Input closed. Exiting.` and returns without redisplaying the menu; unexpected runtime exception prints the fixed message and returns to the menu.
- **Verification:** `mvn test -Dtest=ConsoleAppTest`. Expected: pass.
- **Risks (high care):** Catch ordering; infinite loop on EOF if `NoSuchElementException` were swallowed.

### M6 — Integration and quality

#### IMP-017 — `Main` composition root and end-to-end scenarios
- **Objective:** Wire the object graph and verify full workflows through the real console classes.
- **Requirements / ACs:** FR-010, FR-001-FR-009 end to end; AC-FR-010b, 010c, AC-FR-009a-d; NFR-004.
- **Components:** `Main`, all layers.
- **Dependencies:** IMP-016.
- **Files:** `Main.java`; end-to-end test methods added to `console/ConsoleAppTest.java` (wired with real stores/services, `Scanner` over `ByteArrayInputStream`, `PrintStream` over `ByteArrayOutputStream`, fixed `Clock`; no new test class is introduced).
- **Guidance:** `Main.main` per architecture §7.1: one `Scanner(System.in)`, `System.out`, `Clock.systemDefaultZone()`, stores, services, `InputReader`, `ConsoleFormatter`, console classes, `new ConsoleApp(...).run()`, then return. No `System.exit`.
- **End-to-end scenarios (each its own test method):** (1) add expense -> list -> delete confirm `y` -> list empty; (2) set budget 500 -> add expense 500.00 same month -> warning printed; (3) type change during edit EXPENSE->INCOME -> balance summary reflects change; (4) invalid menu input then Exit; (5) EOF mid-flow terminates cleanly.
- **Acceptance criteria:** All scenarios pass; smoke run exits with status 0.
- **Verification:** `mvn test`; then `mvn -q compile` and `printf '9\n' | java -cp target/classes org.capstone.expense.Main; echo "exit=$?"` (Git Bash). Expected: menu printed once; `exit=0`.

**Checkpoint CP-B (end of M5/M6 integration):** all components integrated; human review recommended before Stage 6 (Section 9).

#### IMP-018 — Compliance scans (preliminary)
- **Objective:** Run the static checks for the non-functional ACs and record actual output.
- **Requirements / ACs:** NFR-001, 002, 003, 004, 006, 007; AC-NFR-001, 002, 003, 004, 006, 007.
- **Dependencies:** IMP-017.
- **Files:** none created. Results recorded in `docs/changelog.md` (summary) and later `docs/test-report.md` by the Test Engineer.
- **Commands (Git Bash, project root) and expected output:**

| AC | Command | Expected |
|---|---|---|
| AC-NFR-001 | `grep -rnE "\b(double\|float\|Double\|Float)\b" src/main` | no output |
| AC-NFR-002 | `grep -rnE "java\.util\.(Date\|Calendar)" src/main` | no output |
| AC-NFR-003 | `grep -rnE "import java\.(io\|nio\|sql\|net)\." src/main` **and** `grep -rnE "java\.(io\|nio\|sql\|net)[^s]" src/main` (check both imports and fully qualified references) | only `java.io.PrintStream` imports and `System.in`/`System.out` usage permitted in the `console` package; no file I/O (`java.io.*`, `java.nio.*`), network I/O, or database I/O (`java.sql.*`); no `java.net.*` classes (C-5) |
| A-ARCH-10 | `grep -rnE "System\.exit" src/main` | no output |
| AC-NFR-004 | `grep -rnE "import (javax\.swing\|java\.awt\|javafx\|java\.util\.logging\|org\.slf4j\|org\.apache\.log4j\|org\.apache\.logging)" src/main` | no output |
| AC-NFR-006 | `grep -rn "org.junit" src/test` | no output |
| AC-NFR-007 | `grep -L "lombok" src/main/java/org/capstone/expense/model/{Transaction,BudgetKey,CategorySpending,BalanceSummary,BudgetAlert,TransactionResult}.java` | no output (each file mentions Lombok) |

  In the `\|` forms above, use a plain `|` inside the grep alternation; the backslash is only a Markdown table escape.
- **Acceptance criteria:** Every expected result observed; any hit is investigated and fixed or escalated.
- **Verification:** actual command output reported verbatim in summary form. This does not replace the Stage 7 evidence.

#### IMP-019 — Full build verification and NFR-009 inventory
- **Objective:** Confirm the whole build and the public-method test inventory.
- **Requirements / ACs:** NFR-005, NFR-008, NFR-009; AC-NFR-005, 008, 009.
- **Dependencies:** IMP-018.
- **Files:** none created.
- **Commands and expected results:**
  - `mvn clean test` -> `BUILD SUCCESS`, `Failures: 0, Errors: 0` (actual test counts reported from output).
  - `mvn clean compile -Dmaven.compiler.showWarnings=true` -> zero `[WARNING]` lines originating from `src/main` compilation (AC-NFR-008). Do not edit `pom.xml` to achieve this; if a Lombok or compiler note appears, escalate (architecture §14.3 risk).
  - NFR-009 inventory: list every `public` method in `src/main/java/org/capstone/expense/service/*.java` (`grep -n "public " ...`) and map each to at least one `@Test` method; confirm the seven mandatory edge-case tests exist as separate methods (Section 7).
- **Acceptance criteria:** All three hold with actual evidence; inventory table complete.

#### IMP-020 — Documentation and changelog updates
- **Objective:** Keep audit trail and traceability current.
- **Dependencies:** IMP-019 (and incrementally after each milestone).
- **Owner:** Documentation Agent (when delegated by the Orchestrator), otherwise Java Developer for entries about own tasks.
- **Files:** `docs/changelog.md` (append rows per milestone, with evidence and status; no approval claims), root `CHANGELOG.md` (application changes), `docs/implementation-plan.md` task status only (never plan scope), and `docs/user-stories.md` only if an approved clarification requires a traceability/status correction. Do not create `docs/test-report.md`/`code-review.md` content (later stages).
- **User-story handling:** Preserve US-001 to US-010 and their FR mappings. Do not silently expand stories, add acceptance criteria, or change their approval status. Any story change must follow the same change-control path as the requirements/architecture.
- **Acceptance criteria:** Entries state only what was actually done and observed; related requirement and user-story IDs remain traceable. Documentation updates do not imply human approval.

---

## 5. Task Dependencies and Execution Order

```
IMP-001
  |-- IMP-002 --+-- IMP-004 -- IMP-005 -- IMP-007
  |-- IMP-003   |                |            |
  |             +----------------+-- IMP-006 --+
                                       |        |
                          IMP-008 (needs 006, 007)
                             |            \
                          IMP-009 -- IMP-010   IMP-011 (needs 005, 006)
  IMP-012 (needs 005)   IMP-013 (needs 002, 003)
       \                    /
        IMP-014 (needs 009, 010, 012, 013)    IMP-015 (needs 008, 011, 012, 013)
                  \                           /
                          IMP-016 -- IMP-017 -- IMP-018 -- IMP-019 -- IMP-020
```

**Recommended linear execution order** (sequential, single developer agent; all dependencies satisfied):

`IMP-001 -> 002 -> 003 -> 004 -> 005 -> 006 -> 007 -> 008 -> 009 -> 010 -> 011 -> 012 -> 013 -> 014 -> 015 -> 016 -> 017 -> 018 -> 019 -> 020`

Parallelisable (if the Orchestrator chooses): IMP-011 with IMP-009/010; IMP-012 and IMP-013 with M4 work. Not required.

---

## 6. Milestones

| Milestone | Tasks | Exit criteria | Human checkpoint |
|---|---|---|---|
| **M1 Foundation** | IMP-001 | Baseline `mvn test` BUILD SUCCESS | none |
| **M2 Domain model** | IMP-002-005 | Model + exceptions compile; `CategoryTest`, `TransactionTest` pass | none |
| **M3 Storage** | IMP-006-007 | Store tests pass | none |
| **M4 Services** | IMP-008-011 | All service tests pass; NFR-009 interim inventory | **CP-A (recommended)**: brief human review of services/tests before console work |
| **M5 Console** | IMP-012-016 | Console tests pass | none unless design change arises |
| **M6 Integration and quality** | IMP-017-020 | `mvn clean test` green; scans clean; docs updated | **CP-B (recommended)**: human review of implementation evidence before Stage 6 |

Mandatory human gates remain those in `CLAUDE.md` section 6: plan approval (before any code), milestone approval where significant scope or design changes occur, independent code review findings, final verification, PR creation.

---

## 7. Test Planning Matrix

All tests: TestNG 7.11.0 only (`@Test`, `@BeforeMethod`, `@DataProvider`). Fresh stores/services per `@BeforeMethod`; explicit dates in service tests; fixed `Clock` for console tests; `Scanner` over `ByteArrayInputStream` and `PrintStream` over `ByteArrayOutputStream` for console tests. Method names below are recommended, not mandated.

| Test class | Task | Required test cases | ACs / requirements |
|---|---|---|---|
| `CategoryTest` | IMP-002 | exact expense and income enum values; `transactionType()` for every value; `fromName` case-insensitive and trimming; unknown name empty; names unique across both enums | FR-003, C-011, C-012, AC-FR-003b |
| `TransactionTest` | IMP-004 | valid build; `zeroAmountRejected`; `negativeAmountRejected`; null fields rejected; category/type mismatch rejected; `toBuilder` new instance, same ID; scale `12.505` preserved | FR-001, FR-002, A-003 |
| `TransactionStoreTest` | IMP-006 | first ID 1; IDs increase; IDs not reused after remove; duplicate-ID save rejected; insertion order; replace keeps position; remove true/false; findById present/absent; findMatching; returned lists unmodifiable | A-004, AC-FR-003a, AC-FR-005a |
| `BudgetStoreTest` | IMP-007 | put/find; absent empty; `putOverwritesExistingValue`; key equality by value | AC-FR-008a, 008b |
| `BudgetServiceTest` | IMP-008 | set/get happy path; **`setBudgetOverwritesExistingBudget`**; zero, negative, null amount rejected; year 999 and 10000 rejected; month 0 and 13 rejected; `getBudget` absent empty and invalid range rejected; **`alertFiresAtExactly100Percent`** (budget `500`, spend `500.00`); alert above budget; no alert below; no alert when no budget; no alert for INCOME; sums only same category and same month; other month/category ignored; alert carries category, budget, spend, year, month | AC-FR-008a/b, AC-FR-009a-d, NFR-001 |
| `TransactionServiceTest` | IMP-009, 010 | add expense fields intact; **`addExpenseRejectsZeroAmount`**; **`addExpenseRejectsNegativeAmount`**; same two for income; rejected add stores nothing and consumes no ID; null description becomes empty and description trimmed; amount `12.505` stored unchanged; shared ID sequence; **`findByIdNonExistentThrows`**; `listAll` insertion order and count; `listByCategory` for `FOOD`/`food`/`Food` and an income name; **`listByCategoryUnknownThrows`**; `listByDateRange` inclusive start and end, start==end, none in range empty; **`listByDateRangeStartAfterEndThrows`** (store unchanged); edit amount only; **`editTypeChangeExpenseToIncome`** (valid income category, absent from expense summary); edit category/type mismatch rejected and record unchanged; **`editNonExistentIdThrows`** (store unchanged); invalid edit amount leaves record unchanged; edit keeps list position; edit moved to other month alerts only for the new month; edit returns alert at budget; delete `true` removes; delete `false` keeps and returns `CANCELLED`; **`deleteNonExistentIdThrows`**; ID not reused after delete then add; add expense returns alert when budget met, income returns empty alert | FR-001-FR-005, AC-FR-001a/b, 002a/b, 003a-d, 004a-c, 005a-c, 009 (trigger) |
| `SummaryServiceTest` | IMP-011 | sorted descending; categories without spend omitted; ties by ordinal; per-item `HALF_UP` (e.g. exact 14.285 -> 14.29); counterexample seven rows sum 100.03 within `0.005 x 7`; single category 100.00; empty list when no expenses; income ignored; balance normal; **`balanceSummaryEmptyIsZero`** (all `BigDecimal.ZERO`); balance negative net; decimal exactness (`0.1 + 0.2 = 0.3`) | AC-FR-006a/b, 007a/b, NFR-001 |
| `ConsoleFormatterTest` | IMP-012 | money 2 dp and `HALF_UP` (`12.505` -> `12.51`); `12.5` -> `12.50`; zero; negative; no grouping/locale; percent; row field order and date format; warning text contains category, budget, spend and `yyyy-MM` | NFR-001, AC-FR-009a |
| `InputReaderTest` | IMP-013 | menu valid, non-numeric message, out-of-range message; amount valid (`12`, `12.5`, `12.50`, `12.505`); amount rejected via data provider: blank, `abc`, `1e3`, `1,000`, `.5`, `5.`, `12 50`; signed amounts accepted syntactically (`-5`, `+5`); **`blankDateDefaultsToToday`**; **`rejectsImpossibleCalendarDate`** (`2026-02-30`); **`rejectsMalformedDate`** (`2026-1-5`, `05/01/2026`); **`acceptsLeapDay`** (`2024-02-29`); optional date/line blank -> empty; `readInt`; category valid and `0`, `8`, `abc` rejected; confirmation `y`/`Y`/`n`/`N` ok and `maybe` rejected; trimming; **`eofPropagatesNoSuchElementException`** | AC-FR-001c, AC-FR-010a, NFR-002, OQ-04, OQ-05 |
| `TransactionConsoleTest` | IMP-014 | add expense success output; blank date defaults to today; zero amount aborts with error and nothing stored; add income no warning; budget warning printed after expense confirmation; list all/category/date range; empty result message; unknown category and reversed range show error; edit: Enter keeps type, change type forces category from new list, blank fields keep values, unknown ID aborts; delete `y`, `n` ("Deletion cancelled."), other answer errors and keeps, unknown ID errors | AC-FR-001-005, AC-FR-009a-d (display) |
| `ReportConsoleTest` | IMP-015 | spending table at 2 dp sorted; empty-summary message; balance three lines incl. negative net; set budget success; overwrite message on second set; invalid year/month/amount show error; **no warning after setting a budget already exceeded** (OQ-08) | AC-FR-006a, 007a/b, 008a/b |
| `ConsoleAppTest` | IMP-016, 017 | menu text; non-numeric then valid choice (AC-FR-010a); out-of-range choice; each option dispatches; **Exit returns normally** (AC-FR-010c); `ValidationException` shown as `Error: ...` then menu again; unexpected `RuntimeException` shows fixed message and menu again; **EOF prints "Input closed. Exiting." and returns without redisplaying menu** (DR-001); five end-to-end scenarios of IMP-017 | AC-FR-010a-c, DR-001 |

**Mandatory NFR-009 edge cases (each its own method):** zero amount, negative amount, non-existent ID, start > end date range, type change during edit, budget overwrite, budget alert at exactly 100% (bold above).

**Additional planned methods (architecture §11.4):** `12.505` stored and displayed as `12.51`; seven-category percentage tolerance; unknown category filter throws; reversed date range throws and returns nothing; EOF clean termination.

**Regression areas to re-run after changes:** `TransactionStore` (ID/ordering), `BudgetService.evaluateAlert`, `TransactionService.editTransaction`, `InputReader` date and amount parsers, `ConsoleApp` catch ordering. Re-run full `mvn test` after each task, not only the new class.

---

## 8. Integration Checkpoints

| ID | Components | Preconditions | Verification | Acceptance |
|---|---|---|---|---|
| INT-1 (after IMP-010) | `TransactionService` + `BudgetService` + both stores | IMP-006..010 complete | `mvn test -Dtest=TransactionServiceTest,BudgetServiceTest` | Add/edit returns alert at >= 100%; income never alerts; edit to another month alerts only for the new month |
| INT-2 (after IMP-011) | `SummaryService` + `TransactionService` | IMP-009..011 | `mvn test -Dtest=SummaryServiceTest,TransactionServiceTest` | Type change moves a record out of expense summary into income total |
| INT-3 (after IMP-016) | Console classes + services (real instances) | IMP-012..016 | `mvn test -Dtest=ConsoleAppTest,TransactionConsoleTest,ReportConsoleTest` | Scripted sessions produce expected output; errors never escape; EOF exits cleanly |
| INT-4 (after IMP-017) | `Main` + everything | IMP-017 | Full `mvn test`; scripted smoke run `printf '9\n' \| java -cp target/classes org.capstone.expense.Main` | Process exits 0; menu shown once |

---

## 9. Human Review and Approval Checkpoints

| ID | When | Type | Decision requested |
|---|---|---|---|
| HC-0 | Now | **Mandatory (Stage 4 gate)** | Approve, amend or reject this plan. D-1 and D-2 were resolved during planning; D-3 through D-9 were accepted during planning discussions and are incorporated into this plan, subject to final Stage 4 approval. Confirm whether the supplementary user-story document is acceptable as a draft traceability artifact. No code before explicit approval. |
| CP-A | End of M4 | Recommended | Review service layer and tests; confirm business rules before console work. |
| CP-B | End of M6 | Recommended, **mandatory if any scope/design change occurred** | Review implementation evidence before Stage 6 code review. |
| CC | Any time | Change control | Any gap or conflict found during implementation is escalated to the Orchestrator and human; no silent change of requirements or architecture. |
| Later | Stages 6-8 | Mandatory (CLAUDE.md §6) | Code review findings, final verification, PR creation, plus explicit approval before any commit/push. |

---

## 10. Out-of-Scope Work

Persistence of any kind; REST/HTTP; web or GUI; Spring or any DI framework; authentication; concurrency; cloud/external services; i18n; recurring transactions; data export; clear-all/reset option; new categories or fields; per-field re-prompting (ADR-012 aborts to menu); editing budgets outside FR-008; budget alert on budget save (OQ-08); `lombok.config`, `util`/`common` packages, new dependencies or plugins, `testng.xml`, additional model types (e.g. `MonthlyBudget`, `YearMonth` keys); changes to `pom.xml` unless escalated and approved; Stage 6-8 artefacts (`docs/code-review.md`, `docs/test-report.md`, PR documents).

---

## 11. Requirement-to-Task Traceability Matrix

### 11.1 Functional

| Req | User story | Acceptance criteria | Architecture components | Tasks | Test classes |
|---|---|---|---|---|---|
| FR-001 | US-001 | AC-FR-001a, b, c | `Transaction`, `TransactionStore`, `TransactionService.addExpense`, `InputReader`, `TransactionConsole.addExpenseFlow` | IMP-002, 004, 006, 009, 013, 014 | TransactionTest, TransactionStoreTest, TransactionServiceTest, InputReaderTest, TransactionConsoleTest |
| FR-002 | US-002 | AC-FR-002a, b | `TransactionService.addIncome`, `TransactionConsole.addIncomeFlow` | IMP-002, 004, 006, 009, 013, 014 | TransactionTest, TransactionStoreTest, TransactionServiceTest, InputReaderTest, TransactionConsoleTest |
| FR-003 | US-003 | AC-FR-003a, b, c, d | `TransactionService` list ops, `Category.fromName`, `ConsoleFormatter`, `TransactionConsole.listFlow` | IMP-002, 006, 009, 012, 013, 014 | CategoryTest, TransactionStoreTest, TransactionServiceTest, ConsoleFormatterTest, InputReaderTest, TransactionConsoleTest |
| FR-004 | US-004 | AC-FR-004a, b, c | `TransactionService.editTransaction`, `Transaction.toBuilder`, `TransactionStore.replace`, `TransactionConsole.editFlow` | IMP-002, 004, 006, 010, 013, 014 | CategoryTest, TransactionTest, TransactionStoreTest, TransactionServiceTest, InputReaderTest, TransactionConsoleTest |
| FR-005 | US-005 | AC-FR-005a, b, c | `TransactionService.deleteTransaction`, `DeleteOutcome`, `TransactionConsole.deleteFlow` | IMP-002, 006, 010, 013, 014 | TransactionStoreTest, TransactionServiceTest, InputReaderTest, TransactionConsoleTest |
| FR-006 | US-006 | AC-FR-006a, b | `SummaryService.spendingByCategory`, `CategorySpending`, `ReportConsole` | IMP-005, 011, 012, 015 | SummaryServiceTest, ConsoleFormatterTest, ReportConsoleTest |
| FR-007 | US-007 | AC-FR-007a, b | `SummaryService.balanceSummary`, `BalanceSummary`, `ReportConsole` | IMP-005, 011, 015 | SummaryServiceTest, ReportConsoleTest |
| FR-008 | US-008 | AC-FR-008a, b | `BudgetService.setBudget/getBudget`, `BudgetStore`, `BudgetKey`, `ReportConsole.setBudgetFlow` | IMP-005, 007, 008, 013, 015 | BudgetStoreTest, BudgetServiceTest, InputReaderTest, ReportConsoleTest |
| FR-009 | US-009 | AC-FR-009a, b, c, d | `BudgetService.evaluateAlert`, `BudgetAlert`, `TransactionResult`, `ConsoleFormatter.budgetWarning` | IMP-005, 008, 009, 010, 012, 014, 017 | BudgetServiceTest, TransactionServiceTest, ConsoleFormatterTest, TransactionConsoleTest, ConsoleAppTest |
| FR-010 | US-010 | AC-FR-010a, b, c | `ConsoleApp`, `InputReader`, exceptions, `Main` | IMP-003, 013, 014, 015, 016, 017 | InputReaderTest, TransactionConsoleTest, ReportConsoleTest, ConsoleAppTest |

### 11.2 Non-functional

| Req | AC | Realised/verified by tasks | Verification |
|---|---|---|---|
| NFR-001 | AC-NFR-001 | IMP-004..012 (BigDecimal everywhere); IMP-018 | grep scan; rounding tests in SummaryServiceTest, ConsoleFormatterTest |
| NFR-002 | AC-NFR-002 | IMP-004, 013; IMP-018 | grep scan; InputReaderTest date cases |
| NFR-003 | AC-NFR-003 (C-5) | IMP-006, 007, 018 | grep scan (only `java.io.PrintStream` in console permitted) |
| NFR-004 | AC-NFR-004 | IMP-013, 016, 017; IMP-018 | grep scan |
| NFR-005 | AC-NFR-005 | IMP-001, 019 | `mvn test` BUILD SUCCESS |
| NFR-006 | AC-NFR-006 | all test tasks; IMP-018 | grep `org.junit` in `src/test` |
| NFR-007 | AC-NFR-007 | IMP-004, 005; IMP-018 | grep per file; `mvn compile` |
| NFR-008 | AC-NFR-008 | IMP-001, 019 | `mvn clean compile -Dmaven.compiler.showWarnings=true` |
| NFR-009 | AC-NFR-009 | IMP-008..011, 019 | public-method inventory vs tests; `mvn test` |

Every FR (FR-001 to FR-010) and NFR (NFR-001 to NFR-009) has at least one implementation task and one verification task. All 37 acceptance criteria appear above (28 FR + 9 NFR). The ten user stories US-001 to US-010 are mapped to the corresponding FRs in this matrix and expanded in Section 11.4.

### 11.3 Stage 3 findings

| Finding | Realised in |
|---|---|
| DR-001 EOF handling | IMP-013 (do not catch), IMP-016 (separate catch, clean return), `ConsoleAppTest` |
| DR-002 / C-4 date handling | IMP-013 (`readDate`, round-trip), four dedicated date tests |
| DR-003 / C-5 I/O scope | IMP-018 scan interpretation (only `PrintStream` in console) |
| DR-004 edit type prompt | IMP-014 edit flow, `TransactionConsoleTest` |

### 11.4 User-story traceability

`docs/user-stories.md` contains ten concise, stakeholder-readable stories. The mapping below is intentionally one-to-one with the ten approved functional requirements. It is a navigation and traceability aid, not a second requirements baseline.

| User story | Story title / capability | Authoritative functional requirement | Primary implementation tasks | Principal verification |
|---|---|---|---|---|
| US-001 | Add expense | FR-001 | IMP-002, IMP-004, IMP-006, IMP-009, IMP-013, IMP-014 | `TransactionTest`, `TransactionStoreTest`, `TransactionServiceTest`, `InputReaderTest`, `TransactionConsoleTest` |
| US-002 | Add income | FR-002 | IMP-002, IMP-004, IMP-006, IMP-009, IMP-013, IMP-014 | `TransactionTest`, `TransactionStoreTest`, `TransactionServiceTest`, `InputReaderTest`, `TransactionConsoleTest` |
| US-003 | View and filter transactions | FR-003 | IMP-002, IMP-006, IMP-009, IMP-012, IMP-013, IMP-014 | `CategoryTest`, `TransactionStoreTest`, `TransactionServiceTest`, `ConsoleFormatterTest`, `InputReaderTest`, `TransactionConsoleTest` |
| US-004 | Edit a transaction | FR-004 | IMP-002, IMP-004, IMP-006, IMP-010, IMP-013, IMP-014 | `CategoryTest`, `TransactionTest`, `TransactionStoreTest`, `TransactionServiceTest`, `InputReaderTest`, `TransactionConsoleTest` |
| US-005 | Delete a transaction | FR-005 | IMP-002, IMP-006, IMP-010, IMP-013, IMP-014 | `TransactionStoreTest`, `TransactionServiceTest`, `InputReaderTest`, `TransactionConsoleTest` |
| US-006 | View category spending | FR-006 | IMP-005, IMP-011, IMP-012, IMP-015 | `SummaryServiceTest`, `ConsoleFormatterTest`, `ReportConsoleTest` |
| US-007 | View financial balance | FR-007 | IMP-005, IMP-011, IMP-015 | `SummaryServiceTest`, `ReportConsoleTest` |
| US-008 | Set monthly category budgets | FR-008 | IMP-005, IMP-007, IMP-008, IMP-013, IMP-015 | `BudgetStoreTest`, `BudgetServiceTest`, `InputReaderTest`, `ReportConsoleTest` |
| US-009 | Receive budget alerts | FR-009 | IMP-005, IMP-008, IMP-009, IMP-010, IMP-012, IMP-014, IMP-017 | `BudgetServiceTest`, `TransactionServiceTest`, `ConsoleFormatterTest`, `TransactionConsoleTest`, `ConsoleAppTest` |
| US-010 | Navigate the console and handle invalid input | FR-010 | IMP-003, IMP-013, IMP-014, IMP-015, IMP-016, IMP-017 | `InputReaderTest`, `TransactionConsoleTest`, `ReportConsoleTest`, `ConsoleAppTest` |

**Traceability rules**

1. `docs/requirements.md` remains authoritative for functional behaviour and all 37 acceptance criteria.
2. `docs/user-stories.md` is supplementary and remains a draft until the human stakeholder approves it separately.
3. The story IDs identify the user-facing capability; the FR and AC identifiers define the binding scope and testable outcomes.
4. If a story sentence or story-level acceptance-criteria summary conflicts with an approved requirement or AC, the approved requirement/AC takes precedence. Resolve the story wording through change control; do not change implementation behaviour to match a conflicting draft story.
5. A new or changed story must not silently create a new feature, field, menu option, validation rule, or acceptance criterion.
6. The implementation tasks and tests remain governed by the approved requirements and architecture. The user-story mapping is an additional traceability column, not a reason to duplicate tests or create a separate story implementation phase.

---

## 12. Risks and Mitigations

| ID | Risk | Likelihood / impact | Related tasks | Mitigation |
|---|---|---|---|---|
| R-1 | Lombok `@Value` + explicit validating constructor + `@Builder(toBuilder)` misconfigured; compiler warnings/notes break NFR-008 | Medium / Medium | IMP-004, 005, 019 | Annotate the constructor with `@Builder`; verify `toBuilder` in `TransactionTest`; check `mvn clean compile` output; escalate, do not edit `pom.xml` unilaterally |
| R-2 | `BigDecimal.equals` used instead of `compareTo` (alert at 100%, positivity) | Medium / High | IMP-004, 008, 011 | Mandatory test `alertFiresAtExactly100Percent` with `500` vs `500.00`; code-review checkpoint |
| R-3 | Percentage rounding/tolerance misunderstood (`n` = displayed rows) | Medium / Medium | IMP-011 | Use the architecture counterexample as a test; independent per-item `HALF_UP` |
| R-4 | Impossible date accepted because default resolver clips it | Medium / Medium | IMP-013 | Round-trip check plus dedicated tests; unverified assumption stays unverified until test runs |
| R-5 | Type-change edit leaves partial or invalid state | Medium / High | IMP-010, 014 | Validate before mutate; mismatch and atomicity tests; end-to-end scenario 3 |
| R-6 | Catch ordering in `ConsoleApp` swallows EOF, causing infinite loop | Low / High | IMP-013, 016 | `InputReader` never catches `NoSuchElementException`; dedicated EOF test; test runs with a finite stream |
| R-7 | Second `Scanner` on `System.in` loses input | Low / High | IMP-013, 017 | Single `Scanner` in `Main`; scan for `new Scanner` count in IMP-018 review |
| R-8 | Public helpers added to services without tests (NFR-009) | Medium / Medium | IMP-008..011 | Helpers private; IMP-019 inventory; Stage 6 check |
| R-9 | Failed add consumes an ID or leaves partial state | Low / Medium | IMP-009 | Validate before `nextId()`; test |
| R-10 | Time-dependent tests flaky (blank date = today) | Low / Medium | IMP-013, 014 | Inject fixed `Clock`; never call `LocalDate.now()` without a clock in console code |
| R-11 | Forbidden-word scan false positives (`double`/`float` in comments) | Low / Low | IMP-018 | Avoid those words in comments |
| R-12 | Environment: JDK not 21 or offline Maven | Low / Medium | IMP-001 | Check at IMP-001; escalate |
| R-13 | Cross-document traceability drift between requirements, user stories, architecture, implementation plan and changelog misleads later stages | Medium / Low | IMP-020 | Keep requirements authoritative; maintain explicit FR-to-US-to-task traceability; record approved changes in the changelog; review draft story status and document consistency at each documentation checkpoint |

---

## 13. Definition of Done

**Task done** only when all hold: objective implemented; complies with approved requirements, architecture and Section 1.2 observations; the task's acceptance criteria checked against actual behaviour; relevant tests actually executed (`mvn test` or targeted command) with real results reported; no unsupported verification claims; related requirement IDs remain traceable; related US IDs remain traceable where applicable; documentation and changelog updated (IMP-020 conventions). Creating files alone is never completion.

**Implementation done** only when: all IMP tasks done; `mvn clean test` passes with actual output reported (AC-NFR-005); the seven mandatory edge-case methods and all Section 7 cases exist; every public service method has a test (AC-NFR-009); IMP-018 scans return the expected results; `mvn clean compile` shows no warnings from `src/main` (AC-NFR-008); the application remains console-only, in-memory, BigDecimal/LocalDate-based, with no added dependencies; nothing committed or pushed without explicit human approval; hand-off to Stage 6 awaits human approval.

---

## 14. Decisions Requiring Human Review

**Status:** Decisions D-1 and D-2 were resolved during planning (see table below). Decisions D-3 through D-9 were accepted during planning discussions and are incorporated into this plan implementation approach, subject to final human approval of Stage 4 (this plan). No decision in this table blocks Stage 4 approval or implementation readiness; each decision reflects an architectural choice or planning assumption that is consistent with approved requirements and architecture.

| ID | Decision / gap | Recommendation |
|---|---|---|
| D-1 | **[RESOLVED]** Naming: the earlier `CLAUDE.md` reference used `docs/impl-plan.md`, while the canonical plan is `docs/implementation-plan.md`; an older stub `docs/impl-plan.md` may remain in the repository. | `CLAUDE.md` now points to `docs/implementation-plan.md`. Keep that file authoritative. The old stub should not be treated as the active plan; its removal or archival is a separate documentation-cleanup choice and is not required for implementation planning. |
| D-2 | **[RESOLVED]** Historical cross-document status discrepancy: the original draft of this plan reported conflicting Stage 3 status in `docs/design-review.md`, `docs/architecture.md` and `docs/changelog.md`. | The design-review and architecture documents were corrected to record the Stage 3 targeted re-review PASS and formal human approval on 2026-11-12; the changelog records the corrections. The corrected documents and dated changelog entries are the current baseline. No further Stage 3 correction is requested by this plan. |
| D-3 | Who writes Stage 5 unit tests. | Java Developer writes tests alongside each task (as in this plan); Test Engineer verifies independently in Stage 7. |
| D-4 | Whether to add a TestNG test performing the source scans (architecture 11.5 leaves it to Stage 4). | No: use the grep scans in IMP-018 and Stage 7; avoids test code using `java.nio` and extra maintenance. |
| D-5 | Lombok accessor naming (`getX()` versus architecture prose `x()`). | Use Lombok defaults; no `lombok.config`. |
| D-6 | `ConsoleFormatterTest` missing from architecture 3 directory listing. | Include it (per 11.3). |
| D-7 | Commit cadence. | No commits until the human asks; suggest one commit per milestone at the human's request. |
| D-8 | `TransactionStore.replace` on unknown ID. | `IllegalArgumentException` (unreachable through services). |
| D-9 | NFR-008 "zero warnings" verification without a pom change. | Use `-Dmaven.compiler.showWarnings=true` on the command line; propose a pom change only if needed, via change control. |

No requirements or architecture gap blocks planning. D-1 and D-2 (resolved during planning) and D-3 through D-9 (accepted planning assumptions) do not alter approved requirements or architecture and are incorporated into the plan subject to final Stage 4 human approval.

---

## 15. Plan Status

| Item | Status |
|---|---|
| Task count | 20 (IMP-001 to IMP-020) in 6 milestones |
| Requirement coverage | FR-001-FR-010, NFR-001-NFR-009, all 37 ACs mapped |
| User-story coverage | US-001 to US-010 mapped one-to-one to FR-001 to FR-010; draft story document is supplementary and does not add acceptance criteria |
| User-story approval status | `docs/user-stories.md` remains **DRAFT — awaiting separate human review/approval** |
| Stage 3 status | **Approved** by the human on 2026-11-12; corrected design-review and architecture records are reflected in the baseline |
| Build / tests run by the planner | **None.** No source, test, or `pom.xml` file was created or modified by the planner. Source code created by Java Developer post-planning and approved by human on 2026-11-14. |
| Human approval of this plan | **APPROVED by the human on 2026-11-12.** Stage 4 — Implementation Planning is approved. All 20 tasks, traceability matrices, and planning decisions are authorized. |
| Implementation authorized | **AUTHORIZED by the human on 2026-11-14.** Stage 5 — Application Implementation approved. Initial source code (exceptions, models, CategoryTest) reviewed and approved. Implementation work (IMP-001 through IMP-020) may proceed. |
| Implementation task execution | **IMP-001 through IMP-020 completed on 2026-11-14**; implementation and task-level tests are recorded in `docs/changelog.md`. |
| Implementation verification evidence | `mvn clean test`: BUILD SUCCESS, 110 tests, 0 failures, 0 errors, 0 skipped. The warning-enabled compile and source-compliance checks also passed; see the dated activity-log entry. |
| Subsequent approval gates | Stage 6 independent whole-project code review and Stage 7 independent verification remain pending. Stage 5 completion does not approve either stage or authorize a commit, push, or PR. |
