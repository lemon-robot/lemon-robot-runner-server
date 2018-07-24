package cn.lemonit.robot.runner.server.define;


import cn.lemonit.robot.runner.common.beans.general.Response;

/**
 * 响应宏定义
 *
 * @author liuri
 */
public class ResponseDefine {

    /**
     * 任务创建失败，服务器内部错误
     */
    public final static Response FAILED_TASK_CREATE_FAILED_SERVER_ERROR = Response.failed("task_create_failed_server_error", 1001);
    /**
     * 任务创建失败，任务标识已经存在
     */
    public final static Response FAILED_TASK_CREATE_FAILED_KEY_EXISTS = Response.failed("task_create_failed_key_exists", 1002);

}
