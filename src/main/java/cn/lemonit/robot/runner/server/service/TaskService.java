package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.common.beans.task.Task;
import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.common.utils.JsonUtil;
import cn.lemonit.robot.runner.server.define.StringDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务相关的业务
 *
 * @author liuri
 */
@Service
public class TaskService {

    private static Logger logger = LoggerFactory.getLogger(TaskService.class);

    private static final String JS_FILE_END = ".js";
    private static final String TASK_MAIN_FILE_NAME = "task.json";

    /**
     * 创建一个本地任务
     *
     * @param key  任务标识
     * @param name 任务名称
     */
    public boolean createTask(String key, String name) {
        if (containTaskKey(key.trim())) {
            return false;
        }
        Task task = new Task();
        task.setTaskKey(key.trim());
        task.setTaskName(name.trim());
        task.setCreateTime(System.currentTimeMillis() / 1000);
        // 保存到本地
        saveBaseInfoToHd(task);
        return true;
    }

    /**
     * 删除本地任务
     *
     * @param key 任务标识
     * @return 是否删除成功的布尔值
     */
    public boolean deleteTask(String key) {
        File taskFile = getTaskDir(key);
        if (taskFile != null) {
            return taskFile.delete();
        }
        return true;
    }

    /**
     * 保存本地任务的指定指令集脚本语句
     *
     * @param taskKey              任务标识
     * @param instructionSetKey    指令集标识
     * @param instructionSetScript 指令集脚本语句
     * @return 是否保存成功的布尔值
     */
    public boolean saveInstructionSetScriptToHd(String taskKey, String instructionSetKey, String instructionSetScript) {
        if ("".equals(taskKey.trim()) || "".equals(instructionSetKey.trim())) {
            return false;
        }
        File taskDir = getTaskDir(taskKey);
        if (taskDir != null) {
            File instructionSetFile = FileUtil.getFile(taskDir.getAbsolutePath() + File.separator + instructionSetKey + JS_FILE_END);
            if (instructionSetFile != null) {
                // 输出到文件
                return FileUtil.writeStringToFile(instructionSetScript, instructionSetFile);
            }
        }
        return false;
    }

    /**
     * 保存任务的基本信息到硬盘
     *
     * @param task 要保存基本信息的任务对象
     * @return 保存是否成功的布尔值
     */
    public boolean saveBaseInfoToHd(Task task) {
        File workspaceDir = FileUtil.getRuntimeDir(StringDefine.TASK);
        if (workspaceDir != null) {
            // 文件夹存在
            File taskDir = getTaskDir(task.getTaskKey());
            if (taskDir != null) {
                File taskFile = FileUtil.getFile(taskDir.getAbsolutePath() + File.separator + TASK_MAIN_FILE_NAME);
                if (taskFile != null) {
                    FileUtil.writeStringToFile(
                            JsonUtil.gsonObj().toJson(task), taskFile
                    );
                    logger.info("Task [" + task.getTaskKey() + "] 's base info saved success ：" + taskFile.getAbsolutePath());
                    return true;
                }
            }
        }
        logger.info("Task [" + task.getTaskKey() + "] saved failed");
        return false;
    }

    /**
     * 判断任务列表中是否存在这个任务标识
     *
     * @param key 任务标识字符串
     * @return 是否存在这个key的布尔值
     */
    public boolean containTaskKey(String key) {
        File taskDir = getTaskDir(key);
        if (taskDir != null) {
            File file = new File(taskDir.getAbsolutePath() + File.separator + TASK_MAIN_FILE_NAME);
            return file.exists();
        }
        return true;
    }

    /**
     * 从本地硬盘中读取指定任务的指令集脚本
     *
     * @param taskKey           任务标识
     * @param instructionSetKey 指令集标识
     * @return 指令集脚本
     */
    public String readTaskInstructionSetScriptFromHd(String taskKey, String instructionSetKey) {
        File taskDirFile = getTaskDir(taskKey);
        if (taskDirFile != null) {
            File instructionSetFile = new File(taskDirFile.getAbsolutePath() + File.separator + instructionSetKey + ".js");
            if (instructionSetFile.exists()) {
                // 读取文件输出到变量
                return FileUtil.readStringFromFile(instructionSetFile);
            }
        }
        return "";
    }

    /**
     * 从硬盘中读取本地任务列表
     *
     * @return 序列化后的本地任务list
     */
    public List<Task> readTaskListFromHd() {
        logger.info("Now start a local scan of the existing tasks");
        List<Task> taskList = new ArrayList<>();
        File workspaceDir = FileUtil.getRuntimeDir(StringDefine.TASK);
        if (workspaceDir == null) {
            // 工作区文件夹不存在
            logger.error("Local task scan failed. Workspace folder does not exist and cannot be created.");
            return taskList;
        }
        File[] taskDirFiles = workspaceDir.listFiles();
        if (taskDirFiles == null || taskDirFiles.length == 0) {
            // 工作区文件夹下有文件
            logger.warn("There are no local task files in the workspace");
            return taskList;
        }
        for (File taskDirFile : taskDirFiles) {
            if (taskDirFile.isDirectory()) {
                // 工程文件夹存在
                File taskFile = new File(taskDirFile.getAbsolutePath() + File.separator + "task.json");
                if (taskFile.exists()) {
                    // 任务文件存在
                    String taskJSON = FileUtil.readStringFromFile(taskFile);
                    Task task = JsonUtil.gsonObj().fromJson(taskJSON, Task.class);
                    taskList.add(task);
                }
            }
        }
        logger.info("The local task was scanned successfully, with the number of: " + taskList.size());
        return taskList;
    }

    /**
     * 获取任务的工作目录文件对象
     *
     * @param taskKey 任务标识
     * @return 工作目录文件对象
     */
    public File getTaskDir(String taskKey) {
        File workspaceDir = FileUtil.getRuntimeDir(StringDefine.TASK);
        if (workspaceDir != null) {
            // 工作区文件夹存在
            return FileUtil.getDir(workspaceDir.getAbsolutePath() + File.separator + taskKey);
        }
        return null;
    }

}
