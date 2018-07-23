package cn.lemonit.robot.runner.server.manager;

import cn.lemonit.robot.runner.common.beans.general.WebSocketMsg;
import cn.lemonit.robot.runner.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Websocket长连接管理器
 *
 * @author LemonIT.CN
 */
public class WebSocketManager {

    private static WebSocketManager defaultManager;
    private Logger logger = LoggerFactory.getLogger(WebSocketManager.class);

    public synchronized static WebSocketManager defaultManager() {
        if (defaultManager == null) {
            defaultManager = new WebSocketManager();
        }
        return defaultManager;
    }

    /**
     * websocket会话池
     * < , Session>
     */
    private Map<String, Session> sessionPool;
    /**
     * 会话关系存储池
     * <LRCT , SessionID>
     */
    private Map<String, String> sessionRelationPool;
    /**
     * 反向会话关系存储池
     * <SessionID , LRCT>
     */
    private Map<String, String> reverseSessionRelationPool;
    /**
     * 会话激活码池
     * <ActiveCode, SessionID>
     */
    private Map<String, String> sessionActiveCodePool;
    /**
     * 会话ID与激活码的关系池
     * 此池为了防止会话未激活就断开造成的sessionActiveCodePool中数据无法销毁的问题
     * <SessionID, ActiveCode>
     */
    private Map<String, String> reverseSessionActiveCodePool;

    private Map<String, Session> getSessionPool() {
        if (sessionPool == null) {
            sessionPool = new HashMap<>(0);
        }
        return sessionPool;
    }

    private Map<String, String> getSessionRelationPool() {
        if (sessionRelationPool == null) {
            sessionRelationPool = new HashMap<>(0);
        }
        return sessionRelationPool;
    }

    private Map<String, String> getReverseSessionRelationPool() {
        if (reverseSessionRelationPool == null) {
            reverseSessionRelationPool = new HashMap<>(0);
        }
        return reverseSessionRelationPool;
    }

    private Map<String, String> getSessionActiveCodePool() {
        if (sessionActiveCodePool == null) {
            sessionActiveCodePool = new HashMap<>(0);
        }
        return sessionActiveCodePool;
    }

    private Map<String, String> getReverseSessionActiveCodePool() {
        if (reverseSessionActiveCodePool == null) {
            reverseSessionActiveCodePool = new HashMap<>(0);
        }
        return reverseSessionActiveCodePool;
    }

    /**
     * 初始化会话
     * 当一个会话新建的时候需要调用此方法
     * 在方法内会将会话放入待激活池，同时通过长连接发送给客户端激活码
     *
     * @param session 会话对象
     */
    public void initSession(Session session) {
        if (session != null && session.isOpen()) {
            // 会话是有效的
            String sessionId = session.getId();
            String activeCode = UUID.randomUUID().toString();
            getSessionActiveCodePool().put(activeCode, sessionId);
            getSessionPool().put(sessionId, session);
            sendTextMsg(session, new WebSocketMsg(WebSocketMsg.CODE_ACTIVE_CODE_DISTRIBUTE, activeCode));
            logger.info("A web socket long connection is initialized, sessionId = " + sessionId + " ，activeCode = " + activeCode);
        }
    }

    /**
     * 激活会话
     *
     * @param lrct       Lemon Robot Connector Tag
     * @param activeCode 长连接标识
     */
    public boolean activeSession(String lrct, String activeCode) {
        String sessionId = getSessionActiveCodePool().get(activeCode);
        if (sessionId == null) {
            // 激活码不存在，放弃继续操作
            return false;
        }
        // 移除激活码
        getSessionActiveCodePool().remove(activeCode);
        getReverseSessionActiveCodePool().remove(sessionId);

        // 获取激活码对应的会话
        Session session = getSessionPool().get(sessionId);
        if (session != null && session.isOpen()) {
            // 会话是开启状态
            String containSessionId = getSessionRelationPool().get(lrct);
            if (containSessionId != null) {
                logger.info("This LRCT [ " + lrct + " ] already has a long connection, now close the old long connection");
                try {
                    getSessionPool().get(containSessionId).close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.info("An error occurred when the old long connection was closed. LRCT = " + lrct);
                }
            }
            // 添加关系池
            getSessionRelationPool().put(lrct, sessionId);
            getReverseSessionRelationPool().put(sessionId, lrct);
            logger.info("Successful establishment of conversational relationship, LRCT = " + lrct + " , SessionID = " + sessionId);
            logger.info("The current number of LRC is: " + getSessionRelationPool().size());
            WebSocketManager.defaultManager().sendTextMsg(lrct, new WebSocketMsg(WebSocketMsg.CODE_ACTIVE_RESULT_SUCCESS, null));
            return true;
        } else {
            // 会话已失效，断开连接并清除
            getSessionPool().remove(sessionId);
        }
        return false;
    }

    /**
     * 关闭websocket会话
     *
     * @param session 要关闭的长连接会话对象
     */
    public void closeSession(Session session) {
        if (session != null) {
            // 移除激活码，防止未激活的连接断开后存在冗余数据
            String activeCode = getReverseSessionActiveCodePool().get(session.getId());
            if (activeCode != null) {
                getSessionActiveCodePool().remove(activeCode);
            }
            getReverseSessionActiveCodePool().remove(session.getId());

            String lrct = getReverseSessionRelationPool().get(session.getId());
            if (lrct != null) {
                getSessionRelationPool().remove(lrct);
            }
            getSessionRelationPool().remove(session.getId());
            getSessionPool().remove(session.getId());
            logger.info("A LRC connection has been disconnected, the number of remaining LRC connections is: " + getSessionRelationPool().size());
            if (session.isOpen()) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过LRCT关闭会话
     *
     * @param lrct Lemon Robot Connector Tag
     */
    public void closeSession(String lrct) {
        String sessionId = getSessionRelationPool().get(lrct);
        if (sessionId != null) {
            Session session = getSessionPool().get(sessionId);
            if (session != null) {
                closeSession(session);
            }
        }
    }

    /**
     * 向指定的LRCT已激活的长连接发送文本消息
     *
     * @param lrct Lemon Robot Connector Tag
     * @param msg  要发送的消息对象
     * @return 发送结果
     */
    public boolean sendTextMsg(String lrct, WebSocketMsg msg) {
        String sessionId = getSessionRelationPool().get(lrct);
        if (sessionId == null) {
            return false;
        }
        return sendTextMsg(getSessionPool().get(sessionId), msg);
    }

    /**
     * 向指定的会话发送文本消息
     *
     * @param session 会话对象
     * @param msg     消息对象
     * @return 发送结果
     */
    public boolean sendTextMsg(Session session, WebSocketMsg msg) {
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(JsonUtil.gsonObj().toJson(msg));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 通过SessionID获取对应LRC的LRCT
     *
     * @param sessionId 会话对象ID
     * @return LRCT Lemon Robot Connector Tag
     */
    public String getLrctBySessionId(String sessionId) {
        return getReverseSessionRelationPool().get(sessionId);
    }

}
