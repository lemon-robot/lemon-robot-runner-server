package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.general.Response;
import cn.lemonit.robot.runner.common.beans.plugin.PluginDescription;
import cn.lemonit.robot.runner.server.define.ResponseDefine;
import cn.lemonit.robot.runner.server.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 插件请求的Controller
 *
 * @author liuri
 */
@RequestMapping("/plugin")
@RestController
public class PluginController {

    @Autowired
    private PluginService pluginService;

    @PostMapping("/upload")
    public Response uploadPlugin(@RequestParam("file") MultipartFile file) {
        PluginDescription description = pluginService.uploadPluginJar(file);
        if (description == null) {
            return ResponseDefine.FAILED_PLUGIN_UPLOAD_FILE_ILLEGAL;
        }
        return Response.success(description);
    }

}
