package com.solvd.lab.automation.v2.mybatis.model;

public class Item extends AbstractEntity{
    private String name;

    public String getName() {
        return name;
    }

    public Item() {

    }

    public Item(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
