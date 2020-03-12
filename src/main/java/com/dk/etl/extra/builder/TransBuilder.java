package com.dk.etl.extra.builder;

import com.dk.etl.extra.config.DatabaseMetaCfg;
import com.dk.etl.extra.node.KtrNode;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleMissingPluginsException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.logging.StepLogTable;
import org.pentaho.di.core.logging.TransLogTable;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.variables.Variables;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/9 14:32
 * @Version:1.0
 */
@Slf4j
public class TransBuilder {

    public TransBuilder() {
    }

    public static TransMeta buildTran(String name, List<DatabaseMetaCfg> dataSources, List<KtrNode> nodes) {
        TransMeta transMeta = new TransMeta();
        transMeta.setName(name);
        Map<String, DatabaseMeta> datasources = new HashMap(16);
        dataSources.stream().map((cfg) -> {
            return cfg.toDatabaseMeta();
        }).forEach((dbMeta) -> {
            transMeta.addDatabase(dbMeta);
            datasources.put(dbMeta.getName(), dbMeta);
        });
        StepMeta preStepMeta = null;
        int x = 200;
        int y = 200;
        int k = 0;
        Iterator var9 = nodes.iterator();

        StepMeta stepMeta;
        while(var9.hasNext()) {
            KtrNode node = (KtrNode)var9.next();
            stepMeta = new StepMeta(node.getName(), node.getCfg().toStepMetaInterface(datasources, preStepMeta));
            stepMeta.setLocation(x + 200 * k++, y);
            stepMeta.setDraw(true);
            preStepMeta = stepMeta;
            transMeta.addStep(stepMeta);
        }

        int stepSize = transMeta.getSteps().size();

        for(int i = 0; i < stepSize - 1; ++i) {
            stepMeta = (StepMeta)transMeta.getSteps().get(i);
            StepMeta nextStepMeta = (StepMeta)transMeta.getSteps().get(i + 1);
            if (nextStepMeta != null) {
                transMeta.addTransHop(new TransHopMeta(stepMeta, nextStepMeta));
            }
        }

        logTables(transMeta, "etl_log");
        return transMeta;
    }

    private static void logTables(TransMeta transMeta, String connectionName) {
//        VariableSpace space = new Variables();
//        TransLogTable transLogTable = TransLogTable.getDefault(space, transMeta, transMeta.getSteps());
//        transLogTable.setTableName("etl_trans_logs");
//        transLogTable.setConnectionName(connectionName);
//        StepLogTable stepLogTable = StepLogTable.getDefault(space, transMeta);
//        stepLogTable.setTableName("etl_trans_step_logs");
//        stepLogTable.setConnectionName(connectionName);
//        transMeta.setTransLogTable(transLogTable);
//        transMeta.setStepLogTable(stepLogTable);

        VariableSpace space = new Variables();
        TransLogTable transLogTable = TransLogTable.getDefault(space, transMeta, transMeta.getSteps());
        transLogTable.setTableName("etl_trans_logs");
        transLogTable.setConnectionName("bigdata_etl");
        StepLogTable stepLogTable = StepLogTable.getDefault(space, transMeta);
        stepLogTable.setTableName("etl_trans_step_logs");
        stepLogTable.setConnectionName("bigdata_etl");
        transMeta.setTransLogTable(transLogTable);
        transMeta.setStepLogTable(stepLogTable);
    }

    public static TransMeta buildTran(String filePath, DatabaseMeta databaseMeta, Repository repository, RepositoryDirectoryInterface repositoryDirectory){
        TransMeta transMeta = null;
        try {
            // 创建ktr元对象
            transMeta = new TransMeta(filePath);
            transMeta.addDatabase(databaseMeta);
            //设置资源连接
            transMeta.setRepository(repository);
            transMeta.setRepositoryDirectory(repositoryDirectory);
            logTables(transMeta, "bigdata_etl");
        } catch (KettleXMLException e) {
            log.error("解析转换{}出错",e.getMessage());
        } catch (KettleMissingPluginsException e) {
            log.error("kettle插件出现异常{}",e.getMessage());
            e.printStackTrace();
        }
        return transMeta;
    }

    public  static TransMeta renderTransMeta(TransMeta transMeta, DatabaseMeta databaseMeta, Repository repository, RepositoryDirectoryInterface repositoryDirectory){
        if(transMeta != null){
            transMeta.addDatabase(databaseMeta);
            //设置资源连接
            transMeta.setRepository(repository);
            transMeta.setRepositoryDirectory(repositoryDirectory);
            logTables(transMeta, "bigdata_etl");
        }
        return transMeta;
    }
}
