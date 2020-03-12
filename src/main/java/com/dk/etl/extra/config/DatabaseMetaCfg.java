package com.dk.etl.extra.config;

import com.dk.etl.extra.exception.InitException;
import com.dk.etl.util.StringUtils;
import lombok.Data;
import org.pentaho.di.core.database.DatabaseMeta;

import java.util.Map;
import java.util.Properties;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:28
 * @Version:1.0
 */
@Data
public class DatabaseMetaCfg extends BaseCfg {
    private String host;
    private String port;
    private String access;
    private String type;
    private String username;
    private String password;
    private String hostname;
    private String driverClass;
    private String dbName;

    public DatabaseMetaCfg() {
    }



    @Override
    public void init(Map<String, Object> params) throws InitException {
        this.setName(params.get("name").toString());
        this.setType(params.get("datasourceTypeName").toString());
        this.setDriverClass(params.get("driverClass").toString());
        this.setHostname(params.get("url").toString());
        this.setPort(params.get("port").toString());
        this.setUsername(params.get("username").toString());
        this.setPassword(params.get("password").toString());
        this.setDbName(params.get("dbName").toString());
        if (StringUtils.isEmpty(this.getName()) || StringUtils.isEmpty(this.driverClass) || StringUtils.isEmpty(this.hostname) || StringUtils.isEmpty(this.username) || StringUtils.isEmpty(this.password)) {
            throw new InitException("数据源参数缺失");
        }
    }

    @Override
    public String getConfKey() {
        return "database";
    }

    public DatabaseMeta toDatabaseMeta() {
        Properties pi = new Properties();
        pi.setProperty("CUSTOM_DRIVER_CLASS", this.getDriverClass());
        pi.setProperty("CUSTOM_URL", this.getHostname());
        pi.setProperty("PORT_NUMBER", this.getPort());
        DatabaseMeta databaseMeta = new DatabaseMeta();
        databaseMeta.setDefault();
        databaseMeta.setDatabaseType(this.getType());
        databaseMeta.setName(this.getName());
        databaseMeta.setHostname(this.hostname);
        databaseMeta.setDBName(this.dbName);
        databaseMeta.setUsername(this.getUsername());
        databaseMeta.setPassword(this.getPassword());
        databaseMeta.setAttributes(pi);
        return databaseMeta;
    }
}
