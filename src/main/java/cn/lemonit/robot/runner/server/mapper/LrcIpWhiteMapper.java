package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.LrcIpWhite;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface LrcIpWhiteMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrLrcIpWhite
     * @return 成功数量
     */
    Integer insertLrcIpWhite(LrcIpWhite lrLrcIpWhite);

    /**
     * 删除 
     * @param key NO
     * @return 成功数量
     */
    Integer deleteLrcIpWhiteByKey(@Param("key") String key);

    /**
     * 删除 
     * @return 成功数量
     */
    Integer deleteLrcIpWhite(LrcIpWhite lrLrcIpWhite);

    /**
     * 修改 
     * @param lrLrcIpWhite
     * @return 成功数量
     */
    Integer updateLrcIpWhite(LrcIpWhite lrLrcIpWhite);

    /**
     * 查询 
     * @param lrLrcIpWhite
     * @return 集合
     */
    List<LrcIpWhite> selectLrcIpWhite(LrcIpWhite lrLrcIpWhite);

    /**
     * 统计数量 
     * @param lrLrcIpWhite
     * @return 数量
     */
    Integer countLrcIpWhite(LrcIpWhite lrLrcIpWhite);

}

