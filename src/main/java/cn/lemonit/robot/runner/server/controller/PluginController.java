package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.general.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 插件请求的Controller
 *
 * @author liuri
 */
@RequestMapping("/plugin")
public class PluginController {

    @PostMapping("/upload")
    public Response uploadPlugin(@RequestParam MultipartFile pluginFile) {
        return Response.SUCCESS_NULL;
    }

}
