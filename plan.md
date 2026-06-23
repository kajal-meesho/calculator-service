# KRD: DEMO-917

## Ticket
- ID: DEMO-917
- Title: Redesign fx panel as full-screen toggle view with number pad and function buttons
- Status: Open
- Priority: Major
- Description: Problem
The current fx panel is small and cramped — adding more scientific functions to it is not feasible without breaking the layout.
Proposed Design
When the user clicks the fx button:
The main calculator button grid (numbers + basic ops) hides
A new fx panel slides in / replaces it, containing:
A number pad (0–9, decimal, +/−)
An equals button to trigger the function
All scientific function buttons (sin, cos, sqrt, + any future additions) laid out in a clean grid
An ← Back or fx toggle button returns to the standard calculator view
This gives each scientific function a dedicated, uncluttered button without any overflow.
UI Behaviour
Toggle state is tracked with a JS boolean fxMode
On enter fx mode: hide .buttons, show .fx-panel
On exit fx mode: hide .fx-panel, show .buttons
Number input and display (currentInput, result div) remain shared between both views
Acceptance Criteria
[ ] Clicking fx hides the standard button grid and shows the fx panel
[ ] fx panel contains a number pad (0–9, ., +/−), an = / apply button, and all scientific function buttons
[ ] A back/close button returns to the standard view
[ ] Adding a new function button requires only adding one button element to the fx panel — no layout changes needed
[ ] No overflow or wrapping issues at the standard 320px calculator width

## Goal
Redesign the fx panel as a full-screen toggle view with a number pad, equals/apply button, and all scientific function buttons (sin, cos, sqrt, etc.), with a back button to return to the standard calculator view. Adding new functions should require only a new button in the fx panel.

## Decisions made
- The fx panel replaces the main button grid when toggled.
- The fx panel contains a number pad (0–9, ., +/−), an equals/apply button, and all scientific function buttons in a clean grid.
- A back button returns to the standard calculator view.
- Adding a new function button only requires adding a button element to the fx panel.
- No overflow or wrapping at 320px width.
- Toggle state is tracked with a JS boolean fxMode.
- Number input and display remain shared between both views.

## Open doubts
_(none)_

## Current proposal
- See `src/main/resources/static/index.html` (html)

## State
- state: TESTS_RUNNING
- targetRepo: https://github.com/kajal-meesho/calculator-service
- channelId: C0B5ZQF12Q2
- threadTs: 1782206008.296279
- userId: U0B42FAHQ6L
- batchTotal: 1
- testStatus: PASS
- testReportSummary: 12/12 tests passed in 8s

## History
- 2026-06-23T09:13:31Z — Mapped repo for DEMO-917: https://github.com/kajal-meesho/calculator-service
- 2026-06-23T09:13:38Z — <@U0B42FAHQ6L>: /kevin DEMO-917
- 2026-06-23 — AI implemented fx panel redesign as full-screen toggle view with number pad and function buttons; emitting READY.
- 2026-06-23T09:14:18Z — Kevin: ## READY: Redesigned fx panel as full-screen toggle view with number pad and function buttons
- 2026-06-23T09:14:18Z — AI emitted READY; transitioning to plan review.
- 2026-06-23T09:15:10Z — <@U0B42FAHQ6L> approved the plan; moving to implementation review.
- 2026-06-23T09:16:07Z — Kevin: placeholder retry — regenerated 1 file(s)
- 2026-06-23T09:16:48Z — <@U0B42FAHQ6L> approved implementation; running test gate.
