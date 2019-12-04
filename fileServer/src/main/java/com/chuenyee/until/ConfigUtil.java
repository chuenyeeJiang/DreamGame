package com.chuenyee.until;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
    private static Properties config = null;
    private static String pathResource  = "fileService.properties";



    public static String getProperty(String key){
        if(config == null){
            synchronized (ConfigUtil.class){
                if(null == config){
                    try {
                        Resource resource = new ClassPathResource(pathResource);
                        config = PropertiesLoaderUtils.loadProperties(resource);
                    } catch (IOException e) {
                       logger.error(e.getMessage(),e);
                    }

                }
            }
        }
        return config.getProperty(key);
    }
}
