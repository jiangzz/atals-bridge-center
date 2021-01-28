package com.jdkj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;


@ConfigurationProperties(prefix = "atlas-bridge-center")
public class AtalsBridgeCenterConfigurations {
    //订阅的Pattern
    private Pattern subscribePattern;
    private String user;
    private String password;
    private String[] atlasServers;

    public Pattern getSubscribePattern() {
        return subscribePattern;
    }

    public void setSubscribePattern(Pattern Pattern) {
        this.subscribePattern = Pattern;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getAtlasServers() {
        return atlasServers;
    }

    public void setAtlasServers(String[] atlasServers) {
        this.atlasServers = atlasServers;
    }

}
