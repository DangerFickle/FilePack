package com.belongme.controller;

import com.belongme.pojo.R;
import com.belongme.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: FileController
 * @ProjectName FilePack
 * @Description: TODO
 * @Author DengChao
 * @Date 2022/11/2012:17
 */
@Controller
public class FileController {

    @Resource
    FileService fileService;


    @RequestMapping("/file")
    public String getFile(MultipartFile uploadedFiles) {
        String result = null;
        try {
            result = fileService.handleFile(uploadedFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 下载文件给浏览器
    @ResponseBody
    @RequestMapping("/downLoadFile")
    public R downLoadFile(HttpServletResponse response){
        try {
            fileService.downLoadFile(response);
            return new R(0, "下载成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
