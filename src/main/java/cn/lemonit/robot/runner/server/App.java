package cn.lemonit.robot.runner.server;

import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.server.define.StringDefine;
import cn.lemonit.robot.runner.server.filter.LrcRefreshFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用入口类
 *
 * @author liuri
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ServletComponentScan
public class App {

    public static void main(String[] args) {
        FileUtil.setRuntimePath(FileUtil.getProgramPath() + File.separator + StringDefine.WORKSPACE + File.separator);
        SpringApplication.run(App.class, args);
    }

}
