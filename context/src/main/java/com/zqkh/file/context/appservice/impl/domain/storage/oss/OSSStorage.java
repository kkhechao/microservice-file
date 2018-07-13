package com.zqkh.file.context.appservice.impl.domain.storage.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.zqkh.file.context.appservice.impl.domain.storage.Storage;
import com.zqkh.file.eventdto.FileIndexDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Map;

@Component("ossStorage")
public class OSSStorage implements Storage {

    //private static String bucketName;

//    @Value("${oss.bucketName}")
//    public static void setBucketName(String bucketName) {
//       bucketName = bucketName;
//    }

    @Override
    public FileIndexDto uploadFile(byte[] fileBytes, String contentType, String fileName) {

        OSSClient ossClient = DefaultOSSClient.getDefaultOSSClient();

        ObjectMetadata meta = new ObjectMetadata();

        meta.setCacheControl("no-cache");
        meta.setHeader("Pragma", "no-cache");
        // 设置上传内容类型
        meta.setContentType(contentType);
        // 设置上传文件长度
        meta.setContentLength(fileBytes.length);
        // 设置上传MD5校验
        String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(fileBytes));
        meta.setContentMD5(md5);


        PutObjectResult putObjectResult = ossClient.putObject(OSSConstant.bucketName, fileName, new ByteArrayInputStream(fileBytes), meta);

        // 获取ETAG 文件的MD5值
        String eTag = putObjectResult.getETag();
        String url = OSSConstant.ossUrl + "/" + fileName;
        FileIndexDto fileIndexDto=new FileIndexDto();
        fileIndexDto.setETag(eTag);
        fileIndexDto.setName(fileName);
        fileIndexDto.setImgUrl(url);
        fileIndexDto.setSize(String.valueOf(fileBytes.length));
        fileIndexDto.setId(fileName);
        fileIndexDto.setUploadDate(new Date());
        fileIndexDto.setType(contentType);

        return  fileIndexDto;

    }

    @Override
    public Map<String, Object> uploadFile(MultipartFile multipartFile, String remotePath) {
        //OSS上传文件
        Map<String, Object> map = OSSStorageUtil.uploadFile(multipartFile, remotePath);
        //保存上传记录
//        EntityLoader<FileEntity> userLoader = new ConstructLoader<>(FileEntity.class);
//        FileEntity savefile = userLoader.create(IdGenerator.getInstance().generate(FileEntity.class));
//        savefile.setSize(String.valueOf(map.get("fileSize")));
//        savefile.setType(String.valueOf(map.get("fileType")));
//        savefile.setETag(String.valueOf(map.get("eTag")));
//        savefile.setName(String.valueOf(map.get("fileName")));
//        savefile.setImgUrl(String.valueOf(map.get("imgUrl")));
//        map.put("id",savefile.getId().toValue());
        return map;
    }


}
