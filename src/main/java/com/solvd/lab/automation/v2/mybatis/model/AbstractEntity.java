package com.solvd.lab.automation.v2.mybatis.model;

public abstract class AbstractEntity {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
