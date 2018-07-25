package cn.lemonit.robot.runner.server.define;


import cn.lemonit.robot.runner.common.beans.general.Response;
import org.springframework.web.bind.annotation.ResponseBody;

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
    /**
     * 任务操作失败，任务已经不存在
     */
    public final static Response FAILED_TASK_OPERATE_FAILED_NOT_EXISTS = Response.failed("task_operate_failed_not_exists", 10003);
    /**
     * 任务更新失败，基础数据不匹配
     * 目前通常指TASK-ID不匹配
     */
    public final static Response FAILED_TASK_UPDATE_BASE_INFO_MISMATCH = Response.failed("task_update_base_info_mismatch", 10004);

}
