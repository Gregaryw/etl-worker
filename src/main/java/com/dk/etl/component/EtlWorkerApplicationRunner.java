package com.dk.etl.component;

import com.alibaba.fastjson.JSONObject;
import com.dk.etl.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 项目启动之后执行实例run方法
 * @Author: HarlanW
 * @Date: 2020/1/18 16:22
 * @Version:1.0
 */
@Slf4j
@Component
public class EtlWorkerApplicationRunner implements ApplicationRunner {
    /**
     * master主机地址及端口
     */
    @Value("${etl.master.host}")
    private String masterHost;
    @Value("${etl.master.port}")
    private String masterPort;
    @Value("${etl.worker.name}")
    private String workerName;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServerConfig serverConfig;


    /**
     * 项目启动时执行，向master注册信息
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String serverInfo = serverConfig.getServerInfo();
        log.info("工作节点服务器信息{}",serverInfo);
        String url = "http://" + this.masterHost + ":" + this.masterPort + "/etl/master/node/save/json";
        JSONObject jsonObject = JSONObject.parseObject(serverInfo);
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name",workerName);
        paramMap.add("host",jsonObject.getString("host"));
        paramMap.add("port",jsonObject.getString("port"));
        restTemplate.postForEntity(url, paramMap, String.class);
    }
}
