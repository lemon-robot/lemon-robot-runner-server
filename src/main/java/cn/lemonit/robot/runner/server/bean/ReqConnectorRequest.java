package cn.lemonit.robot.runner.server.bean;

/**
 * Connector发起连接请求的RequestBody
 *
 * @author LemonIT.CN
 */
public class ReqConnectorRequest {

    /**
     * Lemon Robot Connector Secret
     */
    private String lrcs;

    public String getLrcs() {
        return lrcs;
    }

    public void setLrcs(String lrcs) {
        this.lrcs = lrcs;
    }
}
