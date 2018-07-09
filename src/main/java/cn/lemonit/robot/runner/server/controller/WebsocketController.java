package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.server.manager.WebsocketManager;
import cn.lemonit.robot.runner.server.service.ConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * WS长连接Controller
 * 所有的主动推送通信都要通过该controller进行输出
 *
 * @author LemonIT.CN
 */
@ServerEndpoint("/websocket")
@Component
public class WebsocketController {

    @Autowired
    private ConnectorService connectorService;

    @OnOpen
    public void onOpen(Session session) {
        WebsocketManager.getDefaultManager().initSession(session);
    }

    @OnClose
    public void onClose(Session session) {
        connectorService.lostConnector(WebsocketManager.getDefaultManager().getLrctBySessionId(session.getId()));
    }

}
