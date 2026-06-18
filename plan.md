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
- Incorporate feedback to style the x² button with gold background (#f0c040) and dark text (#3f0e40) to make it stand out from the purple operation buttons.

## Decisions made
- Square uses a new request model (SquareRequest) with a single double field n.
- API endpoint is POST /api/square, returns result, operation, and expression.
- UI: 0 button no longer spans 2 columns; x² button added after 0; squareCurrent() JS function added.
- Three new unit tests for square: 4, 0, -3.
- x² button will be styled with gold background and dark text per feedback.

## Open doubts
_(none)_

## Current proposal
- See `src/main/resources/static/index.html` (html)

## State
- state: FEEDBACK_IMPL_REVIEW
- prNumber: 1
- prUrl: https://github.com/kajal-meesho/calculator-service/pull/1
- branch: feature/spse-5852-ai-summary
- targetRepo: https://github.com/kajal-meesho/calculator-service
- channelId: C0B5ZQF12Q2
- threadTs: 1781809586.091679
- userId: U0B42FAHQ6L
- lastCommentSeen: 2026-06-18T19:11:07Z
- batchTotal: 1
- testStatus: PASS
- testReportSummary: 8/8 tests passed in 8s
- feedbackQueueJson: W3sic291cmNlIjoiU2xhY2siLCJnaENvbW1lbnRJZCI6bnVsbCwiZ2hVc2VyTG9naW4iOm51bGwsImJvZHkiOiJzdHlsZSB0aGUgeMKyIGJ1dHRvbiB3aXRoIGdvbGQgYmFja2dyb3VuZCBjb2xvciAjZjBjMDQwIGFuZCBkYXJrIHRleHQgIzNmMGU0MCB0byBtYWtlIGl0IHN0YW5kIG91dCBmcm9tIHRoZSBwdXJwbGUgb3BlcmF0aW9uIGJ1dHRvbnMiLCJza2lwUmVhc29uIjpudWxsLCJza2lwcGVkQXQiOm51bGx9LHsic291cmNlIjoiR2l0SHViIiwiZ2hDb21tZW50SWQiOjQ3NDUzMTIwOTUsImdoVXNlckxvZ2luIjoia2FqYWwtbWVlc2hvIiwiYm9keSI6ImFkZCBhbiBcImlucHV0XCIgZmllbGQgdG8gdGhlIHNxdWFyZSByZXNwb25zZSBzaG93aW5nIHRoZSBvcmlnaW5hbCBudW1iZXIgdGhhdCB3YXMgc3F1YXJlZC4gRXhhbXBsZToge1wicmVzdWx0XCI6IDI1LjAsIFwib3BlcmF0aW9uXCI6IFwic3F1YXJlXCIsIFwiZXhwcmVzc2lvblwiOiBcIjUuMMKyXCIsIFwiaW5wdXRcIjogNS4wfS4gQWxzbyB1cGRhdGUgdGhlIHRlc3QgdG8gYXNzZXJ0IHRoZSBpbnB1dCBmaWVsZCBpcyBwcmVzZW50LiIsInNraXBSZWFzb24iOm51bGwsInNraXBwZWRBdCI6bnVsbH1d
- activeFeedbackIndex: 0
- feedbackPlanPanelTs: 1781810097.624449
- feedbackImplPanelTs: 1781810196.038269
- feedbackActivePlanText: Plan to style the x² button with gold background and dark text\n\n- File: src/main/resources/static/index.html\n  - Add a new CSS rule for the x² button to give it a gold background color (#f0c040) and dark text color (#3f0e40).\n  - Apply a unique class (e.g., .btn-square) to the x² button in the HTML markup.\n  - Ensure the new style makes the x² button visually distinct from the purple operation buttons.\n  - Do not affect the styling of other operation buttons.
- feedbackActiveImplText: Style the x² button with gold background (#f0c040) and dark text (#3f0e40) as per feedback.\n\nFILENAME: src/main/resources/static/index.html\n```html\n<!DOCTYPE html>\n<html lang="en">\n<head>\n  <meta charset="UTF-8" />\n  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>\n  <title>Meesho Calculator</title>\n  <style>\n    * { box-sizing: border-box; margin: 0; padding: 0; }\n\n    body {\n      min-height: 100vh;\n      display: flex;\n      flex-direction: column;\n      align-items: center;\n      justify-content: center;\n      background: linear-gradient(135deg, #3f0e40 0%, #831a85 50%, #b44eb6 100%);\n      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n    }\n\n    h1 {\n      color: #fff;\n      font-size: 1.4rem;\n      margin-bottom: 24px;\n      letter-spacing: 2px;\n      text-transform: uppercase;\n      opacity: 0.9;\n    }\n\n    .calculator {\n      background: rgba(255, 255, 255, 0.08);\n      backdrop-filter: blur(12px);\n      border: 1px solid rgba(255, 255, 255, 0.2);\n      border-radius: 24px;\n      padding: 28px;\n      width: 320px;\n      box-shadow: 0 20px 60px rgba(0, 0, 0, 0.4);\n    }\n\n    .display {\n      background: rgba(0, 0, 0, 0.35);\n      border-radius: 14px;\n      padding: 16px 20px;\n      margin-bottom: 20px;\n      text-align: right;\n      min-height: 80px;\n      display: flex;\n      flex-direction: column;\n      justify-content: flex-end;\n    }\n\n    .display .expression {\n      font-size: 0.85rem;\n      color: rgba(255, 255, 255, 0.5);\n      min-height: 20px;\n      margin-bottom: 4px;\n    }\n\n    .display .result {\n      font-size: 2.2rem;\n      font-weight: 300;\n      color: #fff;\n      word-break: break-all;\n    }\n\n    .buttons {\n      display: grid;\n      grid-template-columns: repeat(4, 1fr);\n      gap: 10px;\n    }\n\n    button {\n      padding: 18px 0;\n      border: none;\n      border-radius: 12px;\n      font-size: 1.1rem;\n      cursor: pointer;\n      transition: all 0.15s ease;\n      font-weight: 500;\n    }\n\n    button:active { transform: scale(0.94); }\n\n    .btn-num {\n      background: rgba(255, 255, 255, 0.15);\n      color: #fff;\n    }\n    .btn-num:hover { background: rgba(255, 255, 255, 0.25); }\n\n    .btn-op {\n      background: rgba(234, 189, 251, 0.25);\n      color: #eabdfb;\n    }\n    .btn-op:hover { background: rgba(234, 189, 251, 0.4); }\n    .btn-op.active-op { background: #eabdfb; color: #3f0e40; }\n\n    .btn-equals {\n      background: #eabdfb;\n      color: #3f0e40;\n      font-size: 1.3rem;\n    }\n    .btn-equals:hover { background: #f5d6ff; }\n\n    .btn-clear {\n      background: rgba(255, 100, 100, 0.25);\n      color: #ff9999;\n    }\n    .btn-clear:hover { background: rgba(255, 100, 100, 0.4); }\n\n    /* Removed .btn-zero rule */\n\n    .error { color: #ff9999 !important; font-size: 1rem !important; }\n\n    /* --- FEEDBACK: Style for x² button --- */\n    .btn-square {\n      background: #f0c040;\n      color: #3f0e40;\n      font-weight: 700;\n    }\n    .btn-square:hover {\n      background: #ffd880;\n      color: #3f0e40;\n    }\n  </style>\n</head>\n<body>\n  <h1>Meesho Calculator</h1>\n  <div class="calculator">\n    <div class="display">\n      <div class="expression" id="expression"></div>\n      <div class="result" id="result">0</div>\n    </div>\n    <div class="buttons">\n      <button class="btn-clear" onclick="clearAll()">AC</button>\n      <button class="btn-clear" onclick="clearEntry()">CE</button>\n      <button class="btn-num"   onclick="inputDecimal()">.</button>\n      <button class="btn-op"    onclick="setOp('divide')">÷</button>\n\n      <button class="btn-num" onclick="inputDigit('7')">7</button>\n      <button class="btn-num" onclick="inputDigit('8')">8</button>\n      <button class="btn-num" onclick="inputDigit('9')">9</button>\n      <button class="btn-op"  onclick="setOp('multiply')">×</button>\n\n      <button class="btn-num" onclick="inputDigit('4')">4</button>\n      <button class="btn-num" onclick="inputDigit('5')">5</button>\n      <button class="btn-num" onclick="inputDigit('6')">6</button>\n      <button class="btn-op"  onclick="setOp('minus')">−</button>\n\n      <button class="btn-num" onclick="inputDigit('1')">1</button>\n      <button class="btn-num" onclick="inputDigit('2')">2</button>\n      <button class="btn-num" onclick="inputDigit('3')">3</button>\n      <button class="btn-op"  onclick="setOp('plus')">+</button>\n\n      <button class="btn-num" onclick="inputDigit('0')">0</button>\n      <button class="btn-square" onclick="squareCurrent()">x²</button>\n      <button class="btn-num" onclick="inputPlusMinus()">+/−</button>\n      <button class="btn-equals" onclick="calculate()">=</button>\n    </div>\n  </div>\n\n  <script>\n    let firstNum = null;\n    let currentOp = null;\n    let currentInput = '0';\n    let justCalculated = false;\n\n    const display = document.getElementById('result');\n    const exprDisplay = document.getElementById('expression');\n\n    function updateDisplay(val) {\n      display.classList.remove('error');\n      display.textContent = val;\n    }\n\n    function inputDigit(d) {\n      if (justCalculated) { currentInput = d; justCalculated = false; }\n      else currentInput = currentInput === '0' ? d : currentInput + d;\n      updateDisplay(currentInput);\n    }\n\n    function inputDecimal() {\n      if (justCalculated) { currentInput = '0.'; justCalculated = false; }\n      else if (!currentInput.includes('.')) currentInput += '.';\n      updateDisplay(currentInput);\n    }\n\n    function inputPlusMinus() {\n      currentInput = String(parseFloat(currentInput) * -1);\n      updateDisplay(currentInput);\n    }\n\n    function setOp(op) {\n      document.querySelectorAll('.btn-op').forEach(b => b.classList.remove('active-op'));\n      event.target.classList.add('active-op');\n      firstNum = parseFloat(currentInput);\n      currentOp = op;\n      justCalculated = false;\n      exprDisplay.textContent = firstNum + ' ' + opSymbol(op);\n      currentInput = '0';\n    }\n\n    function opSymbol(op) {\n      return { plus: '+', minus: '−', multiply: '×', divide: '÷' }[op] || op;\n    }\n\n    async function calculate() {\n      if (firstNum === null || currentOp === null) return;\n      const b = parseFloat(currentInput);\n      exprDisplay.textContent = firstNum + ' ' + opSymbol(currentOp) + ' ' + b + ' =';\n      try {\n        const res = await fetch('/api/' + currentOp, {\n          method: 'POST',\n          headers: { 'Content-Type': 'application/json' },\n          body: JSON.stringify({ a: firstNum, b })\n        });\n        const data = await res.json();\n        if (data.error) {\n          display.classList.add('error');\n          display.textContent = data.error;\n        } else {\n          updateDisplay(data.result);\n          currentInput = String(data.result);\n        }\n      } catch (e) {\n        display.classList.add('error');\n        display.textContent = 'Error';\n      }\n      document.querySelectorAll('.btn-op').forEach(b => b.classList.remove('active-op'));\n      firstNum = null; currentOp = null; justCalculated = true;\n    }\n\n    function clearAll() {\n      firstNum = null; currentOp = null; currentInput = '0'; justCalculated = false;\n      updateDisplay('0'); exprDisplay.textContent = '';\n      document.querySelectorAll('.btn-op').forEach(b => b.classList.remove('active-op'));\n    }\n\n    function clearEntry() {\n      currentInput = '0'; updateDisplay('0');\n    }\n\n    // --- FEEDBACK: Square function JS handler ---\n    async function squareCurrent() {\n      const n = parseFloat(currentInput);\n      exprDisplay.textContent = n + '² =';\n      try {\n        const res = await fetch('/api/square', {\n          method: 'POST',\n          headers: { 'Content-Type': 'application/json' },\n          body: JSON.stringify({ n })\n        });\n        const data = await res.json();\n        updateDisplay(data.result);\n        currentInput = String(data.result);\n      } catch (e) {\n        display.classList.add('error');\n        display.textContent = 'Error';\n      }\n      justCalculated = true;\n    }\n  </script>\n</body>\n</html>\n```

## History
- 2026-06-18T19:06:29Z — Mapped repo for SPSE-5852: https://github.com/kajal-meesho/calculator-service
- 2026-06-18T19:06:35Z — <@U0B42FAHQ6L>: /kevin SPSE-5852
- 2026-06-18 — Session started, repo mapped.
- 2026-06-18 — Implemented square function in backend, UI, and tests as per ticket; emitting READY.
- 2026-06-18T19:07:31Z — Kevin: ## READY: Adds square function to backend, UI, and tests as specified in SPSE-5852
- 2026-06-18T19:07:31Z — AI emitted READY; transitioning to plan review.
- 2026-06-18T19:08:22Z — <@U0B42FAHQ6L> approved the plan; moving to implementation review.
- 2026-06-18T19:09:23Z — <@U0B42FAHQ6L> approved implementation; running test gate.
- 2026-06-18T19:10:00Z — PR opened at https://github.com/kajal-meesho/calculator-service/pull/1
- 2026-06-18T19:14:32Z — <@U0B42FAHQ6L> started interactive feedback loop: 2 item(s)
- 2026-06-18 — AI received feedback to style x² button with gold background and dark text; plan drafted.
- 2026-06-18T19:14:56Z — Kevin: feedback plan 1/2: Plan to style the x² button with gold background and dark text
- File: src/main/resources/static/index.html
- Add a new CSS rule for the x² button to give it a gold background color (#f0c040) and d…
- 2026-06-18T19:15:27Z — <@U0B42FAHQ6L> approved feedback plan 1
- 2026-06-18 — AI implemented feedback: x² button styled with gold background and dark text; emitting READY.
- 2026-06-18T19:16:32Z — Kevin: feedback impl 1/2: Style the x² button with gold background (#f0c040) and dark text (#3f0e40) as per feedback.
- 2026-06-18T19:17:58Z — <@U0B42FAHQ6L> approved feedback impl 1
