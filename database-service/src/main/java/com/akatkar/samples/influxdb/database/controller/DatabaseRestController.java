package com.akatkar.samples.influxdb.database.controller;

import com.akatkar.samples.influxdb.database.dto.MeasurementDTO2;
import com.akatkar.samples.influxdb.database.model.Database;
import com.akatkar.samples.influxdb.database.model.Measure;
import com.akatkar.samples.influxdb.database.repository.MeasurementRepository;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/db")
public class DatabaseRestController {

    @Autowired
    private MeasurementRepository measurementRepository;

    @GetMapping
    public List<Database> getDatabases() {
        return measurementRepository.getDatabases();
    }

    @GetMapping("/{dbname}")
    public List<Measure> getAllBuilds(@PathVariable String dbname) {
        return measurementRepository.getMeasure(dbname);
    }

    @GetMapping("/{dbname}/{measurement}")
    public MeasurementDTO2 getRawMeasurement(@PathVariable String dbname, @PathVariable String measurement) {
        return measurementRepository.showMeasurement(dbname, measurement);
    }

    @GetMapping("/{dbname}/{measurement}/data")
    public QueryResult getRawMeasurementData(@PathVariable String dbname, @PathVariable String measurement) {
        return measurementRepository.findRawData(dbname, measurement);
    }

    @GetMapping("/{dbname}/retention")
    public QueryResult getRetention(@PathVariable String dbname) {
        return measurementRepository.getRetention(dbname);
    }
    
    @GetMapping("/{dbname}/series")
    public QueryResult getSeries(@PathVariable String dbname) {
        return measurementRepository.getSeries(dbname);
    }
}
