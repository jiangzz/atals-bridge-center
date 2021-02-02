package com.jdkj.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "atlas",type ="_doc")
public class Task extends ESHiveModel implements Serializable {
    private String taskType;
    private String taskspace;
    private List<TaskLineage> taskLineages;
    private List<String> businessTables;//关联业务表
    private List<String> tags;//关联标签

    @PersistenceConstructor
    public Task(String name, String displayName, String description, String owner, String updater, Date createTime, Date lastUpdate) {
        super(Task.class.getSimpleName(), name, displayName, description, owner, updater, createTime, lastUpdate);
    }

    public Task() {
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public List<String> getBusinessTables() {
        return businessTables;
    }

    public void setBusinessTables(List<String> businessTables) {
        this.businessTables = businessTables;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTaskspace() {
        return taskspace;
    }

    public void setTaskspace(String taskspace) {
        this.taskspace = taskspace;
    }

    public List<TaskLineage> getTaskLineages() {
        return taskLineages;
    }

    public void setTaskLineages(List<TaskLineage> taskLineages) {
        this.taskLineages = taskLineages;
    }
}
