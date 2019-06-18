package com.akatkar.samples.influxdb.database.dto;

import java.time.Instant;

public class ErrorResponse {

    private final Instant timestamp;
    private final String message;
    private final String details;

    public ErrorResponse(String message, String details) {
        this.timestamp = Instant.now();
        this.message = message;
        this.details = details;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
