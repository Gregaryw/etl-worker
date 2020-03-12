package com.dk.etl.service;

import org.pentaho.di.core.exception.KettleException;

/**
 * etl任务服务接口
 * @Author: HarlanW
 * @Date: 2020/1/8 22:19
 * @Version:1.0
 */

public interface EtlTaskService {

    /**
     * 执行转换
     * @param fileName 文件目录
     * @param transName 转换文件名
     * @throws KettleException
     */
    void execute(String fileName,String transName) throws KettleException;

    /**
     * 暂停转换
     * @param fileName 文件目录
     * @param transName 转换文件名
     * @return 0-成功 -1-失败
     * @throws KettleException
     */
    int pause(String fileName,String transName) throws KettleException;

    /**
     * 停止转换
     * @param fileName 文件目录
     * @param transName 转换文件名
     * @return 0-成功 -1-失败
     * @throws KettleException
     */
    int stop(String fileName,String transName) throws KettleException;
}
