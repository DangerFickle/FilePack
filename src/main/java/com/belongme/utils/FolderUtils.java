package com.belongme.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @Title: CleanFolderUtils
 * @ProjectName FilePack
 * @Description: TODO 封装清理目录的代码
 * @Author DengChao
 * @Date 2023/1/1313:04
 */
@Slf4j
public class FolderUtils {
    public static void cleanFolder(String folderPath, boolean isClean) {
        String folderName = folderPath.substring(folderPath.lastIndexOf("\\") + 1);
        if (!isClean) {
            log.info("未清理" + folderName + "目录！");
            return;
        }
        long start = System.currentTimeMillis();
        File folder = new File(folderPath);
        File[] listFiles = folder.listFiles();
        for (File file : listFiles) {
            if (file.isFile()) {
                file.delete();
            }
        }
        long end = System.currentTimeMillis();
        log.info("清理" + folderName + "目录完成，耗时：" + (end - start) + " ms");
    }
}
