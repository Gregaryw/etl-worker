package com.dk.etl.config;

import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: HarlanW
 * @Date: 2020/1/8 20:44
 * @Version:1.0
 */
@Slf4j
@Component
public class KettleEnvironmentConfig implements ApplicationRunner {

    @Value("${etl.log.datasource.name}")
    private String name;
    @Value("${etl.log.datasource.type}")
    private String type;
    @Value("${etl.log.datasource.access}")
    private String access;
    @Value("${etl.log.datasource.host}")
    private String host;
    @Value("${etl.log.datasource.db}")
    private String db;
    @Value("${etl.log.datasource.port}")
    private String port;
    @Value("${etl.log.datasource.username}")
    private String username;
    @Value("${etl.log.datasource.password}")
    private String password;
    @Value("${etl.repository.username}")
    private String repositoryUsername;
    @Value("${etl.repository.password}")
    private String repositoryPassword;

    /**
     * 数据库对象
     */
    private DatabaseMeta databaseMeta = null;

    /**
     * 资源库元对象
     */
    private KettleDatabaseRepositoryMeta kettleDatabaseRepositoryMeta = null;

    private KettleDatabaseRepository repository = null;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            // 指定xml解析
            System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
                    "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
            //初始化kettle
            KettleEnvironment.init();
            //实例化kettle数据库
            databaseMeta = new DatabaseMeta(name, type, access, host, db, port, username, password);
            //资源库元对象,名称参数，id参数，描述等可以随便定义
            kettleDatabaseRepositoryMeta =
                    new KettleDatabaseRepositoryMeta("bigdata_etl", "bigdata_etl", "bigdata_etl description",databaseMeta);
            //创建资源库对象，此时的对象还是一个空对象
           repository = new KettleDatabaseRepository();
            //给资源库赋值
            repository.init(kettleDatabaseRepositoryMeta);
            //连接资源库
            repository.connect(repositoryUsername,repositoryPassword);
        } catch (KettleException e) {
            log.error("初始kettle出错{}",e.getMessage());
        }
    }

    public DatabaseMeta getDatabaseMeta(){
        return databaseMeta;
    }

    public KettleDatabaseRepository getRepository(){
        return repository;
    }
}
