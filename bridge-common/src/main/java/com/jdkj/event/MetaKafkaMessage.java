package com.jdkj.event;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
public class MetaKafkaMessage implements Serializable {
    private ActionType actionType;
    private ServiceIdentify serviceIdentify;
    private String dispalyName;
    private String description;
    private String owner;
    private String updater;
    private Date createTime;



    private Date lastUpdate;
    private TableIdentify[] tableRefs;
    private ServiceIdentify[] relationsRef;
    private Map<String,Object> extInfo;

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public ServiceIdentify getServiceIdentify() {
        return serviceIdentify;
    }

    public void setServiceIdentify(ServiceIdentify serviceIdentify) {
        this.serviceIdentify = serviceIdentify;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public TableIdentify[] getTableRefs() {
        return tableRefs;
    }

    public void setTableRefs(TableIdentify[] tableRefs) {
        this.tableRefs = tableRefs;
    }

    public ServiceIdentify[] getRelationsRef() {
        return relationsRef;
    }

    public void setRelationsRef(ServiceIdentify[] relationsRef) {
        this.relationsRef = relationsRef;
    }

    public Map<String, Object> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }

    public String getDispalyName() {
        return dispalyName;
    }

    public void setDispalyName(String dispalyName) {
        this.dispalyName = dispalyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}
