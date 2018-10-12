package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.general.Response;
import cn.lemonit.robot.runner.common.beans.lrc.*;
import cn.lemonit.robot.runner.server.define.ResponseDefine;
import cn.lemonit.robot.runner.server.manager.ConfigManager;
import cn.lemonit.robot.runner.server.service.LrcService;
import cn.lemonit.robot.runner.server.util.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 连接器控制器
 *
 * @author LemonIT.CN
 */
@RestController
@RequestMapping("/lrc")
public class LrcController {

    @Autowired
    private LrcService lrcService;
    @Autowired
    private ConfigManager configManager;

    @PutMapping("/create")
    public Response create(@RequestBody LrcCreate createRequest) {
        return lrcService.create(createRequest) != null
                ? Response.SUCCESS_NULL
                : ResponseDefine.FAILED_COMMON_SERVER_ERROR;
    }


    @DeleteMapping("/delete")
    public Response delete(@RequestBody LrcDelete deleteRequest) {
        LrcPublicInfo publicInfo = lrcService.getPublic(deleteRequest.getLrcKey());
        if (publicInfo != null && publicInfo.getType() == 0 && lrcService.countWithLrcType(publicInfo.getType()) == 1) {
            return ResponseDefine.FAILED_LRC_DELETE_FAILED_PLEASE_ADD;
        }
        return lrcService.delete(deleteRequest)
                ? Response.SUCCESS_NULL
                : ResponseDefine.FAILED_COMMON_SERVER_ERROR;
    }


    @PostMapping("/active")
    public Response active(
            @RequestBody LrcActive activeRequest,
            HttpServletRequest request) {
        LrcActiveResult activeResult = new LrcActiveResult();
        activeResult.setHeartbeatLength(configManager.getLemonRobotConfig().getSession().getHeartbeatLength());
        return lrcService.active(activeRequest, NetUtil.getIpAddr(request))
                ? Response.success(activeResult) : ResponseDefine.FAILED_LRC_ACTIVE_FAILED;
    }

    @PostMapping("/update")
    public Response update(@RequestBody LrcUpdate update) {
        LrcPublicInfo publicInfo = lrcService.getPublic(update.getLrcKey());
        if (publicInfo == null) {
            return ResponseDefine.FAILED_LRC_UPDATE_LRCT_NOT_EXISTS;
        }
        return lrcService.update(update)
                ? Response.SUCCESS_NULL
                : ResponseDefine.FAILED_COMMON_SERVER_ERROR;
    }

    @PostMapping("/heartbeat")
    public Response heartbeat() {
        return Response.SUCCESS_NULL;
    }

    @GetMapping("/list")
    public Response list() {
        return Response.success(lrcService.listPublic());
    }

}
