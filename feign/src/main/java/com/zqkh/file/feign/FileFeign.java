package com.zqkh.file.feign;

import com.zqkh.file.dto.ResourceInfoDto;
import feign.Param;
import feign.RequestLine;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@FeignClient(name = "microservice-file-context",configuration = FileFeign.MultipartSupportConfig.class)
public interface FileFeign {

//    /**
//     * 上传一个文件，并且返回该文件的key值
//     * @param fileBytes
//     * @param contentType
//     * @return
//     */
//    @PostMapping(value = "/file/uploadFileByte")
//    FileIndexDto updateFile(@RequestParam("fileBytes")byte[] fileBytes, @RequestParam("contentType")String contentType, @RequestParam("fileName")String fileName);


    /**
     * 上传一个图片，并且返回该文件的md5值,src
     *
     * @return
     */
            @PostMapping(value = "/file/uploadFile")
            @RequestLine("POST /file/uploadFile")
            Object upload(@Param("file") MultipartFile multipartFile) throws IllegalAccessException, IntrospectionException, InvocationTargetException;


     class MultipartSupportConfig {

        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }


    /**
     * 根据文件MD5值获取文件URL
     * @param md5:文件MD5
     * @return
     */
    @GetMapping("/file/url")
    @RequestLine("GET /file/url")
    String getUrlByMd5(@RequestParam("md5")String md5);

    /**
     * 获取资源详情
     * @param id:资源编号或者文件MD5值
     * @return
     */
    @GetMapping("/file/resource/info")
    @RequestLine("GET /file/resource/info")
    List<ResourceInfoDto> getResourceInfo(@RequestParam("id") List<String> id);
}
