package com.dk.etl.util;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 9:20
 * @Version:1.0
 */

public class JsonUtil {
    public JsonUtil() {
    }

    public static Map json2map(String json) {
        Map map = (Map) JSON.parseObject(json, Map.class);
        return map;
    }

    public static List<String> json2array(String json) {
        List list = (List)JSON.parseObject(json, List.class);
        return list;
    }
}
