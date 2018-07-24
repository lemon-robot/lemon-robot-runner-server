package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.general.Response;
import cn.lemonit.robot.runner.common.beans.task.TaskCreateInfo;
import cn.lemonit.robot.runner.server.define.ResponseDefine;
import cn.lemonit.robot.runner.server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务相关的API接口
 *
 * @author liuri
 */
@RequestMapping("/task")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Response create(@RequestBody TaskCreateInfo info) {
        if (taskService.containTaskKey(info.getKey())) {
            return ResponseDefine.FAILED_TASK_CREATE_FAILED_KEY_EXISTS;
        }
        return taskService.createTask(info.getKey(), info.getName())
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_CREATE_FAILED_SERVER_ERROR;
    }

}
