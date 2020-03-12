package com.dk.etl.service;

import com.dk.etl.extra.exception.InitException;
import org.pentaho.di.core.exception.KettleException;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 9:38
 * @Version:1.0
 */

public interface GenMetaDataService {

    /**
     *  生成转换元数据
     * @param content
     * @throws KettleException
     * @throws InitException
     */
    void createTransMeta(String content) throws KettleException, InitException;
}
