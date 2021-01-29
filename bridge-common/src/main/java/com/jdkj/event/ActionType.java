package com.jdkj.event;

public enum ActionType {
    INSERT("insert"),UPDATE("update"),DELETE("delete"),UPDATE_RELATIONS("update_relations");
    private String name;
    ActionType(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
