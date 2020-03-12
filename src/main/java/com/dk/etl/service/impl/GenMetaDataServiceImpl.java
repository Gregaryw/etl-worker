package com.dk.etl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dk.etl.abs.AbstractMetaData;
import com.dk.etl.config.KettleEnvironmentConfig;
import com.dk.etl.extra.builder.TransBuilder;
import com.dk.etl.extra.exception.InitException;
import com.dk.etl.genfile.template.Table2Table;
import com.dk.etl.service.GenMetaDataService;
import com.dk.etl.util.ExceptionUtils;
import com.dk.etl.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositorySecurityProvider;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 9:41
 * @Version:1.0
 */
@Slf4j
@Service
public class GenMetaDataServiceImpl extends AbstractMetaData implements GenMetaDataService {

    @Autowired
    private KettleEnvironmentConfig kettleEnvironmentConfig;

    @PostConstruct
    @Override
    public void init() {
        super.kettleEnvironmentConfig = this.kettleEnvironmentConfig;
    }

    @Override
    public void createTransMeta(String content) throws KettleException, InitException {
        if(StringUtils.isEmpty(content)){
            throw ExceptionUtils.build("创建转换内容为空",null);
        }
        Map map = JSONObject.parseObject(content, Map.class);
        String type = (String) map.get("type");
        String directory = (String) map.get("directory");

        switch (type){
            case "1":
                table2table(content,directory);
                break;
            default:
                break;
        }
    }

    private void table2table(String content,String directoryPath) throws KettleException, InitException {
        Table2Table template = new Table2Table(content);
        TransMeta transMeta = template.getTransMeta();
        KettleDatabaseRepository repository = getRepository();
        RepositoryDirectoryInterface directory = getDirectory();
        DatabaseMeta databaseMeta = getDatabaseMeta();
        // 设置目录
        RepositoryDirectoryInterface repositoryDirectory = repository.findDirectory(directoryPath);
        if(repositoryDirectory == null){
            repositoryDirectory =repository.createRepositoryDirectory(directory, directoryPath);
        }
        //构建转换元数据
        TransBuilder.renderTransMeta(transMeta,databaseMeta,repository,repositoryDirectory);
        //版本号
        String versionComment = getVersionComment(transMeta, repository);
        //保存转换元数据
        repository.save(transMeta, versionComment);
    }


}
