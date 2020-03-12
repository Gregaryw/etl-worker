package com.dk.etl.extra.config;

import com.dk.etl.extra.convert.JobEntryConvert;
import org.pentaho.di.job.entries.trans.JobEntryTrans;
import org.pentaho.di.job.entry.JobEntryInterface;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 9:46
 * @Version:1.0
 */

public class JobEntryTransCfg extends BaseCfg implements JobEntryConvert {
    private String fileName;

    public JobEntryTransCfg(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void init(Map<String, Object> params) {
    }

    public String getConfKey() {
        return null;
    }

    @Override
    public JobEntryInterface toJobEntryInterface() {
        JobEntryTrans jobEntryTrans = new JobEntryTrans();
        jobEntryTrans.setName(this.getName());
        jobEntryTrans.setFileName(this.getFileName());
        return jobEntryTrans;
    }
}
