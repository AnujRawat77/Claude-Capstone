---
name: pr-preparation
description: Prepare, validate, and document a review-ready pull request with automated and semi-automated creation options during Stage 8.
---

# PR Preparation Skill

## Purpose

Provide a consistent process for preparing a complete, traceable, review-ready pull request.

This skill supports the PR Preparation agent during Stage 8.

It covers final readiness checks, documentation, Git inspection, and controlled PR creation.

It does not grant permission to push, create, or merge pull requests.

## 1. Mandatory Preconditions

Before beginning Stage 8, verify:

- Stage 1 Requirements Analysis is approved.
- Stage 2 Architecture Design is approved.
- Stage 3 Design Review is approved.
- Stage 4 Implementation Planning is approved.
- Stage 5 Implementation is completed and accepted.
- Stage 6 Independent Code Review is approved.
- Stage 7 Verification and Testing is approved.
- The Orchestrator explicitly authorizes Stage 8.

If any prerequisite is missing, stop and report the blocker.

## 2. Authoritative Inputs

Read:

- `CLAUDE.md`
- `docs/requirements.md`
- `docs/architecture.md`
- `docs/design-review.md`
- `docs/implementation-plan.md`
- `docs/code-review.md`
- `docs/test-plan.md`
- `docs/test-report.md`
- `docs/changelog.md`
- `CHANGELOG.md`
- `reviewer-checklist.md`

Inspect the actual repository and Git state.

Do not rely exclusively on previous agent summaries.

## 3. Final Readiness Assessment

### Step 1: Verify SDLC approvals

Confirm that every required stage has an explicit human approval record.

Distinguish between:

- Agent recommendation.
- Completed work.
- Human approval.

These are not interchangeable.

### Step 2: Inspect Git state

Use appropriate non-destructive commands, including:

- `git status --short`
- `git branch --show-current`
- `git diff --stat`
- `git diff --check`
- `git log`

Verify:

- Current branch.
- Intended base branch.
- Changed files.
- Untracked files.
- Unexpected generated artifacts.
- Unrelated modifications.
- Potential secrets or credentials.

Do not assume the base branch is `main`.

Do not reset, clean, discard, or overwrite user changes.

### Step 3: Verify build and tests

Inspect actual verification evidence.

Confirm:

- `mvn test` was executed.
- Actual results are recorded.
- Required tests passed.
- Known limitations are documented.
- No unsupported test claims exist.

Do not claim successful verification without evidence.

### Step 4: Verify code review

Confirm:

- Independent review was performed.
- Findings are documented.
- Blocking findings are resolved.
- Any accepted observations have explicit human acceptance.
- The final review recommendation is recorded.

### Step 5: Verify documentation

Ensure:

- Requirements match the approved baseline.
- Architecture matches the implementation.
- Implementation plan reflects actual completion.
- Code review and test reports are current.
- Root `CHANGELOG.md` records application changes.
- `docs/changelog.md` preserves the SDLC audit history.
- Reviewer checklist items are evidence-based.

## 4. Prepare the PR Description

Create or update:

`docs/pr-description.md`

Include:

1. PR title.
2. Project overview.
3. Summary of changes.
4. Features implemented.
5. Architecture overview.
6. Important technical decisions.
7. Requirements and acceptance criteria addressed.
8. Test commands and actual results.
9. Code review outcome.
10. Known limitations.
11. Documentation references.
12. Reviewer notes.

Keep the description concise and factual.

Do not include fabricated test results, unsupported claims, or sensitive information.

## 5. Prepare the Reviewer Checklist

Update `reviewer-checklist.md` where necessary.

Verify:

- [ ] Requirements compliance.
- [ ] Architecture compliance.
- [ ] Functional behaviour.
- [ ] Monetary precision.
- [ ] Date handling.
- [ ] Transaction integrity.
- [ ] Budget behaviour.
- [ ] Input validation.
- [ ] TestNG compliance.
- [ ] Maven verification.
- [ ] Code review findings resolved.
- [ ] Documentation consistency.
- [ ] Scope and security restrictions respected.

Only mark items complete when evidence supports them.

Keep unresolved observations visible.

## 6. Automated PR Creation

Use GitHub CLI (`gh`) only when installed and authenticated.

Before execution:

1. Verify repository identity.
2. Verify current branch.
3. Verify target base branch.
4. Verify the working tree is in the required state.
5. Verify the PR description exists.
6. Present the proposed PR title and description.
7. Obtain explicit human authorization to create the PR.

After authorization:

- Execute the approved `gh pr create` command.
- Verify the actual PR URL and number.
- Report the result.

Do not assume PR creation succeeded merely because a command was issued.

If the branch is not available remotely, explain that pushing is required and request separate explicit authorization.

Never push automatically.

If GitHub CLI is unavailable or unauthenticated, switch to manual mode.

## 7. Semi-Automated / Manual PR Creation

When automated creation is unavailable or not preferred, prepare:

- PR title.
- Complete PR description.
- Base branch.
- Feature branch.
- Test evidence.
- Code review outcome.
- Reviewer checklist.
- Manual GitHub instructions.

The human user creates the PR through GitHub or their preferred CLI workflow.

If requested, verify the resulting PR using available authorized tools.

Do not claim to have created a PR manually.

## 8. Changelog Management

Maintain two separate records:

### Root CHANGELOG.md

Records application and release changes.

### docs/changelog.md

Records SDLC stages, decisions, evidence, and human approvals.

Do not mix their purposes.

Preserve historical entries and approval records.

## 9. Final Readiness Report

Provide:

1. SDLC approval status.
2. Current branch and proposed base branch.
3. Change-set summary.
4. Build and test evidence.
5. Code review outcome.
6. Documentation readiness.
7. Reviewer checklist status.
8. Unresolved issues.
9. Recommended PR creation mode.
10. Actions requiring human authorization.

Choose one readiness outcome:

- **READY** — Required approvals and checks are complete.
- **READY WITH OBSERVATIONS** — Only documented non-blocking observations remain.
- **NOT READY** — Required checks or corrections are incomplete.
- **BLOCKED** — Required access, approval, or evidence is unavailable.

## 10. Human Approval and Safety

The following always require explicit authorization:

- Pushing commits.
- Creating pull requests.
- Modifying remote branches.
- Merging pull requests.
- Deleting branches.
- Destructive Git operations.

Approval to prepare PR materials is not approval to perform remote actions.

Never infer authorization from earlier stage approvals.

## 11. Approval Boundary

The PR Preparation agent recommends readiness and presents the final materials.

Only the human user authorizes PR creation.

Stage 8 is complete only when its required deliverables and final outcome are documented.

PR creation and merging remain separate actions requiring explicit authorization.

## 12. Prohibited Actions

- Do not implement new features.
- Do not alter approved requirements.
- Do not conceal unresolved findings.
- Do not fabricate build or test evidence.
- Do not push without explicit authorization.
- Do not create a PR without explicit authorization.
- Do not merge a PR.
- Do not delete branches.
- Do not expose secrets.
- Do not discard user changes.