package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.common.beans.plugin.PluginDescription;
import cn.lemonit.robot.runner.common.beans.plugin.PluginInstance;
import cn.lemonit.robot.runner.common.factory.PluginInstanceFactory;
import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.server.component.FileOperator;
import cn.lemonit.robot.runner.server.define.StringDefine;
import cn.lemonit.robot.runner.server.manager.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 插件相关业务
 *
 * @author liuri
 */
@Service
public class PluginService {

    @Autowired
    private ConfigManager configManager;
    @Autowired
    private FileOperator fileOperator;
    private static Logger logger = LoggerFactory.getLogger(PluginService.class);

    /**
     * 上传插件Jar包
     *
     * @param multipartFile 分段上传文件对象
     * @return 保存到本地文件夹
     */
    public PluginDescription uploadPluginJar(MultipartFile multipartFile) {
        try {
            File tempPluginFile = File.createTempFile(StringDefine.WORKSPACE, StringDefine.PLUGINS);
            logger.debug("Save put file to temp path: " + tempPluginFile.getAbsolutePath() + ", file original filename: " + multipartFile.getOriginalFilename());
            multipartFile.transferTo(tempPluginFile);
            PluginInstance instance = PluginInstanceFactory.generate(tempPluginFile.toURI().toURL());
            PluginDescription description = instance.toDescription();
            if (description != null) {
                String outputFilename = StringDefine.PLUGINS + File.separator + generatePluginStr(instance, "!");
                fileOperator.getLemoiOperator().put(tempPluginFile, outputFilename, FileOperator.getCommonLogProgressListener());
                logger.info("Check put file result : " + outputFilename + " - success: " + fileOperator.getLemoiOperator().contain(outputFilename));
                return description;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除已添加的插件
     *
     * @param pluginStr 插件描述字符串
     * @return 是否删除成功的布尔值
     */

    public boolean delete(String pluginStr) {
        File pluginFile = getPlugin(pluginStr);
        return pluginFile != null && pluginFile.delete();
    }

    /**
     * 获取插件信息列表
     *
     * @return 插件信息对象列表
     */
    public List<PluginDescription> list() {
        File pluginDir = getPluginDir();
        File[] pluginFiles = pluginDir.listFiles();
        List<PluginDescription> descriptions = new ArrayList<>();
        if (pluginFiles != null) {
            for (File pluginFile : pluginFiles) {
                String pluginStr = pluginFile.getName().substring(0, pluginFile.getName().length() - 4);
                if (pluginStr.contains("@") && pluginStr.contains("#")) {
                    try {
                        PluginDescription description = PluginInstanceFactory.generate(pluginFile.toURI().toURL()).toDescription();
                        description.setStore(pluginStr.split("@")[1]);
                        descriptions.add(description);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return descriptions;
    }

    /**
     * 拼接插件信息字符串
     *
     * @param packageName 插件名称
     * @param version     插件版本
     * @param store       插件所属商店
     * @return 插件信息字符串
     */
    public String getPluginStr(String packageName, String version, String store) {
        return packageName + "#" + version + "@" + store;
    }

    public File getPluginDir() {
        return FileUtil.getRuntimeDir(StringDefine.PLUGINS);
    }

    public String generatePluginStr(PluginInstance pluginInstance, String store) {
        if (pluginInstance == null) {
            return null;
        }
        return getPluginStr(pluginInstance.toDescription().getConfig().getPackageName(),
                pluginInstance.toDescription().getConfig().getVersion(),
                store);
    }

    public File getPlugin(String pluginStr) {
        File pluginDir = getPluginDir();
        if (pluginDir == null) {
            return null;
        }
        return FileUtil.getFile(pluginDir.getAbsolutePath() + File.separator + pluginStr + StringDefine.POINT_JAR);
    }

}
