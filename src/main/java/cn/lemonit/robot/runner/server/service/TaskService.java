package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.common.beans.entity.Task;
import cn.lemonit.robot.runner.common.beans.entity.TaskInstructionSet;
import cn.lemonit.robot.runner.common.beans.entity.TaskParameterDef;
import cn.lemonit.robot.runner.common.beans.entity.TaskPluginUsage;
import cn.lemonit.robot.runner.common.beans.task.TaskPluginUsageUpdate;
import cn.lemonit.robot.runner.common.beans.task.TaskRename;
import cn.lemonit.robot.runner.common.utils.RuleUtil;
import cn.lemonit.robot.runner.server.define.StringDefine;
import cn.lemonit.robot.runner.server.mapper.TaskInstructionSetMapper;
import cn.lemonit.robot.runner.server.mapper.TaskMapper;
import cn.lemonit.robot.runner.server.mapper.TaskParameterDefMapper;
import cn.lemonit.robot.runner.server.mapper.TaskPluginUsageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务相关的业务
 *
 * @author liuri
 */
@Service
public class TaskService {

    private static Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskInstructionSetMapper taskInstructionSetMapper;
    @Autowired
    private TaskParameterDefMapper taskParameterDefMapper;
    @Autowired
    private TaskPluginUsageMapper taskPluginUsageMapper;

    /**
     * 创建一个本地任务
     *
     * @param name 任务名称
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean create(String name) {
        Long createTime = System.currentTimeMillis();
        // 创建任务
        Task task = new Task();
        task.setCreateTime(String.valueOf(createTime));
        task.setTaskName(name);
        task.setTaskKey(RuleUtil.generatePrimaryKey());
        taskMapper.insertTask(task);
        // 创建指令集
        TaskInstructionSet instructionSet = new TaskInstructionSet();
        instructionSet.setName(StringDefine.MAIN);
        instructionSet.setScript(StringDefine.DEFAULT_INSTRUCTION_SET_SCRIPT);
        instructionSet.setTaskInstructionSetKey(RuleUtil.generatePrimaryKey());
        instructionSet.setTaskKey(task.getTaskKey());
        taskInstructionSetMapper.insertTaskInstructionSet(instructionSet);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(String taskKey) {
        // 删除任务表中数据
        taskMapper.deleteTaskByTaskKey(taskKey);
        // 删除任务对应的所有指令集
        TaskInstructionSet instructionSetExp = new TaskInstructionSet();
        instructionSetExp.setTaskKey(taskKey);
        taskInstructionSetMapper.deleteTaskInstructionSet(instructionSetExp);
        // 删除任务对应的所有参数定义
        TaskParameterDef parameterDefExp = new TaskParameterDef();
        parameterDefExp.setTaskKey(taskKey);
        taskParameterDefMapper.deleteTaskParameterDef(parameterDefExp);
        // TODO 删除所有任务对应的数据执行结果
        // TODO 删除所有任务参数定义中的二进制数据
        // TODO 删除所有与任务有关的PLAN
        return true;
    }

    public boolean rename(TaskRename rename) {
        Task taskExp = new Task();
        taskExp.setTaskKey(rename.getTaskKey());
        taskExp.setTaskName(rename.getTaskName());
        return taskMapper.updateTask(taskExp) > 0;
    }

    public List<Task> list() {
        return taskMapper.selectTask(new Task());
    }

    public boolean contain(String taskKey) {
        Task taskExp = new Task();
        taskExp.setTaskKey(taskKey);
        return taskMapper.countTask(taskExp) > 0;
    }

    public boolean updatePluginUsageState(TaskPluginUsageUpdate taskPluginUsageUpdate) {
        TaskPluginUsage pluginUsageExp = new TaskPluginUsage();
        pluginUsageExp.setTaskKey(taskPluginUsageUpdate.getTaskKey());
        pluginUsageExp.setPluginStoreCode(taskPluginUsageUpdate.getPluginStoreCode());
        pluginUsageExp.setPluginPackageName(taskPluginUsageUpdate.getPluginPackageName());
        pluginUsageExp.setPluginVersion(taskPluginUsageUpdate.getPluginVersion());
        Boolean contain = taskPluginUsageMapper.countTaskPluginUsage(pluginUsageExp) > 0;
        if (contain && !taskPluginUsageUpdate.getEnabledState()) {
            // 存在，想删除
            return taskPluginUsageMapper.deleteTaskPluginUsage(pluginUsageExp) > 0;
        }
        if (!contain && taskPluginUsageUpdate.getEnabledState()) {
            // 不存在，想添加
            pluginUsageExp.setTaskPluginUsageKey(RuleUtil.generatePrimaryKey());
            return taskPluginUsageMapper.insertTaskPluginUsage(pluginUsageExp) > 0;
        }
        return true;
    }

    public List<TaskPluginUsage> listPluginUsage(String taskKey){
        TaskPluginUsage pluginUsageExp = new TaskPluginUsage();
        pluginUsageExp.setTaskKey(taskKey);
        return taskPluginUsageMapper.selectTaskPluginUsage(pluginUsageExp);
    }

}
