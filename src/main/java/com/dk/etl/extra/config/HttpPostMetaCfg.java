package com.dk.etl.extra.config;

import com.dk.etl.extra.convert.TranStepMetaConvert;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.httppost.HTTPPOSTMeta;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:32
 * @Version:1.0
 */

public class HttpPostMetaCfg extends BaseCfg implements TranStepMetaConvert {
    private String url;
    private String encoding;
    private String[] argumentField;
    private String[] argumentParameter;
    private boolean[] argumentHeader;

    public HttpPostMetaCfg() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String[] getArgumentField() {
        return this.argumentField;
    }

    public void setArgumentField(String[] argumentField) {
        this.argumentField = argumentField;
    }

    public String[] getArgumentParameter() {
        return this.argumentParameter;
    }

    public void setArgumentParameter(String[] argumentParameter) {
        this.argumentParameter = argumentParameter;
    }

    public boolean[] getArgumentHeader() {
        if (this.argumentHeader == null) {
            this.argumentHeader = new boolean[this.argumentParameter.length];
        }

        return this.argumentHeader;
    }

    public void setArgumentHeader(boolean[] argumentHeader) {
        this.argumentHeader = argumentHeader;
    }

    public void init(Map<String, Object> params) {
    }

    @Override
    public String getConfKey() {
        return "httppost";
    }

    @Override
    public StepMetaInterface toStepMetaInterface(Map<String, DatabaseMeta> datasouces, StepMeta preste) {
        HTTPPOSTMeta httppostMeta = new HTTPPOSTMeta();
        httppostMeta.setDefault();
        httppostMeta.setUrl(this.getUrl());
        httppostMeta.setEncoding(this.getEncoding());
        httppostMeta.setArgumentField(this.getArgumentField());
        httppostMeta.setArgumentParameter(this.getArgumentParameter());
        httppostMeta.setArgumentHeader(this.getArgumentHeader());
        return httppostMeta;
    }
}
