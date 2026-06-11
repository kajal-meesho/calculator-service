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
Add POST /sqrt endpoint to the existing calculator service, reusing the existing CalculateRequest model (only uses 'a' field). Service throws IllegalArgumentException for a < 0; controller returns HTTP 400. Also add a sqrt button to the frontend index.html that only uses input A.

## Decisions made
- 2026-06-11 — Use existing CalculateRequest model for the /sqrt endpoint (a is the primary field; b and operation are ignored).
- 2026-06-11 — Add sqrt(double a) method to CalculatorService; controller catches IllegalArgumentException and returns error JSON.
- 2026-06-11 — Add three test cases to existing CalculatorServiceTest: positive, negative (exception), and zero.
- 2026-06-11 — Updated index.html: added sqrt button with class `op-btn`, only uses input A, calls POST /sqrt with `{"a": parseFloat(a)}`, shows result in the same result div. Matches existing button style and error handling pattern.

## Open doubts
_(none)_

## Current proposal
- See `src/main/java/com/meesho/calculator/controller/CalculatorController.java` (java)
- See `src/main/java/com/meesho/calculator/service/CalculatorService.java` (java)
- See `src/test/java/com/meesho/calculator/service/CalculatorServiceTest.java` (java)
- See `src/main/resources/static/index.html` (html)

## State
- state: TESTS_RUNNING
- targetRepo: https://github.com/kajal-meesho/calculator-service
- channelId: C0B5ZQF12Q2
- threadTs: 1781182438.896559
- userId: U0B42FAHQ6L
- batchTotal: 1
- testStatus: PASS
- testReportSummary: 15/15 tests passed in 4s

## History
- 2026-06-11T12:54:02Z — Mapped repo for SPSE-5820: https://github.com/kajal-meesho/calculator-service
- 2026-06-11T12:54:05Z — <@U0B42FAHQ6L>: /kevin SPSE-5820
- 2026-06-11T12:54:02Z — Implemented POST /sqrt endpoint, service sqrt method, and unit tests. No new files created; all changes applied to existing files.
- 2026-06-11T12:54:49Z — Kevin: ## READY: Added POST /sqrt endpoint with service sqrt() method and JUnit tests.
- 2026-06-11T12:54:49Z — AI emitted READY; transitioning to plan review.
- 2026-06-11T12:55:42Z — <@U0B42FAHQ6L> requested revision at plan: Also update src/main/resources/static/index.html to add a sqrt button matching the existing button style. The sqrt operation only needs input A (no B). Keep everything else the same.
- 2026-06-11T12:55:47Z — <@U0B42FAHQ6L>: User reviewed the plan and asked you to revise: Also update src/main/resources/static/index.html to add a sqrt button matching the existing button style. The sqrt operation only needs input A (no B). Keep everything else the same. — please update the plan/code, keep approved decisions intact, and emit ## READY again.
- 2026-06-11T12:55:50Z — AI asked for current content of index.html to add the sqrt button consistently.
- 2026-06-11T12:57:00Z — Kevin: ## DOUBT: Could you provide the current content of `src/main/resources/static/index.html`? I need to see the existing button/input style to add the sqrt button consistently.
- 2026-06-11T12:58:58Z — <@U0B42FAHQ6L>: Reply in Slack thread:
- 2026-06-11T12:55:50Z — AI asked for current content of index.html.
- 2026-06-11T12:57:00Z — Kevin: ## DOUBT: Could you provide the current content of `src/main/resources/static/index.html`?
- 2026-06-11T12:58:00Z — User directed not to ask again; index.html is in repo context. AI proceeded with frontend update and emitted READY.
- 2026-06-11T12:59:38Z — Kevin: ## READY: Updated index.html with sqrt button matching existing operation button style.
- 2026-06-11T12:59:38Z — AI emitted READY; transitioning to plan review.
- 2026-06-11T13:02:20Z — <@U0B42FAHQ6L> requested revision at plan: Also add src/main/resources/static/index.html to the list of files you will modify.
- 2026-06-11T13:02:25Z — <@U0B42FAHQ6L>: User reviewed the plan and asked you to revise: Also add src/main/resources/static/index.html to the list of files you will modify. — please update the plan/code, keep approved decisions intact, and emit ## READY again.
- 2026-06-11T13:03:xxZ — Kevin: Updated Current proposal to include index.html; emitting READY.
- 2026-06-11T13:02:43Z — Kevin: ## READY: Updated KRD with index.html listed in Current proposal.
- 2026-06-11T13:02:43Z — AI emitted READY; transitioning to plan review.
- 2026-06-11T13:04:49Z — <@U0B42FAHQ6L> approved the plan; moving to implementation review.
- 2026-06-11T13:13:53Z — <@U0B42FAHQ6L> approved implementation; running test gate.
