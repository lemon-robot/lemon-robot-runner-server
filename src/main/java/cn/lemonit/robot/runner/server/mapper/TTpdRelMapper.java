package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.TTpdRel;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface TTpdRelMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrTTpdRel
     * @return 成功数量
     */
    Integer insertTTpdRel(TTpdRel lrTTpdRel);

    /**
     * 删除 
     * @param key NO
     * @return 成功数量
     */
    Integer deleteTTpdRelByTaskKey(@Param("key") String key);

    /**
     * 删除 
     * @return 成功数量
     */
    Integer deleteTTpdRel(TTpdRel lrTTpdRel);

    /**
     * 修改 
     * @param lrTTpdRel
     * @return 成功数量
     */
    Integer updateTTpdRel(TTpdRel lrTTpdRel);

    /**
     * 查询 
     * @param lrTTpdRel
     * @return 集合
     */
    List<TTpdRel> selectTTpdRel(TTpdRel lrTTpdRel);

    /**
     * 统计数量 
     * @param lrTTpdRel
     * @return 数量
     */
    Integer countTTpdRel(TTpdRel lrTTpdRel);

}

