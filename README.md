# Expense Tracker — Agentic SDLC Capstone

Java 21 console-based Personal Expense Tracker built to demonstrate an eight-stage Agentic SDLC with Claude Code.

Constraints: Maven, TestNG 7.11.0, Lombok 1.18.46, in-memory storage, console interface, BigDecimal for money. No database, Spring Boot, REST API, web UI or external runtime services.

## Build, test, and run

From the project root, run the TestNG suite with:

```powershell
mvn test
```

Run the full test suite and the source-compliance scans with:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\scripts\verify.ps1
```

The policy override applies only to that PowerShell process. To launch the console application after compiling:

```powershell
mvn compile
java -cp target\classes org.capstone.expense.Main
```

The application stores transactions and budgets in memory for the current process only.
