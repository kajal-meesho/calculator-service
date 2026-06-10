package com.drishtix.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompoundRequest {
    private double a;
    private double b;
    private double c;
}