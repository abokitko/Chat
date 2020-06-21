package com.solvd.lab.automation.v2.mybatis.model;

import java.util.List;

public class Category extends AbstractEntity{
    private String name;

    private List<Item> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category() {

    }

    public Category(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    @Override
    public String toString() {
        return super.getId() + " " + name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
