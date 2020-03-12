package com.dk.etl.controller;

import com.dk.etl.extra.exception.InitException;
import com.dk.etl.service.GenMetaDataService;
import com.dk.etl.util.Result;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: HarlanW
 * @Date: 2020/1/14 14:02
 * @Version:1.0
 */
@RestController
@RequestMapping("/generate")
public class GenMetaDataController {

    @Autowired
    private GenMetaDataService genMetaDataService;

    /**
     * 根据content内容创建TransMeta对象
     * @param content 生成元素数据json数据格式内容
     * @return
     * @throws InitException
     */
    @PostMapping("/trans/meta")
    public Result createTransMeta(@RequestBody String content) throws KettleException, InitException {
        genMetaDataService.createTransMeta(content);
        return Result.success();
    }
}
