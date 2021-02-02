package com.jdkj.entity;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

public class ExtProperty implements Serializable {
    private String key;
    private String value;

    public ExtProperty(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public ExtProperty() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if(this!=null){
            try {
                return new ObjectMapper().writeValueAsString(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "{}";
    }
}
