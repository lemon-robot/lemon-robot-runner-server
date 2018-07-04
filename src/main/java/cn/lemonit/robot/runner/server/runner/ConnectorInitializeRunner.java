package cn.lemonit.robot.runner.server.runner;

import cn.lemonit.robot.runner.core.util.FileUtil;
import cn.lemonit.robot.runner.server.service.ConnectorService;
import cn.lemonit.robot.runner.server.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

/**
 * Connector连接服务初始化
 *
 * @author LemonIT.CN
 */
@Component
@Order(2)
public class ConnectorInitializeRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(ConnectorInitializeRunner.class);

    @Autowired
    private ConnectorService connectorService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Starting Connector Initialize Runner...");
        logger.info("Program path = " + FileUtil.getProgramPath());
        connectorService.saveLRCInfoToLocal(connectorService.randomLRCInfo("hello"));

    }
}
