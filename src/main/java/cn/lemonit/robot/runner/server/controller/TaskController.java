package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.general.Response;
import cn.lemonit.robot.runner.common.beans.task.Task;
import cn.lemonit.robot.runner.common.beans.task.TaskCreateInfo;
import cn.lemonit.robot.runner.server.define.ResponseDefine;
import cn.lemonit.robot.runner.server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/create")
    public Response create(@RequestBody TaskCreateInfo info) {
        if (taskService.containTaskKey(info.getKey())) {
            return ResponseDefine.FAILED_TASK_CREATE_FAILED_KEY_EXISTS;
        }
        return taskService.createTask(info.getKey(), info.getName())
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_CREATE_FAILED_SERVER_ERROR;
    }

    @DeleteMapping("/delete")
    public Response delete(@RequestParam("taskKey") String taskKey) {
        taskService.deleteTask(taskKey);
        return Response.SUCCESS_NULL;
    }

    @PostMapping("/update")
    public Response update(@RequestBody Task task) {
        if (!taskService.containTaskKey(task.getTaskKey())) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        Task oldTask = taskService.readTaskFromHd(task.getTaskKey());
        if (oldTask == null) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        if (!oldTask.getTaskId().equals(task.getTaskId())) {
            return ResponseDefine.FAILED_TASK_UPDATE_BASE_INFO_MISMATCH;
        }
        taskService.saveBaseInfoToHd(task);
        return Response.SUCCESS_NULL;
    }

    @GetMapping("/get")
    public Response get(@RequestParam("taskKey") String taskKey) {
        if (!taskService.containTaskKey(taskKey)) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        Task task = taskService.readTaskFromHd(taskKey);
        return task == null ? ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS : Response.success(task);
    }

    @GetMapping("/list")
    public Response list() {
        return Response.success(taskService.readTaskListFromHd());
    }

    @GetMapping("/instruction/get")
    public Response instruction_get(@RequestParam("taskKey") String taskKey,
                                    @RequestParam("instructionSetKey") String instructionSetKey) {
        return Response.success(taskService.readTaskInstructionSetScriptFromHd(taskKey, instructionSetKey));
    }

}
