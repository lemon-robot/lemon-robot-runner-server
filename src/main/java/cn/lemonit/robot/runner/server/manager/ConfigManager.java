package cn.lemonit.robot.runner.server.manager;

import cn.lemonit.robot.runner.server.bean.LemonRobotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 全局配置管理器
 *
 * @author liuri
 */
@Component
public class ConfigManager {

    @Autowired
    private LemonRobotConfig lemonRobotConfig;

    private static LemonRobotConfig checkedLemonRobotConfig = null;

    public LemonRobotConfig getLemonRobotConfig() {
        if (checkedLemonRobotConfig == null) {
            checkedLemonRobotConfig = lemonRobotConfig;
        }
        return lemonRobotConfig;
    }

    //    @Value("${cn.lemonit.robot.mode}")
//    private String configMode;
//    @Autowired
//    private LrDataSourceConfig lrDataSourceConfig;
//    @Value("${cn.lemonit.robot.session.heartbeat-length}")
//    private String configHeartbeatLength;
//    @Value("${cn.lemonit.robot.session.expired-length}")
//    private String configExpiredLength;
//
//    /**
//     * 数据源
//     * JDBC - URL
//     */
//    private String dataSourceJdbcUrl = null;
//    /**
//     * 数据源
//     * 数据库类型
//     */
//    private String dataSourceDbType = null;
//    /**
//     * 数据源
//     * JDBC - 所使用的驱动
//     */
//    private String dataSourceJdbcDriver = null;
//    /**
//     * 数据源
//     * 插件存储路径
//     */
//    private String dataSourcePluginStoragePath = null;
//    /**
//     * 运行模式
//     * standalone - 单机运行
//     * cluster - 集群模式
//     */
//    private String runMode = "cluster";
//
//    private void pluginStoragePath() {
////        if (path == null || path.trim().equals("")) {
////            FileUtil.setRuntimePath(FileUtil.getProgramPath() + File.separator + StringDefine.WORKSPACE + File.separator);
////            path = FileUtil.getRuntimePath() + File.separator + StringDefine.PLUGINS;
////        }
////        dataSourcePluginStoragePath = path;
//    }
//
//    public String getConfigJdbcUrl() {
//        if (lrDataSourceConfig.getJdbcUrl() == null || lrDataSourceConfig.getJdbcUrl().trim().equals("")) {
//            lrDataSourceConfig.setJdbcUrl(
//                    StringDefine.SQLITE_JDBC_PREFIX +
//                            FileUtil.getRuntimePath() +
//                            File.separator + StringDefine.SQLITE_DB_NAME);
//        }
//        return lrDataSourceConfig.getJdbcUrl();
//    }
//
//    public String getDataSourceJdbcUrl() {
//        if (dataSourceJdbcUrl == null) {
//            dataSourceJdbcUrl = getConfigJdbcUrl();
//        }
//        return dataSourceJdbcUrl;
//    }
//
//    public String getDataSourceDbType() {
//        if (dataSourceDbType == null) {
//            dataSourceDbType = getConfigJdbcUrl().split(":")[1];
//        }
//        return dataSourceDbType;
//    }
//
//    public String getDataSourceJdbcDriver() {
//        if (dataSourceJdbcDriver == null) {
//            if (getDataSourceDbType().equals("sqlite")) {
//                dataSourceJdbcDriver = "org.sqlite.JDBC";
//            } else if (dataSourceDbType.equals("mysql")) {
//                dataSourceJdbcDriver = "com.mysql.jdbc.Driver";
//            } else {
//                System.err.println("DATA SOURCE DB TYPE [" + dataSourceDbType + "] NOT SUPPORT !!!");
//                System.exit(-1);
//            }
//        }
//        return dataSourceJdbcDriver;
//    }
//
//    public String getRunMode() {
//        if (dataSourceDbType.equals("sqlite")) {
//            runMode = "alone";
//        }
//        return runMode;
//    }
//
//    /**
//     * 获取过期时长
//     * 单位：分钟
//     * 默认60
//     */
//    public Integer getExpiredLength() {
//        if (configExpiredLength == null || configExpiredLength.equals("")) {
//            // 如果没配置，默认60分钟
//            configExpiredLength = StringDefine.NUM_SIXTY;
//        }
//        return Integer.valueOf(configExpiredLength);
//    }
//
//    /**
//     * 获取心跳包时长
//     * 单位秒
//     * 默认60
//     */
//    public Integer getHeartbeatLength() {
//        if (configHeartbeatLength == null || configHeartbeatLength.trim().equals("")) {
//            configHeartbeatLength = StringDefine.NUM_SIXTY;
//        }
//        return Integer.valueOf(configHeartbeatLength);
//    }

}
