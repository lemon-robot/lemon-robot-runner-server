package cn.lemonit.robot.runner.server.mapper;

import cn.lemonit.robot.runner.common.beans.entity.Plugin;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 插件mapper
 *
 * @author liuri
 */
@Mapper
public interface PluginMapper extends TableMapper {

    /**
     * 新增
     * @param lrPlugin
     * @return 成功数量
     */
    Integer insertPlugin(Plugin lrPlugin);

    /**
     * 删除
     * @param key NO
     * @return 成功数量
     */
    Integer deletePluginByPluginKey(@Param("key") String key);

    /**
     * 删除
     * @return 成功数量
     */
    Integer deletePlugin(Plugin lrPlugin);

    /**
     * 修改
     * @param lrPlugin
     * @return 成功数量
     */
    Integer updatePlugin(Plugin lrPlugin);

    /**
     * 查询
     * @param lrPlugin
     * @return 集合
     */
    List<Plugin> selectPlugin(Plugin lrPlugin);

    /**
     * 统计数量
     * @param lrPlugin
     * @return 数量
     */
    Integer countPlugin(Plugin lrPlugin);

}

