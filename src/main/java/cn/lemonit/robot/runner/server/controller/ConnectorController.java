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
        connectorService.checkRequest(lrct, connectorRequest.getLrcs());
        return Response.SUCCESS_NULL;
    }

//    public Response create(){
//
//    }

}
