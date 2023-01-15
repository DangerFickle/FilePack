package com.belongme.service.impl;

import com.belongme.service.FileService;
import com.belongme.utils.FolderUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

/**
 * @Title: FileServiceImple
 * @ProjectName FilePack
 * @Description: TODO
 * @Author DengChao
 * @Date 2022/11/2012:16
 */
@Data
@Slf4j
@Service("fileService")
@ConfigurationProperties(prefix = "data")
public class FileServiceImpl implements FileService {

    // classpath所在的真实文件系统路径
    private String classpath;

    // 不同系统对应的项目运行的文件根路径
    private String filePathBySystem;

    private List<String> students;

    // 用户上传准备处理的文件存放的文件夹
    private String sourceFolder = "sourceFiles";

    // 复制好的文件存放的文件夹
    private String copyFolder = "copyFiles";

    // 打包复制的文件为zip的文件夹
    private String zipFolder = "zipFiles";

    // 最终在浏览器下载下来的文件名，后缀固定为.zip
    @Value("${fileNames.downloadFileName}")
    private String downloadFileName;


    // 初始化项目运行时会使用到的文件夹，文件路径等
    {
        // 初始化不同系统对应的文件路径 filePathBySystem， 要debug运行项目才能获取到
        String path = System.getProperty("java.class.path");
        int firstIndex = path.lastIndexOf(System.getProperty("path.separator")) + 1;
        int lastIndex = path.lastIndexOf(File.separator) + 1;
        path = path.substring(firstIndex, lastIndex);
        try {
            filePathBySystem = URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // 初始化classpath所在的真实文件系统路径
        try {
            classpath = ResourceUtils.getURL("classpath:").getPath();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // 初始化所有文件夹
        File source = new File(filePathBySystem + sourceFolder);
        if (!source.exists()) {
            source.mkdir();
        }
        File copyFiles = new File(filePathBySystem + copyFolder);
        if (!copyFiles.exists()) {
            copyFiles.mkdir();
        }
        File zipFiles = new File(filePathBySystem + zipFolder);
        if (!zipFiles.exists()) {
            zipFiles.mkdir();
        }

        log.warn("文件系统的路径 ---> " + filePathBySystem);
    }


    @Override
    public String handleFile(MultipartFile uploadedFiles) throws Exception {
        long size = uploadedFiles.getSize();
        // 限制上传文件为20MB
        if (size > 20 * 1024 * 1024) {
            return "redirect:";
        }

        //获取上传的文件的文件名
        String originalFileName = uploadedFiles.getOriginalFilename();

        // 根据不同的系统拼接source文件夹路径
        String sourceFilePath = filePathBySystem + sourceFolder;

        log.warn(filePathBySystem);
        // 上传的源文件最终会放在source文件夹下
        String uploadedPath = sourceFilePath + File.separator + originalFileName;

        //实现上传功能
        uploadedFiles.transferTo(new File(uploadedPath));
        log.info("源文件上传成功！");

        //复制上传的源文件
        copyFile(uploadedPath);

        //文件复制完毕后清除源文件所在的文件夹， isClean参数决定清除与否
        FolderUtils.cleanFolder(sourceFilePath, false);

        // 获取复制好的所有文件
        List<File> fileList = Arrays.asList(new File(filePathBySystem + copyFolder).listFiles());

        long start = System.currentTimeMillis();
        // 开始打包files目录下的所有文件为zip
        new ZipFile(filePathBySystem + zipFolder + File.separator + downloadFileName + ".zip").addFiles(fileList);
        long end = System.currentTimeMillis();
        log.info("zip文件打包完成，耗时：" + (end - start) + " ms");

        // 打包完毕后清空复制来的文件所在的目录copyFolder， isClean参数决定清除与否
        FolderUtils.cleanFolder(filePathBySystem + copyFolder, true);
        return "forward:downLoadFile"; // 转发到下载到浏览器的控制器方法
    }

    @Override
    public void downLoadFile(HttpServletResponse response) throws IOException {
        long start = System.currentTimeMillis();
        String realPath = filePathBySystem + zipFolder + File.separator + downloadFileName + ".zip";
        File file = new File(realPath);
        if (!file.exists()) {
            return;
        }
        String filename = file.getName();
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        try {
            inputStream = Files.newInputStream(file.toPath());
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            FileCopyUtils.copy(bufferedInputStream, bufferedOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (bufferedOutputStream != null) {
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
        }
        long end = System.currentTimeMillis();
        log.info("下载文件完成，耗时：" + (end - start) + " ms");
        FolderUtils.cleanFolder(filePathBySystem + zipFolder,true);// 清空zip文件夹
    }

    @Override
    public void copyFile(String sourceFilePath) {
        long start = System.currentTimeMillis();
        students.forEach(student -> {
            File copiedFile = new File(sourceFilePath);// 待复制的文件
            FileInputStream fis;
            FileOutputStream fos;
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                // 获取待复制文件的后缀名
                String suffixName = copiedFile.getName().substring(copiedFile.getName().lastIndexOf("."));
                // 最终输出的文件
                File file1 = new File(filePathBySystem + copyFolder + File.separator + student + suffixName);
                fis = new FileInputStream(copiedFile);
                bis = new BufferedInputStream(fis);
                fos = new FileOutputStream(file1);
                bos = new BufferedOutputStream(fos);
                byte[] bytes = new byte[1024];
                int len;
                while ((len = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        long end = System.currentTimeMillis();
        log.info("复制并重命名文件完成，耗时：" + (end - start) + " ms");
    }
}
