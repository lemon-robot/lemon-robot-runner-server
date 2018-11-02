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
     * 指令集不存在
     */
    public final static Response FAILED_INSTRUCTION_SET_NOT_EXISTS = Response.failed("instruction_set_not_exists", 10005);

    /**
     * 上传插件的时候，所上传的文件不合法（格式不正确）
     */
    public final static Response FAILED_PLUGIN_UPLOAD_FILE_ILLEGAL = Response.failed("plugin_upload_file_illegal", 10101);

    /**
     * 名称不合法
     */
    public final static Response FAILED_COMMON_NAME_ILLEGAL = Response.failed("common_name_illegal", 20000);
    /**
     * 服务器内部发生错误
     */
    public final static Response FAILED_COMMON_SERVER_ERROR = Response.failed("common_server_error", 20001);
    /**
     * 名称已经蔡遵
     */
    public final static Response FAILED_COMMON_NAME_ALREADY_EXISTS = Response.failed("common_name_already_exists", 20002);

    /**
     * LRC激活失败
     * 可能产生原因：
     * 连接者发起的LRC信息不存在
     * 连接者不在LRC白名单范围内
     * LRC的类型与客户端类型不匹配
     */
    public final static Response FAILED_LRC_ACTIVE_FAILED = Response.failed("lrc_active_failed", 30000);
    /**
     * 更新LRC信息失败，提供的LRCT不存在
     */
    public final static Response FAILED_LRC_UPDATE_LRCT_NOT_EXISTS = Response.failed("lrc_update_lrct_not_exists", 30001);
    /**
     * 删除LRC失败，请添加LRC之后再删除，因为当前要删除的类型数量已经为1，删除后无法连接
     */
    public final static Response FAILED_LRC_DELETE_FAILED_PLEASE_ADD = Response.failed("lrc_delete_failed_please_add", 30002);
    /**
     * LRC会话已过期
     */
    public final static Response FAILED_LRC_SESSION_EXPIRED = Response.failed("lrc_session_expired", 30003);

}
