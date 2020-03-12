package com.dk.etl.extra.config;

import com.dk.etl.extra.exception.InitException;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:27
 * @Version:1.0
 */

public abstract class BaseCfg {
    private String name;

    public BaseCfg() {
    }

    public BaseCfg(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void init(Map<String, Object> var1) throws InitException;

    public abstract String getConfKey();
}
