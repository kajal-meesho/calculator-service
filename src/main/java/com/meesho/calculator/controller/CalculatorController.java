package com.meesho.calculator.controller;

import com.meesho.calculator.model.CalculateRequest;
import com.meesho.calculator.model.CompoundRequest;
import com.meesho.calculator.model.PowerRequest;
import com.meesho.calculator.model.SqrtRequest;
import com.meesho.calculator.service.CalculatorService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * REST API for the calculator service.
 *
 * POST /calculate        — { a, b, operation } → { result }
 * POST /compound         — { a, b, c } → { result }
 * POST /power            — { a, b } → { result }
 * POST /sqrt             — { a } → { result }
 * GET  /health           — { status: "UP" }
 * GET  /metrics/summary  — aggregated request metrics
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;
    private final MeterRegistry registry;

    @PostMapping("/calculate")
    public ResponseEntity<Map<String, Object>> calculate(@RequestBody CalculateRequest req) {
        try {
            double result = calculatorService.calculate(req.getA(), req.getB(), req.getOperation());
            return ResponseEntity.ok(Map.of("result", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/compound")
    public ResponseEntity<Map<String, Object>> compound(@RequestBody CompoundRequest req) {
        try {
            double result = calculatorService.compound(req.getA(), req.getB(), req.getC());
            return ResponseEntity.ok(Map.of("result", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/power")
    public ResponseEntity<Map<String, Object>> power(@RequestBody PowerRequest req) {
        try {
            double result = calculatorService.power(req.getA(), req.getB());
            return ResponseEntity.ok(Map.of("result", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/sqrt")
    public ResponseEntity<Map<String, Object>> sqrt(@RequestBody SqrtRequest req) {
        try {
            double result = calculatorService.sqrt(req.getA());
            return ResponseEntity.ok(Map.of("result", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }

    @GetMapping("/metrics/summary")
    public ResponseEntity<Map<String, Object>> metricsSummary() {
        long count2xx = 0, count4xx = 0, count5xx = 0;
        double totalDurationMs = 0;
        long totalCount = 0;

        for (Timer timer : registry.find("http.server.requests").timers()) {
            String status = timer.getId().getTag("status");
            long cnt = (long) timer.count();
            totalCount += cnt;
            totalDurationMs += timer.totalTime(TimeUnit.MILLISECONDS);
            if (status != null) {
                int statusCode = 0;
                try { statusCode = Integer.parseInt(status); } catch (Exception ignore) {}
                if (statusCode >= 200 && statusCode < 300) count2xx += cnt;
                else if (statusCode >= 400 && statusCode < 500) count4xx += cnt;
                else if (statusCode >= 500) count5xx += cnt;
            }
        }

        double errorCount = count4xx + count5xx;
        double errorRatePct = totalCount > 0 ? Math.round((errorCount / totalCount) * 1000.0) / 10.0 : 0.0;
        double avgLatencyMs = totalCount > 0 ? Math.round((totalDurationMs / totalCount) * 10.0) / 10.0 : 0.0;

        double p99Ms = 0.0;
        Timer aggTimer = registry.find("http.server.requests").timer();
        if (aggTimer != null) {
            p99Ms = Math.round(aggTimer.percentile(0.99, TimeUnit.MILLISECONDS) * 10.0) / 10.0;
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalRequests", totalCount);
        result.put("statusCode2xx", count2xx);
        result.put("statusCode4xx", count4xx);
        result.put("statusCode5xx", count5xx);
        result.put("errorCount", (long) errorCount);
        result.put("errorRatePct", errorRatePct);
        result.put("avgLatencyMs", avgLatencyMs);
        result.put("p99LatencyMs", p99Ms);
        result.put("status", "UP");

        return ResponseEntity.ok(result);
    }
}