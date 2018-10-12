package cn.lemonit.robot.runner.server.bean;

import cn.lemonit.robot.runner.common.beans.general.OssConfig;

/**
 * 数据源配置信息模型
 *
 * @author liuri
 */
public class LrDataSourceConfig {

    private String jdbcUrl;
    private String fileMode;
    private String disk;
    private OssConfig oss;

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getFileMode() {
        return fileMode;
    }

    public void setFileMode(String fileMode) {
        this.fileMode = fileMode;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public OssConfig getOss() {
        return oss;
    }

    public void setOss(OssConfig oss) {
        this.oss = oss;
    }

    public String getDbType() {
        return getJdbcUrl().split(":")[1];
    }

    public String getJdbcDriver() {
        String driver = null;
        if (getDbType().equals("sqlite")) {
            driver = "org.sqlite.JDBC";
        } else if (getDbType().equals("mysql")) {
            driver = "com.mysql.jdbc.Driver";
        }
        return driver;
    }
}
