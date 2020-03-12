package com.dk.etl.extra.config;

import com.dk.etl.extra.convert.TranStepMetaConvert;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.getfilenames.GetFileNamesMeta;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:30
 * @Version:1.0
 */

public class GetFileNamesMetaCfg extends BaseCfg implements TranStepMetaConvert {
    private String fileName;
    private String fileMask;

    public GetFileNamesMetaCfg() {
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileMask() {
        return this.fileMask;
    }

    public void setFileMask(String fileMask) {
        this.fileMask = fileMask;
    }

    public void init(Map<String, Object> params) {
    }

    @Override
    public String getConfKey() {
        return "getfilenames";
    }

    @Override
    public StepMetaInterface toStepMetaInterface(Map<String, DatabaseMeta> datasouces, StepMeta preste) {
        GetFileNamesMeta getFileNamesMeta = new GetFileNamesMeta();
        getFileNamesMeta.setDefault();
        getFileNamesMeta.setFileName(new String[]{this.getFileName()});
        getFileNamesMeta.setFileMask(new String[]{this.getFileMask()});
        return getFileNamesMeta;
    }
}
