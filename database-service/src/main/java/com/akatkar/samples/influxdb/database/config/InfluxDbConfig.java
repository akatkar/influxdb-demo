package com.akatkar.samples.influxdb.database.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "influxdb.server")
public class InfluxDbConfig {
    
    private static final String HTTP = "http://";
    
    private String host ;
    private String port ;
    private String dbname ;
    private String username ;
    private String password ;
    private String retentionPolicy ;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetentionPolicy() {
        return retentionPolicy;
    }

    public void setRetentionPolicy(String retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
    }

    public String getUrl(){
        if("localhost".equals(host)){
            return HTTP + host + ":" + port;
        }
        return HTTP + host;
    }

    @Override
    public String toString() {
        return getUrl() + "?dbname=" + dbname;
    }
}
