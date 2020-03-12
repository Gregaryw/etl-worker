package com.dk.etl.abs;

import com.dk.etl.config.KettleEnvironmentConfig;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositorySecurityProvider;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.trans.TransMeta;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 14:29
 * @Version:1.0
 */

public abstract class AbstractMetaData {

    protected KettleEnvironmentConfig kettleEnvironmentConfig;

    /**
     * 继承AbstractMetaData类时，实现该方法，设置kettleEnvironmentConfig引用
     */
    public abstract void init();


    public KettleDatabaseRepository getRepository() throws KettleException {
        //获取资源仓库
        KettleDatabaseRepository repository = kettleEnvironmentConfig.getRepository();
        //获取元数据库
        DatabaseMeta databaseMeta = kettleEnvironmentConfig.getDatabaseMeta();
        return repository;
    }

    public RepositoryDirectoryInterface getDirectory() throws KettleException{
        Repository repository = getRepository();
        // 获取要保存的目录
        RepositoryDirectoryInterface directory = repository.findDirectory("/");
        return directory;
    }

    public DatabaseMeta getDatabaseMeta(){
        return kettleEnvironmentConfig.getDatabaseMeta();
    }

    public String getVersionComment(TransMeta transMeta, KettleDatabaseRepository repository) {
        boolean versioningEnabled = true;
        boolean versionCommentsEnabled = true;
        String fullPath = transMeta.getRepositoryDirectory() + "/" + transMeta.getName() + transMeta.getRepositoryElementType().getExtension();
        RepositorySecurityProvider repositorySecurityProvider = repository.getSecurityProvider() != null ? repository.getSecurityProvider() : null;
        if (repositorySecurityProvider != null) {
            versioningEnabled = repositorySecurityProvider.isVersioningEnabled(fullPath);
            versionCommentsEnabled = repositorySecurityProvider.allowsVersionComments(fullPath);
        }
        String versionComment = null;
        if (!versioningEnabled || !versionCommentsEnabled) {
            versionComment = "";
        } else {
            versionComment = "no comment";
        }
        return versionComment;
    }
}
