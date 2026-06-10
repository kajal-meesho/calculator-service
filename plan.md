# KRD: SPSE-5816

## Ticket
- ID: SPSE-5816
- Title: Add compound calculator endpoint
- Status: Open
- Priority: Major
- Description: Repo:  Create a new POST /compound endpoint in the calculator service.Formula: a * (b + c) / bRequirements:
Takes three inputs: a, b, c (all numbers)
Returns: { "result": <value> }
If b is 0, return HTTP 400 with error "b cannot be zero"
Add JUnit tests for normal case and b=0 edge case

## Goal
Implement a `POST /compound` endpoint that accepts a JSON body with fields `a`, `b`, `c` (all numbers), computes `a * (b + c) / b`, returns `{ "result": <value> }`, and returns HTTP 400 with error `"b cannot be zero"` when `b` is 0.

## Decisions made
- 2026-06-11 — Package base: `com.drishtix.calculator` (with sub‑packages `model`, `controller`, `service`)
- 2026-06-11 — Use `double` for numeric fields (JSON number → Java double)
- 2026-06-11 — Validation of `b == 0` in the controller layer before calling the service
- 2026-06-11 — Error response as `{"error": "b cannot be zero"}` via `Map.of`
- 2026-06-11 — Unit test for the controller uses `MockMvc` standalone setup with `@ExtendWith(MockitoExtension.class)`
- 2026-06-11 — Unit test for the service covers normal, negative, and zero‑b scenarios

## Open doubts
_(none)_

## Current proposal
- See `src/main/java/com/drishtix/calculator/model/CompoundRequest.java` (java)
- See `src/main/java/com/drishtix/calculator/model/CompoundResponse.java` (java)

## State
- state: TESTS_RUNNING
- targetRepo: https://github.com/kajal-meesho/calculator-service
- channelId: C0B5ZQF12Q2
- threadTs: 1781090436.626699
- userId: U0B42FAHQ6L
- batchTotal: 1
- testStatus: PASS
- testReportSummary: 12/12 tests passed in 4s

## History
- 2026-06-10T11:20:40Z — Mapped repo for SPSE-5816: https://github.com/kajal-meesho/calculator-service
- 2026-06-10T11:20:40Z — <@U0B42FAHQ6L>: /kevin SPSE-5816
- 2026-06-10T11:22:06Z — Kevin: ## READY: POST /compound endpoint with DTOs, Service, Controller, and unit tests shipped.
- 2026-06-10T11:22:06Z — AI emitted READY; transitioning to plan review.
- 2026-06-10T11:22:28Z — <@U0B42FAHQ6L> approved the plan; moving to implementation review.
- 2026-06-10T11:23:23Z — <@U0B42FAHQ6L> approved implementation; running test gate.
