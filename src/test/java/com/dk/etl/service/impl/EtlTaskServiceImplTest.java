package com.dk.etl.service.impl;

import org.junit.jupiter.api.Test;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: HarlanW
 * @Date: 2020/1/11 9:26
 * @Version:1.0
 */
@SpringBootTest
class EtlTaskServiceImplTest {

    @Autowired
    private EtlTaskServiceImpl etlTaskServiceImpl;

    @Test
    public void execute(){
//        TransMeta transMeta = TransBuilder.buildCopyTable(
//                transformationName,
//                sourceDatabaseName,
//                sourceTableName,
//                sourceFields,
//                targetDatabaseName,
//                targetTableName,
//                targetFields
//        );
    }

    @Test
    public void create() throws KettleException {
        etlTaskServiceImpl.createTranMeta("");
    }

}