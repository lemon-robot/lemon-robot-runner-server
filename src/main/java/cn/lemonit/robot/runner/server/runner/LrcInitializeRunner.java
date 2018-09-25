package cn.lemonit.robot.runner.server.runner;

import cn.lemonit.robot.runner.common.beans.entity.Lrc;
import cn.lemonit.robot.runner.common.beans.lrc.LrcCreate;
import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.server.define.StringDefine;
import cn.lemonit.robot.runner.server.service.LrcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Order(3)
public class LrcInitializeRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(LrcInitializeRunner.class);

    @Autowired
    private LrcService lrcService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Starting LRC Initialize Runner...");
        if (lrcService.countWithLrcType(0) == 0) {
            LrcCreate lrcCreate = new LrcCreate();
            lrcCreate.setType(0);
            lrcCreate.setIntro(StringDefine.DEFAULT);
            Lrc lrc = lrcService.create(lrcCreate);
            logger.info("No client LRC information is found in the system, and it is automatically created.\n" +
                    "LRC KEY: " + lrc.getLrcKey() + " \n" +
                    "LRCK: " + lrc.getPublicKey());
        }
//        LrcManager.defaultManager().initLocalWorkspace();
    }
}
