package cn.lemonit.robot.runner.server.bean;

import java.io.Serializable;
import java.security.KeyPair;
import java.util.Base64;
import java.util.List;

/**
 * LemonRobotConnector信息对象
 *
 * @author LemonIT.CN
 */
public class LRCInfo implements Serializable {

    /**
     * RSA秘钥对
     */
    private KeyPair keyPair;
    /**
     * Lemon Robot Connector Tag
     * 连接密钥标识
     */
    private String lrct;
    /**
     * 连接配置创建时间
     */
    private Long createTime;
    /**
     * 简介
     */
    private String intro;
    /**
     * 连接类型
     * 0 - 控制客户端
     */
    private Integer type;
    /**
     * ip白名单，如果为空，表示不限制
     */
    private List<String> ipWhiteList;

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public String getLrct() {
        return lrct;
    }

    public void setLrct(String lrct) {
        this.lrct = lrct;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getIpWhiteList() {
        return ipWhiteList;
    }

    public void setIpWhiteList(List<String> ipWhiteList) {
        this.ipWhiteList = ipWhiteList;
    }

    public String getLrck() {
        return Base64.getEncoder().encodeToString(getKeyPair().getPublic().getEncoded());
    }

}
