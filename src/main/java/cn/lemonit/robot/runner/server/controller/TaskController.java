package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.general.Response;
import cn.lemonit.robot.runner.common.beans.task.InstructionSetSaveInfo;
import cn.lemonit.robot.runner.common.beans.task.Task;
import cn.lemonit.robot.runner.common.beans.task.TaskCreate;
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
    public Response create(@RequestBody TaskCreate info) {
        return taskService.taskCreate(info.getName())
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @DeleteMapping("/delete")
    public Response delete(@RequestParam("taskId") String taskId) {
        taskService.taskDelete(taskId);
        return Response.SUCCESS_NULL;
    }

    @PostMapping("/update")
    public Response update(@RequestBody Task task) {
        if (!taskService.taskContain(task.getTaskId())) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        Task oldTask = taskService.taskReadFromHd(task.getTaskId());
        if (oldTask == null) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        if (!oldTask.getCreateTime().equals(task.getCreateTime())) {
            return ResponseDefine.FAILED_TASK_UPDATE_BASE_INFO_MISMATCH;
        }
        taskService.taskWriteToHd(task);
        return Response.SUCCESS_NULL;
    }

    @GetMapping("/get")
    public Response get(@RequestParam("taskId") String taskId) {
        if (!taskService.taskContain(taskId)) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        Task task = taskService.taskReadFromHd(taskId);
        return task == null ? ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS : Response.success(task);
    }

    @GetMapping("/list")
    public Response list() {
        return Response.success(taskService.taskReadListFromHd());
    }

    @PostMapping("/instruction/save")
    public Response instruction_save(@RequestBody InstructionSetSaveInfo instructionSetSaveInfo) {
        return taskService.saveInstructionSetToHd(
                instructionSetSaveInfo.getTaskId(),
                instructionSetSaveInfo.getInstructionSetId(),
                instructionSetSaveInfo.getScript()
        ) ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @GetMapping("/instruction/get")
    public Response instruction_get(@RequestParam("taskId") String taskId,
                                    @RequestParam("instructionSetId") String instructionSetId) {
        return Response.success(taskService.instructionSetReadFromHd(taskId, instructionSetId));
    }

}
