package cn.lemonit.robot.runner.server.bean;

/**
 * Connector发起连接请求的RequestBody
 *
 * @author LemonIT.CN
 */
public class ReqConnectorActiveRequest {

    /**
     * Lemon Robot Connector Tag
     */
    private String lrct;
    /**
     * Lemon Robot Connector Session
     */
    private String lrcs;
    /**
     * 通过长连接发送的激活码
     */
    private String activeCode;

    public String getLrct() {
        return lrct;
    }

    public void setLrct(String lrct) {
        this.lrct = lrct;
    }

    public String getLrcs() {
        return lrcs;
    }

    public void setLrcs(String lrcs) {
        this.lrcs = lrcs;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
}
