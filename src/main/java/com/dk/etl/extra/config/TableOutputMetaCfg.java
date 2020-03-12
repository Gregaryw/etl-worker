package com.dk.etl.extra.config;

import com.dk.etl.extra.convert.TranStepMetaConvert;
import com.dk.etl.extra.exception.InitException;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:41
 * @Version:1.0
 */

public class TableOutputMetaCfg extends BaseCfg implements TranStepMetaConvert {
    private String dataSource;
    private String tableName;
    private boolean truncateTable;
    private String[] fieldDatabase;
    private String[] fieldStream;

    public TableOutputMetaCfg() {
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

    public boolean isTruncateTable() {
        return this.truncateTable;
    }

    public void setTruncateTable(boolean truncateTable) {
        this.truncateTable = truncateTable;
    }

    public String[] getFieldDatabase() {
        return this.fieldDatabase;
    }

    public void setFieldDatabase(String[] fieldDatabase) {
        this.fieldDatabase = fieldDatabase;
    }

    public String[] getFieldStream() {
        return this.fieldStream;
    }

    public void setFieldStream(String[] fieldStream) {
        this.fieldStream = fieldStream;
    }

    public void init(Map<String, Object> params) throws InitException {
    }

    @Override
    public String getConfKey() {
        return "tableoutput";
    }

    @Override
    public StepMetaInterface toStepMetaInterface(Map<String, DatabaseMeta> datasouces, StepMeta preste) {
        TableOutputMeta tableOutputMeta = new TableOutputMeta();
        tableOutputMeta.setDefault();
        tableOutputMeta.setDatabaseMeta((DatabaseMeta)datasouces.get(this.dataSource));
        tableOutputMeta.setTableName(this.getTableName());
        tableOutputMeta.setTruncateTable(this.isTruncateTable());
        tableOutputMeta.setFieldDatabase(this.getFieldDatabase());
        tableOutputMeta.setFieldStream(this.getFieldStream());
        return tableOutputMeta;
    }
}