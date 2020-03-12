package com.dk.etl.controller;

import com.dk.etl.service.EtlTaskService;
import com.dk.etl.util.Result;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: HarlanW
 * @Date: 2020/1/8 21:35
 * @Version:1.0
 */
@RestController
@RequestMapping("/task")
public class EtlTaskController {

    @Autowired
    private EtlTaskService etlTaskService;

    @RequestMapping("/start")
    public Result start(String fileName,String transName) throws KettleException {
       return this.execute(fileName,transName);
    }

    @RequestMapping("/execute")
    public Result execute(String fileName,String transName) throws KettleException {
        etlTaskService.execute(fileName, transName);
        return Result.success();
    }

    @RequestMapping("/pause")
    public Result pause(String fileName,String transName) throws KettleException {
        int code = etlTaskService.pause(fileName, transName);
        if(code == 0){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @RequestMapping("/stop")
    public Result stop(String fileName,String transName) throws KettleException {
        int code = etlTaskService.stop(fileName, transName);
        if(code == 0){
            return Result.success();
        }else {
            return Result.error();
        }
    }



}
