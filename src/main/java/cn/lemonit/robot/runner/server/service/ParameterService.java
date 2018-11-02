package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.common.beans.entity.TaskParameterDef;
import cn.lemonit.robot.runner.common.beans.task.ParameterCreate;
import cn.lemonit.robot.runner.common.beans.task.ParameterDelete;
import cn.lemonit.robot.runner.common.beans.task.ParameterUpdate;
import cn.lemonit.robot.runner.common.utils.RuleUtil;
import cn.lemonit.robot.runner.server.define.IntegerDefine;
import cn.lemonit.robot.runner.server.mapper.TaskParameterDefMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务参数相关业务
 *
 * @author liuri
 */
@Service
public class ParameterService {

    @Autowired
    private TaskParameterDefMapper parameterMapper;
    private Logger logger = LoggerFactory.getLogger(ParameterService.class);

    public boolean create(ParameterCreate parameterCreate) {
        TaskParameterDef parameterDef = new TaskParameterDef();
        parameterDef.setTaskKey(parameterCreate.getTaskKey());
        parameterDef.setName(parameterDef.getName());
        parameterDef.setIsBinary(IntegerDefine.BOOL_VAL(parameterCreate.isBinary()));
        parameterDef.setIsRequired(IntegerDefine.BOOL_VAL(parameterCreate.isRequired()));
        parameterDef.setRegex(parameterCreate.getRegex());
        parameterDef.setTaskParameterDefKey(RuleUtil.generatePrimaryKey());
        return parameterMapper.insertTaskParameterDef(parameterDef) > 0;
    }

    public boolean delete(ParameterDelete parameterDelete) {
        logger.info("User request delete parameter, parameter key: " + parameterDelete.getParameterKey());
        return parameterMapper.deleteTaskParameterDefByTaskParameterDefKey(parameterDelete.getParameterKey()) > 0;
    }

    public boolean update(ParameterUpdate parameterUpdate) {
        TaskParameterDef parameterDef = new TaskParameterDef();
        parameterDef.setName(parameterDef.getName());
        parameterDef.setIsBinary(IntegerDefine.BOOL_VAL(parameterUpdate.isBinary()));
        parameterDef.setIsRequired(IntegerDefine.BOOL_VAL(parameterUpdate.isRequired()));
        parameterDef.setRegex(parameterUpdate.getRegex());
        parameterDef.setTaskParameterDefKey(parameterUpdate.getParameterKey());
        return parameterMapper.updateTaskParameterDef(parameterDef) > 0;
    }

    public TaskParameterDef get(String parameterKey) {
        TaskParameterDef parameterDefExp = new TaskParameterDef();
        parameterDefExp.setTaskParameterDefKey(parameterKey);
        List<TaskParameterDef> results = parameterMapper.selectTaskParameterDef(parameterDefExp);
        return results.size() > 0 ? results.get(0) : null;
    }

    public List<TaskParameterDef> list(String taskKey) {
        TaskParameterDef parameterDefExp = new TaskParameterDef();
        parameterDefExp.setTaskKey(taskKey);
        return parameterMapper.selectTaskParameterDef(parameterDefExp);
    }

    public boolean contain(String taskKey, String parameterName) {
        TaskParameterDef parameterDefExp = new TaskParameterDef();
        parameterDefExp.setTaskKey(taskKey);
        parameterDefExp.setName(parameterName);
        return parameterMapper.countTaskParameterDef(parameterDefExp) > 0;
    }

}
