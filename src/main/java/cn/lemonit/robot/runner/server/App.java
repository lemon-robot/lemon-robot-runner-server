package cn.lemonit.robot.runner.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 应用入口类
 *
 * @author liuri
 */
@ComponentScan({"cn.lemonit.robot.runner.server.manager", "cn.lemonit.robot.runner.server"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
