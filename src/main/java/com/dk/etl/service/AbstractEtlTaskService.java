package com.dk.etl.service;

import com.dk.etl.util.ExceptionUtils;
import com.dk.etl.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * @Author: HarlanW
 * @Date: 2020/3/3 15:40
 * @Version:1.0
 */
@Slf4j
public abstract class AbstractEtlTaskService {

    private Trans trans;

    /**
     * 获取转换对象
     * @param fileName
     * @param transName
     * @param repository
     * @return
     * @throws KettleException
     */
   protected Trans getTrans(String fileName, String transName, KettleDatabaseRepository repository) throws KettleException {
       check(fileName,transName);
       RepositoryDirectoryInterface directory = repository.findDirectory(fileName);
       if(directory == null){
           log.error("根据文件名{}获取的文件目录为空",fileName);
           throw  ExceptionUtils.build("文件名目录为空");
       }
       TransMeta transformationMeta = ((Repository) repository).loadTransformation(transName, directory, null, true, null ) ;
       //创建ktr
       if(trans == null || trans.isFinished()){
           trans = new Trans(transformationMeta);
       }
       return trans;
   }

    /**
     * 检查文件名和转换名
     * @param fileName
     * @param transName
     */
    private void check(String fileName,String transName){
        if(StringUtils.isEmpty(fileName) || StringUtils.isEmpty(transName)){
            throw  ExceptionUtils.build("文件名或转换名为空");
        }
    }
}
