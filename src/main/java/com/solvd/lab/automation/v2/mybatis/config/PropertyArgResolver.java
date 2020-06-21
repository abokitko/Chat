package com.solvd.lab.automation.v2.mybatis.config;

import com.solvd.lab.automation.v2.mybatis.constant.IOConstant;
import com.solvd.lab.automation.v2.mybatis.exception.BadExtensionException;
import com.solvd.lab.automation.v2.mybatis.exception.UnableToReadException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyArgResolver {
    private Properties props;

    private static PropertyArgResolver instance;

    public static PropertyArgResolver getInstance() throws UnableToReadException {
        if (instance == null) {
            synchronized (PropertyArgResolver.class) {
                if (instance == null) {
                    instance = new PropertyArgResolver();
                }
            }
        }
        return instance;
    }

    private PropertyArgResolver() throws UnableToReadException{
        this.props = new Properties();
        try {
            if (!IOConstant.CONFIG_PATH.endsWith(".properties")) {
                throw new BadExtensionException("Provide the config file in proper format");
            }
            InputStream inputStream = new FileInputStream(new File(IOConstant.CONFIG_PATH).getAbsolutePath());
            this.props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnableToReadException("");
        }
    }

    public String get(String key) {
        return this.props.getProperty(key);
    }
}
