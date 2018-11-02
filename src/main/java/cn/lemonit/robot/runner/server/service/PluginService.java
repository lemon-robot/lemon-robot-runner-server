package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.common.beans.entity.Plugin;
import cn.lemonit.robot.runner.common.beans.plugin.PluginDescription;
import cn.lemonit.robot.runner.common.beans.plugin.PluginInstance;
import cn.lemonit.robot.runner.common.factory.PluginInstanceFactory;
import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.common.utils.JsonUtil;
import cn.lemonit.robot.runner.common.utils.RuleUtil;
import cn.lemonit.robot.runner.server.component.FileOperator;
import cn.lemonit.robot.runner.server.define.StringDefine;
import cn.lemonit.robot.runner.server.manager.ConfigManager;
import cn.lemonit.robot.runner.server.mapper.PluginMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 插件相关业务
 *
 * @author liuri
 */
@Service
public class PluginService {

    @Autowired
    private PluginMapper pluginMapper;
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
            File tempPluginFile = FileUtil.getFile(configManager.getLemonRobotConfig().getTempPath() + File.separator + StringDefine.PLUGINS + File.separator + UUID.randomUUID().toString());
            logger.debug("Save put file to temp path: " + tempPluginFile.getAbsolutePath() + ", file original filename: " + multipartFile.getOriginalFilename());
            multipartFile.transferTo(tempPluginFile);
            PluginInstance instance = PluginInstanceFactory.generate(tempPluginFile.toURI().toURL());
            PluginDescription description = instance.toDescription();
            if (description != null) {
                String pluginStr = generatePluginStr(instance, description.getStore());
                String outputFilename = StringDefine.PLUGINS + File.separator + pluginStr;
                fileOperator.getLemoiOperator().put(tempPluginFile, outputFilename, FileOperator.getCommonLogProgressListener());
                logger.info("Check put file result : " + outputFilename + " - success: " + fileOperator.getLemoiOperator().contain(outputFilename));
                Plugin plugin = new Plugin();
                plugin.setInstallTime(System.currentTimeMillis() + "");
                plugin.setPackageName(description.getConfig().getPackageName());
                plugin.setPluginKey(RuleUtil.generatePrimaryKey());
                plugin.setPluginDetail(JsonUtil.gsonObj().toJson(description));
                plugin.setVersion(description.getConfig().getVersion());
                plugin.setStoreCode(description.getStore());
                plugin.setPluginName(description.getConfig().getName());
                plugin.setKeyword(description.getConfig().getKey());
                if (pluginMapper.insertPlugin(plugin) > 0) {
                    logger.info("Plugin data saved success:" + pluginStr);
                }
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
     * @param packageName 插件的包名
     * @param store       插件的所属市商店编码，如果是用户上传，那么code为英文半角叹号
     * @param version     插件版本号
     * @return 是否删除成功的布尔值
     */

    public boolean delete(String packageName, String version, String store) {
        Plugin plugin = new Plugin();
        plugin.setPackageName(packageName);
        plugin.setVersion(version);
        plugin.setStoreCode(store);
        if (pluginMapper.deletePlugin(plugin) > 0) {
            String pluginStr = getPluginStr(packageName, version, store);
            String outputFilename = StringDefine.PLUGINS + File.separator + pluginStr;
            fileOperator.getLemoiOperator().delete(outputFilename);
            logger.info("Delete plugin complete! packageName: " + packageName + ", version: " + version + ", store: " + store);
            return true;
        }
        logger.info("Delete plugin failed! packageName: " + packageName + ", version: " + version + ", store: " + store);
        return false;
    }

    /**
     * 获取插件信息列表
     *
     * @return 插件信息对象列表
     */
    public List<PluginDescription> list() {
        List<Plugin> plugins = pluginMapper.selectPlugin(new Plugin());
        List<PluginDescription> pluginDescriptions = new ArrayList<>();
        for (Plugin plugin : plugins) {
            pluginDescriptions.add(JsonUtil.gsonObj().fromJson(plugin.getPluginDetail(), PluginDescription.class));
        }
        return pluginDescriptions;
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

    public String generatePluginStr(PluginInstance pluginInstance, String store) {
        if (pluginInstance == null) {
            return null;
        }
        return getPluginStr(pluginInstance.toDescription().getConfig().getPackageName(),
                pluginInstance.toDescription().getConfig().getVersion(),
                store);
    }

}
