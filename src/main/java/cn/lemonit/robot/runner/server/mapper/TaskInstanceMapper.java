package cn.lemonit.robot.runner.server.mapper;

import java.util.List;

import cn.lemonit.robot.runner.common.beans.entity.TaskInstance;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper
 */
@Mapper
public interface TaskInstanceMapper extends TableMapper {

    /**
     * 新增
     *
     * @param lrTaskInstance
     * @return 成功数量
     */
    Integer insertTaskInstance(TaskInstance lrTaskInstance);

    /**
     * 删除
     *
     * @param key NO
     * @return 成功数量
     */
    Integer deleteTaskInstanceByTaskInstanceKey(@Param("key") String key);

    /**
     * 删除
     *
     * @return 成功数量
     */
    Integer deleteTaskInstance(TaskInstance lrTaskInstance);

    /**
     * 修改
     *
     * @param lrTaskInstance
     * @return 成功数量
     */
    Integer updateTaskInstance(TaskInstance lrTaskInstance);

    /**
     * 查询
     *
     * @param lrTaskInstance
     * @return 集合
     */
    List<TaskInstance> selectTaskInstance(TaskInstance lrTaskInstance);

    /**
     * 统计数量
     *
     * @param lrTaskInstance
     * @return 数量
     */
    Integer countTaskInstance(TaskInstance lrTaskInstance);

}

