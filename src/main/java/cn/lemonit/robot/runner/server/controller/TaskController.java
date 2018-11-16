package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.entity.TaskParameterDef;
import cn.lemonit.robot.runner.common.beans.general.Response;
import cn.lemonit.robot.runner.common.beans.task.*;
import cn.lemonit.robot.runner.server.define.ResponseDefine;
import cn.lemonit.robot.runner.server.define.StringDefine;
import cn.lemonit.robot.runner.server.service.InstructionSetService;
import cn.lemonit.robot.runner.server.service.ParameterService;
import cn.lemonit.robot.runner.server.service.TaskService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private ParameterService parameterService;

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

    @PostMapping("/rename")
    public Response rename(@RequestBody TaskRename taskRename) {
        return taskService.rename(taskRename) ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
    }

    @GetMapping("/list")
    public Response list() {
        return Response.success(taskService.list());
    }

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

    private Response checkInstructionContain(String instructionSetKey) {
        if (!instructionSetService.contain(instructionSetKey)) {
            return ResponseDefine.FAILED_INSTRUCTION_SET_NOT_EXISTS;
        }
        return null;
    }

    @PutMapping("/parameter/create")
    public Response parameterCreate(@RequestBody ParameterCreate parameterCreate) {
        parameterCreate.setName(parameterCreate.getName().trim());
        if (!taskService.contain(parameterCreate.getTaskKey())) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        if (parameterCreate.getName().length() < 1) {
            return ResponseDefine.FAILED_COMMON_NAME_ILLEGAL;
        }
        if (parameterService.contain(parameterCreate.getTaskKey(), parameterCreate.getName())) {
            return ResponseDefine.FAILED_COMMON_NAME_ALREADY_EXISTS;
        }
        return parameterService.create(parameterCreate)
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @DeleteMapping("/parameter/delete")
    public Response parameterDelete(@RequestBody ParameterDelete parameterDelete) {
        parameterService.delete(parameterDelete);
        return Response.SUCCESS_NULL;
    }

    @PostMapping("/parameter/update")
    public Response parameterUpdate(@RequestBody ParameterUpdate parameterUpdate) {
        TaskParameterDef parameterDef = parameterService.get(parameterUpdate.getTaskParameterDefKey());
        if (parameterDef == null) {
            return ResponseDefine.FAILED_TASK_OPERATE_FAILED_NOT_EXISTS;
        }
        if (parameterUpdate.getName().length() < 1) {
            return ResponseDefine.FAILED_COMMON_NAME_ILLEGAL;
        }
        if (!parameterDef.getName().equals(parameterUpdate.getName()) && parameterService.contain(parameterDef.getTaskKey(), parameterUpdate.getName())) {
            // 修改了名称，且新的名称已经在当前任务中存在
            return ResponseDefine.FAILED_COMMON_NAME_ALREADY_EXISTS;
        }
        return parameterService.update(parameterUpdate)
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_TASK_OPERATE_FAILED_SERVER_ERROR;
    }

    @GetMapping("/parameter/list")
    public Response parameterList(@RequestParam("taskKey") String taskKey) {
        List<ParameterInfo> parameterInfos = new ArrayList<>();
        for (TaskParameterDef parameterDef : parameterService.list(taskKey)) {
            parameterInfos.add(new ParameterInfo(parameterDef));
        }
        return Response.success(parameterInfos);
    }

}
