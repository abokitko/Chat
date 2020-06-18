package com.solvd.lab.automation.v2.mybatis.model;

import java.util.List;

public class Author extends AbstractEntity{
    private String name;
    private List<Item> items;

    public String getName() {
        return name;
    }

    public Author(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public Author() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
