package com.elnassera.sdk.screen.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
/**
 * <p>
 * Title: Details.java
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright(c) Bishoy George, 2022
 * </p>
 *
 *
 * @version 1.0
 * @date 24/03/2022
 */
@Configuration
public class Details {
    @Autowired
    private Environment env;
    private  String [] detalis;

    public void detailsForSN(String s) {
        detalis = env.getProperty(s).split(",");
    }
    public String getIP(){
        return detalis[0];
    }
    public String getUserName(){
        return detalis[1];
    }
    public String getPassword(){
        return detalis[2];
    }
}
