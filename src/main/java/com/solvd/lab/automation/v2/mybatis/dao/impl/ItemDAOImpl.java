package com.solvd.lab.automation.v2.mybatis.dao.impl;

import com.solvd.lab.automation.v2.mybatis.dao.ItemDAO;
import com.solvd.lab.automation.v2.mybatis.model.Item;

import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    private final static String namespace = "item_mapper";

    @Override
    public void create(Item author) {

    }

    @Override
    public Item getById(long id) {
        return null;
    }

    @Override
    public List<Item> get() {
        return null;
    }

    @Override
    public void update(Item author) {

    }

    @Override
    public void delete(long id) {

    }
}
