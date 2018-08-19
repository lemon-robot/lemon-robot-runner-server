package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.common.beans.plugin.PluginDescription;
import cn.lemonit.robot.runner.common.beans.plugin.PluginInstance;
import cn.lemonit.robot.runner.common.factory.PluginInstanceFactory;
import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.server.define.StringDefine;
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

    /**
     * 上传插件Jar包
     *
     * @param multipartFile 分段上传文件对象
     * @return 保存到本地文件夹
     */
    public PluginDescription uploadPluginJar(MultipartFile multipartFile) {
        File pluginDir = getPluginDir();
        if (pluginDir != null && pluginDir.isDirectory()) {
            // 插件存储文件夹存在
            String fileId = UUID.randomUUID().toString();
            File pluginFileOld = FileUtil.getFile(pluginDir.getAbsolutePath() + File.separator + fileId);
            try {
                multipartFile.transferTo(pluginFileOld);
                PluginInstance instance = PluginInstanceFactory.generate(pluginFileOld.toURI().toURL());
                PluginDescription description = instance.toDescription();
                if (description != null) {
                    String pluginStr = generatePluginStr(instance, "!");
                    File pluginFile = getPlugin(pluginStr);
                    pluginFile.delete();
                    if (!pluginFileOld.renameTo(pluginFile)) {
                        return null;
                    }
                    return description;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
