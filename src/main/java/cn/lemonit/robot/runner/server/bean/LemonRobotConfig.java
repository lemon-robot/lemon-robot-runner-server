package cn.lemonit.robot.runner.server.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * LemonRobot配置项对象
 * @author liuri
 */
@Component
@ConfigurationProperties(prefix = "cn.lemonit.robot")
public class LemonRobotConfig {

    private String mode;
    private LrDataSourceConfig dataSource;
    private LrSessionConfig session;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public LrDataSourceConfig getDataSource() {
        return dataSource;
    }

    public void setDataSource(LrDataSourceConfig dataSource) {
        this.dataSource = dataSource;
    }

    public LrSessionConfig getSession() {
        return session;
    }

    public void setSession(LrSessionConfig session) {
        this.session = session;
    }
}
