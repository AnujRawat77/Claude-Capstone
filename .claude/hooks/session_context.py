print("""CLAUDE CAPSTONE — SESSION RULES

1. Follow the eight-stage SDLC in order:
   Requirements → Architecture → Design Review → Implementation Planning
   → Implementation → Code Review → Verification & Testing → PR Preparation.

2. Never cross a major stage gate without explicit human approval.

3. Do not write application code until Stages 1–4 are approved.

4. Follow the approved requirements and architecture. Do not invent requirements.

5. Technology constraints:
   - Java 21
   - Maven
   - TestNG 7.11.0 exclusively
   - Lombok 1.18.46
   - BigDecimal for monetary values
   - LocalDate for dates
   - In-memory storage only

6. Preserve traceability, independent review, and verifiable evidence.

7. Conserve usage: avoid unnecessary agent calls, repeated analysis,
   and oversized context.

8. Never push commits or create a PR without explicit human approval.
""")