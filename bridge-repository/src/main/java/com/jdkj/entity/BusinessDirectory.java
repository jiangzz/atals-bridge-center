package com.jdkj.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "atlas",type ="_doc")
public class BusinessDirectory extends ESHiveModel implements Serializable {
    private String parent;
    private List<String> children;

    @PersistenceConstructor
    public BusinessDirectory( String name, String displayName, String description, String owner, String updater, Date createTime, Date lastUpdate) {
        super(BusinessDirectory.class.getSimpleName(), name, displayName, description, owner, updater, createTime, lastUpdate);
    }

    public BusinessDirectory() {
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
