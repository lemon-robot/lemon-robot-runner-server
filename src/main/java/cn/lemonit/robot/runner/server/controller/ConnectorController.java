package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.server.bean.ReqConnectorRequest;
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

    @PostMapping("/request")
    public Response request(
            @RequestHeader("lrct") String lrct,
            @RequestBody ReqConnectorRequest connectorRequest) {
        String lrcs = connectorService.checkRequest(lrct, connectorRequest.getLrcs());
        if (lrcs != null) {
            connectorService.putLrcs(lrct, lrcs);
        }
        return lrcs != null ? Response.SUCCESS_NULL : Response.FAILED_VERIFICATION_FAILURE;
    }

//    public Response create(){
//
//    }

}
