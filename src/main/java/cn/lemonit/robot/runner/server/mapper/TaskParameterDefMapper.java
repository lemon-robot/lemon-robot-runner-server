package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.TaskParameterDef;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface TaskParameterDefMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrTaskParameterDef
     * @return 成功数量
     */
    Integer insertTaskParameterDef(TaskParameterDef lrTaskParameterDef);

    /**
     * 删除 
     * @param key NO
     * @return 成功数量
     */
    Integer deleteTaskParameterDefByTaskParameterDefKey(@Param("key") String key);

    /**
     * 删除 
     * @return 成功数量
     */
    Integer deleteTaskParameterDef(TaskParameterDef lrTaskParameterDef);

    /**
     * 修改 
     * @param lrTaskParameterDef
     * @return 成功数量
     */
    Integer updateTaskParameterDef(TaskParameterDef lrTaskParameterDef);

    /**
     * 查询 
     * @param lrTaskParameterDef
     * @return 集合
     */
    List<TaskParameterDef> selectTaskParameterDef(TaskParameterDef lrTaskParameterDef);

    /**
     * 统计数量 
     * @param lrTaskParameterDef
     * @return 数量
     */
    Integer countTaskParameterDef(TaskParameterDef lrTaskParameterDef);

}

