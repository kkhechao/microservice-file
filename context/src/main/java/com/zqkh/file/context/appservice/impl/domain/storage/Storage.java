package com.zqkh.file.context.appservice.impl.domain.storage;

import com.zqkh.file.eventdto.FileIndexDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface Storage {
    /**
     * 文件上传
     * @param fileBytes
     * @param contentType
     * @param fileName
     */
    FileIndexDto uploadFile(byte[] fileBytes, String contentType, String fileName);

    /**
     * 上传一个图片，并且返回该文件的md5值,imgURL
     * @param multipartFile
     * @param remotePath
     * @return
     */
    Map<String,Object> uploadFile(MultipartFile multipartFile, String remotePath);
}
