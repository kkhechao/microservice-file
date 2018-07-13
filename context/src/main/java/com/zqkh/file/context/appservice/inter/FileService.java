package com.zqkh.file.context.appservice.inter;


import com.zqkh.file.dto.ResourceInfoDto;
import com.zqkh.file.eventdto.FileIndexDto;
import org.springframework.web.multipart.MultipartFile;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

public interface FileService  {
        /**
         * 上传一个文件，并且返回该文件的md5值
         * @param fileBytes
         * @param contentType
         * @return
         */
        FileIndexDto updateFile(byte[] fileBytes, String contentType, String fileName);

        /**
         * 上传一个图片，并且返回该文件的md5值,imgURL
         * @param multipartFile
         * @param remotePath
         * @return
         */
        Object uploadFile(MultipartFile multipartFile, String remotePath) throws IllegalAccessException, IntrospectionException, InvocationTargetException;

        /**
         * 添加文件的使用记录
         * @param fileId
         * @param bizPath
         * @param bizId
         */
        void appendUsedRecord(String[] fileId, String bizPath, String bizId);

        /**
         * 移除文件记录
         * @param fileId
         * @param bizPath
         * @param bizId
         */
        void removeUsedRecord(String[] fileId, String bizPath, String bizId);

        /**
         * 清理指定时间之前没有使用过的文件 任务
         * @param beforDateTime
         */
        void clearUnusedFiles(Date beforDateTime);

        /**
         * 根据文件MD5值获取文件URL
         * @param md5:文件MD5
         * @return
         */
        String getUrlByMd5(String md5);


        /**
         * 获取资源详情
         * @param id:资源编号或者文件MD5值
         * @return
         */
        List<ResourceInfoDto> getResourceInfo(List<String> id);
}

