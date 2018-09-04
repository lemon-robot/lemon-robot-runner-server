package cn.lemonit.robot.runner.server.manager;

import cn.lemonit.robot.runner.common.beans.lrc.LrcActive;
import cn.lemonit.robot.runner.common.beans.lrc.LrcInfo;
import cn.lemonit.robot.runner.common.beans.lrc.LrcPublicInfo;
import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.common.utils.RsaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.KeyPair;
import java.util.*;

/**
 * 连接器相关业务
 *
 * @author LemonIT.CN
 */
public class LrcManager {

    private static Logger logger = LoggerFactory.getLogger(LrcManager.class);
    private static final String LRC = "lrc";

    private static LrcManager defaultManager;

    public static LrcManager defaultManager() {
        if (defaultManager == null) {
            defaultManager = new LrcManager();
        }
        return defaultManager;
    }

    /**
     * 全局的Lrc池
     */
    private Map<String, LrcInfo> globalLrcPool = null;
    /**
     * 全局LRCS存储池
     * <LRCT , LRCS>
     */
    private Map<String, String> lrcsPool = new HashMap<>();

    public Map<String, LrcInfo> getGlobalLrcPool() {
        if (globalLrcPool == null) {
            globalLrcPool = new HashMap<>();
        }
        return globalLrcPool;
    }

    public Map<String, String> getLrcsPool() {
        if (lrcsPool == null) {
            lrcsPool = new HashMap<>();
        }
        return lrcsPool;
    }

    /**
     * 初始化Lrc本地工作区
     *
     * @return 是否初始化成功的布尔值
     */
    public synchronized boolean initLocalWorkspace() {
        globalLrcPool = readAllLrcInfoFromLocal();
        if (getGlobalLrcPool().size() == 0) {
            // 工作区中没有LRC
            LrcInfo lrcInfo = randomLrcInfo("FIRST LRC", 0);
            if (saveLrcInfo(lrcInfo, true)) {
                // 工作区中没有LRC，默认随机创建一个LRC并打印出来
                logger.info("There are no LRC objects in the workspace.");
                logger.info("Now the system automatically creates a LRC object for you!");
            }
        }
        return getGlobalLrcPool().size() > 0;
    }

    /**
     * 保存LRC信息
     * 同时向本地工作区和全局池中进行保存
     *
     * @param lrcInfo LRC信息对象
     * @return 是否保存成功的布尔值
     */
    public synchronized boolean saveLrcInfo(LrcInfo lrcInfo, boolean isNew) {
        if (saveLrcInfoToLocal(lrcInfo)) {
            getGlobalLrcPool().put(lrcInfo.getLrct(), lrcInfo);
            if (isNew) {
                System.out.println("=====CREATE A NEW LRC INFO=====");
                System.out.println("=============LRCT==============");
                System.out.println(lrcInfo.getLrct());
                System.out.println("===============================");
                System.out.println("=============LRCK==============");
                System.out.println(lrcInfo.getLrck());
                System.out.println("===============================");
            }
            return true;
        }
        return false;
    }

    /**
     * 获取LRC连接信息
     *
     * @param lrct 要获取的LRC信息的LRCT
     * @return LRC信息对象
     */
    public LrcInfo getLrcInfo(String lrct) {
        return getGlobalLrcPool().get(lrct);
    }

    /**
     * 随机生成LRC数据
     *
     * @return LRC信息对象
     */
    public LrcInfo randomLrcInfo(String intro, Integer type) {
        LrcInfo lrcInfo = new LrcInfo();
        lrcInfo.setCreateTime(System.currentTimeMillis());
        lrcInfo.setIntro(intro);
        // 防错判断
        if (type < 0 || type > 1) {
            type = 0;
        }
        lrcInfo.setType(type);
        lrcInfo.setLrct(UUID.randomUUID().toString());
        lrcInfo.setKeyPair(RsaUtil.randomKeyPair());
        return lrcInfo;
    }

