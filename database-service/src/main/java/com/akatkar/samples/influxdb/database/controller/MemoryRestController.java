package com.akatkar.samples.influxdb.database.controller;

import com.akatkar.samples.influxdb.database.dto.MeasurementDTO;
import com.akatkar.samples.influxdb.database.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class MemoryRestController {

    @Autowired
    private MeasurementRepository measurementRepository;

    @PostMapping("/memory/metric")
    private void saveMemoryMetric(@RequestBody MeasurementDTO measurementDTO) {

        System.out.println(measurementDTO.getName());
        measurementRepository.save(measurementDTO);
    }
}
