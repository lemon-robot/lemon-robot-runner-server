package cn.lemonit.robot.runner.server.filter;

import cn.lemonit.robot.runner.common.utils.JsonUtil;
import cn.lemonit.robot.runner.server.define.ResponseDefine;
import cn.lemonit.robot.runner.server.service.LrcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Lrc刷新过滤器
 * 刷新LRC使用时间的过滤器
 *
 * @author lemonit_cn
 */
@WebFilter(urlPatterns = "/*", filterName = "lrcRefreshFilter")
public class LrcRefreshFilter implements Filter {

    @Autowired
    private LrcService lrcService;
    private static Logger logger = LoggerFactory.getLogger(LrcRefreshFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String lrcs = req.getHeader("lrcs");
        if (lrcService.heartbeat(lrcs) == 0) {
            writeResp(servletRequest, servletResponse, ResponseDefine.FAILED_LRC_SESSION_EXPIRED);
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }

    private void writeResp(ServletRequest servletRequest, ServletResponse servletResponse, Object outObj) {
        PrintWriter writer = null;
        OutputStreamWriter osw = null;
        try {
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("application/json; charset=utf-8");
            osw = new OutputStreamWriter(servletResponse.getOutputStream(),
                    "UTF-8");
            writer = new PrintWriter(osw, true);
            String jsonStr = JsonUtil.gsonObj().toJson(outObj);
            writer.write(jsonStr);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
                if (null != osw) {
                    osw.close();
                }
            } catch (Exception e) {
            }
        }
    }

}
