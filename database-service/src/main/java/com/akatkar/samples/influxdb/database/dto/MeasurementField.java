package com.akatkar.samples.influxdb.database.dto;

import java.util.List;

class MeasurementField {

    private String fieldKey;

    private String fieldType;

    public MeasurementField(String fieldKey, String fieldType) {
        this.fieldKey = fieldKey;
        this.fieldType = fieldType;
    }

    public MeasurementField(List<Object> fields) {
        this.fieldKey = fields.get(0).toString();
        this.fieldType = fields.get(1).toString();
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
