package com.akatkar.samples.influxdb.collector.service;

import java.util.Map;

public interface InfluxDbService {

    void writeMetrics(Map<String, String> tags, Map<String, Object> fields);
}
