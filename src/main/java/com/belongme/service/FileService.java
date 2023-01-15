package com.belongme.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Title: FileService
 * @ProjectName FilePack
 * @Description: TODO
 * @Author DengChao
 * @Date 2022/11/2012:14
 */
public interface FileService {
    String handleFile(MultipartFile uploadedFiles) throws Exception;

    void downLoadFile(HttpServletResponse response) throws IOException;

    void copyFile(String sourceFilePath) throws Exception;

}
