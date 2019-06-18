package com.akatkar.samples.influxdb.collector.service;

import com.akatkar.samples.influxdb.collector.config.Applications;
import com.akatkar.samples.influxdb.collector.config.MicroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataCollectorService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${measurement.metrics}")
    private String[] metricsToMonitor ;

    @Autowired
    private InfluxDbService influxDbService ;

    @Autowired
    private DiscoveryService discoveryService ;

    @Autowired
    private RestTemplate restTemplate;

    private Applications applications;

    @Autowired
    public void setApp(Applications applications) {
        this.applications = applications;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void run(){
        if(applications.getServices() != null){
            for (MicroService service: applications.getServices()){
                URI uri = discoveryService.getUri(service.getServiceId()) ;
                if(uri != null){
                    HashMap<String,String> tags = new HashMap<>() ;
                    tags.put("serviceId",service.getServiceId()) ;
                    tags.put("serviceName",service.getName()) ;
                    tags.put("serviceDescription",service.getDescription()) ;

                    try {
                        Map<String, Long> metrics = restTemplate.getForObject(uri + "/metrics",HashMap.class) ;
                        Map<String, Object> fields = metrics.entrySet().stream()
                                .filter(entry->Arrays.asList(metricsToMonitor).contains(entry.getKey()))
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                        influxDbService.writeMetrics(tags, fields);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }
        }
    }

}
