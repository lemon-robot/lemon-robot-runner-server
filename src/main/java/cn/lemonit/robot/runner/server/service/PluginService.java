package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.common.beans.plugin.PluginDescription;
import cn.lemonit.robot.runner.common.beans.plugin.PluginInstance;
import cn.lemonit.robot.runner.common.factory.PluginInstanceFactory;
import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.server.define.StringDefine;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    public File getPluginDir() {
        return FileUtil.getRuntimeDir(StringDefine.PLUGINS);
    }

    public String generatePluginStr(PluginInstance pluginInstance, String source) {
        if (pluginInstance == null) {
            return null;
        }
        return pluginInstance.getPluginConfig().getPackageName() + ":" + pluginInstance.getPluginConfig().getVersion() + "@" + source;
    }

    public File getPlugin(String pluginStr) {
        File pluginDir = getPluginDir();
        if (pluginDir == null) {
            return null;
        }
        return FileUtil.getFile(pluginDir.getAbsolutePath() + File.separator + pluginStr + StringDefine.POINT_JAR);
    }

}
