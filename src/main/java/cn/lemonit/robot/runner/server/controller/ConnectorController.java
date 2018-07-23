package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.general.Response;
import cn.lemonit.robot.runner.common.beans.lrc.LrcActiveRequest;
import cn.lemonit.robot.runner.server.manager.LrcManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 连接器控制器
 *
 * @author LemonIT.CN
 */
@RestController
@RequestMapping("/connector")
public class ConnectorController {

    @PostMapping("/active")
    public Response active(
            @RequestBody LrcActiveRequest connectorRequest) {
        return LrcManager.defaultManager().activeConnector(connectorRequest)
                ? Response.SUCCESS_NULL : Response.FAILED_VERIFICATION_FAILURE;
    }

}
