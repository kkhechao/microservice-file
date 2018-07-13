package com.zqkh.file.context.appservice.impl;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.builder.ConstructLoader;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.builder.RepositoryLoader;
import com.jovezhao.nest.ddd.identifier.IdGenerator;
import com.jovezhao.nest.starter.AppService;
import com.zqkh.common.exception.BusinessException;
import com.zqkh.file.context.appservice.impl.domain.FileEntity;
import com.zqkh.file.context.appservice.impl.domain.storage.IStorageManager;
import com.zqkh.file.context.appservice.impl.domain.storage.Storage;
import com.zqkh.file.context.appservice.impl.domain.storage.StorageType;
import com.zqkh.file.context.appservice.impl.domain.storage.oss.FileException;
import com.zqkh.file.context.appservice.impl.domain.storage.oss.OSSConstant;
import com.zqkh.file.context.appservice.impl.domain.storage.oss.OSSStorageUtil;
import com.zqkh.file.context.appservice.inter.FileEntityQuery;
import com.zqkh.file.context.appservice.inter.FileService;
import com.zqkh.file.dto.ResourceInfoDto;
import com.zqkh.file.eventdto.FileIndexDto;
import lombok.extern.log4j.Log4j;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AppService
@Log4j
public class FileServiceImpl implements FileService {

    @Autowired
    IStorageManager storageManager;
    @Autowired
    FileEntityQuery fileEntityQuery;
    @Autowired
    DozerBeanMapper dozerBeanMapper;


    private   EntityLoader<FileEntity> fileEntityEntityLoader = new RepositoryLoader<>(FileEntity.class);

    @Override
    public FileIndexDto updateFile(byte[] fileBytes, String contentType, String fileName) {
        Identifier identifier = IdGenerator.getInstance("md5").generate(FileEntity.class, fileBytes);

        FileEntity fileIndex = null;

        EntityLoader<FileEntity> repositoryLoader = new RepositoryLoader<>(FileEntity.class);
        fileIndex = repositoryLoader.create(identifier);
        if (fileIndex != null) {

            FileIndexDto fileIndexDto = dozerBeanMapper.map(fileIndex, FileIndexDto.class);
            return fileIndexDto;
        }

        EntityLoader<FileEntity> loader = new ConstructLoader<>(FileEntity.class);
        fileIndex = loader.create(identifier);
        //  fileIndex.init(identifier.toValue(), String.valueOf(fileBytes.length), StorageType.Ali_OSS);
        Storage defaultStorage = storageManager.getStorageByName(OSSConstant.defaultStorage);
        FileIndexDto fileIndexDto = defaultStorage.uploadFile(fileBytes, contentType, identifier.toValue());

        dozerBeanMapper.map(fileIndexDto, fileIndex);
        fileIndex.setType(contentType);

        return fileIndexDto;
    }

