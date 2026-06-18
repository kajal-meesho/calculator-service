# KRD: SPSE-5852

## Ticket
- ID: SPSE-5852
- Title: Add square function to calculator service
- Status: Open
- Priority: Major
- Description: The calculator service supports plus, minus, multiply, divide. Add a square function that computes n².
Implementation requirements:
New request model — SquareRequest.java Create a new class (do NOT reuse CalculatorRequest which has a and b):
public class SquareRequest {
    private double n;
    // getter + setter
}
API endpoint — add to CalculatorController.java
POST /api/square
Body: { "n": 5 }
Response: { "result": 25.0, "operation": "square", "expression": "5.0²" }
Follow the same pattern as the existing plus/minus/multiply/divide methods.
UI change — index.html (very specific instructions)
Step A — Remove btn-zero class from the 0 button so it no longer spans 2 columns:
<!-- change this -->
<button class="btn-num btn-zero" onclick="inputDigit('0')">0</button>
<!-- to this -->
<button class="btn-num" onclick="inputDigit('0')">0</button>
Also delete the .btn-zero { grid-column: span 2; } CSS rule since it is no longer needed.
Step B — Add the x² button immediately after the 0 button:
<button class="btn-op" onclick="squareCurrent()">x²</button>
Step C — Add the squareCurrent() JavaScript function:
async function squareCurrent() {
    const n = parseFloat(currentInput);
    exprDisplay.textContent = n + '² =';
    try {
        const res = await fetch('/api/square', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ n })
        });
        const data = await res.json();
        updateDisplay(data.result);
        currentInput = String(data.result);
    } catch (e) {
        display.classList.add('error');
        display.textContent = 'Error';
    }
    justCalculated = true;
}
Unit tests — add to CalculatorControllerTest.java Three new test cases:
square(4) → result: 16.0
square(0) → result: 0.0
square(-3) → result: 9.0
Acceptance Criteria:
POST /api/square with {"n":5} returns 200 + {"result":25.0,"operation":"square","expression":"5.0²"}
Negative numbers work: square(-3) = 9.0
Zero works: square(0) = 0.0
UI last row is now: 0 | x² | +/− | =
x² button takes current display value, calls the API, shows result
All 5 existing tests still pass + 3 new tests added

## Goal
Implement a square function in the calculator service, including backend API, UI, and unit tests, as described.

## Decisions made
- Square uses a new request model (SquareRequest) with a single double field n.
- API endpoint is POST /api/square, returns result, operation, and expression.
- UI: 0 button no longer spans 2 columns; x² button added after 0; squareCurrent() JS function added.
- Three new unit tests for square: 4, 0, -3.

## Open doubts
_(none)_

## Current proposal
- See `src/main/java/com/meesho/calculator/SquareRequest.java` (java)
- See `src/main/java/com/meesho/calculator/CalculatorController.java` (java)
- See `src/main/resources/static/index.html` (html)
- See `src/test/java/com/meesho/calculator/CalculatorControllerTest.java` (java)

## State
- state: TESTS_RUNNING
- targetRepo: https://github.com/kajal-meesho/calculator-service
- channelId: C0B5ZQF12Q2
- threadTs: 1781809586.091679
- userId: U0B42FAHQ6L
- batchTotal: 1
- testStatus: PASS
- testReportSummary: 8/8 tests passed in 8s

## History
- 2026-06-18T19:06:29Z — Mapped repo for SPSE-5852: https://github.com/kajal-meesho/calculator-service
- 2026-06-18T19:06:35Z — <@U0B42FAHQ6L>: /kevin SPSE-5852
- 2026-06-18 — Session started, repo mapped.
- 2026-06-18 — Implemented square function in backend, UI, and tests as per ticket; emitting READY.
- 2026-06-18T19:07:31Z — Kevin: ## READY: Adds square function to backend, UI, and tests as specified in SPSE-5852
- 2026-06-18T19:07:31Z — AI emitted READY; transitioning to plan review.
- 2026-06-18T19:08:22Z — <@U0B42FAHQ6L> approved the plan; moving to implementation review.
- 2026-06-18T19:09:23Z — <@U0B42FAHQ6L> approved implementation; running test gate.
