# Reviewer Checklist

**Project:** Personal Expense Tracker  
**Review type:** Draft PR — Agentic SDLC Implementation Checkpoint  
**Status:** Pending review

## 1. SDLC Documentation

- [ ] Review `docs/requirements.md`.
- [ ] Review `docs/architecture.md`.
- [ ] Review `docs/design-review.md`.
- [ ] Review `docs/implementation-plan.md`.
- [ ] Review `docs/user-stories.md`.
- [ ] Review `docs/changelog.md`.
- [ ] Confirm that Stage 1 through Stage 4 approvals are documented.

## 2. Claude Code Framework

- [ ] Review `CLAUDE.md` and project instructions.
- [ ] Review custom agent definitions under `.claude/agents/`.
- [ ] Review skill definitions under `.claude/skills/`.
- [ ] Review hook scripts under `.claude/hooks/`.
- [ ] Review `.claude/settings.json`.
- [ ] Confirm that agent responsibilities and approval boundaries are clear.
- [ ] Confirm that destructive Git operations are appropriately guarded.

## 3. Implementation Review

- [ ] Verify IMP-001 build configuration.
- [ ] Review IMP-002 category and enum implementation.
- [ ] Review IMP-003 exception implementation.
- [ ] Confirm that implementation has not been misrepresented as complete.
- [ ] Confirm that IMP-004 through IMP-020 remain outstanding.

## 4. Testing and Code Review Evidence

- [ ] Review `docs/test-report.md`.
- [ ] Verify the recorded 19 CategoryTest executions.
- [ ] Review `docs/code-review.md`.
- [ ] Confirm that the IMP-002 review scope is accurately represented.
- [ ] Confirm that full application verification is marked pending.
- [ ] Confirm that formal Stage 7 verification has not been claimed.

## 5. Scope and Quality

- [ ] Confirm that no unapproved dependencies or frameworks were introduced.
- [ ] Confirm that implementation follows the approved architecture.
- [ ] Check for accidental credentials, secrets, or environment-specific files.
- [ ] Check that generated build artifacts are not included.
- [ ] Review all tracked and untracked files intended for the PR.
- [ ] Check for unrelated changes or accidental scope expansion.

## 6. Pull Request Readiness

- [ ] Verify the PR base branch is `main`.
- [ ] Verify the compare branch is `feature/expense-tracker-capstone`.
- [ ] Verify the PR title and description accurately identify this as an implementation checkpoint.
- [ ] Confirm the PR is created as a Draft.
- [ ] Confirm no merge has occurred.
- [ ] Confirm all outstanding work and limitations are disclosed.

## 7. Final Reviewer Decision

**Reviewer verdict:**

- [ ] Approved for continued development.
- [ ] Changes requested.
- [ ] Not approved.

**Comments / findings:**

| Finding | Severity | File/line | Description | Recommendation | Status |
|---|---|---|---|---|---|

**Reviewer:**  
**Review date:**  

---

**Important:** This checklist supports review of the current implementation checkpoint. It does not replace the formal Stage 6 whole-project code review or Stage 7 independent verification.