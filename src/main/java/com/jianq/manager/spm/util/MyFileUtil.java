package com.jianq.manager.spm.util;

import com.jianq.manager.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;


/**
 * @author Leo on 2017/8/15
 */
public final class MyFileUtil {
    private MyFileUtil() {
    }

    /**
     * 日志服务
     */
    private static final Log LOG = LogFactory.getLog(MyFileUtil.class);

    /**
     * 复制文件
     *
     * @param fileAbsolutePath 原文件夹
     * @param affixFolder      目标文件夹
     * @param fileSystemName   文件系统名称
     * @return 返回结果
     */
    public static Integer copyFile(String fileAbsolutePath, String affixFolder, String fileSystemName) {
        Integer count = null;
        if (!StringUtil.isEmptyAll(fileAbsolutePath, affixFolder)) {
            File file = new File(fileAbsolutePath);
            if (file.exists()) {
                File newFileDir = new File(affixFolder);
                if (!newFileDir.exists()) {
                    boolean isMkdir = newFileDir.mkdirs();
                    if (isMkdir) {
                        count = saveFile(fileAbsolutePath, newFileDir, fileSystemName);
                    }
                } else {
                    count = saveFile(fileAbsolutePath, newFileDir, fileSystemName);
                }
            }
        }
        return count;
    }

    /**
     * 保存文件
     *
     * @param fileAbsolutePath 源文件
     * @param savePath         目标文件
     * @param newFileName      文件名称
     * @return
     */
    public static Integer saveFile(String fileAbsolutePath, File savePath, String newFileName) {
        Integer count = null;
        if (StringUtil.isNotEmpty(fileAbsolutePath) && savePath != null) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(fileAbsolutePath);
                FileOutputStream outputStream = new FileOutputStream(new File(savePath, newFileName));
                count = IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException ex) {
                LOG.error("文件复制异常,{}", ex);
                return null;
            }
        }
        return count;
    }
}
