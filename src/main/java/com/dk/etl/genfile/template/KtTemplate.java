package com.dk.etl.genfile.template;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dk.etl.common.Constants;
import com.dk.etl.common.EtlLog;
import com.dk.etl.extra.builder.JobBuilder;
import com.dk.etl.extra.builder.TransBuilder;
import com.dk.etl.extra.config.BaseCfg;
import com.dk.etl.extra.config.DatabaseMetaCfg;
import com.dk.etl.extra.exception.InitException;
import com.dk.etl.extra.node.KjbNode;
import com.dk.etl.extra.node.KtrNode;
import com.dk.etl.util.JsonUtil;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.TransMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/13 21:50
 * @Version:1.0
 */

public abstract class KtTemplate {
    protected String json;
    protected String name;
    protected List<DatabaseMetaCfg> dataSources = new ArrayList();
    protected List<KjbNode> jobNodeList = null;
    protected List<KtrNode> tranNodeList = null;

    public KtTemplate(String json) throws InitException {
        this.json = json;
        Map<String, Map<String, Object>> params = JsonUtil.json2map(this.json);
        this.design();
        JSONObject jsonObject = (JSONObject)JSONObject.parse(json);
        this.name = jsonObject.getString("name");
        this.initDataSources(params);
        this.initTranComponent(this.tranNodeList, params);
       this.initJobComponent(this.jobNodeList, params);
    }

    protected String getTranFilePath() {
        return Constants.ETL_TASK_HOME + File.separator + this.name + File.separator + this.name + ".ktr";
    }

    protected abstract void design();

    public JobMeta getJobMeta() {
        return JobBuilder.buildJobMeta(this.name, this.dataSources, this.jobNodeList);
    }

    public TransMeta getTransMeta() {
        return TransBuilder.buildTran(this.name, this.dataSources, this.tranNodeList);
    }

    private void initDataSources(Map<String, Map<String, Object>> params) throws InitException {
        JSONArray array = (JSONArray)params.get("datasource");
        if (array != null) {
            for(int i = 0; i < array.size(); ++i) {
                Map<String, Object> ds = (Map)array.get(i);
                DatabaseMetaCfg cfg = new DatabaseMetaCfg();
                cfg.init(ds);
                this.dataSources.add(cfg);
            }
        }

        DatabaseMetaCfg cfg = new DatabaseMetaCfg();
        EtlLog etlLog = Constants.getInstance().getEtlLog();
        cfg.init(JsonUtil.json2map(JSONObject.toJSONString(etlLog)));
        this.dataSources.add(cfg);
    }
    private void initTranComponent(List<KtrNode> nodes, Map<String, Map<String, Object>> params) throws InitException {
        Iterator var3 = nodes.iterator();

        while(var3.hasNext()) {
            KtrNode node = (KtrNode)var3.next();
            BaseCfg cfg = (BaseCfg)node.getCfg();
            cfg.init((Map)params.get(cfg.getConfKey()));
        }

    }

    private void initJobComponent(List<KjbNode> nodes, Map<String, Map<String, Object>> params) throws InitException {
        Iterator var3 = nodes.iterator();

        while(var3.hasNext()) {
            KjbNode node = (KjbNode)var3.next();
            BaseCfg cfg = (BaseCfg)node.getCfg();
            cfg.init((Map)params.get(cfg.getConfKey()));
        }

    }
}
