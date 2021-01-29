package com.jdkj.event;

public class TableIdentify {
    private String cluster;
    private String database;
    private String table;

    public TableIdentify(String cluster, String database, String table) {
        this.cluster = cluster;
        this.database = database;
        this.table = table;
    }

    public TableIdentify() {
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
