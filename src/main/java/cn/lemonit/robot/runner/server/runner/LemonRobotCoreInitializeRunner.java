package cn.lemonit.robot.runner.server.runner;

import cn.lemonit.robot.runner.core.LemonRobotRunner;
import cn.lemonit.robot.runner.core.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * LemonRobotCore初始化
 *
 * @author LemonIT.CN
 */
@Component
@Order(1)
public class LemonRobotCoreInitializeRunner implements ApplicationRunner {

    @Value("${cn.lemonit.robot.workspaceDirName}")
    private String workspaceDirName;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LemonRobotRunner.init(FileUtil.getProgramPath() + File.separator + workspaceDirName + File.separator);
    }

}
