package com.dk.etl.extra.config;

import com.alibaba.fastjson.JSONArray;
import com.dk.etl.extra.convert.TranStepMetaConvert;
import com.dk.etl.extra.exception.InitException;
import com.dk.etl.util.StringUtils;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.insertupdate.InsertUpdateMeta;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:33
 * @Version:1.0
 */

public class InsertUpdateMetaCfg extends BaseCfg implements TranStepMetaConvert {
    private String dataSource;
    private String tableName;
    private String[] keyLookup;
    private String[] keyStream;
    private String[] keyStream2;
    private String[] keyCondition;
    private String[] updateLookup;
    private String[] updateStream;
    private Boolean[] update;

    public InsertUpdateMetaCfg() {
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String[] getKeyLookup() {
        return this.keyLookup;
    }

    public void setKeyLookup(String[] keyLookup) {
        this.keyLookup = keyLookup;
    }

    public String[] getKeyStream() {
        return this.keyStream;
    }

    public void setKeyStream(String[] keyStream) {
        this.keyStream = keyStream;
    }

    public String[] getKeyStream2() {
        return this.keyStream2;
    }

    public void setKeyStream2(String[] keyStream2) {
        this.keyStream2 = keyStream2;
    }

    public String[] getKeyCondition() {
        return this.keyCondition;
    }

    public void setKeyCondition(String[] keyCondition) {
        this.keyCondition = keyCondition;
    }

    public String[] getUpdateLookup() {
        return this.updateLookup;
    }

    public void setUpdateLookup(String[] updateLookup) {
        this.updateLookup = updateLookup;
    }

    public String[] getUpdateStream() {
        return this.updateStream;
    }

    public void setUpdateStream(String[] updateStream) {
        this.updateStream = updateStream;
    }

    public Boolean[] getUpdate() {
        return this.update;
    }

    public void setUpdate(Boolean[] update) {
        this.update = update;
    }

    @Override
    public void init(Map<String, Object> params) throws InitException {
        this.dataSource = params.get("dataSource").toString();
        this.tableName = params.get("tableName").toString();
        JSONArray keyLookupArray = (JSONArray)params.get("keyLookup");
        JSONArray keyStreamArray = (JSONArray)params.get("keyStream");
        JSONArray keyConditionArray = (JSONArray)params.get("keyCondition");
        JSONArray updateLookupArray = (JSONArray)params.get("updateLookup");
        JSONArray updateStreamArray = (JSONArray)params.get("updateStream");
        JSONArray updateArray = (JSONArray)params.get("update");
        this.keyLookup = new String[keyLookupArray.size()];
        this.keyStream = new String[keyStreamArray.size()];
        this.keyStream2 = new String[keyStreamArray.size()];
        this.keyCondition = new String[keyConditionArray.size()];
        this.updateLookup = new String[updateLookupArray.size()];
        this.updateStream = new String[updateStreamArray.size()];
        this.update = new Boolean[updateArray.size()];
        keyLookupArray.toArray(this.keyLookup);
        keyStreamArray.toArray(this.keyStream);
        keyConditionArray.toArray(this.keyCondition);
        updateLookupArray.toArray(this.updateLookup);
        updateStreamArray.toArray(this.updateStream);
        updateArray.toArray(this.update);
        if (StringUtils.isEmpty(this.tableName) || StringUtils.isEmpty(this.dataSource) || this.keyLookup == null || this.updateLookup == null) {
            throw new InitException("插入更新表参数缺失");
        }
    }

    @Override
    public String getConfKey() {
        return "insertupdate";
    }

    @Override
    public StepMetaInterface toStepMetaInterface(Map<String, DatabaseMeta> datasouces, StepMeta preste) {
        InsertUpdateMeta insertUpdateMeta = new InsertUpdateMeta();
        insertUpdateMeta.setDefault();
        insertUpdateMeta.setDatabaseMeta((DatabaseMeta)datasouces.get(this.getDataSource()));
        insertUpdateMeta.setTableName(this.getTableName());
        insertUpdateMeta.setKeyLookup(this.getKeyLookup());
        insertUpdateMeta.setKeyCondition(this.getKeyCondition());
        insertUpdateMeta.setKeyStream(this.getKeyStream());
        insertUpdateMeta.setKeyStream2(this.getKeyStream2());
        insertUpdateMeta.setUpdateLookup(this.getUpdateLookup());
        insertUpdateMeta.setUpdateStream(this.getUpdateStream());
        insertUpdateMeta.setUpdate(this.getUpdate());
        return insertUpdateMeta;
    }
}

