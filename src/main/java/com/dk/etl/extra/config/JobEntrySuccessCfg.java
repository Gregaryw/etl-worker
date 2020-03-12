package com.dk.etl.extra.config;

import com.dk.etl.extra.convert.JobEntryConvert;
import org.pentaho.di.job.entries.success.JobEntrySuccess;
import org.pentaho.di.job.entry.JobEntryInterface;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 9:47
 * @Version:1.0
 */

public class JobEntrySuccessCfg extends BaseCfg implements JobEntryConvert {
    public JobEntrySuccessCfg() {
    }

    @Override
    public void init(Map<String, Object> params) {
    }

    @Override
    public String getConfKey() {
        return null;
    }

    @Override
    public JobEntryInterface toJobEntryInterface() {
        JobEntrySuccess success = new JobEntrySuccess();
        success.setName(this.getName());
        return success;
    }
}