    /**
     * 从本地工作区读取所有的LRC信息
     *
     * @return LRC数据池，<LRCT , LrcInfo>
     */
    private Map<String, LrcInfo> readAllLrcInfoFromLocal() {
        HashMap<String, LrcInfo> infoPool = new HashMap<>(0);
        File lrcDirFile = FileUtil.getRuntimeDir(LRC);
        try {
            for (File lrcFile : Objects.requireNonNull(lrcDirFile.listFiles())) {
                FileInputStream inputStream = new FileInputStream(lrcFile);
                ObjectInputStream lrcInputStream = new ObjectInputStream(inputStream);
                try {
                    LrcInfo lrcInfo = (LrcInfo) lrcInputStream.readObject();
                    if (lrcInfo != null) {
                        infoPool.put(lrcInfo.getLrct(), lrcInfo);
                        logger.info("Restore a LRC file from the workspace , LRCT = " + lrcInfo.getLrct());
                        continue;
                    }
                    logger.warn("Read a file that is not LRC type in the workspace.");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("A Exception ! Read a file that is not LRC type in the workspace.");
                } finally {
                    inputStream.close();
                    lrcInputStream.close();
                }
            }
            logger.info(infoPool.size() + " LRC objects are read from the workspace");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infoPool;
    }

    /**
     * 获取当前硬盘中所有的所有Lrc公开信息
     *
     * @return Lrc公开信息对象
     */
    public List<LrcPublicInfo> getAllLrcPublicInfo() {
        List<LrcPublicInfo> publicInfoList = new ArrayList<>();
        for (LrcInfo lrcInfo : readAllLrcInfoFromLocal().values()) {
            publicInfoList.add(lrcInfo.toPublicInfo());
        }
        return publicInfoList;
    }

    /**
     * 保存LRC对象到本地工作区
     * 如果这个LRC的LRCT已经存在，那么会覆盖
     *
     * @param lrcInfo LRC信息对象
     * @return 是否保存成功的布尔值
     */
    private boolean saveLrcInfoToLocal(LrcInfo lrcInfo) {
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
        }
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
        return false;
    }

    /**
     * 从本地硬盘删除LRC信息
     *
     * @param lrct 要删除的LRC信息的LRCT
     * @return 删除是否成功的布尔值
     */
    private boolean removeLrcInfoFromLocal(String lrct) {
        File lrcDirFile = FileUtil.getRuntimeDir(LRC);
        File infoFile = new File(lrcDirFile.getAbsolutePath() + File.separator + lrct);
        if (infoFile.exists()) {
            return infoFile.delete();
        }
        return true;
    }

    /**
     * 保存LRCT与LRCS的关系到LRCS池中
     *
     * @param lrct Lemon Robot Connector Tag
     * @param lrcs Lemon Robot Connector Secret
     * @return 如果LRCS池中已经存在这个LRCT的数据，那么返回true，否则返回false
     */
    public boolean putLrcs(String lrct, String lrcs) {
        boolean result = getLrcsPool().containsKey(lrct);
        getLrcsPool().put(lrct, lrcs);
        return result;
    }

    /**
     * 从LRCS池中取出LRCT对应的LRCS
     *
     * @param lrct Lemon Robot Connector Tag
     * @return Lemon Robot Connector Secret，如果池中没有对应的LRCT，那么返回null
     */
    public String getLrcs(String lrct) {
        return getLrcsPool().get(lrct);
    }

    /**
     * 断开LRC连接
     *
     * @param lrct Lemon Robot Connector Tag
     */
    public void disconnectLrc(String lrct) {
        getLrcsPool().remove(lrct);
        WebSocketManager.defaultManager().closeSession(lrct);
    }

    /**
     * 删除LRC
     * 删除内存和本地的LRC信息
     * 断开当前已连接的LRC客户端
     *
     * @param lrct 要删除的LRC的LRCT
     */
    public boolean deleteLrc(String lrct) {
        disconnectLrc(lrct);
        getGlobalLrcPool().remove(lrct);
        return removeLrcInfoFromLocal(lrct);
    }

    public boolean activeLrc(LrcActive req) {
        if (!getGlobalLrcPool().containsKey(req.getLrct())) {
            return false;
        }
        LrcInfo lrcInfo = getGlobalLrcPool().get(req.getLrct());
        if (!lrcInfo.getType().equals(req.getClientType())) {
            // 客户端类型与LRC类型不匹配
            return false;
        }

        KeyPair keyPair = lrcInfo.getKeyPair();
        try {
            String lrcs = new String(
                    RsaUtil.decrypt(
                            keyPair.getPrivate(),
                            Base64.getDecoder().decode(req.getLrcs())
                    ), "UTF-8"
            );
            logger.debug("LRC active success! LRCT = " + req.getLrct() + " - LRCS = " + lrcs + " - activeCode = " + req.getActiveCode());
            putLrcs(req.getLrct(), lrcs);
            return WebSocketManager.defaultManager().activeSession(req.getLrct(), req.getActiveCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取指定类型的LRC的数量
     *
     * @param type LRC的类型
     * @return LRC的数量
     */
    public int countWithLrcType(int type) {
        int count = 0;
        for (LrcPublicInfo publicInfo : getAllLrcPublicInfo()) {
            if (publicInfo.getType() == type) {
                count++;
            }
        }
        return count;
    }

}
