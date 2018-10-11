package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.general.Response;
import cn.lemonit.robot.runner.common.beans.task.*;
import cn.lemonit.robot.runner.server.define.ResponseDefine;
import cn.lemonit.robot.runner.server.define.StringDefine;
import cn.lemonit.robot.runner.server.service.InstructionSetService;
import cn.lemonit.robot.runner.server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private InstructionSetService instructionSetService;

    @PutMapping("/create")
    public Response create(@RequestBody TaskCreate info) {
        if (info.getName().trim().length() < 1) {
            return ResponseDefine.FAILED_COMMON_NAME_ILLEGAL;
        }
        return taskService.create(info.getName())
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @DeleteMapping("/delete")
    public Response delete(@RequestBody TaskDelete taskDelete) {
        taskService.delete(taskDelete.getTaskKey());
        return Response.SUCCESS_NULL;
    }
//
//    @PostMapping("/update")
//    public Response update(@RequestBody Task task) {
//        if (!taskService.taskContain(task.getTaskId())) {
//            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
//        }
//        Task oldTask = taskService.taskReadFromHd(task.getTaskId());
//        if (oldTask == null) {
//            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
//        }
//        if (!oldTask.getCreateTime().equals(task.getCreateTime())) {
//            return ResponseDefine.FAILED_TASK_UPDATE_BASE_INFO_MISMATCH;
//        }
//        taskService.taskWriteToHd(task);
//        return Response.SUCCESS_NULL;
//    }

    @PostMapping("/rename")
    public Response rename(@RequestBody TaskRename taskRename) {
        return taskService.rename(taskRename) ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
    }

    //    @GetMapping("/get")
//    public Response get(@RequestParam("taskId") String taskId) {
//        if (!taskService.taskContain(taskId)) {
//            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
//        }
//        Task task = taskService.taskReadFromHd(taskId);
//        return task == null ? ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS : Response.success(task);
//    }
//
    @GetMapping("/list")
    public Response list() {
        return Response.success(taskService.list());
    }

    //
    @PutMapping("/instruction/create")
    public Response instructionCreate(@RequestBody InstructionSetCreate instructionSetCreate) {
        if (!taskService.contain(instructionSetCreate.getTaskKey())) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        if (instructionSetCreate.getName().trim().length() < 1) {
            return ResponseDefine.FAILED_COMMON_NAME_ILLEGAL;
        }
        return instructionSetService.create(instructionSetCreate)
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @DeleteMapping("/instruction/delete")
    public Response instructionDelete(@RequestBody InstructionSetDelete instructionSetDelete) {
        instructionSetService.delete(instructionSetDelete);
        return Response.SUCCESS_NULL;
    }

    @PostMapping("/instruction/rename")
    public Response instructionRename(@RequestBody InstructionSetRename rename) {
        Response result = checkInstructionContain(rename.getInstructionSetKey());
        if (result != null) {
            return result;
        }
        if (rename.getInstructionSetName().trim().length() < 1) {
            return ResponseDefine.FAILED_COMMON_NAME_ILLEGAL;
        }
        return instructionSetService.rename(rename)
                ? Response.SUCCESS_NULL :
                ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @PostMapping("/instruction/saveScript")
    public Response instructionSaveScript(@RequestBody InstructionSetSave save) {
        Response result = checkInstructionContain(save.getInstructionSetKey());
        if (result != null) {
            return result;
        }
        return instructionSetService.saveScript(save)
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @GetMapping("/instruction/get")
    public Response instructionGet(@RequestParam("instructionSetKey") String instructionSetKey) {
        return Response.success(instructionSetService.getScript(instructionSetKey));
    }

    @GetMapping("/instruction/list")
    public Response instructionList(@RequestParam("taskKey") String taskKey) {
        return Response.success(instructionSetService.list(taskKey));
    }

    //
//    @PostMapping("/parameter-bin")
//    public Response uploadParameterBin(@RequestParam("taskId") String taskId, @RequestParam("file") MultipartFile multipartFile) {
//        String fileId = taskService.saveParameterBin(taskId, multipartFile);
//        return fileId == null ? ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR : Response.success(fileId);
//    }
//
    private Response checkInstructionContain(String instructionSetKey) {
        if (!instructionSetService.contain(instructionSetKey)) {
            return ResponseDefine.FAILED_INSTRUCTION_SET_NOT_EXISTS;
        }
        return null;
    }

}