    @Override
    public Map<String, Object> uploadFile(MultipartFile multipartFile, String remotePath) throws IllegalAccessException, IntrospectionException, InvocationTargetException {


        if (multipartFile.isEmpty()) {
            log.info("上传了一个内容为空的文件");
            throw new FileException("文件内容不能为空");
        }

        Identifier identifier = null;
        try {
            identifier = IdGenerator.getInstance("md5").generate(FileEntity.class, multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileEntity fileIndex = null;

        EntityLoader<FileEntity> repositoryLoader = new RepositoryLoader<>(FileEntity.class);
        fileIndex = repositoryLoader.create(identifier);
        if (fileIndex != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("eTag", fileIndex.geteTag());
            map.put("fileName", fileIndex.getName());
            map.put("fileSize", fileIndex.getSize());
            map.put("fileType", fileIndex.getType());
            map.put("imgUrl", fileIndex.getImgUrl());
            map.put("id", fileIndex.getId().toValue());
            return map;
        }
        Storage storage = storageManager.getStorage(StorageType.Ali_OSS);
        //OSS上传文件
        Map<String, Object> map = storage.uploadFile(multipartFile, remotePath);

        EntityLoader<FileEntity> loader = new ConstructLoader<>(FileEntity.class);
        fileIndex = loader.create(identifier);
        if (map != null) {
            fileIndex.setUploadDate(new Date());
            fileIndex.setSize(String.valueOf(map.get("fileSize")));
            fileIndex.setType(String.valueOf(map.get("fileType")));
            fileIndex.setETag(String.valueOf(map.get("eTag")));
            fileIndex.setName(String.valueOf(map.get("fileName")));
            fileIndex.setImgUrl(String.valueOf(map.get("imgUrl")));
        }
        map.put("id", fileIndex.getId().toValue());
        return map;
    }

    @Override
    public void appendUsedRecord(String[] fileId, String bizPath, String bizId) {

        if (fileId == null) {
            return;
        }

        for (int i = 0; i < fileId.length; i++) {
            String id = fileId[i];
            EntityLoader<FileEntity> builder = new RepositoryLoader<FileEntity>(FileEntity.class);

            FileEntity file = builder.create(new StringIdentifier(id));

            if (file == null) {
                log.info("要添加使用记录的文件不存在：" + id);
                return;
            }
            file.addFileRecord(bizPath, bizId);

        }

    }

    @Override
    public void removeUsedRecord(String[] fileId, String bizPath, String bizId) {

        if (fileId == null) {
            return;
        }

        for (int i = 0; i < fileId.length; i++) {
            String id = fileId[i];
            EntityLoader<FileEntity> builder = new RepositoryLoader<FileEntity>(FileEntity.class);
            FileEntity file = builder.create(new StringIdentifier(id));
            if (file == null) {
                log.info("数据库中不存在该文件:" + id);
                return;
            }
            file.removeUseRecord(bizPath, bizId);
            log.info("清除使用记录:" + id);

        }


    }

    @Override
    public void clearUnusedFiles(Date beforDateTime) {

        EntityLoader<FileEntity> builder = new RepositoryLoader<FileEntity>(FileEntity.class);

        List<String> dataList = fileEntityQuery.unUsedFile(beforDateTime);

//        for (String id : strings) {
//            FileEntity fileEntity = builder.create(new StringIdentifier(id));
//            if (fileEntity == null) {
//                log.info("数据库中不存在该文件:" + id);
//                return;
//            }
//            //删除OSS上的未使用文件
//            OSSStorageUtil.deleteFile(id);
//            //删除数据库中的未使用文件索引
//            fileEntity.delete();
//        }

        if (dataList != null && dataList.size() > 0) {
            //限制条数
            int pointsDataLimit = 1000;
            Integer size = dataList.size();
            //是否分批
            if (size > pointsDataLimit) {
                //分批数
                int part = size / pointsDataLimit;
                for (int i = 0; i < part; i++) {
                    //1000条
                    List<String> listPage = dataList.subList(0, pointsDataLimit);
                    OSSStorageUtil.deleteFiles(listPage);
                    dataList.subList(0, pointsDataLimit).clear();
                }
                if (!dataList.isEmpty()) {
                    OSSStorageUtil.deleteFiles(dataList);
                }
            } else {
                OSSStorageUtil.deleteFiles(dataList);
            }

        }
    }

    @Override
    public String getUrlByMd5(String md5) {
        if(ObjectUtils.isEmpty(md5)){
            throw new BusinessException("资源MD5值为空",md5);
        }
        FileEntity fileEntity=fileEntityEntityLoader.create(StringIdentifier.valueOf(md5));
        if(ObjectUtils.isEmpty(fileEntity)){
            return null;
        }
        return fileEntity.getImgUrl();
    }

    @Override
    public List<ResourceInfoDto> getResourceInfo(List<String> id) {
        if(ObjectUtils.isEmpty(id)){
            return null;
        }


        return id.stream().map(n -> {
            FileEntity fileEntity=fileEntityEntityLoader.create(StringIdentifier.valueOf(n));
            ResourceInfoDto resourceInfoDto=null;
            if(!ObjectUtils.isEmpty(fileEntity)){
                resourceInfoDto=new ResourceInfoDto();
                resourceInfoDto.setId(fileEntity.getId().toValue());
                resourceInfoDto.setUrl(fileEntity.getImgUrl());
            }
            return resourceInfoDto;
        }).filter(n -> !ObjectUtils.isEmpty(n)).distinct().collect(Collectors.toList());




    }
}
