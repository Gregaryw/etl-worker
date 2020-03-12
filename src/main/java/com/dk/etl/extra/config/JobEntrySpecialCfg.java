package com.dk.etl.extra.config;

import com.dk.etl.extra.convert.JobEntryConvert;
import org.pentaho.di.job.entries.special.JobEntrySpecial;
import org.pentaho.di.job.entry.JobEntryInterface;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 9:45
 * @Version:1.0
 */

public class JobEntrySpecialCfg extends BaseCfg implements JobEntryConvert {
    public JobEntrySpecialCfg() {
    }

    public void init(Map<String, Object> params) {
    }

    @Override
    public String getConfKey() {
        return "job";
    }

    public JobEntryInterface toJobEntryInterface() {
        JobEntrySpecial jobEntrySpecial = new JobEntrySpecial();
        jobEntrySpecial.setStart(true);
        jobEntrySpecial.setName(this.getName());
        jobEntrySpecial.setDummy(false);
        jobEntrySpecial.setRepeat(false);
        jobEntrySpecial.setSchedulerType(0);
        return jobEntrySpecial;
    }
}
