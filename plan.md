# KRD: SPSE-5820

## Ticket
- ID: SPSE-5820
- Title: Add square root endpoint to calculator service
- Status: Open
- Priority: Major
- Description: Create a new POST /sqrt endpoint in the calculator service.Formula: Math.sqrt(a)
Requirements:
Takes one input: a (number)
Returns: { "result": <value> }
If a is negative, return HTTP 400 with error "cannot sqrt negative number"
Add JUnit tests for normal case and negative edge case

## Goal
Implement POST /sqrt that receives a JSON body { "a": <number> }, returns { "result": <Math.sqrt(a)> }, and returns 400 with "cannot sqrt negative number" when a is negative.

## Decisions made
- 2026-06-11 — Logic placed in `CalculatorService.sqrt()` to keep service layer responsible for computation.
- 2026-06-11 — New request model `SqrtRequest` created for consistency with existing pattern.
- 2026-06-11 — Controller method catches `IllegalArgumentException` and maps to 400.
- 2026-06-11 — Tests added to existing `CalculatorServiceTest`.

## Open doubts
_(none)_

## Current proposal
- See `src/main/java/com/meesho/calculator/model/SqrtRequest.java` (java)
- See `src/main/java/com/meesho/calculator/service/CalculatorService.java` (java)
- See `src/main/java/com/meesho/calculator/controller/CalculatorController.java` (java)
- See `src/test/java/com/meesho/calculator/service/CalculatorServiceTest.java` (java)

## State
- state: TESTS_RUNNING
- targetRepo: https://github.com/kajal-meesho/calculator-service
- channelId: C0B5ZQF12Q2
- threadTs: 1781174826.606559
- userId: U0B42FAHQ6L
- batchTotal: 1
- testStatus: PASS
- testReportSummary: 18/18 tests passed in 5s

## History
- 2026-06-11T10:47:11Z — Mapped repo for SPSE-5820: https://github.com/kajal-meesho/calculator-service
- 2026-06-11T10:47:15Z — <@U0B42FAHQ6L>: /kevin SPSE-5820
- 2026-06-11 — User provided full repo structure; code implemented and emitted as READY.
- 2026-06-11T10:47:44Z — Kevin: ## READY: POST /sqrt endpoint with service logic and tests
- 2026-06-11T10:47:44Z — AI emitted READY; transitioning to plan review.
- 2026-06-11T10:49:52Z — <@U0B42FAHQ6L> approved the plan; moving to implementation review.
- 2026-06-11T10:50:24Z — <@U0B42FAHQ6L> approved implementation; running test gate.
