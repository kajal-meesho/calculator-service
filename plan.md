# KRD: SPSE-5852 — Add square function to calculator service

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
- Incorporate feedback to add an "input" field to the square response showing the original number that was squared, and update the test to assert the input field is present.

## Decisions made
- Square uses a new request model (SquareRequest) with a single double field n.
- API endpoint is POST /api/square, returns result, operation, and expression.
- UI: 0 button no longer spans 2 columns; x² button added after 0; squareCurrent() JS function added.
- Three new unit tests for square: 4, 0, -3.
- x² button will be styled with gold background and dark text per feedback.

## Open doubts
_(none)_

## Current proposal
- See `src/main/java/com/meesho/calculator/CalculatorResponse.java` (java)
- See `src/main/java/com/meesho/calculator/CalculatorController.java` (java)
- See `src/main/java/com/meesho/calculator/SquareRequest.java` (java)
- See `src/test/java/com/meesho/calculator/CalculatorControllerTest.java` (java)

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
- activeFeedbackIndex: 1
- feedbackPlanPanelTs: 1781810307.953789
- feedbackImplPanelTs: 1781810434.730269
- feedbackActivePlanText: Plan to add "input" field to square response and update test to assert its presence.\n\n- File: src/main/java/com/meesho/calculator/CalculatorResponse.java\n  - Add a new field `input` (double) to the class.\n  - Add a constructor overload or update the existing constructor(s) to accept and set the `input` value.\n  - Add a getter for `input`.\n  - For plus/minus/multiply/divide, leave the field unset or default (since only square needs it).\n- File: src/main/java/com/meesho/calculator/CalculatorController.java\n  - (Assuming the square endpoint is implemented in this file but not shown in the current code: will need to add/modify the square endpoint to include the `input` field in the response.)\n- File: src/test/java/com/meesho/calculator/CalculatorControllerTest.java\n  - Update the test for the square endpoint to assert that the `input` field is present and matches the input value sent.
- feedbackActiveImplText: Add "input" field to square response and update test to assert its presence\n\nFILENAME: src/main/java/com/meesho/calculator/CalculatorResponse.java\n```java\npackage com.meesho.calculator;\n\npublic class CalculatorResponse {\n    private double result;\n    private String operation;\n    private String expression;\n    private Double input; // Use Double to allow null for non-square operations\n\n    public CalculatorResponse(double result, String operation, String expression) {\n        this.result = result;\n        this.operation = operation;\n        this.expression = expression;\n        this.input = null;\n    }\n\n    public CalculatorResponse(double result, String operation, String expression, double input) {\n        this.result = result;\n        this.operation = operation;\n        this.expression = expression;\n        this.input = input;\n    }\n\n    public double getResult() { return result; }\n    public String getOperation() { return operation; }\n    public String getExpression() { return expression; }\n    public Double getInput() { return input; }\n}\n```\n\nFILENAME: src/main/java/com/meesho/calculator/CalculatorController.java\n```java\npackage com.meesho.calculator;\n\nimport org.springframework.http.MediaType;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.*;\n\n@RestController\n@RequestMapping("/api")\npublic class CalculatorController {\n\n    @PostMapping(value = "/plus", produces = MediaType.APPLICATION_JSON_VALUE)\n    public CalculatorResponse plus(@RequestBody CalculatorRequest req) {\n        return new CalculatorResponse(req.getA() + req.getB(), "plus",\n                req.getA() + " + " + req.getB());\n    }\n\n    @PostMapping(value = "/minus", produces = MediaType.APPLICATION_JSON_VALUE)\n    public CalculatorResponse minus(@RequestBody CalculatorRequest req) {\n        return new CalculatorResponse(req.getA() - req.getB(), "minus",\n                req.getA() + " - " + req.getB());\n    }\n\n    @PostMapping(value = "/multiply", produces = MediaType.APPLICATION_JSON_VALUE)\n    public CalculatorResponse multiply(@RequestBody CalculatorRequest req) {\n        return new CalculatorResponse(req.getA() * req.getB(), "multiply",\n                req.getA() + " × " + req.getB());\n    }\n\n    @PostMapping(value = "/divide", produces = MediaType.APPLICATION_JSON_VALUE)\n    public ResponseEntity<?> divide(@RequestBody CalculatorRequest req) {\n        if (req.getB() == 0) {\n            return ResponseEntity.badRequest().body("{\"error\": \"Division by zero is not allowed\"}");\n        }\n        return ResponseEntity.ok(new CalculatorResponse(req.getA() / req.getB(), "divide",\n                req.getA() + " ÷ " + req.getB()));\n    }\n\n    @PostMapping(value = "/square", produces = MediaType.APPLICATION_JSON_VALUE)\n    public CalculatorResponse square(@RequestBody SquareRequest req) {\n        double n = req.getN();\n        double result = n * n;\n        String expr = n + "²";\n        return new CalculatorResponse(result, "square", expr, n);\n    }\n}\n```\n\nFILENAME: src/main/java/com/meesho/calculator/SquareRequest.java\n```java\npackage com.meesho.calculator;\n\npublic class SquareRequest {\n    private double n;\n\n    public double getN() { return n; }\n    public void setN(double n) { this.n = n; }\n}\n```\n\nFILENAME: src/test/java/com/meesho/calculator/CalculatorControllerTest.java\n```java\npackage com.meesho.calculator;\n\nimport org.junit.jupiter.api.Test;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;\nimport org.springframework.http.MediaType;\nimport org.springframework.test.web.servlet.MockMvc;\n\nimport static org.hamcrest.Matchers.is;\nimport static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;\nimport static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;\n\n@WebMvcTest(CalculatorController.class)\nclass CalculatorControllerTest {\n\n    @Autowired\n    private MockMvc mockMvc;\n\n    @Test\n    void plus() throws Exception {\n        mockMvc.perform(post("/api/plus")\n                .contentType(MediaType.APPLICATION_JSON)\n                .content("{\"a\": 3, \"b\": 4}"))\n                .andExpect(status().isOk())\n                .andExpect(jsonPath("$.result", is(7.0)))\n                .andExpect(jsonPath("$.operation", is("plus")));\n    }\n\n    @Test\n    void minus() throws Exception {\n        mockMvc.perform(post("/api/minus")\n                .contentType(MediaType.APPLICATION_JSON)\n                .content("{\"a\": 10, \"b\": 4}"))\n                .andExpect(status().isOk())\n                .andExpect(jsonPath("$.result", is(6.0)))\n                .andExpect(jsonPath("$.operation", is("minus")));\n    }\n\n    @Test\n    void multiply() throws Exception {\n        mockMvc.perform(post("/api/multiply")\n                .contentType(MediaType.APPLICATION_JSON)\n                .content("{\"a\": 5, \"b\": 6}"))\n                .andExpect(status().isOk())\n                .andExpect(jsonPath("$.result", is(30.0)))\n                .andExpect(jsonPath("$.operation", is("multiply")));\n    }\n\n    @Test\n    void divide() throws Exception {\n        mockMvc.perform(post("/api/divide")\n                .contentType(MediaType.APPLICATION_JSON)\n                .content("{\"a\": 20, \"b\": 4}"))\n                .andExpect(status().isOk())\n                .andExpect(jsonPath("$.result", is(5.0)))\n                .andExpect(jsonPath("$.operation", is("divide")));\n    }\n\n    @Test\n    void divideByZeroReturnsBadRequest() throws Exception {\n        mockMvc.perform(post("/api/divide")\n                .contentType(MediaType.APPLICATION_JSON)\n                .content("{\"a\": 10, \"b\": 0}"))\n                .andExpect(status().isBadRequest());\n    }\n\n    @Test\n    void squareOf4() throws Exception {\n        mockMvc.perform(post("/api/square")\n                .contentType(MediaType.APPLICATION_JSON)\n                .content("{\"n\": 4}"))\n                .andExpect(status().isOk())\n                .andExpect(jsonPath("$.result", is(16.0)))\n                .andExpect(jsonPath("$.operation", is("square")))\n                .andExpect(jsonPath("$.expression", is("4.0²")))\n                .andExpect(jsonPath("$.input", is(4.0)));\n    }\n\n    @Test\n    void squareOf0() throws Exception {\n        mockMvc.perform(post("/api/square")\n                .contentType(MediaType.APPLICATION_JSON)\n                .content("{\"n\": 0}"))\n                .andExpect(status().isOk())\n                .andExpect(jsonPath("$.result", is(0.0)))\n                .andExpect(jsonPath("$.operation", is("square")))\n                .andExpect(jsonPath("$.expression", is("0.0²")))\n                .andExpect(jsonPath("$.input", is(0.0)));\n    }\n\n    @Test\n    void squareOfMinus3() throws Exception {\n        mockMvc.perform(post("/api/square")\n                .contentType(MediaType.APPLICATION_JSON)\n                .content("{\"n\": -3}"))\n                .andExpect(status().isOk())\n                .andExpect(jsonPath("$.result", is(9.0)))\n                .andExpect(jsonPath("$.operation", is("square")))\n                .andExpect(jsonPath("$.expression", is("-3.0²")))\n                .andExpect(jsonPath("$.input", is(-3.0)));\n    }\n}\n```

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
- 2026-06-18T19:18:03Z — Feedback 1/2 implemented and pushed
- 2026-06-18T19:19:00Z — AI received feedback to add "input" field to square response and update test to assert it; plan drafted.
- 2026-06-18T19:18:26Z — Kevin: feedback plan 2/2: Plan to add "input" field to square response and update test to assert its presence.
- File: src/main/java/com/meesho/calculator/CalculatorResponse.java
- Add a new field `input` (double) to the cl…
- 2026-06-18T19:19:46Z — <@U0B42FAHQ6L> approved feedback plan 2
- 2026-06-18 — AI implemented feedback: add "input" field to square response and update test to assert its presence; emitting READY.
- 2026-06-18T19:20:26Z — Kevin: feedback impl 2/2: Add "input" field to square response and update test to assert its presence
- 2026-06-18T19:21:18Z — <@U0B42FAHQ6L> approved feedback impl 2
- 2026-06-18T19:21:29Z — <@U0B42FAHQ6L> approved feedback impl 2
