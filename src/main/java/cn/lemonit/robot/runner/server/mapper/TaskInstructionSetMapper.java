package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.TaskInstructionSet;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface TaskInstructionSetMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrTaskInstructionSet
     * @return 成功数量
     */
    Integer insertTaskInstructionSet(TaskInstructionSet lrTaskInstructionSet);

    /**
     * 删除 
     * @param no NO
     * @return 成功数量
     */
    Integer deleteTaskInstructionSetByNo(String no);

    /**
     * 修改 
     * @param lrTaskInstructionSet
     * @return 成功数量
     */
    Integer updateTaskInstructionSet(TaskInstructionSet lrTaskInstructionSet);

    /**
     * 查询 
     * @param lrTaskInstructionSet
     * @return 集合
     */
    List<TaskInstructionSet> selectTaskInstructionSet(TaskInstructionSet lrTaskInstructionSet);

    /**
     * 统计数量 
     * @param lrTaskInstructionSet
     * @return 数量
     */
    Integer countTaskInstructionSet(TaskInstructionSet lrTaskInstructionSet);

}

