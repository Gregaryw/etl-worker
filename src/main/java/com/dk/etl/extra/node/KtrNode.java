package com.dk.etl.extra.node;

import com.dk.etl.extra.convert.TranStepMetaConvert;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:24
 * @Version:1.0
 */

public class KtrNode {
    private String name;
    private TranStepMetaConvert cfg;

    public KtrNode(String name, TranStepMetaConvert cfg) {
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

    public TranStepMetaConvert getCfg() {
        return this.cfg;
    }

    public void setCfg(TranStepMetaConvert cfg) {
        this.cfg = cfg;
    }
}
