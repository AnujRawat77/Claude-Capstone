#!/usr/bin/env bash
set -euo pipefail
# Automated mode only. Does not push commits. Run only after explicit human approval.
command -v gh >/dev/null 2>&1 || { echo "gh unavailable; use semi-automated/manual PR mode."; exit 1; }
gh auth status >/dev/null 2>&1 || { echo "gh is not authenticated; use semi-automated/manual PR mode."; exit 1; }
git rev-parse --is-inside-work-tree >/dev/null
branch="$(git branch --show-current)"
[[ -n "$branch" && "$branch" != "main" && "$branch" != "master" ]] || { echo "Create a feature branch first."; exit 1; }
[[ -z "$(git status --porcelain)" ]] || { echo "Working tree must be clean."; exit 1; }
[[ -s docs/pr-description.md ]] || { echo "docs/pr-description.md is missing or empty."; exit 1; }
echo "Review docs/pr-description.md and confirm you approve PR creation."
read -r -p "Type CREATE to proceed: " approval
[[ "$approval" == "CREATE" ]] || { echo "Cancelled."; exit 0; }
gh pr create --base main --head "$branch" --title "Add Personal Expense Tracker" --body-file docs/pr-description.md
