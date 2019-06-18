package com.akatkar.samples.influxdb.collector.config;

public class MicroService {
    String name;
    String description;
    String serviceId;

    public MicroService(String name, String description, String serviceId) {
        this.name = name;
        this.description = description;
        this.serviceId = serviceId;
    }

    public MicroService() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
