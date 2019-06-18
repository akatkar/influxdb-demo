package com.akatkar.samples.influxdb.database.dto;

import org.influxdb.dto.QueryResult;

import java.util.List;
import java.util.stream.Collectors;

public class MeasurementDTO2 {

    private final String name;
    private final List<MeasurementTag> tags;
    private final List<MeasurementField> fields;

    public MeasurementDTO2(String name, QueryResult tags, QueryResult fields) {

        this.name = name;
        
        this.tags = tags.getResults().stream()
                .filter(r -> !r.hasError())
                .flatMap(r -> r.getSeries().stream())
                .flatMap(s -> s.getValues().stream())
                .flatMap(List::stream)
                .map(Object::toString)
                .map(MeasurementTag::new)
                .collect(Collectors.toList());

        this.fields = fields.getResults().stream()
                .filter(r -> !r.hasError())
                .flatMap(r -> r.getSeries().stream())
                .flatMap(s -> s.getValues().stream())
                .map(MeasurementField::new)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public List<MeasurementTag> getTags() {
        return tags;
    }

    public List<MeasurementField> getFields() {
        return fields;
    }
}
