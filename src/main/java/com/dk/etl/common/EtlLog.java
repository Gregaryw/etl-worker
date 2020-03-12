package com.dk.etl.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 10:11
 * @Version:1.0
 */
@Component
@Data
public class EtlLog {
    @Value("${etl.log.datasource.name}")
    private String name;
    @Value("${etl.log.datasource.host}")
    private String url;
    @Value("${etl.log.datasource.port}")
    private String port;
    @Value("${etl.log.datasource.driverClass}")
    private String driverClass;
    @Value("${etl.log.datasource.username}")
    private String username;
    @Value("${etl.log.datasource.password}")
    private String password;
    @Value("${etl.log.datasource.type}")
    private String datasourceTypeName;
    @Value("${etl.log.datasource.db}")
    private String dbName;
}
