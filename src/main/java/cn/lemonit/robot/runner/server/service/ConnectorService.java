package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.core.util.FileUtil;
import cn.lemonit.robot.runner.server.bean.LRCInfo;
import cn.lemonit.robot.runner.server.util.RsaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * 连接器相关业务
 *
 * @author LemonIT.CN
 */
@Service
public class ConnectorService {

    private static Logger logger = LoggerFactory.getLogger(ConnectorService.class);
    private static final String LRC = "lrc";

    /**
     * 全局的Connector池
     */
    private static Map<String, LRCInfo> globalConnectorPool = null;

    /**
     * 初始化Connector本地工作区
     *
     * @return 是否初始化成功的布尔值
     */
    public synchronized boolean initLocalWorkspace() {
        if (globalConnectorPool == null) {
            globalConnectorPool = readAllLRCInfoFromLocal();
        }
        if (globalConnectorPool.size() == 0) {
            // 工作区中没有LRC
            LRCInfo lrcInfo = randomLRCInfo("FIRST LRC");
            if (saveLRCInfo(lrcInfo)) {
                // 工作区中没有LRC，默认随机创建一个LRC并打印出来
                logger.info("There are no LRC objects in the workspace.");
                logger.info("Now the system automatically creates a LRC object for you!");
                logger.info("=============LRCT==============");
                logger.info(lrcInfo.getLrct());
                logger.info("===============================");
                logger.info("=============LRCK==============");
                logger.info(lrcInfo.getLrck());
                logger.info("===============================");
            }
        }
        return globalConnectorPool.size() > 0;
    }

    /**
     * 保存LRC信息
     * 同时向本地工作区和全局池中进行保存
     *
     * @param lrcInfo LRC信息对象
     * @return 是否保存成功的布尔值
     */
    public synchronized boolean saveLRCInfo(LRCInfo lrcInfo) {
        if (saveLRCInfoToLocal(lrcInfo)) {
            globalConnectorPool.put(lrcInfo.getLrct(), lrcInfo);
            return true;
        }
        return false;
    }

    public LRCInfo getLRCInfo(String lrct) {
        return globalConnectorPool.get(lrct);
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

    /**
     * 从本地工作区读取所有的LRC信息
     *
     * @return LRC数据池，<LRCT , LRCInfo>
     */
    private Map<String, LRCInfo> readAllLRCInfoFromLocal() {
        HashMap<String, LRCInfo> infoPool = new HashMap<>();
        File lrcDirFile = FileUtil.getRuntimeDir(LRC);
        try {
            for (File lrcFile : Objects.requireNonNull(lrcDirFile.listFiles())) {
                FileInputStream inputStream = new FileInputStream(lrcFile);
                ObjectInputStream lrcInputStream = new ObjectInputStream(inputStream);
                try {
                    LRCInfo lrcInfo = (LRCInfo) lrcInputStream.readObject();
                    if (lrcInfo != null) {
                        infoPool.put(lrcInfo.getLrct(), lrcInfo);
                        logger.info("Restore a LRC file from the workspace , LRCT = " + lrcInfo.getLrct());
                        continue;
                    }
                    logger.warn("Read a file that is not LRC type in the workspace.");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("A Exception ! Read a file that is not LRC type in the workspace.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infoPool;
    }

    /**
     * 保存LRC对象到本地工作区
     * 如果这个LRC的LRCT已经存在，那么会覆盖
     *
     * @param lrcInfo LRC信息对象
     * @return 是否保存成功的布尔值
     */
    private boolean saveLRCInfoToLocal(LRCInfo lrcInfo) {
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
