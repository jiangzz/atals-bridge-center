package com.jdkj.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "atlas",type ="_doc")
public abstract class ESHiveModel extends ESBaseModel implements Serializable {
    private List<String> tableRefs; //关联Hive表

    @PersistenceConstructor
    public ESHiveModel(String type, String name, String displayName, String description, String owner, String updater, Date createTime, Date lastUpdate) {
        super(type, name, displayName, description, owner, updater, createTime, lastUpdate);
    }

    public ESHiveModel() {
    }
}
