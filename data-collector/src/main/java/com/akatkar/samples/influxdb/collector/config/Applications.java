package com.akatkar.samples.influxdb.collector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "application")
public class Applications {
    List<MicroService> services ;

    public List<MicroService> getServices() {
        return services;
    }

    public void setServices(List<MicroService> services) {
        this.services = services;
    }
}
