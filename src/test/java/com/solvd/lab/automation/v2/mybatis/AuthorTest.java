package com.solvd.lab.automation.v2.mybatis;

import com.solvd.lab.automation.v2.mybatis.model.Author;
import com.solvd.lab.automation.v2.mybatis.model.Category;
import com.solvd.lab.automation.v2.mybatis.service.AuthorService;
import com.solvd.lab.automation.v2.mybatis.service.CategoryService;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class AuthorTest {
    private static final Logger LOGGER = Logger.getLogger(AuthorTest.class);

    @Test
    public void testAuthorGetById() {
        Author a = new AuthorService().getAuthorById(1);
        LOGGER.info(a.toString());
        Assert.assertEquals(a.getName(), "Lem");
    }

    @Test
    public void testAuthorGetAll() {
        List<Author> as = new AuthorService().getAllAuthors();
        as.forEach(e -> LOGGER.info(e.toString()));
        Assert.assertTrue(as.size()!=0);
    }

    @Test()
    public void testAuthorDeleteById() {
        new AuthorService().deleteAuthorById(1);
    }

    @Test
    public void testAuthorCreate() {
        Author a = new Author("Hawking", null);
        new AuthorService().createAuthor(a);
    }

    @Test
    public void testCategoryUpdate() {
        Author a = new AuthorService().getAuthorById(1);
        a.setName("SLem");
        new AuthorService().updateAuthor(a);
    }
}
