package cn.lemonit.robot.runner.server.mapper;

import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TDsdRelMapper extends TableMapper {

    Integer createTable();

}
