package com.meesho.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void plus() throws Exception {
        mockMvc.perform(post("/api/plus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"a\": 3, \"b\": 4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(7.0)))
                .andExpect(jsonPath("$.operation", is("plus")));
    }

    @Test
    void minus() throws Exception {
        mockMvc.perform(post("/api/minus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"a\": 10, \"b\": 4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(6.0)))
                .andExpect(jsonPath("$.operation", is("minus")));
    }

    @Test
    void multiply() throws Exception {
        mockMvc.perform(post("/api/multiply")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"a\": 5, \"b\": 6}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(30.0)))
                .andExpect(jsonPath("$.operation", is("multiply")));
    }

    @Test
    void divide() throws Exception {
        mockMvc.perform(post("/api/divide")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"a\": 20, \"b\": 4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(5.0)))
                .andExpect(jsonPath("$.operation", is("divide")));
    }

    @Test
    void divideByZeroReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/divide")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"a\": 10, \"b\": 0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void squareOf4() throws Exception {
        mockMvc.perform(post("/api/square")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"n\": 4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(16.0)))
                .andExpect(jsonPath("$.operation", is("square")))
                .andExpect(jsonPath("$.expression", is("4.0²")))
                .andExpect(jsonPath("$.input", is(4.0)));
    }

    @Test
    void squareOf0() throws Exception {
        mockMvc.perform(post("/api/square")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"n\": 0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(0.0)))
                .andExpect(jsonPath("$.operation", is("square")))
                .andExpect(jsonPath("$.expression", is("0.0²")))
                .andExpect(jsonPath("$.input", is(0.0)));
    }

    @Test
    void squareOfMinus3() throws Exception {
        mockMvc.perform(post("/api/square")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"n\": -3}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(9.0)))
                .andExpect(jsonPath("$.operation", is("square")))
                .andExpect(jsonPath("$.expression", is("-3.0²")))
                .andExpect(jsonPath("$.input", is(-3.0)));
    }
}