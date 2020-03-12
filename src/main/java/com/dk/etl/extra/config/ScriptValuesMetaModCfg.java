package com.dk.etl.extra.config;

import com.alibaba.fastjson.JSONArray;
import com.dk.etl.extra.convert.TranStepMetaConvert;
import com.dk.etl.extra.exception.InitException;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.util.StringUtil;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.scriptvalues_mod.ScriptValuesMetaMod;
import org.pentaho.di.trans.steps.scriptvalues_mod.ScriptValuesScript;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:56
 * @Version:1.0
 */

public class ScriptValuesMetaModCfg extends BaseCfg implements TranStepMetaConvert {
    private String script;
    private String[] fieldName;
    private String[] rename;
    private boolean[] replace;
    private int[] length;
    private int[] type;
    private int[] precision;

    public ScriptValuesMetaModCfg() {
    }

    public String getScript() {
        return this.script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String[] getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String[] fieldName) {
        this.fieldName = fieldName;
    }

    public String[] getRename() {
        return this.rename;
    }

    public void setRename(String[] rename) {
        this.rename = rename;
    }

    public boolean[] getReplace() {
        return this.replace;
    }

    public void setReplace(boolean[] replace) {
        this.replace = replace;
    }

    public int[] getLength() {
        return this.length;
    }

    public void setLength(int[] length) {
        this.length = length;
    }

    public int[] getType() {
        return this.type;
    }

    public void setType(int[] type) {
        this.type = type;
    }

    public int[] getPrecision() {
        return this.precision;
    }

    public void setPrecision(int[] precision) {
        this.precision = precision;
    }

    public void init(Map<String, Object> params) throws InitException {
        this.script = params.get("script").toString();
        JSONArray fieldNameArray = (JSONArray)params.get("fieldName");
        JSONArray renameArray = (JSONArray)params.get("rename");
        JSONArray typeArray = (JSONArray)params.get("type");
        JSONArray lengthArray = (JSONArray)params.get("length");
        JSONArray precisionArray = (JSONArray)params.get("precision");
        JSONArray replaceArray = (JSONArray)params.get("replace");
        this.fieldName = (String[])fieldNameArray.stream().toArray((x$0) -> {
            return new String[x$0];
        });
        this.rename = (String[])renameArray.stream().toArray((x$0) -> {
            return new String[x$0];
        });
        this.type = typeArray.stream().mapToInt((s) -> {
            boolean itype = false;
            String var2 = s.toString().toLowerCase();
            byte var3 = -1;
            switch(var2.hashCode()) {
                case -1388966911:
                    if (var2.equals("binary")) {
                        var3 = 5;
                    }
                    break;
                case -1034364087:
                    if (var2.equals("number")) {
                        var3 = 1;
                    }
                    break;
                case -891985903:
                    if (var2.equals("string")) {
                        var3 = 0;
                    }
                    break;
                case 3076014:
                    if (var2.equals("date")) {
                        var3 = 2;
                    }
                    break;
                case 55126294:
                    if (var2.equals("timestamp")) {
                        var3 = 4;
                    }
                    break;
                case 64711720:
                    if (var2.equals("boolean")) {
                        var3 = 6;
                    }
                    break;
                case 1753782345:
                    if (var2.equals("bignumber")) {
                        var3 = 7;
                    }
                    break;
                case 1958052158:
                    if (var2.equals("integer")) {
                        var3 = 3;
                    }
            }

            byte itypex;
            switch(var3) {
                case 0:
                    itypex = 2;
                    break;
                case 1:
                    itypex = 1;
                    break;
                case 2:
                    itypex = 3;
                    break;
                case 3:
                    itypex = 5;
                    break;
                case 4:
                    itypex = 9;
                    break;
                case 5:
                    itypex = 8;
                    break;
                case 6:
                    itypex = 4;
                    break;
                case 7:
                    itypex = 6;
                    break;
                default:
                    itypex = 2;
            }

            return itypex;
        }).toArray();
        this.length = lengthArray.stream().mapToInt((i) -> {
            return (Integer)i;
        }).toArray();
        this.precision = precisionArray.stream().mapToInt((i) -> {
            return (Integer)i;
        }).toArray();
        this.replace = new boolean[replaceArray.size()];
        IntStream.range(0, replaceArray.size()).forEach((i) -> {
            this.replace[i] = (Boolean)replaceArray.get(i);
        });
    }

    @Override
    public String getConfKey() {
        return "jscript";
    }

    @Override
    public StepMetaInterface toStepMetaInterface(Map<String, DatabaseMeta> datasouces, StepMeta preste) {
        String content = null;

        try {
            content = StringUtil.isEmpty(this.script) ? "//Script here" : new String(Base64.getDecoder().decode(this.script.getBytes("utf-8")));
        } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
            content = "//Script here";
        }

        ScriptValuesScript script = new ScriptValuesScript(0, "custumscript", content);
        ScriptValuesMetaMod scriptValuesMetaMod = new ScriptValuesMetaMod();
        scriptValuesMetaMod.setDefault();
        scriptValuesMetaMod.setJSScripts(new ScriptValuesScript[]{script});
        scriptValuesMetaMod.setFieldname(this.getFieldName());
        scriptValuesMetaMod.setRename(this.getRename());
        scriptValuesMetaMod.setReplace(this.getReplace());
        scriptValuesMetaMod.setLength(this.getLength());
        scriptValuesMetaMod.setType(this.getType());
        scriptValuesMetaMod.setPrecision(this.getPrecision());
        return scriptValuesMetaMod;
    }

}
