import json
import re
import sys


DANGEROUS_GIT_PATTERNS = [
    re.compile(r"\bgit\s+reset\b[^;\n]*--hard\b", re.IGNORECASE),
    re.compile(r"\bgit\s+clean\b[^;\n]*-[a-zA-Z]*f[a-zA-Z]*", re.IGNORECASE),
    re.compile(r"\bgit\s+push\b[^;\n]*(--force(?:-with-lease)?\b|(?:^|\s)-f(?:\s|$))", re.IGNORECASE),
    re.compile(r"\bgit\s+branch\b[^;\n]*\s-D(?:\s|$)", re.IGNORECASE),
    re.compile(r"\bgit\s+checkout\b[^;\n]*--\s+\.", re.IGNORECASE),
    re.compile(r"\bgit\s+restore\b[^;\n]*--worktree\b", re.IGNORECASE),
]


def main():
    try:
        hook_input = json.load(sys.stdin)
    except (json.JSONDecodeError, OSError):
        print("Git safety hook: unable to read hook input.", file=sys.stderr)
        return 2

    tool_input = hook_input.get("tool_input", {})
    command = tool_input.get("command", "")

    if not isinstance(command, str):
        return 0

    for pattern in DANGEROUS_GIT_PATTERNS:
        if pattern.search(command):
            print(
                "Blocked by Git safety hook: this command may destructively "
                "modify or discard Git history or working-tree changes. "
                "Review the command and obtain explicit human approval "
                "before proceeding.",
                file=sys.stderr,
            )
            return 2

    return 0


if __name__ == "__main__":
    sys.exit(main())