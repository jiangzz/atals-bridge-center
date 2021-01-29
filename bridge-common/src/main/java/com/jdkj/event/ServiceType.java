package com.jdkj.event;

public enum ServiceType {
    BusinessSystem("BusinessSystem"),//业务体系
    BusinessSector("BusinessSector"),//业务板块
    BusinessDirectory("BusinessDirectory"),//业务目录
    SubjectDomain("SubjectDomain"),//主题区域
    Task("Task"),//作业
    TaskSpace("TaskSpace"),//作业空间
    Market("Market"),//集市
    Tag("Tag"),//标签
    BusinessTable("BusinessTable");//业务表

    private String name;
    ServiceType(String name){
        this.name=name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
