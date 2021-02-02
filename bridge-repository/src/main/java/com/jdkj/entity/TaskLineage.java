package com.jdkj.entity;

import java.io.Serializable;

public class TaskLineage implements Serializable {
    private String task;
    private String direction;
    private String description;

    public TaskLineage(String task, String direction, String description) {
        this.task = task;
        this.direction = direction;
        this.description = description;
    }

    public TaskLineage() {
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
