package cn.lemonit.robot.runner.server.bean;

/**
 * 响应信息对象
 *
 * @author LemonIT.CN
 */
public class Response {

    private boolean success;
    private String msg;
    private Integer code;
    private Object data;

    public static final Response SUCCESS_NULL = Response.success(null);

    public Response() {
    }

    public Response(boolean success, String msg, Integer code, Object data) {
        this.success = success;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static Response success(String data) {
        return new Response(true, "", 0, data);
    }

    public static Response failed(String msg, Integer code, Object data) {
        return new Response(false, msg, code, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
