package com.solvd.lab.automation.v2.mybatis.config;


import com.solvd.lab.automation.v2.mybatis.constant.IOConstant;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.*;


public class SessionFactory {

    public static final Logger LOGGER = Logger.getLogger(SessionFactory.class);

    private static SqlSessionFactory sqlSessionFactory = null;

    static {
        try {
            String resource = "mybatisconfig.xml";
            InputStream is  = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public static SqlSession getSession(){
        return sqlSessionFactory.openSession();
    }
}
