package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.DsdDspdRel;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface DsdDspdRelMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrDsdDspdRel
     * @return 成功数量
     */
    Integer insertDsdDspdRel(DsdDspdRel lrDsdDspdRel);

    /**
     * 删除 
     * @param key NO
     * @return 成功数量
     */
    Integer deleteDsdDspdRelByDataSetDefKey(@Param("key") String key);

    /**
     * 删除 
     * @return 成功数量
     */
    Integer deleteDsdDspdRel(DsdDspdRel lrDsdDspdRel);

    /**
     * 修改 
     * @param lrDsdDspdRel
     * @return 成功数量
     */
    Integer updateDsdDspdRel(DsdDspdRel lrDsdDspdRel);

    /**
     * 查询 
     * @param lrDsdDspdRel
     * @return 集合
     */
    List<DsdDspdRel> selectDsdDspdRel(DsdDspdRel lrDsdDspdRel);

    /**
     * 统计数量 
     * @param lrDsdDspdRel
     * @return 数量
     */
    Integer countDsdDspdRel(DsdDspdRel lrDsdDspdRel);

}

