package com.jdkj.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "atlas",type ="_doc")
public class BusinessSector extends ESBaseModel implements Serializable {
    private String businessSystem;
    private List<String> tableRefs; //关联Hive表
    @PersistenceConstructor
    public BusinessSector( String name, String qualifiedName, String displayName, String description, String owner, String updater, Date createTime, Date lastUpdate) {
        super(BusinessSector.class.getSimpleName(), name, displayName, description, owner, updater, createTime, lastUpdate);
    }

    public BusinessSector() {
    }

    public String getBusinessSystem() {
        return businessSystem;
    }

    public void setBusinessSystem(String businessSystem) {
        this.businessSystem = businessSystem;
    }
}
