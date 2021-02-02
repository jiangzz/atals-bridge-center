package com.jdkj.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "atlas",type ="_doc")
public class BusinessTable extends ESBaseModel implements Serializable {
    private List<String> tasks;

    public BusinessTable() {
    }

    @PersistenceConstructor
    public BusinessTable( String name, String displayName, String description, String owner, String updater, Date createTime, Date lastUpdate) {
        super(BusinessTable.class.getSimpleName(), name, displayName, description, owner, updater, createTime, lastUpdate);
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }
}
