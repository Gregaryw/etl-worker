package com.dk.etl.extra.config;

import com.dk.etl.extra.convert.TranStepMetaConvert;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.rowgenerator.RowGeneratorMeta;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:45
 * @Version:1.0
 */

public class RowGeneratorMetaCfg extends BaseCfg implements TranStepMetaConvert {
    private String rowLimit;

    public RowGeneratorMetaCfg() {
    }

    public String getRowLimit() {
        return this.rowLimit;
    }

    public void setRowLimit(String rowLimit) {
        this.rowLimit = rowLimit;
    }

    public void init(Map<String, Object> params) {
    }

    @Override
    public String getConfKey() {
        return "row";
    }

    @Override
    public StepMetaInterface toStepMetaInterface(Map<String, DatabaseMeta> datasouces, StepMeta preste) {
        RowGeneratorMeta rowGeneratorMeta = new RowGeneratorMeta();
        rowGeneratorMeta.setDefault();
        rowGeneratorMeta.setRowLimit(this.getRowLimit());
        return rowGeneratorMeta;
    }
}

