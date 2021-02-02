package com.jdkj.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "atlas",type ="_doc")
public class BusinessSystem extends ESBaseModel implements Serializable {
    private List<String> businessSectors;

    @PersistenceConstructor
    public BusinessSystem(String name, String displayName, String description, String owner, String updater, Date createTime, Date lastUpdate) {
        super(BusinessSystem.class.getSimpleName(), name, displayName, description, owner, updater, createTime, lastUpdate);
    }

    public BusinessSystem() {
    }

    public List<String> getBusinessSectors() {
        return businessSectors;
    }
    public void setBusinessSectors(List<String> businessSectors) {
        this.businessSectors = businessSectors;
    }

}
