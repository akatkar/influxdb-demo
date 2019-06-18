package com.akatkar.samples.influxdb.database.model;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name="measurements")
public class Measure {

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
