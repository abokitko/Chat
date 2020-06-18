package com.solvd.lab.automation.v2.mybatis.dao.impl;

import com.solvd.lab.automation.v2.mybatis.config.SessionFactory;
import com.solvd.lab.automation.v2.mybatis.dao.CategoryDAO;
import com.solvd.lab.automation.v2.mybatis.model.Category;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private final static String namespace = "category_mapper";

    public void create(Category category) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.insert(namespace + ".create", category);
        sqlSession.commit();
        sqlSession.close();
    }

    public Category getById(long id) {
        SqlSession sqlSession = SessionFactory.getSession();
        Category cat = sqlSession.selectOne(namespace + ".getById", id);
        sqlSession.close();
        return cat;
    }

    public List<Category> get() {
        SqlSession sqlSession = SessionFactory.getSession();
        List<Category> cats = sqlSession.selectList(namespace + ".get");
        sqlSession.close();
        return cats;
    }

    public void update(Category category) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.update(namespace + ".update", category);
        sqlSession.commit();
        sqlSession.close();
    }

    public void delete(long id) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.delete(namespace + ".deleteById", id);
        sqlSession.commit();
        sqlSession.close();
    }
}
