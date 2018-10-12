package cn.lemonit.robot.runner.server.runner;

import cn.lemonit.robot.runner.server.manager.ConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
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
@Order(1)
public class LemonRobotCoreInitializeRunner implements ApplicationRunner {

    @Autowired
    private ConfigManager configManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 检查用户配置项是否合法
        if (configManager.getLemonRobotConfig().getMode().equals("cluster") && !configManager.getLemonRobotConfig().getDataSource().getDbType().equals("mysql")){
            throw new Exception("In cluster mode, db type must be mysql!");
        }
        System.out.println("************* SYSTEM STATE **************");
        System.out.println("RUN MODE: " + configManager.getLemonRobotConfig().getMode());
        System.out.println("DATA SOURCE DB TYPE: " + configManager.getLemonRobotConfig().getDataSource().getDbType());
        System.out.println("FILE SYSTEM MODE: " + configManager.getLemonRobotConfig().getDataSource().getFileMode());
        System.out.println("*****************************************");
    }

}
