# KRD: SPSE-5853

## Ticket
- ID: SPSE-5853
- Title: Add square root function to calculator service
- Status: Open
- Priority: Major
- Description: The calculator service (repo: kajal-meesho/calculator-service) is a Spring Boot 2.7.18 / Java 11 application running on port 8080. It serves a Meesho-themed calculator UI backed by a REST API. Below is a full description of the current design so changes fit naturally.
Current backend structure:
The controller is CalculatorController.java annotated with @RestController @RequestMapping("/api"). It has five POST endpoints: /api/plus, /api/minus, /api/multiply, /api/divide, /api/square.
The binary operations (plus, minus, multiply, divide) all use CalculatorRequest.java which has two double fields a and b. The square operation uses a separate SquareRequest.java which has a single double field n — follow this same pattern for sqrt.
All endpoints return CalculatorResponse.java which has four fields: result (double), operation (String), expression (String), and input (Double, nullable — only set for square). The divide endpoint returns HTTP 400 with a JSON error body when dividing by zero — follow this same error pattern for sqrt of a negative number.
What to add — backend:
Add a square root function that computes √n.
Create a new request model class SqrtRequest.java in com.meesho.calculator with a single double field n with getter and setter — do not reuse SquareRequest or CalculatorRequest.
Add a new POST endpoint /api/sqrt to CalculatorController.java. It takes a SqrtRequest body. If n is negative, return HTTP 400 with a JSON error body using the same pattern as the divide-by-zero error. Otherwise compute the square root using Java's Math.sqrt(n) and return a CalculatorResponse with operation set to "sqrt" and expression formatted as "√" + n (for example input 9.0 → expression is "√9.0"). The input field in the response should be set to n (same as the square endpoint does).
Current UI structure:
The UI is a single index.html with an inline stylesheet and inline JavaScript. The calculator is 320px wide with a 4-column CSS grid layout for the buttons. There are two display areas: a small expression line on top (shows the operation being performed) and a large result number below.
The current button grid layout is exactly:
Row 1:  AC    CE    .     ÷
Row 2:  7     8     9     ×
Row 3:  4     5     6     −
Row 4:  1     2     3     +
Row 5:  0     x²    +/−   =
There are five button CSS classes currently defined:
.btn-num — translucent white, used for number buttons and decimal point
.btn-op — translucent purple with purple text, used for ÷ × − +
.btn-clear — translucent red, used for AC and CE
.btn-equals — solid light purple (#eabdfb), used for = button
.btn-square — solid gold (#f0c040) with dark text (#3f0e40) and bold font, used only for the x² button
The JavaScript has a squareCurrent() function that reads currentInput, sets the expression display to show the operation before the API call returns, calls POST /api/square with {n: parseFloat(currentInput)}, then updates the result display with data.result. Error responses are shown in red using display.classList.add('error').
What to add — UI:
The current last row is 0 | x² | +/− | =. Since the grid is 4 columns and already full, replace the +/− button with a √ button. The +/− button calls inputPlusMinus() — remove it and put √ in its place.
Style the √ button with the same .btn-square gold class as the x² button, since they are related inverse functions and should look like a pair.
Add a sqrtCurrent() JavaScript function following the exact same pattern as squareCurrent(): read currentInput, set the expression display to "√" + n + " =" before the API call, call POST /api/sqrt with {n: parseFloat(currentInput)}. If the response is not OK (negative number error), show the error message in red on the display using display.classList.add('error'). Otherwise update the display with data.result.
Unit tests:
Add four new test cases to CalculatorControllerTest.java following the exact same MockMvc pattern as the existing tests:
sqrt of 9 returns result 3.0
sqrt of 2 returns result approximately 1.4142135623730951
sqrt of 0 returns result 0.0
sqrt of a negative number returns HTTP 400
Acceptance criteria:
POST /api/sqrt with input 9 returns 200 with result 3.0, operation "sqrt", expression "√9.0", input 9.0
POST /api/sqrt with a negative number returns HTTP 400 with a JSON error field
The UI last row becomes: 0 | x² | √ | =
Both x² and √ buttons are styled gold (same .btn-square class)
Clicking √ on the display value shows the expression before the result appears
Negative number input shows a red error on the display
All existing tests still pass

## Goal
Implement a square root function in the calculator service, including backend API, UI, and unit tests, as described.

## Decisions made
- Sqrt uses a new request model (SqrtRequest) with a single double field n.
- API endpoint is POST /api/sqrt, returns result, operation, expression, and input.
- UI: last row is now 0 | x² | √ | =, both x² and √ styled gold, and √ calls sqrtCurrent().
- Four new unit tests for sqrt: 9, 2, 0, and negative input.
- Error handling for sqrt of negative matches divide-by-zero pattern.

## Open doubts
_(none)_

## Current proposal
- See `src/main/java/com/meesho/calculator/SqrtRequest.java` (java)
- See `src/main/java/com/meesho/calculator/CalculatorController.java` (java)
- See `src/main/resources/static/index.html` (html)
- See `src/test/java/com/meesho/calculator/CalculatorControllerTest.java` (java)

## State
- state: TESTS_RUNNING
- targetRepo: https://github.com/kajal-meesho/calculator-service
- channelId: C0B5ZQF12Q2
- threadTs: 1781814541.199239
- userId: U0B42FAHQ6L
- batchTotal: 1
- testStatus: PASS
- testReportSummary: 12/12 tests passed in 20s

## History
- 2026-06-18T20:29:05Z — Mapped repo for SPSE-5853: https://github.com/kajal-meesho/calculator-service
- 2026-06-18T20:29:12Z — <@U0B42FAHQ6L>: /kevin SPSE-5853
- 2026-06-18 — Implemented square root function in backend, UI, and tests as per ticket; emitting READY.
- 2026-06-18T20:30:10Z — Kevin: ## READY: Adds square root function to backend, UI, and tests as specified in SPSE-5853
- 2026-06-18T20:30:10Z — AI emitted READY; transitioning to plan review.
- 2026-06-18T20:31:41Z — <@U0B42FAHQ6L> approved the plan; moving to implementation review.
- 2026-06-18T20:32:16Z — <@U0B42FAHQ6L> approved implementation; running test gate.
