package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.common.beans.entity.TaskInstructionSet;
import cn.lemonit.robot.runner.common.beans.task.InstructionSetCreate;
import cn.lemonit.robot.runner.common.beans.task.InstructionSetDelete;
import cn.lemonit.robot.runner.common.beans.task.InstructionSetRename;
import cn.lemonit.robot.runner.common.beans.task.InstructionSetSave;
import cn.lemonit.robot.runner.common.utils.RuleUtil;
import cn.lemonit.robot.runner.server.define.StringDefine;
import cn.lemonit.robot.runner.server.mapper.TaskInstructionSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 指令集的业务相关代码
 *
 * @author liuri
 */
@Service
public class InstructionSetService {

    @Autowired
    private TaskInstructionSetMapper instructionSetMapper;

    public boolean create(InstructionSetCreate instructionSetCreate) {
        TaskInstructionSet instructionSet = new TaskInstructionSet();
        instructionSet.setTaskKey(instructionSetCreate.getTaskKey());
        instructionSet.setName(instructionSetCreate.getName());
        instructionSet.setTaskInstructionSetKey(RuleUtil.generatePrimaryKey());
        instructionSet.setScript(StringDefine.DEFAULT_INSTRUCTION_SET_SCRIPT);
        return instructionSetMapper.insertTaskInstructionSet(instructionSet) > 0;
    }

    public boolean delete(InstructionSetDelete instructionSetDelete) {
        return instructionSetMapper.deleteTaskInstructionSetByTaskInstructionSetKey(instructionSetDelete.getInstructionSetKey()) > 0;
    }

    public boolean rename(InstructionSetRename instructionSetRename) {
        TaskInstructionSet instructionSetExp = new TaskInstructionSet();
        instructionSetExp.setTaskInstructionSetKey(instructionSetRename.getInstructionSetKey());
        instructionSetExp.setName(instructionSetRename.getInstructionSetName());
        return instructionSetMapper.updateTaskInstructionSet(instructionSetExp) > 0;
    }

    public boolean saveScript(InstructionSetSave instructionSetSave) {
        TaskInstructionSet instructionSetExp = new TaskInstructionSet();
        instructionSetExp.setTaskInstructionSetKey(instructionSetSave.getInstructionSetKey());
        instructionSetExp.setScript(instructionSetSave.getScript());
        return instructionSetMapper.updateTaskInstructionSet(instructionSetExp) > 0;
    }

    public List<TaskInstructionSet> list(String taskKey) {
        TaskInstructionSet instructionSetExp = new TaskInstructionSet();
        instructionSetExp.setTaskKey(taskKey);
        return instructionSetMapper.selectTaskInstructionSet(instructionSetExp);
    }

    public String getScript(String instructionSetKey) {
        TaskInstructionSet instructionSetExp = new TaskInstructionSet();
        instructionSetExp.setTaskInstructionSetKey(instructionSetKey);
        List<TaskInstructionSet> instructionSetList = instructionSetMapper.selectTaskInstructionSet(instructionSetExp);
        if (instructionSetList.size() == 0) {
            return "";
        }
        return instructionSetList.get(0).getScript();
    }

    public boolean contain(String instructionSetKey) {
        TaskInstructionSet instructionSetExp = new TaskInstructionSet();
        instructionSetExp.setTaskInstructionSetKey(instructionSetKey);
        return instructionSetMapper.countTaskInstructionSet(instructionSetExp) > 0;
    }

}
