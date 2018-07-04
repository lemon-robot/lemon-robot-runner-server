package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.core.util.FileUtil;
import cn.lemonit.robot.runner.server.bean.LRCInfo;
import cn.lemonit.robot.runner.server.util.RsaUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 连接器相关业务
 *
 * @author LemonIT.CN
 */
@Service
public class ConnectorService {

    private static final String LRC = "lrc";

    /**
     * 初始化Connector本地工作区
     *
     * @return 是否初始化成功的布尔值
     */
    public boolean initLocalWorkspace() {
        return true;
    }

    /**
     * 随机生成LRC数据
     *
     * @return LRC信息对象
     */
    public LRCInfo randomLRCInfo(String intro) {
        LRCInfo lrcInfo = new LRCInfo();
        lrcInfo.setCreateTime(System.currentTimeMillis());
        lrcInfo.setIntro(intro);
        lrcInfo.setType(0);
        lrcInfo.setLrct(UUID.randomUUID().toString());
        lrcInfo.setKeyPair(RsaUtil.randomKeyPair());
        return lrcInfo;
    }

    public Map<String, LRCInfo> readAllLRCInfoFromLocal() {
        HashMap<String, LRCInfo> infoPool = new HashMap<>();
        return infoPool;
    }

    public boolean saveLRCInfoToLocal(LRCInfo lrcInfo) {
        File lrcDirFile = FileUtil.getRuntimeDir(LRC);
        File infoFile = new File(lrcDirFile.getAbsolutePath() + File.separator + lrcInfo.getLrct());
        if (infoFile.exists()) {
            if (!infoFile.delete()) {
                return false;
            }
        }
        FileOutputStream outputStream = null;
        ObjectOutputStream infoOutputStream = null;

        try {
            if (infoFile.createNewFile()) {
                outputStream = new FileOutputStream(infoFile);
                infoOutputStream = new ObjectOutputStream(outputStream);
                infoOutputStream.writeObject(lrcInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (infoOutputStream != null) {
                    infoOutputStream.close();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
