package cn.lemonit.robot.runner.server.bean;

import java.util.List;

public class ReqConnectorCreate {

    /**
     * LRC的简介
     */
    private String intro;
    /**
     * LRC的连接者IP白名单
     */
    private List<String> ipWihteList;
    /**
     * LRC的连接者类型
     * 0 - 控制客户端
     */
    private Integer type;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<String> getIpWihteList() {
        return ipWihteList;
    }

    public void setIpWihteList(List<String> ipWihteList) {
        this.ipWihteList = ipWihteList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
