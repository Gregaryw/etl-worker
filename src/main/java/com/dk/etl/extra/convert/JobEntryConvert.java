package com.dk.etl.extra.convert;

import org.pentaho.di.job.entry.JobEntryInterface;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:42
 * @Version:1.0
 */

public interface JobEntryConvert {
    JobEntryInterface toJobEntryInterface();

    void setName(String var1);
}
