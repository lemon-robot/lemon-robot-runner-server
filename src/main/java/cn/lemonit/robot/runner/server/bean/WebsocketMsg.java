package cn.lemonit.robot.runner.server.bean;

import cn.lemonit.robot.runner.server.manager.WebsocketManager;
import com.google.gson.Gson;

/**
 * WebSocket推送消息模板
 *
 * @author LemonIT.CN
 */
public class WebsocketMsg {

    private transient static Gson gson = new Gson();

    // 0        激活码
    // 101      日志
    private String type;
    private Object data;

    public static final String CODE_ACTIVE_CODE_DISTRIBUTE = "active_code_distribute";
    public static final String CODE_ACTIVE_RESULT_SUCCESS = "active_result_success";

    public WebsocketMsg() {
    }

    public WebsocketMsg(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
