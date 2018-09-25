package cn.lemonit.robot.runner.server.service;

import cn.lemonit.robot.runner.common.beans.entity.Task;
import cn.lemonit.robot.runner.common.beans.entity.TaskInstructionSet;
import cn.lemonit.robot.runner.common.utils.FileUtil;
import cn.lemonit.robot.runner.common.utils.JsonUtil;
import cn.lemonit.robot.runner.common.utils.RuleUtil;
import cn.lemonit.robot.runner.server.define.StringDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
     * @param name 任务名称
     */
    public boolean create(String name) {
        Integer createTime = Math.toIntExact(System.currentTimeMillis());
        Task task = new Task();
        task.setCreateTime(createTime);
        task.setTaskName(name);
        task.setTaskKey(RuleUtil.generatePrimaryKey());
        TaskInstructionSet instructionSet = new TaskInstructionSet();
//        instructionSet.(task.getTaskKey());

//        Task task = new Task(generateID(createTime));
//        task.setTaskName(name.trim());
//        task.setCreateTime(createTime);
//        taskWriteToHd(task);
//        instructionSetSaveToHd(task.getTaskId(), StringDefine.MAIN, StringDefine.MAIN_DEFAULT_SCRIPT);
        return true;
    }

    /**
     * 删除本地任务
     *
     * @param taskId 任务唯一ID
     * @return 是否删除成功的布尔值
     */
    public boolean taskDelete(String taskId) {
        File taskFile = getTaskDir(taskId);
        if (taskFile != null) {
            if (taskFile.isDirectory()) {
                File[] files = taskFile.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (!file.delete()) {
                            return false;
                        }
                    }
                }
            }
            return taskFile.delete();
        }
        return false;
    }

    /**
     * 保存任务的基本信息到硬盘
     *
     * @param task 要保存基本信息的任务对象
     * @return 保存是否成功的布尔值
     */
    public boolean taskWriteToHd(Task task) {
//        File taskDir = getTaskDir(task.getTaskId());
//        if (taskDir != null) {
//            File taskFile = FileUtil.getFile(taskDir.getAbsolutePath() + File.separator + TASK_MAIN_FILE_NAME);
//            if (taskFile != null) {
//                FileUtil.writeStringToFile(
//                        JsonUtil.gsonObj().toJson(task), taskFile
//                );
//                logger.info("Task [" + task.getTaskId() + "] 's base info saved success ：" + taskFile.getAbsolutePath());
//                return true;
//            }
//        }
//        logger.info("Task [" + task.getTaskId() + "] saved failed");
        return false;
    }

    /**
     * 从硬盘中读取本地任务列表
     *
     * @return 序列化后的本地任务list
     */
    public List<Task> taskReadListFromHd() {
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
                File taskFile = new File(taskDirFile.getAbsolutePath() + File.separator + TASK_MAIN_FILE_NAME);
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
     * 从本地硬盘读取指定任务数据
     *
     * @param taskId 任务标识
     * @return 如果读取成功返回Task对象，否则返回null
     */
    public Task taskReadFromHd(String taskId) {
        File taskDirFile = getTaskDir(taskId);
        if (taskDirFile != null && taskDirFile.isDirectory()) {
            // 工程文件夹存在
            File taskFile = new File(taskDirFile.getAbsolutePath() + File.separator + TASK_MAIN_FILE_NAME);
            if (taskFile.exists()) {
                // 任务文件存在
                String taskJSON = FileUtil.readStringFromFile(taskFile);
                return JsonUtil.gsonObj().fromJson(taskJSON, Task.class);
            }
        }
        return null;
    }

    /**
     * 保存上传的二进制参数文件
     *
     * @param taskId        任务ID
     * @param multipartFile 分段上传文件对象
     * @return 文件ID
     */
    public String saveParameterBin(String taskId, MultipartFile multipartFile) {
        File taskDirFile = getTaskDir(taskId);
        if (taskDirFile != null && taskDirFile.isDirectory()) {
            // 工程文件夹存在
            String fileId = UUID.randomUUID().toString();
            File taskFile = FileUtil.getFile(taskDirFile.getAbsolutePath() + File.separator + StringDefine.PARAMETER_BIN + File.separator + fileId);
            try {
                multipartFile.transferTo(taskFile);
                return fileId;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 判断任务列表中是否存在这个任务ID
     *
     * @param taskId 任务唯一ID
     * @return 是否存在这个任务ID的布尔值
     */
    public boolean taskContain(String taskId) {
        if (taskId == null || "".equals(taskId.trim())) {
            return false;
        }
        File workspaceDir = FileUtil.getRuntimeDir(StringDefine.TASK);
        File taskDir = new File(workspaceDir.getAbsolutePath() + File.separator + taskId);
        return taskDir.exists() && taskDir.isDirectory();
    }

    /**
     * 创建指令集
     *
     * @param taskId            任务ID
     * @param instructionSetKey 指令集关键字
     * @return 是否创建成功的布尔值
     */
    public boolean instructionSetCreate(String taskId, String instructionSetKey) {
        return instructionSetSaveToHd(taskId, instructionSetKey, "// " + instructionSetKey);
    }

    /**
     * 删除指令集
     *
     * @param taskId            任务标识
     * @param instructionSetKey 指令集关键字
     */
    public boolean instructionSetDelete(String taskId, String instructionSetKey) {
        File instructionSetFile = getInstructionSetFile(taskId, instructionSetKey);
        return instructionSetFile == null || instructionSetFile.delete();
    }

    /**
     * 重命名指令集本地文件
     *
     * @param taskId               任务id
     * @param instructionSetKey    指令集关键字
     * @param instructionSetKeyNew 新的指令集关键字
     * @return 修改是否成功的布尔值
     */
    public boolean instructionSetRekeyToHd(String taskId, String instructionSetKey, String instructionSetKeyNew) {
        File instructionSetFile = getInstructionSetFile(taskId, instructionSetKey);
        if (instructionSetFile != null) {
            return instructionSetFile.renameTo(new File(instructionSetFile.getParent() + File.separator + instructionSetKeyNew + JS_FILE_END));
        }
        return false;
    }

    /**
     * 保存本地任务的指定指令集脚本语句
     *
     * @param taskId               任务ID
     * @param instructionSetKey    指令集关键字
     * @param instructionSetScript 指令集脚本语句
     * @return 是否保存成功的布尔值
     */
    public boolean instructionSetSaveToHd(String taskId, String instructionSetKey, String instructionSetScript) {
        File instructionSetFile = getInstructionSetFile(taskId, instructionSetKey);
        // 输出到文件
        return instructionSetFile != null && FileUtil.writeStringToFile(instructionSetScript, instructionSetFile);
    }

    /**
     * 从本地读取指定任务的指令集关键字列表
     *
     * @param taskId 任务ID
     * @return 指令集关键字列表
     */
    public List<String> instructionSetListReadFromHd(String taskId) {
        File taskDir = getTaskDir(taskId);
        List<String> instructionKeys = new ArrayList<>();
        File[] insFiles = taskDir.listFiles();
        if (insFiles != null) {
            for (File insFile : insFiles) {
                if (insFile.getName().endsWith(JS_FILE_END)) {
                    instructionKeys.add(insFile.getName().substring(0, insFile.getName().length() - JS_FILE_END.length()));
                }
            }
        }
        return instructionKeys;
    }

    /**
     * 从本地硬盘中读取指定任务的指令集脚本
     *
     * @param taskId            任务唯一ID
     * @param instructionSetKey 指令集关键字
     * @return 指令集脚本
     */
    public String instructionSetReadFromHd(String taskId, String instructionSetKey) {
        File instructionSetFile = getInstructionSetFile(taskId, instructionSetKey);
        if (instructionSetFile != null && instructionSetFile.exists()) {
            // 读取文件输出到变量
            return FileUtil.readStringFromFile(instructionSetFile);
        }
        return "";
    }

    /**
     * 是否存在这个指令集
     *
     * @param taskId            任务ID
     * @param instructionSetKey 指令集标识
     * @return 是否存在的布尔值
     */
    public boolean instructionSetContain(String taskId, String instructionSetKey) {
        File taskDir = getTaskDir(taskId);
        if (taskDir == null) {
            return false;
        }
        File instructionSetFile = new File(taskDir.getAbsolutePath() + File.separator + instructionSetKey + JS_FILE_END);
        return instructionSetFile.exists();
    }

    /**
     * 获取任务的工作目录文件对象
     *
     * @param taskId 任务标识
     * @return 工作目录文件对象
     */
    public File getTaskDir(String taskId) {
        if ("".equals(taskId.trim())) {
            return null;
        }
        File workspaceDir = FileUtil.getRuntimeDir(StringDefine.TASK);
        if (workspaceDir != null) {
            // 工作区文件夹存在
            return FileUtil.getDir(workspaceDir.getAbsolutePath() + File.separator + taskId);
        }
        return null;
    }

    /**
     * 获取指定任务下的指令集文件
     *
     * @param taskId            任务ID
     * @param instructionSetKey 指令集关键字
     * @return 指令集文件对象
     */
    public File getInstructionSetFile(String taskId, String instructionSetKey) {
        if ("".equals(instructionSetKey)) {
            return null;
        }
        File taskDir = getTaskDir(taskId);
        if (taskDir != null) {
            return FileUtil.getFile(taskDir.getAbsolutePath() + File.separator + instructionSetKey + JS_FILE_END);
        }
        return null;
    }

}
