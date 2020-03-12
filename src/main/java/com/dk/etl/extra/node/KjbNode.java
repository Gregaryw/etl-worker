package com.dk.etl.extra.node;

import com.dk.etl.extra.convert.JobEntryConvert;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:42
 * @Version:1.0
 */

public class KjbNode {
    private String name;
    private JobEntryConvert cfg;

    public KjbNode(String name, JobEntryConvert cfg) {
        this.name = name;
        this.cfg = cfg;
        this.cfg.setName(name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobEntryConvert getCfg() {
        return this.cfg;
    }

    public void setCfg(JobEntryConvert cfg) {
        this.cfg = cfg;
    }
}
