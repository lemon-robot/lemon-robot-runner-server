package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.TaskPluginUsage;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface TaskPluginUsageMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrTaskPluginUsage
     * @return 成功数量
     */
    Integer insertTaskPluginUsage(TaskPluginUsage lrTaskPluginUsage);

    /**
     * 删除 
     * @param no NO
     * @return 成功数量
     */
    Integer deleteTaskPluginUsageByNo(String no);

    /**
     * 修改 
     * @param lrTaskPluginUsage
     * @return 成功数量
     */
    Integer updateTaskPluginUsage(TaskPluginUsage lrTaskPluginUsage);

    /**
     * 查询 
     * @param lrTaskPluginUsage
     * @return 集合
     */
    List<TaskPluginUsage> selectTaskPluginUsage(TaskPluginUsage lrTaskPluginUsage);

    /**
     * 统计数量 
     * @param lrTaskPluginUsage
     * @return 数量
     */
    Integer countTaskPluginUsage(TaskPluginUsage lrTaskPluginUsage);

}

