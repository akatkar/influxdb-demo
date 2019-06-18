package com.akatkar.samples.influxdb.database.dto;

class MeasurementTag {
    
    private String tagKey;

    public MeasurementTag(String tagKey) {
        this.tagKey = tagKey;
    }

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }
}
