package cn.lemonit.robot.runner.server.define;

import org.springframework.web.bind.annotation.PutMapping;

/**
 * 字符串宏定义
 *
 * @author liuri
 */
public class StringDefine {

    public static final String WORKSPACE = "workspace";
    public static final String TASK = "task";
    public static final String PARAMETER_BIN = "parameter_bin";
    public static final String PLUGINS = "plugins";
    public static final String POINT_JAR = ".jar";
    public static final String DEFAULT = "default";
    public static final String MAIN = "main";
    public static final String DEFAULT_INSTRUCTION_SET_SCRIPT = "console.log('hello lemon robot world!')";
    public static final String SQLITE_JDBC_PREFIX = "jdbc:sqlite:";
    public static final String SQLITE_DB_NAME = "lemon-robot.db";
    public static final String NUM_SIXTY = "60";
    public static final String UNIX_FILE_SEPARATOR = "/";

}
