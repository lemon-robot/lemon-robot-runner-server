package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.Task;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface TaskMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrTask
     * @return 成功数量
     */
    Integer insertTask(Task lrTask);

    /**
     * 删除 
     * @param no NO
     * @return 成功数量
     */
    Integer deleteTaskByNo(String no);

    /**
     * 修改 
     * @param lrTask
     * @return 成功数量
     */
    Integer updateTask(Task lrTask);

    /**
     * 查询 
     * @param lrTask
     * @return 集合
     */
    List<Task> selectTask(Task lrTask);

    /**
     * 统计数量 
     * @param lrTask
     * @return 数量
     */
    Integer countTask(Task lrTask);

}

