package com.dk.etl.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取服务ip和端口
 * @Author: HarlanW
 * @Date: 2020/1/18 16:27
 * @Version:1.0
 */
@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;

    public String getServerInfo() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("host",address.getHostAddress());
        jsonObject.put("port",this.serverPort);
        return jsonObject.toJSONString();
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        this.serverPort = webServerInitializedEvent.getWebServer().getPort();
    }
}
