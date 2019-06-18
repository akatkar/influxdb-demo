package com.akatkar.samples.influxdb.collector.service;

import com.akatkar.samples.influxdb.collector.dto.MeasurementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
class InfluxDbServiceImpl implements InfluxDbService {

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void writeMetrics(Map<String, String> tags, Map<String, Object> fields) {

        MeasurementDTO measurementDTO = new MeasurementDTO("Microservice", tags, fields);
        restTemplate.postForEntity("http://localhost:5001/memory/metric", measurementDTO, MeasurementDTO.class);
    }
}
