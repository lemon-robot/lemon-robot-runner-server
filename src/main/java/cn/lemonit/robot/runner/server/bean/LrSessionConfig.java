package cn.lemonit.robot.runner.server.bean;

/**
 * 会话相关配置
 *
 * @author liuri
 */
public class LrSessionConfig {

    private String expiredLength;
    private String heartbeatLength;

    public Integer getExpiredLength() {
        return Integer.valueOf(expiredLength);
    }

    public void setExpiredLength(String expiredLength) {
        this.expiredLength = expiredLength;
    }

    public Integer getHeartbeatLength() {
        return Integer.valueOf(heartbeatLength);
    }

    public void setHeartbeatLength(String heartbeatLength) {
        this.heartbeatLength = heartbeatLength;
    }
}
