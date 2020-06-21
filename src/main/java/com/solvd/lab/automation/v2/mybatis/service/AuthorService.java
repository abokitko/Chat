package com.solvd.lab.automation.v2.mybatis.service;

import com.solvd.lab.automation.v2.mybatis.dao.AuthorDAO;
import com.solvd.lab.automation.v2.mybatis.dao.impl.AuthorDAOImpl;
import com.solvd.lab.automation.v2.mybatis.model.Author;

import java.util.List;

public class AuthorService {
    AuthorDAO authorDAO = new AuthorDAOImpl();

    public Author getAuthorById(long id) {
        return authorDAO.getById(id);
    }

    public List<Author> getAllAuthors() {
        return authorDAO.get();
    }

    public void createAuthor(Author author) {
        authorDAO.create(author);
    }

    public void deleteAuthorById(long id) {
        authorDAO.delete(id);
    }

    public void updateAuthor(Author author) {
        authorDAO.update(author);
    }

}
