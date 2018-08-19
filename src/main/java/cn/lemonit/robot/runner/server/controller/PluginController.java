package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.general.Response;
import cn.lemonit.robot.runner.common.beans.plugin.PluginDelete;
import cn.lemonit.robot.runner.common.beans.plugin.PluginDescription;
import cn.lemonit.robot.runner.server.define.ResponseDefine;
import cn.lemonit.robot.runner.server.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @DeleteMapping("/delete")
    public Response delete(@RequestBody PluginDelete pluginDelete) {
        return Response.success(
                pluginService.delete(
                        pluginService.getPluginStr(
                                pluginDelete.getPackageName(),
                                pluginDelete.getVersion(),
                                pluginDelete.getStore()
                        )
                )
        );
    }

    @GetMapping("/list")
    public Response list() {
        return Response.success(pluginService.list());
    }

}
