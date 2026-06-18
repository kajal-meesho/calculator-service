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
- Incorporate feedback to make the √ button visually distinct from the gold x² button by styling it with a teal background (#40c0c0) and teal text (#0a3f3f).
- Incorporate feedback to show the full equation in the expression line after sqrt result, e.g., "√9.0 = 3.0".

## Decisions made
- Sqrt uses a new request model (SqrtRequest) with a single double field n.
- API endpoint is POST /api/sqrt, returns result, operation, expression, and input.
- UI: last row is now 0 | x² | √ | =, both x² and √ styled gold, and √ calls sqrtCurrent().
- Four new unit tests for sqrt: 9, 2, 0, and negative input.
- Error handling for sqrt of negative matches divide-by-zero pattern.
- √ button will be styled with teal background (#40c0c0) and teal text (#0a3f3f) to distinguish it from the gold x² button.

## Open doubts
_(none)_

## Current proposal
- See `src/main/resources/static/index.html` (html)

## State
- state: FEEDBACK_IMPL_REVIEW
- prNumber: 2
- prUrl: https://github.com/kajal-meesho/calculator-service/pull/2
- branch: feature/spse-5853-ai-summary
- targetRepo: https://github.com/kajal-meesho/calculator-service
- channelId: C0B5ZQF12Q2
- threadTs: 1781814541.199239
- userId: U0B42FAHQ6L
- lastCommentSeen: 2026-06-18T20:34:27Z
- batchTotal: 1
- testStatus: PASS
- testReportSummary: 12/12 tests passed in 20s
- feedbackQueueJson: W3sic291cmNlIjoiR2l0SHViIiwiZ2hDb21tZW50SWQiOjQ3NDU5MDk4MjAsImdoVXNlckxvZ2luIjoia2FqYWwtbWVlc2hvIiwiYm9keSI6IkNoYW5nZSB0aGUg4oiaIGJ1dHRvbiBjb2xvciB0byB0ZWFsOiBiYWNrZ3JvdW5kICM0MGMwYzAsIHRleHQgIzBhM2YzZiDigJQgaXQgc2hvdWxkIGxvb2sgZGlzdGluY3QgZnJvbSB0aGUgZ29sZCB4wrIgYnV0dG9uIiwic2tpcFJlYXNvbiI6bnVsbCwic2tpcHBlZEF0IjpudWxsfSx7InNvdXJjZSI6IlNsYWNrIiwiZ2hDb21tZW50SWQiOm51bGwsImdoVXNlckxvZ2luIjpudWxsLCJib2R5IjoiQWZ0ZXIgc3FydCByZXN1bHQsIHNob3cgdGhlIGZ1bGwgZXF1YXRpb24gaW4gdGhlIGV4cHJlc3Npb24gbGluZSDigJQgZS5nLiDiiJo5LjAgPSAzLjAgKHNhbWUgZm9ybWF0IGFzIHRoZSBleGlzdGluZyBzcXVhcmU6IFwiOS4wwrIgPVwiKSIsInNraXBSZWFzb24iOm51bGwsInNraXBwZWRBdCI6bnVsbH1d
- activeFeedbackIndex: 1
- feedbackPlanPanelTs: 1781815169.842549
- feedbackImplPanelTs: 1781815291.550169
- feedbackActivePlanText: Plan to update the UI so that after sqrt, the expression line shows the full equation (e.g., "√9.0 = 3.0")\n\n- File: src/main/resources/static/index.html\n  - Update the sqrtCurrent() JavaScript function so that after receiving a successful response, the expression line displays the full equation in the format: "√n = result" (e.g., "√9.0 = 3.0").\n  - Ensure this matches the format used for the square operation, which currently shows "n² = result".\n  - Do not change the backend or any other files, as this feedback is UI-only.
- feedbackActiveImplText: Show full equation in expression line after sqrt result (e.g., "√9.0 = 3.0")\n\nFILENAME: src/main/resources/static/index.html\n```html\n<!DOCTYPE html>\n<html lang="en">\n<head>\n  <meta charset="UTF-8" />\n  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>\n  <title>Meesho Calculator</title>\n  <style>\n    * { box-sizing: border-box; margin: 0; padding: 0; }\n\n    body {\n      min-height: 100vh;\n      display: flex;\n      flex-direction: column;\n      align-items: center;\n      justify-content: center;\n      background: linear-gradient(135deg, #3f0e40 0%, #831a85 50%, #b44eb6 100%);\n      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n    }\n\n    h1 {\n      color: #fff;\n      font-size: 1.4rem;\n      margin-bottom: 24px;\n      letter-spacing: 2px;\n      text-transform: uppercase;\n      opacity: 0.9;\n    }\n\n    .calculator {\n      background: rgba(255, 255, 255, 0.08);\n      backdrop-filter: blur(12px);\n      border: 1px solid rgba(255, 255, 255, 0.2);\n      border-radius: 24px;\n      padding: 28px;\n      width: 320px;\n      box-shadow: 0 20px 60px rgba(0, 0, 0, 0.4);\n    }\n\n    .display {\n      background: rgba(0, 0, 0, 0.35);\n      border-radius: 14px;\n      padding: 16px 20px;\n      margin-bottom: 20px;\n      text-align: right;\n      min-height: 80px;\n      display: flex;\n      flex-direction: column;\n      justify-content: flex-end;\n    }\n\n    .display .expression {\n      font-size: 0.85rem;\n      color: rgba(255, 255, 255, 0.5);\n      min-height: 20px;\n      margin-bottom: 4px;\n    }\n\n    .display .result {\n      font-size: 2.2rem;\n      font-weight: 300;\n      color: #fff;\n      word-break: break-all;\n    }\n\n    .buttons {\n      display: grid;\n      grid-template-columns: repeat(4, 1fr);\n      gap: 10px;\n    }\n\n    button {\n      padding: 18px 0;\n      border: none;\n      border-radius: 12px;\n      font-size: 1.1rem;\n      cursor: pointer;\n      transition: all 0.15s ease;\n      font-weight: 500;\n    }\n\n    button:active { transform: scale(0.94); }\n\n    .btn-num {\n      background: rgba(255, 255, 255, 0.15);\n      color: #fff;\n    }\n    .btn-num:hover { background: rgba(255, 255, 255, 0.25); }\n\n    .btn-op {\n      background: rgba(234, 189, 251, 0.25);\n      color: #eabdfb;\n    }\n    .btn-op:hover { background: rgba(234, 189, 251, 0.4); }\n    .btn-op.active-op { background: #eabdfb; color: #3f0e40; }\n\n    .btn-equals {\n      background: #eabdfb;\n      color: #3f0e40;\n      font-size: 1.3rem;\n    }\n    .btn-equals:hover { background: #f5d6ff; }\n\n    .btn-clear {\n      background: rgba(255, 100, 100, 0.25);\n      color: #ff9999;\n    }\n    .btn-clear:hover { background: rgba(255, 100, 100, 0.4); }\n\n    /* Removed .btn-zero rule */\n\n    .error { color: #ff9999 !important; font-size: 1rem !important; }\n\n    /* --- FEEDBACK: Style for x² button --- */\n    .btn-square {\n      background: #f0c040;\n      color: #3f0e40;\n      font-weight: 700;\n    }\n    .btn-square:hover {\n      background: #ffd880;\n      color: #3f0e40;\n    }\n\n    /* --- FEEDBACK: Style for √ button (teal) --- */\n    .btn-sqrt {\n      background: #40c0c0;\n      color: #0a3f3f;\n      font-weight: 700;\n    }\n    .btn-sqrt:hover {\n      background: #5de0e0;\n      color: #0a3f3f;\n    }\n  </style>\n</head>\n<body>\n  <h1>Meesho Calculator</h1>\n  <div class="calculator">\n    <div class="display">\n      <div class="expression" id="expression"></div>\n      <div class="result" id="result">0</div>\n    </div>\n    <div class="buttons">\n      <button class="btn-clear" onclick="clearAll()">AC</button>\n      <button class="btn-clear" onclick="clearEntry()">CE</button>\n      <button class="btn-num"   onclick="inputDecimal()">.</button>\n      <button class="btn-op"    onclick="setOp('divide')">÷</button>\n\n      <button class="btn-num" onclick="inputDigit('7')">7</button>\n      <button class="btn-num" onclick="inputDigit('8')">8</button>\n      <button class="btn-num" onclick="inputDigit('9')">9</button>\n      <button class="btn-op"  onclick="setOp('multiply')">×</button>\n\n      <button class="btn-num" onclick="inputDigit('4')">4</button>\n      <button class="btn-num" onclick="inputDigit('5')">5</button>\n      <button class="btn-num" onclick="inputDigit('6')">6</button>\n      <button class="btn-op"  onclick="setOp('minus')">−</button>\n\n      <button class="btn-num" onclick="inputDigit('1')">1</button>\n      <button class="btn-num" onclick="inputDigit('2')">2</button>\n      <button class="btn-num" onclick="inputDigit('3')">3</button>\n      <button class="btn-op"  onclick="setOp('plus')">+</button>\n\n      <button class="btn-num" onclick="inputDigit('0')">0</button>\n      <button class="btn-square" onclick="squareCurrent()">x²</button>\n      <button class="btn-sqrt" onclick="sqrtCurrent()">√</button>\n      <button class="btn-equals" onclick="calculate()">=</button>\n    </div>\n  </div>\n\n  <script>\n    let firstNum = null;\n    let currentOp = null;\n    let currentInput = '0';\n    let justCalculated = false;\n\n    const display = document.getElementById('result');\n    const exprDisplay = document.getElementById('expression');\n\n    function updateDisplay(val) {\n      display.classList.remove('error');\n      display.textContent = val;\n    }\n\n    function inputDigit(d) {\n      if (justCalculated) { currentInput = d; justCalculated = false; }\n      else currentInput = currentInput === '0' ? d : currentInput + d;\n      updateDisplay(currentInput);\n    }\n\n    function inputDecimal() {\n      if (justCalculated) { currentInput = '0.'; justCalculated = false; }\n      else if (!currentInput.includes('.')) currentInput += '.';\n      updateDisplay(currentInput);\n    }\n\n    function inputPlusMinus() {\n      currentInput = String(parseFloat(currentInput) * -1);\n      updateDisplay(currentInput);\n    }\n\n    function setOp(op) {\n      document.querySelectorAll('.btn-op').forEach(b => b.classList.remove('active-op'));\n      event.target.classList.add('active-op');\n      firstNum = parseFloat(currentInput);\n      currentOp = op;\n      justCalculated = false;\n      exprDisplay.textContent = firstNum + ' ' + opSymbol(op);\n      currentInput = '0';\n    }\n\n    function opSymbol(op) {\n      return { plus: '+', minus: '−', multiply: '×', divide: '÷' }[op] || op;\n    }\n\n    async function calculate() {\n      if (firstNum === null || currentOp === null) return;\n      const b = parseFloat(currentInput);\n      exprDisplay.textContent = firstNum + ' ' + opSymbol(currentOp) + ' ' + b + ' =';\n      try {\n        const res = await fetch('/api/' + currentOp, {\n          method: 'POST',\n          headers: { 'Content-Type': 'application/json' },\n          body: JSON.stringify({ a: firstNum, b })\n        });\n        const data = await res.json();\n        if (data.error) {\n          display.classList.add('error');\n          display.textContent = data.error;\n        } else {\n          updateDisplay(data.result);\n          currentInput = String(data.result);\n        }\n      } catch (e) {\n        display.classList.add('error');\n        display.textContent = 'Error';\n      }\n      document.querySelectorAll('.btn-op').forEach(b => b.classList.remove('active-op'));\n      firstNum = null; currentOp = null; justCalculated = true;\n    }\n\n    function clearAll() {\n      firstNum = null; currentOp = null; currentInput = '0'; justCalculated = false;\n      updateDisplay('0'); exprDisplay.textContent = '';\n      document.querySelectorAll('.btn-op').forEach(b => b.classList.remove('active-op'));\n    }\n\n    function clearEntry() {\n      currentInput = '0'; updateDisplay('0');\n    }\n\n    // --- FEEDBACK: Square function JS handler ---\n    async function squareCurrent() {\n      const n = parseFloat(currentInput);\n      exprDisplay.textContent = n + '² =';\n      try {\n        const res = await fetch('/api/square', {\n          method: 'POST',\n          headers: { 'Content-Type': 'application/json' },\n          body: JSON.stringify({ n })\n        });\n        const data = await res.json();\n        updateDisplay(data.result);\n        currentInput = String(data.result);\n        // Show full equation after result\n        exprDisplay.textContent = n + '² = ' + data.result;\n      } catch (e) {\n        display.classList.add('error');\n        display.textContent = 'Error';\n      }\n      justCalculated = true;\n    }\n\n    // --- FEEDBACK: Sqrt function JS handler ---\n    async function sqrtCurrent() {\n      const n = parseFloat(currentInput);\n      exprDisplay.textContent = "√" + n + " =";\n      try {\n        const res = await fetch('/api/sqrt', {\n          method: 'POST',\n          headers: { 'Content-Type': 'application/json' },\n          body: JSON.stringify({ n })\n        });\n        const data = await res.json();\n        if (data.error) {\n          display.classList.add('error');\n          display.textContent = data.error;\n        } else {\n          updateDisplay(data.result);\n          currentInput = String(data.result);\n          // Show full equation after result, e.g. "√9.0 = 3.0"\n          exprDisplay.textContent = "√" + n + " = " + data.result;\n        }\n      } catch (e) {\n        display.classList.add('error');\n        display.textContent = 'Error';\n      }\n      justCalculated = true;\n    }\n  </script>\n</body>\n</html>\n```

## History
- 2026-06-18T20:29:05Z — Mapped repo for SPSE-5853: https://github.com/kajal-meesho/calculator-service
- 2026-06-18T20:29:12Z — <@U0B42FAHQ6L>: /kevin SPSE-5853
- 2026-06-18 — Implemented square root function in backend, UI, and tests as per ticket; emitting READY.
- 2026-06-18T20:30:10Z — Kevin: ## READY: Adds square root function to backend, UI, and tests as specified in SPSE-5853
- 2026-06-18T20:30:10Z — AI emitted READY; transitioning to plan review.
- 2026-06-18T20:31:41Z — <@U0B42FAHQ6L> approved the plan; moving to implementation review.
- 2026-06-18T20:32:16Z — <@U0B42FAHQ6L> approved implementation; running test gate.
- 2026-06-18T20:33:01Z — PR opened at https://github.com/kajal-meesho/calculator-service/pull/2
- 2026-06-18T20:35:48Z — <@U0B42FAHQ6L> started interactive feedback loop: 2 item(s)
- 2026-06-18 — AI received feedback to make √ button teal and visually distinct from gold x²; plan drafted.
- 2026-06-18T20:36:17Z — Kevin: feedback plan 1/2: Plan to change the √ button color to teal as per feedback
- File: src/main/resources/static/index.html
- Add a new CSS rule for the √ button (likely using a new class, e.g., `.btn-sqrt`) to set its…
- 2026-06-18T20:37:09Z — <@U0B42FAHQ6L> approved feedback plan 1
- 2026-06-18 — AI implemented feedback: style √ button with teal background and text using .btn-sqrt class; emitting READY.
- 2026-06-18T20:37:59Z — Kevin: feedback impl 1/2: Style the √ button with teal background and text, using a new .btn-sqrt class, and update the HTML to use it.
- 2026-06-18T20:39:01Z — <@U0B42FAHQ6L> approved feedback impl 1
- 2026-06-18T20:39:07Z — Feedback 1/2 implemented and pushed
- 2026-06-18T20:40:00Z — AI received feedback to show full equation in expression line after sqrt result; plan drafted.
- 2026-06-18T20:39:28Z — Kevin: feedback plan 2/2: Plan to update the UI so that after sqrt, the expression line shows the full equation (e.g., "√9.0 = 3.0")
- File: src/main/resources/static/index.html
- Update the sqrtCurrent() JavaScript functio…
- 2026-06-18T20:40:39Z — <@U0B42FAHQ6L> approved feedback plan 2
- 2026-06-18 — AI implemented feedback: show full equation in expression line after sqrt result; emitting READY.
- 2026-06-18T20:41:28Z — Kevin: feedback impl 2/2: Show full equation in expression line after sqrt result (e.g., "√9.0 = 3.0")
- 2026-06-18T20:42:14Z — <@U0B42FAHQ6L> approved feedback impl 2
