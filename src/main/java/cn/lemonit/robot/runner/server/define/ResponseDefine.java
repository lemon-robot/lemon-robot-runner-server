package cn.lemonit.robot.runner.server.define;


import cn.lemonit.robot.runner.common.beans.general.Response;

/**
 * 响应宏定义
 *
 * @author liuri
 */
public class ResponseDefine {

    /**
     * 任务操作失败，服务器内部错误
     */
    public final static Response FAILED_TASK_OPERATE_FAILED_SERVER_ERROR = Response.failed("task_operate_failed_server_error", 1001);
    /**
     * 指令集创建失败，指令集关键字已经存在
     */
    public final static Response FAILED_INSTRUCTION_SET_OPERATE_FAILED_KEY_EXISTS = Response.failed("instruction_set_operate_failed_key_exists", 1002);
    /**
     * 任务操作失败，任务已经不存在
     */
    public final static Response FAILED_TASK_OPERATE_FAILED_NOT_EXISTS = Response.failed("task_operate_failed_not_exists", 10003);
    /**
     * 任务更新失败，基础数据不匹配
     * 目前通常指TASK-ID不匹配
     */
    public final static Response FAILED_TASK_UPDATE_BASE_INFO_MISMATCH = Response.failed("task_update_base_info_mismatch", 10004);
    /**
     * 指令集不存在
     */
    public final static Response FAILED_INSTRUCTION_SET_NOT_EXISTS = Response.failed("instruction_set_not_exists", 10005);
    /**
     * 主指令集不能删除
     */
    public final static Response FAILED_INSTRUCTION_SET_MAIN_CANNOT_CHANGE = Response.failed("instruction_set_main_cannot_change", 10006);


    /**
     * 名称不合法
     */
    public final static Response FAILED_COMMON_NAME_ILLEGAL = Response.failed("common_name_illegal", 20000);

}
