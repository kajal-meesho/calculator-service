# KRD: SPSE-5858

## Ticket
- ID: SPSE-5858
- Title: Add trigonometric functions (sin, cos) via a "More Functions" panel
- Status: Open
- Priority: Major
- Description: Background
The Meesho Calculator currently supports basic arithmetic and square. Users doing scientific calculations need sin and cos, but the 4-column button grid is full — there is no room to add more buttons directly. The solution is a "More ƒ" trigger button that opens an overlay panel containing the extra functions.
Acceptance Criteria
Backend
POST /api/sin — accepts { "n": <number> }, treats n as degrees, returns { "result": <double>, "operation": "sin", "expression": "sin(n°)" }. Math.toRadians() must be used internally.
POST /api/cos — same shape, computes cosine in degrees, expression "cos(n°)".
Both endpoints return CalculatorResponse. Reuse the existing SquareRequest class (field n) — no new request class needed.
Unit tests in CalculatorControllerTest: at least 4 tests total — sin(0°)=0, sin(90°)=1, cos(0°)=1, cos(90°)≈0 (use assertEquals(expected, actual, 1e-9) delta).
Frontend (index.html)
Replace the +/− button in the last row with a "ƒ(x)" button styled like .btn-op (purple tint).
Add a hidden panel (id="more-panel") below the .buttons grid, initially display:none. It contains two buttons side by side: sin and cos, each spanning half the panel width.
Clicking ƒ(x) toggles the panel visible/hidden (no page scroll, no new page).
sin button: calls POST /api/sin with { "n": parseFloat(currentInput) }, updates expression line to sin(<n>°) = and result display to the returned value.
cos button: same pattern for cosine.
Panel buttons should use a distinct style — suggest teal (#40c0c0 background, #0a3f3f text) to visually separate them from the main grid.
After a sin/cos call, currentInput is updated to the result string and justCalculated = true (same post-calc convention as squareCurrent()).
Out of Scope
tan, asin, acos, radians mode toggle — future ticket
Mobile keyboard / accessibility — future ticket
Notes for Implementer
Keep changes to two files only: CalculatorController.java and index.html
The SquareRequest class already exists at src/main/java/com/meesho/calculator/SquareRequest.java — use it as-is
CalculatorResponse constructor signature: (double result, String operation, String expression, double n) — use the 4-arg version (same as square())
Angle mode is degrees (standard calculator UX); conversion: Math.sin(Math.toRadians(n))

## Goal
Add sin and cos trigonometric functions to the calculator, accessible via a "More Functions" overlay panel, with backend endpoints and frontend integration.

## Decisions made
- Use POST /api/sin and /api/cos, both accepting { n: <number> } (degrees), using SquareRequest.
- Both endpoints return CalculatorResponse using the 4-arg constructor.
- Expression format: "sin(n°)" and "cos(n°)".
- Add "ƒ(x)" button (styled .btn-op) in place of +/−, which toggles a hidden panel with sin/cos buttons.
- Panel buttons styled teal (#40c0c0 background, #0a3f3f text).
- After sin/cos, update currentInput and justCalculated as with squareCurrent().
- At least 4 unit tests: sin(0)=0, sin(90)=1, cos(0)=1, cos(90)=0 (with delta).

## Open doubts
_(none)_

## Current proposal
- See `src/main/java/com/meesho/calculator/CalculatorController.java` (java)
- See `src/main/resources/static/index.html` (html)

## State
- state: TESTS_RUNNING
- targetRepo: https://github.com/kajal-meesho/calculator-service
- channelId: C0B5ZQF12Q2
- threadTs: 1781969867.491409
- userId: U0B42FAHQ6L
- testStatus: PASS
- testReportSummary: 12/12 tests passed in 6s

## History
- 2026-06-20T15:37:50Z — Mapped repo for SPSE-5858: https://github.com/kajal-meesho/calculator-service
- 2026-06-20T15:37:57Z — <@U0B42FAHQ6L>: /kevin SPSE-5858
- 2026-06-20 — AI implemented /api/sin and /api/cos endpoints, More Functions panel, and frontend integration; emitting READY.
- 2026-06-20T15:38:43Z — Kevin: ## READY: Adds /api/sin and /api/cos endpoints, "ƒ(x)" button, and More Functions panel with sin/cos UI
- 2026-06-20T15:38:43Z — AI emitted READY; transitioning to plan review.
- 2026-06-20T15:38:56Z — <@U0B42FAHQ6L> approved the plan; moving to implementation review.
- 2026-06-20T15:39:53Z — Kevin: placeholder retry — regenerated 2 file(s)
- 2026-06-20T15:40:05Z — <@U0B42FAHQ6L> approved implementation; running test gate.
