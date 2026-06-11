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
Add a POST /power endpoint to the calculator service that computes a^b, correctly handles the 0^0 edge case, and includes tests.

## Decisions made
- 2026-06-11 — Use a dedicated `PowerRequest` DTO (with only `a` and `b`) instead of reusing `CalculateRequest` to keep the endpoint contract clean.
- 2026-06-11 — Add a `power(double a, double b)` method to `CalculatorService` rather than extending the existing `calculate()` method.
- 2026-06-11 — Error response format for 0^0: HTTP 400 with body `{ "error": "0^0 is undefined" }`, consistent with the existing `/calculate` error handling.
- 2026-06-11 — Controller test uses MockMvc standalone setup (no Spring context) to keep tests lightweight.

## Open doubts
_(none)_

## Current proposal
- See `src/main/java/com/meesho/calculator/model/PowerRequest.java` (java)
- See `src/main/java/com/meesho/calculator/service/CalculatorService.java` (java)

## State
- state: TESTS_RUNNING
- targetRepo: https://github.com/kajal-meesho/calculator-service
- channelId: C0B5ZQF12Q2
- threadTs: 1781172759.013749
- userId: U0B42FAHQ6L
- batchTotal: 1
- testStatus: PASS
- testReportSummary: 12/12 tests passed in 4s

## History
- 2026-06-11T10:12:42Z — Mapped repo for SPSE-5819: https://github.com/kajal-meesho/calculator-service
- 2026-06-11T10:12:44Z — <@U0B42FAHQ6L>: /kevin SPSE-5819
- 2026-06-11T10:13:53Z — Kevin: ## READY: POST /power endpoint with service logic, request model, and JUnit tests for both service and controller.
- 2026-06-11T10:13:53Z — AI emitted READY; transitioning to plan review.
- 2026-06-11T10:14:08Z — <@U0B42FAHQ6L> approved the plan; moving to implementation review.
- 2026-06-11T10:14:15Z — <@U0B42FAHQ6L> approved implementation; running test gate.
