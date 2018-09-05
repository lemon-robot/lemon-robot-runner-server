package cn.lemonit.robot.runner.server.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskMapper {

    Integer createTable();

}
