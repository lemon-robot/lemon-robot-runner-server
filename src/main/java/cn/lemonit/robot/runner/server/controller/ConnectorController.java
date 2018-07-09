package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.server.bean.ReqConnectorActiveRequest;
import cn.lemonit.robot.runner.server.bean.Response;
import cn.lemonit.robot.runner.server.service.ConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 连接器控制器
 *
 * @author LemonIT.CN
 */
@RestController
@RequestMapping("/connector")
public class ConnectorController {

    @Autowired
    private ConnectorService connectorService;

    @PostMapping("/active")
    public Response active(
            @RequestBody ReqConnectorActiveRequest connectorRequest) {
        return connectorService.activeConnector(connectorRequest)
                ? Response.SUCCESS_NULL : Response.FAILED_VERIFICATION_FAILURE;
    }

}
