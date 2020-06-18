package com.solvd.lab.automation.v2.mybatis.dao.impl;

import com.solvd.lab.automation.v2.mybatis.config.SessionFactory;
import com.solvd.lab.automation.v2.mybatis.dao.AuthorDAO;
import com.solvd.lab.automation.v2.mybatis.model.Author;
import com.solvd.lab.automation.v2.mybatis.model.Category;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {

    private final static String namespace = "author_mapper";

    @Override
    public void create(Author author) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.insert(namespace + ".create", author);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Author getById(long id) {
        SqlSession sqlSession = SessionFactory.getSession();
        Author a = sqlSession.selectOne(namespace + ".getById", id);
        sqlSession.close();
        return a;
    }

    @Override
    public List<Author> get() {
        SqlSession sqlSession = SessionFactory.getSession();
        List<Author> as = sqlSession.selectList(namespace + ".get");
        sqlSession.close();
        return as;
    }

    @Override
    public void update(Author author) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.update(namespace + ".update", author);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(long id) {
        SqlSession sqlSession = SessionFactory.getSession();
        sqlSession.delete(namespace + ".deleteById", id);
        sqlSession.commit();
        sqlSession.close();
    }
}
