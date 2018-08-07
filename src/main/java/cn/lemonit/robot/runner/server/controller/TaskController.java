package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.general.Response;
import cn.lemonit.robot.runner.common.beans.task.*;
import cn.lemonit.robot.runner.server.define.ResponseDefine;
import cn.lemonit.robot.runner.server.define.StringDefine;
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
        if (info.getName().trim().length() < 1) {
            return ResponseDefine.FAILED_COMMON_NAME_ILLEGAL;
        }
        return taskService.taskCreate(info.getName())
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @DeleteMapping("/delete")
    public Response delete(@RequestBody TaskDelete taskDelete) {
        taskService.taskDelete(taskDelete.getTaskId());
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

    @PostMapping("/rename")
    public Response rename(@RequestBody TaskRename taskRename) {
        if (!taskService.taskContain(taskRename.getTaskId())) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        Task task = taskService.taskReadFromHd(taskRename.getTaskId());
        if (task == null) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
        }
        if (taskRename.getTaskName().trim().length() < 1) {
            return ResponseDefine.FAILED_COMMON_NAME_ILLEGAL;
        }
        task.setTaskName(taskRename.getTaskName());
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

    @PutMapping("/instruction/create")
    public Response instructionCreate(@RequestBody InstructionSetSave save) {
        if (!taskService.taskContain(save.getTaskId())) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        if (taskService.instructionSetContain(save.getTaskId(), save.getInstructionSetKey())) {
            return ResponseDefine.FAILED_INSTRUCTION_SET_OPERATE_FAILED_KEY_EXISTS;
        }
        if (save.getInstructionSetKey().trim().length() < 1) {
            return ResponseDefine.FAILED_COMMON_NAME_ILLEGAL;
        }
        return taskService.instructionSetCreate(save.getTaskId(), save.getInstructionSetKey())
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @DeleteMapping("/instruction/delete")
    public Response instructionDelete(@RequestBody InstructionSetSave save) {
        if (StringDefine.MAIN.equals(save.getInstructionSetKey().trim())) {
            return ResponseDefine.FAILED_INSTRUCTION_SET_MAIN_CANNOT_CHANGE;
        }
        taskService.instructionSetDelete(save.getTaskId(), save.getInstructionSetKey());
        return Response.SUCCESS_NULL;
    }

    @PostMapping("/instruction/rekey")
    public Response instructionRekey(@RequestBody InstructionSetRekey rekey) {
        Response result = checkInstructionContain(rekey.getTaskId(), rekey.getInstructionSetKey());
        if (result != null) {
            return result;
        }
        if (StringDefine.MAIN.equals(rekey.getInstructionSetKey().trim())) {
            return ResponseDefine.FAILED_INSTRUCTION_SET_MAIN_CANNOT_CHANGE;
        }
        if (taskService.instructionSetContain(rekey.getTaskId(), rekey.getInstructionSetKeyNew())) {
            return ResponseDefine.FAILED_INSTRUCTION_SET_OPERATE_FAILED_KEY_EXISTS;
        }
        if (rekey.getInstructionSetKeyNew().trim().length() < 1) {
            return ResponseDefine.FAILED_COMMON_NAME_ILLEGAL;
        }
        return taskService.instructionSetRekeyToHd(rekey.getTaskId(), rekey.getInstructionSetKey(), rekey.getInstructionSetKeyNew())
                ? Response.SUCCESS_NULL :
                ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @PostMapping("/instruction/saveScript")
    public Response instructionSaveScript(@RequestBody InstructionSetSave save) {
        Response result = checkInstructionContain(save.getTaskId(), save.getInstructionSetKey());
        if (result != null) {
            return result;
        }
        return taskService.instructionSetSaveToHd(save.getTaskId(), save.getInstructionSetKey(), save.getScript())
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @GetMapping("/instruction/get")
    public Response instructionGet(@RequestParam("taskId") String taskId,
                                   @RequestParam("instructionSetKey") String instructionSetKey) {
        Response response = checkInstructionContain(taskId, instructionSetKey);
        if (response != null) {
            return response;
        }
        return Response.success(taskService.instructionSetReadFromHd(taskId, instructionSetKey));
    }

    @GetMapping("/instruction/list")
    public Response instructionList(@RequestParam("taskId") String taskId) {
        if (!taskService.taskContain(taskId)) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        return Response.success(taskService.instructionSetListReadFromHd(taskId));
    }

    private Response checkInstructionContain(String taskId, String instructionSetKey) {
        if (!taskService.taskContain(taskId)) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        if (!taskService.instructionSetContain(taskId, instructionSetKey)) {
            return ResponseDefine.FAILED_INSTRUCTION_SET_NOT_EXISTS;
        }
        return null;
    }

}
