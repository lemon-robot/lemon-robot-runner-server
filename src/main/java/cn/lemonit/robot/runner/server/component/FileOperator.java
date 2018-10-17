package cn.lemonit.robot.runner.server.component;

import cn.lemonit.lemoi.exceptions.ConfigInvalidException;
import cn.lemonit.lemoi.interfaces.LemoiOperator;
import cn.lemonit.lemoi.listener.LemoiProgressListener;
import cn.lemonit.lemoi.models.LemoiS3ClientConfig;
import cn.lemonit.lemoi.operators.LemoiS3Operator;
import cn.lemonit.robot.runner.server.bean.LrDataSourceConfig;
import cn.lemonit.robot.runner.server.manager.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 工具操作类
 *
 * @author liuri
 */
@Component
public class FileOperator {

    private static Logger logger = LoggerFactory.getLogger(FileOperator.class);

    @Autowired
    private ConfigManager configManager;

    private LemoiOperator lemoiOperator;
    private static LemoiProgressListener commonLogProgressListener;

    public LemoiOperator getLemoiOperator() {
        if (lemoiOperator == null) {
            LemoiS3ClientConfig clientConfig = new LemoiS3ClientConfig();
            LrDataSourceConfig dataSourceConfig = configManager.getLemonRobotConfig().getDataSource();
            clientConfig.setAccessKey(dataSourceConfig.getOss().getAccessKey())
                    .setSecretKey(dataSourceConfig.getOss().getSecretKey())
                    .setEndpoint(dataSourceConfig.getOss().getEndpoint())
                    .setBucketName(dataSourceConfig.getOss().getBucket())
                    .setUseHttps(false)
                    .setUsePathStyle(true);
            try {
                lemoiOperator = LemoiS3Operator.createInstance(clientConfig);
            } catch (ConfigInvalidException e) {
                e.printStackTrace();
            }
        }
        return lemoiOperator;
    }

    public static LemoiProgressListener getCommonLogProgressListener() {
        if (commonLogProgressListener == null) {
            commonLogProgressListener = new LemoiProgressListener() {
                @Override
                public void onStart(String aimPath) {
                    logger.info("Start put file to: " + aimPath);
                }

                @Override
                public void onException(String aimPath, Exception e) {
                    logger.error("Put file error with: " + aimPath);
                    e.printStackTrace();
                }

                @Override
                public void onComplete(String aimPath, File file) {
                    logger.info("Put file complete to : " + aimPath);
                }

                @Override
                public void onProgress(String aimPath, double progress) {
                    logger.info("Putting file to : " + aimPath + " , progress : " + progress);
                }
            };
        }
        return commonLogProgressListener;
    }
}
