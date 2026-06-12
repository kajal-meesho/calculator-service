package com.meesho.calculator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Binds the {@code background.*} keys from application.properties.
 *
 * Example:
 *   background.color=#191970
 *
 * Kevin / the LLM only needs to change the value of {@code background.color}
 * in application.properties — no other file needs to be touched.
 */
@Component
@ConfigurationProperties(prefix = "background")
public class BackgroundConfig {

    /** CSS colour string for the page background (hex, rgb, named colour, etc.) */
    private String color = "#f1f5f9";

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
