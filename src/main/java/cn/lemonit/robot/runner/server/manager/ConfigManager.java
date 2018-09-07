package cn.lemonit.robot.runner.server.manager;

import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.server.define.StringDefine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ConfigManager {

    /**
     * 数据源
     * JDBC - URL
     */
    private static String dataSourceJdbcUrl = "";
    /**
     * 数据源
     * 数据库类型
     */
    private static String dataSourceDbType = "";
    /**
     * 数据源
     * JDBC - 所使用的驱动
     */
    private static String dataSourceJdbcDriver = "";
    /**
     * 数据源
     * 插件存储路径
     */
    private static String dataSourcePluginStoragePath = "";
    /**
     * 运行模式
     * alone - 单机运行
     * cluster - 集群模式
     */
    private static String runMode = "cluster";

    @Value("${cn.lemonit.robot.data-source.jdbc-url}")
    private void dataSourceJdbcUrl(String jdbcUrl) {
        FileUtil.setRuntimePath(FileUtil.getProgramPath() + File.separator + StringDefine.WORKSPACE + File.separator);
        if (jdbcUrl == null || jdbcUrl.trim().equals("")) {
            jdbcUrl =
                    StringDefine.SQLITE_JDBC_PREFIX +
                            FileUtil.getRuntimePath() +
                            File.separator + StringDefine.SQLITE_DB_NAME;
        }
        dataSourceJdbcUrl = jdbcUrl;
        dataSourceDbType = jdbcUrl.split(":")[1];
        if (dataSourceDbType.equals("sqlite")) {
            dataSourceJdbcDriver = "org.sqlite.JDBC";
            runMode = "alone";
        } else if (dataSourceDbType.equals("mysql")) {
            dataSourceJdbcDriver = "com.mysql.jdbc.Driver";
        } else {
            System.err.println("DATA SOURCE DB TYPE [" + dataSourceDbType + "] NOT SUPPORT !!!");
            System.exit(-1);
        }
    }

    @Value("${cn.lemonit.robot.data-source.plugin-storage-path}")
    private void pluginStoragePath(String path) {
        if (path == null || path.trim().equals("")) {
            FileUtil.setRuntimePath(FileUtil.getProgramPath() + File.separator + StringDefine.WORKSPACE + File.separator);
            path = FileUtil.getRuntimePath() + File.separator + StringDefine.PLUGINS;
        }
        dataSourcePluginStoragePath = path;
    }

    public static String getDataSourceJdbcUrl() {
        return dataSourceJdbcUrl;
    }

    public static String getDataSourceDbType() {
        return dataSourceDbType;
    }

    public static String getDataSourceJdbcDriver() {
        return dataSourceJdbcDriver;
    }

    public static String getDataSourcePluginStoragePath() {
        return dataSourcePluginStoragePath;
    }

    public static String getRunMode() {
        return runMode;
    }
}
