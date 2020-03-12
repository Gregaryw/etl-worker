package com.dk.etl.extra.builder;

import com.dk.etl.extra.config.*;
import com.google.common.collect.Lists;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.fileinput.text.TextFileInputMeta;
import org.pentaho.di.trans.steps.getfilenames.GetFileNamesMeta;
import org.pentaho.di.trans.steps.httppost.HTTPPOSTMeta;
import org.pentaho.di.trans.steps.insertupdate.InsertUpdateMeta;
import org.pentaho.di.trans.steps.rowgenerator.RowGeneratorMeta;
import org.pentaho.di.trans.steps.scriptvalues_mod.ScriptValuesMetaMod;
import org.pentaho.di.trans.steps.scriptvalues_mod.ScriptValuesScript;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassDef;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassMeta;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:44
 * @Version:1.0
 */

public class TransCompMetaBuilder {
    public TransCompMetaBuilder() {
    }

    public static RowGeneratorMeta buildRowGenerator(RowGeneratorMetaCfg cfg) {
        RowGeneratorMeta rowGeneratorMeta = new RowGeneratorMeta();
        rowGeneratorMeta.setDefault();
        rowGeneratorMeta.setRowLimit(cfg.getRowLimit());
        return rowGeneratorMeta;
    }

    public static DatabaseMeta buildDatabaseMeta(DatabaseMetaCfg cfg) {
        Properties pi = new Properties();
        pi.setProperty("CUSTOM_DRIVER_CLASS", cfg.getDriverClass());
        pi.setProperty("CUSTOM_URL", cfg.getHostname());
        DatabaseMeta databaseMeta = new DatabaseMeta();
        databaseMeta.setDefault();
        databaseMeta.setDatabaseType("GENERIC");
        databaseMeta.setName(cfg.getName());
        databaseMeta.setUsername(cfg.getUsername());
        databaseMeta.setPassword(cfg.getPassword());
        databaseMeta.setAttributes(pi);
        return databaseMeta;
    }

    public static TableInputMeta buildTableInputMeta(DatabaseMeta databaseMeta, StepMeta lookupFromStep, TableInputMetaCfg tableInputMetaCfg) {
        TableInputMeta tableInputMeta = new TableInputMeta();
        tableInputMeta.setDefault();
        tableInputMeta.setDatabaseMeta(databaseMeta);
        tableInputMeta.setSQL(tableInputMetaCfg.getSQL());
        tableInputMeta.setLookupFromStep(lookupFromStep);
        tableInputMeta.setVariableReplacementActive(true);
        return tableInputMeta;
    }

    public static GetFileNamesMeta buildGetFileNamesMeta(GetFileNamesMetaCfg cfg) {
        GetFileNamesMeta getFileNamesMeta = new GetFileNamesMeta();
        getFileNamesMeta.setDefault();
        getFileNamesMeta.setFileName(new String[]{cfg.getFileName()});
        getFileNamesMeta.setFileMask(new String[]{cfg.getFileMask()});
        return getFileNamesMeta;
    }

    public static TextFileInputMeta buildTextFileInputMeta(TextFileInputMetaCfg cfg, StepMeta lookupFromStep) {
        TextFileInputMeta textFileInputMeta = new TextFileInputMeta();
        textFileInputMeta.setDefault();
        textFileInputMeta.setAcceptingStep(lookupFromStep);
        return textFileInputMeta;
    }

    public static HTTPPOSTMeta buildHTTPPOSTMeta(HttpPostMetaCfg cfg) {
        HTTPPOSTMeta httppostMeta = new HTTPPOSTMeta();
        httppostMeta.setDefault();
        httppostMeta.setUrl(cfg.getUrl());
        httppostMeta.setEncoding(cfg.getEncoding());
        httppostMeta.setArgumentField(cfg.getArgumentField());
        httppostMeta.setArgumentParameter(cfg.getArgumentParameter());
        httppostMeta.setArgumentHeader(cfg.getArgumentHeader());
        return httppostMeta;
    }

    public static TableOutputMeta buildTableOutputMeta(DatabaseMeta databaseMeta, TableOutputMetaCfg cfg) {
        TableOutputMeta tableOutputMeta = new TableOutputMeta();
        tableOutputMeta.setDefault();
        tableOutputMeta.setDatabaseMeta(databaseMeta);
        tableOutputMeta.setTableName(cfg.getTableName());
        tableOutputMeta.setTruncateTable(cfg.isTruncateTable());
        tableOutputMeta.setFieldDatabase(cfg.getFieldDatabase());
        tableOutputMeta.setFieldStream(cfg.getFieldStream());
        return tableOutputMeta;
    }

    public static InsertUpdateMeta buildInsertUpdateMeta(DatabaseMeta databaseMeta, InsertUpdateMetaCfg cfg) {
        InsertUpdateMeta insertUpdateMeta = new InsertUpdateMeta();
        insertUpdateMeta.setDefault();
        insertUpdateMeta.setDatabaseMeta(databaseMeta);
        insertUpdateMeta.setTableName(cfg.getTableName());
        insertUpdateMeta.setKeyLookup(cfg.getKeyLookup());
        insertUpdateMeta.setKeyCondition(cfg.getKeyCondition());
        insertUpdateMeta.setKeyStream(cfg.getKeyStream());
        insertUpdateMeta.setKeyStream2(cfg.getKeyStream2());
        insertUpdateMeta.setUpdateLookup(cfg.getUpdateLookup());
        insertUpdateMeta.setUpdateStream(cfg.getUpdateStream());
        insertUpdateMeta.setUpdate(cfg.getUpdate());
        return insertUpdateMeta;
    }

    public static UserDefinedJavaClassMeta buildUserDefinedJavaClassMeta(UserDefinedJavaClassMetaCfg cfg) {
        UserDefinedJavaClassMeta userDefinedJavaClassMeta = new UserDefinedJavaClassMeta();
        userDefinedJavaClassMeta.setDefault();
        if (cfg.getFields() != null) {
            userDefinedJavaClassMeta.replaceFields((List)cfg.getFields().stream().map((f) -> {
//                return new FieldInfo(f.getName(), f.getType(), f.getLength(), f.getPrecision());
                return null;
            }).collect(Collectors.toList()));
        }

        userDefinedJavaClassMeta.replaceDefinitions(Lists.newArrayList(new UserDefinedJavaClassDef[]{new UserDefinedJavaClassDef(UserDefinedJavaClassDef.ClassType.TRANSFORM_CLASS, "Processor", cfg.getJavaSource())}));
        return userDefinedJavaClassMeta;
    }

    public static ScriptValuesMetaMod buildScriptValuesMetaMod(ScriptValuesMetaModCfg cfg) {
        ScriptValuesScript script = new ScriptValuesScript(0, "custumscript", "");
        ScriptValuesMetaMod scriptValuesMetaMod = new ScriptValuesMetaMod();
        scriptValuesMetaMod.setDefault();
        scriptValuesMetaMod.setJSScripts(new ScriptValuesScript[]{script});
        scriptValuesMetaMod.setFieldname(cfg.getFieldName());
        scriptValuesMetaMod.setRename(cfg.getRename());
        scriptValuesMetaMod.setReplace(cfg.getReplace());
        scriptValuesMetaMod.setLength(cfg.getLength());
        scriptValuesMetaMod.setType(cfg.getType());
        scriptValuesMetaMod.setPrecision(cfg.getPrecision());
        return scriptValuesMetaMod;
    }
}
