package com.udara.appconfig.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.details")
public class AppDetailsConfig {
    private String name;
    private String version;
    private List<String> servers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getServers() {
        return servers;
    }

    @Override
    public String toString() {
        return "AppDetailsConfig{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", servers=" + servers +
                '}';
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }
}
