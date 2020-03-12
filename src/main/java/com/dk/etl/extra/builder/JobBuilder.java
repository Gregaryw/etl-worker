package com.dk.etl.extra.builder;

import com.dk.etl.extra.config.DatabaseMetaCfg;
import com.dk.etl.extra.node.KjbNode;
import org.pentaho.di.core.logging.JobLogTable;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.variables.Variables;
import org.pentaho.di.job.JobHopMeta;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entry.JobEntryCopy;

import java.util.Iterator;
import java.util.List;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 9:29
 * @Version:1.0
 */

public class JobBuilder {
    public JobBuilder() {
    }

    public static JobMeta buildJobMeta(String name, List<DatabaseMetaCfg> dataSources, List<KjbNode> nodes) {
        JobMeta jobMeta = new JobMeta();
        jobMeta.setName(name);
        dataSources.stream().map((cfg) -> {
            return cfg.toDatabaseMeta();
        }).forEach((dbMeta) -> {
            jobMeta.addDatabase(dbMeta);
        });
        nodes.stream().map((node) -> {
            JobEntryCopy jc = new JobEntryCopy(node.getCfg().toJobEntryInterface());
            jc.setName(node.getName());
            return jc;
        }).forEach((jc) -> {
            jobMeta.addJobEntry(jc);
        });
        int x = 200;
        int y = 200;
        int k = 0;
        Iterator var7 = jobMeta.getJobCopies().iterator();

        while(var7.hasNext()) {
            JobEntryCopy jobEntry = (JobEntryCopy)var7.next();
            jobEntry.setLocation(x + 200 * k++, y);
            jobEntry.setDrawn(true);
        }

        int stepSize = jobMeta.getJobCopies().size();

        for(int i = 0; i < stepSize - 1; ++i) {
            JobEntryCopy jobEntry = (JobEntryCopy)jobMeta.getJobCopies().get(i);
            JobEntryCopy nextJobEntry = (JobEntryCopy)jobMeta.getJobCopies().get(i + 1);
            if (nextJobEntry != null) {
                jobMeta.addJobHop(new JobHopMeta(jobEntry, nextJobEntry));
            }
        }

        logTables(jobMeta, "etl_log");
        return jobMeta;
    }

    private static void logTables(JobMeta jobMeta, String connectionName) {
        VariableSpace space = new Variables();
        JobLogTable jobLogTable = JobLogTable.getDefault(space, jobMeta);
        jobLogTable.setConnectionName(connectionName);
        jobLogTable.setTableName("ktl_job_log");
        jobMeta.setJobLogTable(jobLogTable);
    }
}
