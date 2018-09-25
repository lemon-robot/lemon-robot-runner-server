package cn.lemonit.robot.runner.server.manager;

import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.server.define.StringDefine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ConfigManager {

    @Value("${cn.lemonit.robot.data-source.jdbc-url}")
    private String configJdbcUrl;
    @Value("${cn.lemonit.robot.session.heartbeatLength}")
    private String configHeartbeatLength;

    /**
     * 数据源
     * JDBC - URL
     */
    private String dataSourceJdbcUrl = null;
    /**
     * 数据源
     * 数据库类型
     */
    private String dataSourceDbType = null;
    /**
     * 数据源
     * JDBC - 所使用的驱动
     */
    private String dataSourceJdbcDriver = null;
    /**
     * 数据源
     * 插件存储路径
     */
    private String dataSourcePluginStoragePath = null;
    /**
     * 运行模式
     * alone - 单机运行
     * cluster - 集群模式
     */
    private String runMode = "cluster";

    @Value("${cn.lemonit.robot.data-source.plugin-storage-path}")
    private void pluginStoragePath(String path) {
        if (path == null || path.trim().equals("")) {
            FileUtil.setRuntimePath(FileUtil.getProgramPath() + File.separator + StringDefine.WORKSPACE + File.separator);
            path = FileUtil.getRuntimePath() + File.separator + StringDefine.PLUGINS;
        }
        dataSourcePluginStoragePath = path;
    }

    public String getConfigJdbcUrl() {
        if (configJdbcUrl == null || configJdbcUrl.trim().equals("")) {
            configJdbcUrl =
                    StringDefine.SQLITE_JDBC_PREFIX +
                            FileUtil.getRuntimePath() +
                            File.separator + StringDefine.SQLITE_DB_NAME;
        }
        return configJdbcUrl;
    }

    public String getDataSourceJdbcUrl() {
        if (dataSourceJdbcUrl == null) {
            dataSourceJdbcUrl = getConfigJdbcUrl();
        }
        return dataSourceJdbcUrl;
    }

    public String getDataSourceDbType() {
        if (dataSourceDbType == null) {
            dataSourceDbType = getConfigJdbcUrl().split(":")[1];
        }
        return dataSourceDbType;
    }

    public String getDataSourceJdbcDriver() {
        if (dataSourceJdbcDriver == null) {
            if (getDataSourceDbType().equals("sqlite")) {
                dataSourceJdbcDriver = "org.sqlite.JDBC";
            } else if (dataSourceDbType.equals("mysql")) {
                dataSourceJdbcDriver = "com.mysql.jdbc.Driver";
            } else {
                System.err.println("DATA SOURCE DB TYPE [" + dataSourceDbType + "] NOT SUPPORT !!!");
                System.exit(-1);
            }
        }
        return dataSourceJdbcDriver;
    }

    public String getRunMode() {
        if (dataSourceDbType.equals("sqlite")) {
            runMode = "alone";
        }
        return runMode;
    }

    public Integer getHeartbeatLength() {
        if (configHeartbeatLength == null || configHeartbeatLength.trim().equals("")) {
            configHeartbeatLength = "30";
        }
        return Integer.valueOf(configHeartbeatLength);
    }

}
