package com.jdkj.entity;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "atlas",type ="_doc")
public abstract class ESBaseModel implements Serializable {
    @Id
    private String id;
    private String serviceType;
    private String name;
    private String qualifiedName;
    private String displayName;
    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",store = true)
    private String description;
    private String owner;
    private String updater;
    private Date createTime;
    private Date lastUpdate;
    private List<ExtProperty> extInfos;

    @PersistenceConstructor
    public ESBaseModel(String serviceType, String name, String displayName, String description, String owner, String updater, Date createTime, Date lastUpdate) {
        this.serviceType = serviceType;
        this.name = name;
        this.qualifiedName = name+"@"+ serviceType;
        this.displayName = displayName;
        this.description = description;
        this.owner = owner;
        this.updater = updater;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
    }

    public ESBaseModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.qualifiedName=name+"@"+ serviceType;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<ExtProperty> getExtInfos() {
        return extInfos;
    }

    public void setExtInfos(List<ExtProperty> extInfos) {
        this.extInfos = extInfos;
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
