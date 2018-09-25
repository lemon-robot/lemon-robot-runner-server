package cn.lemonit.robot.runner.server.runner;

import cn.lemonit.robot.runner.server.interfaces.TableMapper;
import cn.lemonit.robot.runner.server.mapper.LrcMapper;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 数据源相关初始化
 *
 * @author LemonIT.CN
 */
@Component
@Order(2)
public class DataSourceInitializeRunner implements ApplicationRunner {

    @Autowired
    private LrcMapper lrcMapper;
    @Autowired
    private ApplicationContext applicationContext;

    private Logger logger = LoggerFactory.getLogger(DataSourceInitializeRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Starting DataSource Initialize Runner...");
        lrcMapper.createTable();
        Map<String, Object> tableMappers = applicationContext.getBeansWithAnnotation(Mapper.class);
        for (Object tableMapper : tableMappers.values()) {
            if (tableMapper instanceof TableMapper) {
                ((TableMapper) tableMapper).createTable();
            }
        }
    }
}
