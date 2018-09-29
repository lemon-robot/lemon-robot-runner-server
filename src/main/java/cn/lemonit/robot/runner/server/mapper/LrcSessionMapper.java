package cn.lemonit.robot.runner.server.mapper;

import java.util.List;

import cn.lemonit.robot.runner.common.beans.entity.LrcSession;
import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper
 */
@Mapper
public interface LrcSessionMapper extends TableMapper {

    /**
     * 清除已过期的会话
     *
     * @param expiredTime 要清除会话的过期时间点
     * @return 清除的数量
     */
    Integer clearExpiredSession(@Param("expiredTime") Long expiredTime);

    /**
     * 新增
     *
     * @param lrLrcSession
     * @return 成功数量
     */
    Integer insertLrcSession(LrcSession lrLrcSession);

    /**
     * 删除
     *
     * @param key NO
     * @return 成功数量
     */
    Integer deleteLrcSessionByLrcSessionKey(@Param("key") String key);

    /**
     * 删除
     *
     * @return 成功数量
     */
    Integer deleteLrcSession(LrcSession lrLrcSession);

    /**
     * 修改
     *
     * @param lrLrcSession
     * @return 成功数量
     */
    Integer updateLrcSession(LrcSession lrLrcSession);

    /**
     * 查询
     *
     * @param lrLrcSession
     * @return 集合
     */
    List<LrcSession> selectLrcSession(LrcSession lrLrcSession);

    /**
     * 统计数量
     *
     * @param lrLrcSession
     * @return 数量
     */
    Integer countLrcSession(LrcSession lrLrcSession);

}

