package com.solvd.lab.automation.v2.mybatis.dao;

import com.solvd.lab.automation.v2.mybatis.model.Author;

import java.util.List;

public interface AuthorDAO {
    void create(Author author);
    Author getById(long id);
    List<Author> get();
    void update(Author author);
    void delete(long id);
}
