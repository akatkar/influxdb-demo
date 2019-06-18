//package com.akatkar.samples.influxdb.database.service;
//
//import com.akatkar.samples.influxdb.database.model.Measure;
//import com.akatkar.samples.influxdb.database.repository.MeasurementRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//
//public abstract class AbstractInfluxDbClient implements InfluxDbClient{
//
//    @Autowired
//    private MeasurementRepository measurementRepository;
//
//    private final String dbname;
//
//    public AbstractInfluxDbClient(String dbname) {
//        this.dbname = dbname;
//    }
//
//    public List<Measure> getMeasurements() {
//        return measurementRepository.getMeasure(dbname);
//    }
//
//    public String getDbname() {
//        return dbname;
//    }
//}
