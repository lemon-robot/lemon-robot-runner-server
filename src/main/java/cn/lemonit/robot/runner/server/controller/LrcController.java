package cn.lemonit.robot.runner.server.controller;

import cn.lemonit.robot.runner.common.beans.general.Response;
import cn.lemonit.robot.runner.common.beans.lrc.*;
import cn.lemonit.robot.runner.server.define.ResponseDefine;
import cn.lemonit.robot.runner.server.manager.LrcManager;
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

    @PutMapping("/create")
    public Response create(@RequestBody LrcCreate createRequest) {
        LrcInfo lrcInfo = LrcManager.defaultManager().randomLrcInfo(createRequest.getIntro(), createRequest.getType());
        lrcInfo.getIpWhiteList().addAll(createRequest.getIpWhiteList());
        return LrcManager.defaultManager().saveLrcInfo(lrcInfo, true)
                ? Response.success(lrcInfo.toPublicInfo())
                : ResponseDefine.FAILED_COMMON_SERVER_ERROR;
    }

    @DeleteMapping("/delete")
    public Response delete(@RequestBody LrcDelete deleteRequest) {
        LrcInfo lrcInfo = LrcManager.defaultManager().getLrcInfo(deleteRequest.getLrct());
        if (lrcInfo != null && lrcInfo.getType() == 0 && (LrcManager.defaultManager().countWithLrcType(lrcInfo.getType()) == 1)) {
            return ResponseDefine.FAILED_LRC_DELETE_FAILED_PLEASE_ADD;
        }
        return LrcManager.defaultManager().deleteLrc(deleteRequest.getLrct())
                ? Response.SUCCESS_NULL
                : ResponseDefine.FAILED_COMMON_SERVER_ERROR;
    }

    @PostMapping("/active")
    public Response active(
            @RequestBody LrcActive activeRequest) {
        return LrcManager.defaultManager().activeLrc(activeRequest)
                ? Response.SUCCESS_NULL : ResponseDefine.FAILED_LRC_ACTIVE_FAILED;
    }

    @PostMapping("/update")
    public Response update(@RequestBody LrcUpdate update) {
        LrcInfo lrcInfo = LrcManager.defaultManager().getLrcInfo(update.getLrct());
        if (lrcInfo == null) {
            return ResponseDefine.FAILED_LRC_UPDATE_LRCT_NOT_EXISTS;
        }
        lrcInfo.setIntro(update.getIntro());
        lrcInfo.getIpWhiteList().clear();
        lrcInfo.getIpWhiteList().addAll(update.getIpWhiteList());
        return LrcManager.defaultManager().saveLrcInfo(lrcInfo, false)
                ? Response.SUCCESS_NULL
                : ResponseDefine.FAILED_COMMON_SERVER_ERROR;
    }

    @GetMapping("/list")
    public Response list() {
        return Response.success(LrcManager.defaultManager().getAllLrcPublicInfo());
    }

}
