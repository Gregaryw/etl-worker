package com.dk.etl.service.impl;

import com.dk.etl.config.KettleEnvironmentConfig;
import com.dk.etl.extra.builder.TransBuilder;
import com.dk.etl.service.AbstractEtlTaskService;
import com.dk.etl.service.EtlTaskService;
import com.dk.etl.util.ExceptionUtils;
import com.dk.etl.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositorySecurityProvider;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMetaDataCombi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @Author: HarlanW
 * @Date: 2020/1/8 22:22
 * @Version:1.0
 */
@Slf4j
@Service
public class EtlTaskServiceImpl extends AbstractEtlTaskService implements EtlTaskService {

    @Autowired
    private KettleEnvironmentConfig kettleEnvironmentConfig;


    @Override
    @Async
    public void execute(String fileName, String transName) throws KettleException {
        //获取资源仓库
        KettleDatabaseRepository repository = kettleEnvironmentConfig.getRepository();
        runKtr(fileName, transName, repository);
    }

    @Override
    public int pause(String fileName, String transName) throws KettleException {
        //获取资源仓库
        KettleDatabaseRepository repository = kettleEnvironmentConfig.getRepository();
        return pauseKtr(fileName, transName, repository);
    }

    @Override
    public int stop(String fileName, String transName) throws KettleException {
        //获取资源仓库
        KettleDatabaseRepository repository = kettleEnvironmentConfig.getRepository();
        return stopKtr(fileName, transName, repository);
    }

    private int pauseKtr(String fileName, String transName, KettleDatabaseRepository repository) throws KettleException {
        int code = 0;
        Trans trans = getTrans(fileName, transName, repository);
        List<StepMetaDataCombi> steps = trans.getSteps();
        if(trans != null && steps != null){
            trans.pauseRunning();
            // 判断执行是否出错
            if (trans.getErrors() > 0) {
                code = -1;
                log.error("暂停文件{}转换出错! -- {}", fileName);
            }
        }
        return code;
    }

    private int stopKtr(String fileName, String transName, KettleDatabaseRepository repository) throws KettleException {
        int code = 0;
        Trans trans = getTrans(fileName, transName, repository);
        if(trans != null){
            trans.setStopped(true);
            // 判断执行是否出错
            if (trans.getErrors() > 0) {
                code = -1;
                log.error("暂停文件{}转换出错! -- {}", fileName);
            }
        }
        return code;
    }

    public Integer createTranMeta(String taskId) throws KettleException {
        int code = -1;
        // ktr文件路径
        String filePath = "F:\\test\\test3.ktr";
        //获取资源仓库
        KettleDatabaseRepository repository = kettleEnvironmentConfig.getRepository();
        //获取元数据库
        DatabaseMeta databaseMeta = kettleEnvironmentConfig.getDatabaseMeta();
        // 获取要保存的目录
        RepositoryDirectoryInterface directory = repository.findDirectory("/");
        // 设置目录
        RepositoryDirectoryInterface repositoryDirectory = repository.createRepositoryDirectory(directory, "/etl");
        //构建转换元对象
        TransMeta transMeta = TransBuilder.buildTran(filePath, databaseMeta, repository, repositoryDirectory);

        //版本号设置
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
        if (transMeta != null) {
            // 保存transformation到资源库
            repository.save(transMeta, versionComment);
            code = 0;
        }
        return code;
    }

    /**
     * 执行转换
     *
     * @param fileName
     * @param transName
     * @param repository
     * @return
     * @throws KettleException
     */
    public void runKtr(String fileName, String transName, KettleDatabaseRepository repository) throws KettleException {
        Trans trans = getTrans(fileName, transName, repository);
        if (trans != null) {
            // 执行ktr
            trans.execute(null);
            // 等待执行完毕
            trans.waitUntilFinished();
            // 判断执行是否出错
            if (trans.getErrors() > 0) {
                log.error("执行文件{}转换出错! -- {}", fileName);
            }
        }
    }


    /**
     * 执行转换
     */
    public static void runKtr() throws KettleException {
        KettleEnvironment.init();
        // ktr文件路径
        String filePath = "F:\\test\\test5.ktr";

        // 创建ktr元对象
        TransMeta transMeta = new TransMeta(filePath);

        DatabaseMeta databaseMeta =
                new DatabaseMeta("bigdata_etl", "Oracle", "Native", "localhost", "orcl", "1521", "bigdata_etl", "123456");

        transMeta.addDatabase(databaseMeta);

        //创建资源库对象，此时的对象还是一个空对象
        KettleDatabaseRepository repository = new KettleDatabaseRepository();


        //资源库元对象,名称参数，id参数，描述等可以随便定义
        KettleDatabaseRepositoryMeta kettleDatabaseMeta =
                new KettleDatabaseRepositoryMeta("bigdata_etl", "bigdata_etl", "bigdata_etl description", databaseMeta);

        //给资源库赋值
        repository.init(kettleDatabaseMeta);
        //连接资源库
        repository.connect("admin", "admin");

        //根据变量查找到模型所在的目录对象,此步骤很重要。
//        RepositoryDirectoryInterface directory = repository.findDirectory("/enfo_worker/wxj");
        //创建ktr元对象
        // TransMeta transformationMeta = ((Repository) repository).loadTransformation("test", directory, null, true, null ) ;
        //创建ktr
        //  Trans trans = new Trans(transformationMeta);


        transMeta.setRepository(repository);


        // 获取要保存的目录
        RepositoryDirectoryInterface directory = repository.findDirectory("/");
//        RepositoryDirectory subDirectory = new RepositoryDirectory();
//        subDirectory.setParent(directory);
//        subDirectory.setName("etl");
//        directory.addSubdirectory(subDirectory);
        // 设置目录
        RepositoryDirectoryInterface repositoryDirectory = repository.createRepositoryDirectory(directory, "/etl");
        transMeta.setRepositoryDirectory(repositoryDirectory);
        // 保存transformation到资源库
        repository.save(transMeta, "0.0");


//        transMeta.setRepository();

//        VariableSpace space = new Variables();
//        TransLogTable transLogTable = TransLogTable.getDefault(space, transMeta, transMeta.getSteps());
//        transLogTable.setTableName("etl_trans_logs");
//        transLogTable.setConnectionName("bigdata_etl");
//        StepLogTable stepLogTable = StepLogTable.getDefault(space, transMeta);
//        stepLogTable.setTableName("etl_trans_step_logs");
//        stepLogTable.setConnectionName("bigdata_etl");
//        transMeta.setTransLogTable(transLogTable);
//        transMeta.setStepLogTable(stepLogTable);


//        // 创建ktr
//        Trans trans = new Trans(transMeta);
//
//
//        // 执行ktr
//        trans.execute(null);
//        // 等待执行完毕
//        trans.waitUntilFinished();
//
//
//        // 判断执行是否出错
//        if (trans.getErrors() > 0) {
//            System.err.println("文件执行出错！！！！！！！！");
//            log.error("执行文件{}转换出错! -- {}", fileName);
//        }
    }


    /**
     * 将java对象系列化成字节数组
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static byte[] obj2byte(Object obj) throws Exception {
        byte[] ret = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(obj);
        out.close();
        ret = baos.toByteArray();
        baos.close();
        return ret;
    }

    /**
     * 将字节转换成java对象
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object byte2obj(byte[] bytes) throws Exception {
        Object ret = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bais);
        ret = in.readObject();
        in.close();
        return ret;
    }

    public static void main(String[] args) throws Exception {
//        runKtr("");
        /*Database database = new Database();
        database.execStatement()*/

    }
}
