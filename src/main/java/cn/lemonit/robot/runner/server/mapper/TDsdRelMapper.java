package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.TDsdRel;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface TDsdRelMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrTDsdRel
     * @return 成功数量
     */
    Integer insertTDsdRel(TDsdRel lrTDsdRel);

    /**
     * 删除 
     * @param no NO
     * @return 成功数量
     */
    Integer deleteTDsdRelByNo(String no);

    /**
     * 修改 
     * @param lrTDsdRel
     * @return 成功数量
     */
    Integer updateTDsdRel(TDsdRel lrTDsdRel);

    /**
     * 查询 
     * @param lrTDsdRel
     * @return 集合
     */
    List<TDsdRel> selectTDsdRel(TDsdRel lrTDsdRel);

    /**
     * 统计数量 
     * @param lrTDsdRel
     * @return 数量
     */
    Integer countTDsdRel(TDsdRel lrTDsdRel);

}

