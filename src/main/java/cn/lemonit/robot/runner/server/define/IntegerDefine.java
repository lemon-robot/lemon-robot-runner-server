package cn.lemonit.robot.runner.server.define;

/**
 * 整数预定义
 *
 * @author liuri
 */
public class IntegerDefine {

    /**
     * 布尔标示值
     * true
     */
    public static final Integer BOOL_VAL_TRUE = 1;
    /**
     * 布尔标示值
     * false
     */
    public static final Integer BOOL_VAL_FALSE = 0;

    public static Integer BOOL_VAL(boolean bool){
        return bool ? BOOL_VAL_TRUE : BOOL_VAL_FALSE;
    }

}
