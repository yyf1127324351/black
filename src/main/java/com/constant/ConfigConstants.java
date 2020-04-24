package com.constant;

import com.utils.PropertiesLoaderUtils;

public class ConfigConstants {
    private static PropertiesLoaderUtils propertiesLoader = new PropertiesLoaderUtils("config.properties");
    public static final String LOGIN_REDIRECT_URL = propertiesLoader.getProperty("login_redirect_url");
    public static final String LOGOUT_URL = propertiesLoader.getProperty("logout_url");

    //redis配置
    public static final String REDIS_IP = propertiesLoader.getProperty("redis_ip");
    public static final String REDIS_PORT = propertiesLoader.getProperty("redis_port");
    public static final String REDIS_PWD = propertiesLoader.getProperty("redis_pwd");
}
