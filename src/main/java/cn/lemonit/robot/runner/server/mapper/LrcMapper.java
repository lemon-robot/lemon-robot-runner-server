package cn.lemonit.robot.runner.server.mapper;

import java.util.List;
import cn.lemonit.robot.runner.common.beans.entity.Lrc;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
 */
@Mapper
public interface LrcMapper  extends TableMapper {

    /**
     * 新增 
     * @param lrLrc
     * @return 成功数量
     */
    Integer insertLrc(Lrc lrLrc);

    /**
     * 删除 
     * @param no NO
     * @return 成功数量
     */
    Integer deleteLrcByNo(String no);

    /**
     * 修改 
     * @param lrLrc
     * @return 成功数量
     */
    Integer updateLrc(Lrc lrLrc);

    /**
     * 查询 
     * @param lrLrc
     * @return 集合
     */
    List<Lrc> selectLrc(Lrc lrLrc);

    /**
     * 统计数量 
     * @param lrLrc
     * @return 数量
     */
    Integer countLrc(Lrc lrLrc);

}

