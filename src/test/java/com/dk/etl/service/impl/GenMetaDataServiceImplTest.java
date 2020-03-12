package com.dk.etl.service.impl;

import com.dk.etl.genfile.template.Table2Table;
import com.dk.etl.service.GenMetaDataService;
import org.junit.jupiter.api.Test;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 9:48
 * @Version:1.0
 */
@SpringBootTest
class GenMetaDataServiceImplTest {

    @Autowired
    private GenMetaDataService genMetaDataService;

    @Test
    public void table2table() throws Exception {
        KettleEnvironment.init();
        String content = "{\"base\":{\"taskname\":\"test6\"},\"datasource\":[{\"name\":\"936176938496823296\",\"url\":\"jdbc:oracle:thin:@192.168.16.93:1521:tip\",\"driverClass\":\"oracle.jdbc.driver.OracleDriver\",\"username\":\"datacenterdb\",\"password\":\"datacenterdb\"},{\"name\":\"936176558358663168\",\"url\":\"jdbc:mysql://192.168.16.221:3306/kettle?useUnicode=true&characterEncoding=utf-8\",\"driverClass\":\"com.mysql.jdbc.Driver\",\"username\":\"root\",\"password\":\"123456\"}],\"tableinput4offset\":{\"dataSource\":\"936176938496823296\",\"SQL\":\"select 1 from dual\"},\"tableinput\":{\"dataSource\":\"936176938496823296\",\"SQL\":\"select * from bu_job_Log\"},\"jscript\":{\"script\":\"UkVTVUxUPVJFU1VMVCoxMDA=\",\"fieldName\":[\"RESULT\"],\"rename\":[\"RESULT\"],\"type\":[\"Integer\"],\"length\":[-1],\"precision\":[-1],\"replace\":[true]},\"insertupdate\":{\"dataSource\":\"936176558358663168\",\"tableName\":\"bu_job_Log\",\"keyLookup\":[\"ID\"],\"keyStream\":[\"ID\"],\"keyCondition\":[\"=\"],\"updateLookup\":[\"ID\",\"JOB_ID\",\"JOB_NAME\",\"START_TIME\",\"END_TIME\",\"COST_TIME\",\"RESULT\"],\"updateStream\":[\"ID\",\"JOB_ID\",\"JOB_NAME\",\"START_TIME\",\"END_TIME\",\"COST_TIME\",\"RESULT\"],\"update\":[true,true,true,true,true,true,true]}}";
        Table2Table template = new Table2Table(content);
        TransMeta transMeta = template.getTransMeta();
        System.out.println(transMeta);
//        genMetaDataService.table2table(content);
    }

}