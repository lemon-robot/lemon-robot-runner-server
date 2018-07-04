package cn.lemonit.robot.runner.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * 日志工具类
 *
 * @author LemonIT.CN
 */
public class LogUtil {

    private static Logger logger;

    @Value("${cn.lemonit.robot.debug}")
    private static String configDebug;
    private static Boolean debugState = null;

    public static Boolean isDebugging() {
        if (debugState == null) {
            debugState = configDebug.trim().equals("true");
        }
        return debugState;
    }

    public static Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger("LR");
        }
        return logger;
    }

    public static void debug(String msg) {
        if (isDebugging()) {
            getLogger().debug(msg);
        }
    }

    public static void info(String msg) {
        getLogger().info(msg);
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        System.out.println(elements[elements.length - 1].getClassName());
    }

    public static void warn(String msg) {
        getLogger().warn(msg);
    }

    public static void error(String msg) {
        getLogger().error(msg);
    }


}
