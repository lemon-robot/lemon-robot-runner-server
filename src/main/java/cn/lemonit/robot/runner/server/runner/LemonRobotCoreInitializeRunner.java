package cn.lemonit.robot.runner.server.runner;

import cn.lemonit.robot.runner.server.manager.ConfigManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * LemonRobotCore初始化
 *
 * @author LemonIT.CN
 */
@Component
@Order(99999)
public class LemonRobotCoreInitializeRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        FileUtil.setRuntimePath(FileUtil.getProgramPath() + File.separator + workspaceDirName + File.separator);
        System.out.println("************* SYSTEM STATE **************");
        System.out.println("RUN MODE: " + ConfigManager.getRunMode());
        System.out.println("DATA SOURCE DB TYPE: " + ConfigManager.getDataSourceDbType());
        System.out.println("PLUGIN STORAGE: " + ConfigManager.getDataSourcePluginStoragePath());
        System.out.println("*****************************************");
    }

}
