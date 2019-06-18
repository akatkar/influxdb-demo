package com.akatkar.samples.influxdb.collector.service;

import java.net.URI;

public interface DiscoveryService {
    URI getUri(String serviceId) ;
}
