package com.solvd.lab.automation.v2.mybatis.dao;

import com.solvd.lab.automation.v2.mybatis.model.Item;

import java.util.List;

public interface ItemDAO {
    void create(Item author);
    Item getById(long id);
    List<Item> get();
    void update(Item author);
    void delete(long id);
}
