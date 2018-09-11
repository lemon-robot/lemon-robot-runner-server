package cn.lemonit.robot.runner.server.runner;

import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.server.manager.LrcManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * LRC连接服务初始化
 *
 * @author LemonIT.CN
 */
@Component
@Order(1)
public class LrcInitializeRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(LrcInitializeRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Starting LRC Initialize Runner...");
        logger.info("Program path = " + FileUtil.getProgramPath());
//        LrcManager.defaultManager().initLocalWorkspace();
    }
}
