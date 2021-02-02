package com.jdkj.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "atlas",type ="_doc")
public class Taskspace extends ESBaseModel implements Serializable {
    private String project;
    private List<String> tasks;

    @PersistenceConstructor
    public Taskspace(String name, String displayName, String description, String owner, String updater, Date createTime, Date lastUpdate) {
        super(Taskspace.class.getSimpleName(), name, displayName, description, owner, updater, createTime, lastUpdate);
    }

    public Taskspace() {
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }
}
