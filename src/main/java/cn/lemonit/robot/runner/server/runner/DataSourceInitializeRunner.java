package cn.lemonit.robot.runner.server.runner;

import cn.lemonit.robot.runner.server.mapper.LrcMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 数据源相关初始化
 *
 * @author LemonIT.CN
 */
@Component
public class DataSourceInitializeRunner implements ApplicationRunner {

    @Autowired
    private LrcMapper lrcMapper;

    private Logger logger = LoggerFactory.getLogger(DataSourceInitializeRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Starting DataSource Initialize Runner...");
        lrcMapper.createTable();
    }
}
