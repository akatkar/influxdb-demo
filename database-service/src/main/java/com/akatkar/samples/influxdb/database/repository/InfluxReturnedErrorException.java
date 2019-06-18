package com.akatkar.samples.influxdb.database.repository;

class InfluxReturnedErrorException extends RuntimeException {
    InfluxReturnedErrorException(String message) {
        super(message);
    }
}
