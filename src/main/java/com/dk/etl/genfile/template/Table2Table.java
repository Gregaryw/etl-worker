package com.dk.etl.genfile.template;

import com.dk.etl.extra.config.*;
import com.dk.etl.extra.exception.InitException;
import com.dk.etl.extra.node.KjbNode;
import com.dk.etl.extra.node.KtrNode;
import com.google.common.collect.Lists;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 9:44
 * @Version:1.0
 */

public class Table2Table extends KtTemplate {
    public Table2Table(String json) throws InitException {
        super(json);
    }

    @Override
    protected void design() {
        KjbNode start = new KjbNode("start", new JobEntrySpecialCfg());
        KjbNode trans = new KjbNode("trans", new JobEntryTransCfg(this.getTranFilePath()));
        KjbNode success = new KjbNode("success", new JobEntrySuccessCfg());
        this.jobNodeList = Lists.newArrayList(new KjbNode[]{start, trans, success});
//        KtrNode queryoffset = new KtrNode("queryoffset", new TableInputMeta4OffsetCfg());
        KtrNode tableInput = new KtrNode("tableInput", new TableInputMetaCfg());
//        KtrNode shuffle = new KtrNode("shuffle ", new ScriptValuesMetaModCfg());
        KtrNode insertUpdate = new KtrNode("insertUpdate ", new InsertUpdateMetaCfg());
        this.tranNodeList = Lists.newArrayList(new KtrNode[]{tableInput, insertUpdate});
    }
}
