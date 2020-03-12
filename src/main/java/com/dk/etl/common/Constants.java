package com.dk.etl.common;

import com.alibaba.fastjson.JSONObject;
import com.dk.etl.util.JsonUtil;
import com.dk.etl.util.SpringContextUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 9:22
 * @Version:1.0
 */
@Data
@Component("constants")
public class Constants {
    private static Constants constants;
    public static String BASE_HOME;
    public static String KETTLE_HOME;
    public static String ETL_TASK_HOME;
    public static String DIR_HOME;
    public static String SHELL_APP_HOME;
    @Autowired
    private EtlLog etlLog;

    public Constants() {
    }

    public static Constants getInstance() {
        if (constants == null) {
            constants = (Constants) SpringContextUtil.getBean("constants");
        }
        return constants;
    }

}
