package com.zqkh.file.context.controller;

import com.zqkh.file.context.appservice.inter.FileService;
import com.zqkh.file.dto.ResourceInfoDto;
import com.zqkh.file.feign.FileFeign;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@Log4j
public class FileController implements FileFeign {


    @Resource
    FileService fileService;

//
//    @Override
//    public FileIndexDto updateFile(byte[] fileBytes, String contentType, String fileName) {
//        return fileService.updateFile(fileBytes, contentType, fileName);
//    }

    @Override
    public Object upload(@RequestParam("file") MultipartFile multipartFile) throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        return fileService.uploadFile(multipartFile,null);
    }

    @Override
    public String getUrlByMd5(@RequestParam("md5")String md5) {
        return fileService.getUrlByMd5(md5);
    }


    @Override
    public List<ResourceInfoDto> getResourceInfo(@RequestParam(value = "id", required = false) List<String> id) {
        return fileService.getResourceInfo(id);
    }
}
