# KRD: SPSE-5819

## Ticket
- ID: SPSE-5819
- Title: Add power endpoint to calculator service
- Status: Open
- Priority: Major
- Description: Repo: https://github.com/kajal-meesho/calculator-service 
Create a new POST /power endpoint in the calculator service.Formula: a raised to the power b (Math.pow(a, b))
Requirements:
Takes two inputs: a, b (both numbers)
Returns: { "result": <value> }
If both a and b are 0, return HTTP 400 with error "0^0 is undefined"
Add JUnit tests for normal case and 0^0 edge case

## Goal
Add POST /power endpoint, service method `power(double, double)`, and unit tests.

## Decisions made
- 2026-06-11 — Endpoint name: /power (POST)
- 2026-06-11 — Use `Math.pow` for calculation
- 2026-06-11 — 0^0 returns HTTP 400 via `IllegalArgumentException`
- 2026-06-11 — Controller pattern follows existing `/calculate` and `/compound` style (try-catch → 200 or 400)
- 2026-06-11 — PowerRequest model already exists; reuse it

## Open doubts
_(none)_

## Current proposal
- See `src/main/java/com/meesho/calculator/controller/CalculatorController.java` (java)
- See `src/main/java/com/meesho/calculator/service/CalculatorService.java` (java)
- See `src/test/java/com/meesho/calculator/service/CalculatorServiceTest.java` (java)

## State
- state: TESTS_RUNNING
- targetRepo: https://github.com/kajal-meesho/calculator-service
- channelId: C0B5ZQF12Q2
- threadTs: 1781173494.166409
- userId: U0B42FAHQ6L
- batchTotal: 1
- testStatus: PASS
- testReportSummary: 16/16 tests passed in 4s

## History
- 2026-06-11T10:24:57Z — Mapped repo for SPSE-5819: https://github.com/kajal-meesho/calculator-service
- 2026-06-11T10:25:00Z — <@U0B42FAHQ6L>: /kevin SPSE-5819
- 2026-06-11Txx:xx:xxZ — Added `POST /power` endpoint, ensured `compound()` service method present, added power unit tests (normal + 0^0)
- 2026-06-11T10:25:46Z — Kevin: ## READY: Added POST /power endpoint with service method and JUnit tests
- 2026-06-11T10:25:46Z — AI emitted READY; transitioning to plan review.
- 2026-06-11T10:26:08Z — <@U0B42FAHQ6L> approved the plan; moving to implementation review.
- 2026-06-11T10:27:27Z — <@U0B42FAHQ6L> approved implementation; running test gate.
