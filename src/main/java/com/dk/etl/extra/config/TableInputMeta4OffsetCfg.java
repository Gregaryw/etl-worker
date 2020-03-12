package com.dk.etl.extra.config;

import com.dk.etl.extra.convert.TranStepMetaConvert;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:34
 * @Version:1.0
 */

public class TableInputMeta4OffsetCfg extends TableInputMetaCfg implements TranStepMetaConvert {
    private int mode;

    public TableInputMeta4OffsetCfg() {
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public String getConfKey() {
        return "tableinput4offset";
    }

    @Override
    public StepMetaInterface toStepMetaInterface(Map<String, DatabaseMeta> datasouces, StepMeta preste) {
        TableInputMeta tableInputMeta = new TableInputMeta();
        tableInputMeta.setDefault();
        tableInputMeta.setDatabaseMeta((DatabaseMeta)datasouces.get(this.getDataSource()));
        tableInputMeta.setSQL(this.getSQL());
        return tableInputMeta;
    }
}
