package com.akatkar.samples.influxdb.collector.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;

@Service
class DiscoveryServiceImpl implements DiscoveryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DiscoveryClient discoveryClient;

    private HashMap<String, ServiceInstance> cacheServiceInstance ;

    public DiscoveryServiceImpl() {
        cacheServiceInstance = new HashMap<>() ;
    }

    private ServiceInstance getServiceInstance(String serviceId) {

        if(cacheServiceInstance.containsKey(serviceId)){
            logger.info("Get {} from pool", serviceId);
            return cacheServiceInstance.get(serviceId) ;
        } else {
            logger.info("Checking if {} is available.", serviceId);
            if(!discoveryClient.getInstances(serviceId).isEmpty()){
                logger.info("Add {} to pool.", serviceId);
                ServiceInstance serviceInstance = discoveryClient.getInstances(serviceId).get(0) ;
                cacheServiceInstance.put(serviceId,serviceInstance) ;
                return serviceInstance;
            } else {
                logger.info("the {} is not available.", serviceId);
            }
        }
        return null;
    }

    @Override
    public URI getUri(String serviceId){
//        List<String> services = discoveryClient.getServices();
//        String description = discoveryClient.description();
        ServiceInstance instance = getServiceInstance(serviceId) ;

        if(instance!=null){
            return instance.getUri() ;
        } else {
            return null ;
        }
    }

    @Scheduled(cron = "5 */5 * * * *")
    private void refreshConnectionWithDiscoveryServer(){
        logger.info("Destroy pool of service instance. Refresh connection with discovery server.");
        this.cacheServiceInstance = new HashMap<>() ;
    }
}
