package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.TTisRel;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
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
     * @param no NO
     * @return 成功数量
     */
    Integer deleteTTisRelByNo(String no);

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

