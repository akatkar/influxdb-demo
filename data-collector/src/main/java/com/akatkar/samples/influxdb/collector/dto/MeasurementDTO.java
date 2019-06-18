package com.akatkar.samples.influxdb.collector.dto;

import java.util.Map;

public class MeasurementDTO {

    private String name;
    private Map<String, String> tags;
    private Map<String, Object> fields;

    public MeasurementDTO() {
    }

    public MeasurementDTO(String name, Map<String, String> tags, Map<String, Object> fields) {
        this.name = name;
        this.tags = tags;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
}
