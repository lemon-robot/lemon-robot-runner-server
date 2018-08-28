package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.server.manager.WebSocketManager;
import cn.lemonit.robot.runner.server.manager.LrcManager;
import org.springframework.stereotype.Component;

import javax.websocket.EndpointConfig;
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
public class WebSocketController {

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        WebSocketManager.defaultManager().initSession(session);
    }

    @OnClose
    public void onClose(Session session) {
        LrcManager.defaultManager().disconnectLrc(WebSocketManager.defaultManager().getLrctBySessionId(session.getId()));
    }

}
