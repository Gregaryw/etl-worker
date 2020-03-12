package com.dk.etl.extra.convert;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:25
 * @Version:1.0
 */

public interface TranStepMetaConvert {
    StepMetaInterface toStepMetaInterface(Map<String, DatabaseMeta> var1, StepMeta var2);

    void setName(String var1);
}
