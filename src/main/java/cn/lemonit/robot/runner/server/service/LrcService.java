package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.common.beans.entity.Lrc;
import cn.lemonit.robot.runner.common.beans.entity.LrcIpWhite;
import cn.lemonit.robot.runner.common.beans.entity.LrcSession;
import cn.lemonit.robot.runner.common.beans.lrc.*;
import cn.lemonit.robot.runner.common.utils.RsaUtil;
import cn.lemonit.robot.runner.common.utils.RuleUtil;
import cn.lemonit.robot.runner.common.utils.StringUtil;
import cn.lemonit.robot.runner.server.mapper.LrcIpWhiteMapper;
import cn.lemonit.robot.runner.server.mapper.LrcMapper;
import cn.lemonit.robot.runner.server.mapper.LrcSessionMapper;
import org.apache.tomcat.util.digester.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 连接器相关业务
 *
 * @author LemonIT.CN
 */
@Service
public class LrcService {

    private static Logger logger = LoggerFactory.getLogger(LrcService.class);
    private static final String LRC = "lrc";

    @Autowired
    private LrcMapper lrcMapper;
    @Autowired
    private LrcIpWhiteMapper lrcIpWhiteMapper;
    @Autowired
    private LrcSessionMapper lrcSessionMapper;

    @Transactional
    public boolean create(LrcCreate lrcCreate) {
        Lrc lrc = new Lrc();
        lrc.setLrcKey(RuleUtil.generatePrimaryKey());
        lrc.setIntro(lrcCreate.getIntro());
        lrc.setType(lrcCreate.getType());
        lrc.setCreateTime(System.currentTimeMillis() + "");
        KeyPair keyPair = RsaUtil.randomKeyPair();
        lrc.setPrivateKey(StringUtil.base64(keyPair.getPrivate().getEncoded()));
        lrc.setPublicKey(StringUtil.base64(keyPair.getPublic().getEncoded()));
        lrcMapper.insertLrc(lrc);
        for (String ip : lrcCreate.getIpWhiteList()) {
            LrcIpWhite ipWhite = new LrcIpWhite();
            ipWhite.setLrcIpWhiteKey(RuleUtil.generatePrimaryKey());
            ipWhite.setIpAddress(ip);
            ipWhite.setLrcKey(lrc.getLrcKey());
            lrcIpWhiteMapper.insertLrcIpWhite(ipWhite);
        }
        return true;
    }

    @Transactional
    public boolean delete(LrcDelete lrcDelete) {
        LrcIpWhite ipWhite = new LrcIpWhite();
        ipWhite.setLrcKey(lrcDelete.getLrcKey());
        lrcIpWhiteMapper.deleteLrcIpWhite(ipWhite);
        LrcSession lrcSession = new LrcSession();
        lrcSession.setLrcKey(lrcDelete.getLrcKey());
        lrcSessionMapper.deleteLrcSession(lrcSession);
        return lrcMapper.deleteLrcByKey(lrcDelete.getLrcKey()) > 0;
    }

    @Transactional
    public boolean update(LrcUpdate lrcUpdate) {
        Lrc lrc = new Lrc();
        lrc.setLrcKey(lrcUpdate.getLrcKey());
        lrc.setIntro(lrcUpdate.getIntro());
        LrcIpWhite ipWhite = new LrcIpWhite();
        ipWhite.setLrcKey(lrcUpdate.getLrcKey());
        lrcIpWhiteMapper.deleteLrcIpWhite(ipWhite);
        for (String ip : lrcUpdate.getIpWhiteList()) {
            LrcIpWhite ipWhiteNew = new LrcIpWhite();
            ipWhiteNew.setLrcKey(lrcUpdate.getLrcKey());
            ipWhiteNew.setLrcIpWhiteKey(RuleUtil.generatePrimaryKey());
            ipWhiteNew.setIpAddress(ip);
            lrcIpWhiteMapper.insertLrcIpWhite(ipWhiteNew);
        }
        return lrcMapper.updateLrc(lrc) > 0;
    }

    public Boolean active(LrcActive active, String clientIpAddr) {
        try {
            Lrc lrcExp = new Lrc();
            lrcExp.setLrcKey(active.getLrcKey());
            Lrc lrcIns = lrcMapper.selectLrc(lrcExp).get(0);
            if (!lrcIns.getType().equals(active.getClientType())) {
                // 客户端类型与LRC类型不匹配
                return false;
            }
            String lrcs = new String(
                    RsaUtil.decrypt(
                            RsaUtil.getRSAPrivateKey(lrcIns.getPrivateKey()),
                            Base64.getDecoder().decode(active.getLrcs())
                    ), "UTF-8"
            );
            // 验证通过，存储lrcs
            LrcSession session = new LrcSession();
            session.setLrcKey(lrcIns.getLrcKey());
            session.setLrcs(lrcs);
            session.setLrcSessionKey(RuleUtil.generatePrimaryKey());
            session.setIpAddress(clientIpAddr);
            session.setActiveTime(System.currentTimeMillis() + "");
            logger.debug("LRC active success! LRC-KEY = " + active.getLrcKey() + " - LRCS = " + lrcs);
            return lrcSessionMapper.insertLrcSession(session) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public LrcPublicInfo getPublic(String lrcKey) {
        Lrc lrcExp = new Lrc();
        lrcExp.setLrcKey(lrcKey);
        try {
            Lrc lrcIns = lrcMapper.selectLrc(lrcExp).get(0);
            LrcIpWhite ipWhiteExp = new LrcIpWhite();
            ipWhiteExp.setLrcKey(lrcKey);
            return new LrcPublicInfo(lrcIns, lrcIpWhiteMapper.selectLrcIpWhite(ipWhiteExp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer countWithLrcType(Integer type) {
        Lrc lrcExp = new Lrc();
        lrcExp.setType(type);
        return lrcMapper.countLrc(lrcExp);
    }

    public List<Lrc> list() {
        return lrcMapper.selectLrc(new Lrc());
    }

    public List<LrcPublicInfo> listPublic() {
        List<LrcPublicInfo> publicInfoList = new ArrayList<>();
        for (Lrc lrc : list()) {
            LrcIpWhite ipWhite = new LrcIpWhite();
            ipWhite.setLrcKey(lrc.getLrcKey());
            List<LrcIpWhite> ipWhiteList = lrcIpWhiteMapper.selectLrcIpWhite(ipWhite);
            publicInfoList.add(new LrcPublicInfo(lrc, ipWhiteList));
        }
        return publicInfoList;
    }

}
