package com.jdkj.event;

/**
 * 服务类型
 */
public enum ServiceType {
    JOB("JOB_TASK"),THEME("THEME"),TAG("TAGS");
    private String name;

    ServiceType(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
