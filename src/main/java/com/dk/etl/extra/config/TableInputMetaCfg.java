package com.dk.etl.extra.config;

import com.dk.etl.extra.convert.TranStepMetaConvert;
import com.dk.etl.extra.exception.InitException;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:35
 * @Version:1.0
 */

public class TableInputMetaCfg extends BaseCfg implements TranStepMetaConvert {
    private String dataSource;
    private String SQL;

    public TableInputMetaCfg() {
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getSQL() {
        return this.SQL;
    }

    public void setSQL(String SQL) {
        this.SQL = SQL;
    }

    public void init(Map<String, Object> params) throws InitException {
        if(params != null){
            this.dataSource = params.get("dataSource").toString();
            this.SQL = params.get("SQL").toString();
        }
    }

    @Override
    public String getConfKey() {
        return "tableinput";
    }

    @Override
    public StepMetaInterface toStepMetaInterface(Map<String, DatabaseMeta> datasouces, StepMeta preste) {
        TableInputMeta tableInputMeta = new TableInputMeta();
        tableInputMeta.setDefault();
        tableInputMeta.setDatabaseMeta((DatabaseMeta)datasouces.get(this.getDataSource()));
        tableInputMeta.setSQL(this.getSQL());
        tableInputMeta.setLookupFromStep(preste);
        tableInputMeta.setVariableReplacementActive(true);
        return tableInputMeta;
    }
}

