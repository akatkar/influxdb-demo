package com.akatkar.samples.influxdb.database.repository;

import com.akatkar.samples.influxdb.database.config.InfluxDbConfig;
import com.akatkar.samples.influxdb.database.model.Measure;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MeasurementRepository extends AbstractRepository<Measure> {

    @Autowired
    public MeasurementRepository(InfluxDbConfig influxDbConfig) {
        super(Measure.class, influxDbConfig);
    }

    public List<Measure> getMeasure(String dbname) {

        String query = "SHOW MEASUREMENTS";
        return super.executeQuery(dbname, query);
    }

    public QueryResult getRetention(String dbname) {

        String query = "SHOW RETENTION POLICIES";
        return super.runQuery(dbname, query);
    }

    public QueryResult getSeries(String dbname) {

        String query = "SHOW SERIES LIMIT 10";
        return super.runQuery(dbname, query);
    }
}
