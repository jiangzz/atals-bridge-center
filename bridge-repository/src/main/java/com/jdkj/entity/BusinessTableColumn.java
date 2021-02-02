package com.jdkj.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "atlas",type ="_doc")
public class BusinessTableColumn extends ESBaseModel implements Serializable {
    private String businessTable;
    @PersistenceConstructor
    public BusinessTableColumn( String name, String displayName, String description, String owner, String updater, Date createTime, Date lastUpdate) {
        super(BusinessTableColumn.class.getSimpleName(), name, displayName, description, owner, updater, createTime, lastUpdate);
    }

    public BusinessTableColumn() {
    }

    public String getBusinessTable() {
        return businessTable;
    }

    public void setBusinessTable(String businessTable) {
        this.businessTable = businessTable;
    }
}
