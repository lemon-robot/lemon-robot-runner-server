package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.TTisRel;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface TTisRelMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrTTisRel
     * @return 成功数量
     */
    Integer insertTTisRel(TTisRel lrTTisRel);

    /**
     * 删除 
     * @param key NO
     * @return 成功数量
     */
    Integer deleteTTisRelByKey(@Param("key") String key);

    /**
     * 删除 
     * @return 成功数量
     */
    Integer deleteTTisRel(TTisRel lrTTisRel);

    /**
     * 修改 
     * @param lrTTisRel
     * @return 成功数量
     */
    Integer updateTTisRel(TTisRel lrTTisRel);

    /**
     * 查询 
     * @param lrTTisRel
     * @return 集合
     */
    List<TTisRel> selectTTisRel(TTisRel lrTTisRel);

    /**
     * 统计数量 
     * @param lrTTisRel
     * @return 数量
     */
    Integer countTTisRel(TTisRel lrTTisRel);

}

