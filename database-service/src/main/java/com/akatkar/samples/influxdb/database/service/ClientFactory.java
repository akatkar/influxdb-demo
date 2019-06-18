//package com.akatkar.samples.influxdb.database.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Component
//class ClientFactory {
//
//    private static final List<String> CLIENT_NAMES =
//            Arrays.asList("jenkinsDbClient",
//                    "jiveDbClient",
//                    "jervisDbClient",
//                    "teamcityDbClient");
//
//    @Autowired
//    private InfluxDbClientFactory factory;
//
//    InfluxDbClient getClient(String clientName){
//        return factory.getClient(clientName + "DbClient");
//    }
//
//    List<InfluxDbClient> getAllClients(){
//        return CLIENT_NAMES.stream()
//                .map(factory::getClient)
//                .collect(Collectors.toList());
//    }
//
//    Optional<InfluxDbClient> getClientByDbName(String dbName){
//        return CLIENT_NAMES.stream()
//                .map(factory::getClient)
//                .filter(c->c.getDbname().equalsIgnoreCase(dbName))
//                .findFirst();
//    }
//}
