package com.zqkh.file.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.EntityObject;
import com.zqkh.file.context.appservice.impl.domain.storage.StorageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Setter
@Getter
@Log4j
public class FileEntity  extends EntityObject {

    /**
     *  文件名
     */
    private String name;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 图片访问URL
     */
    private String imgUrl;
    /**
     * MD5
     */
    private String  eTag;
    /**
     * 上传时间
     */
    private Date uploadDate;

    private StorageType storageType;

    List<FileRecord> recordlist=new ArrayList<>();


    public void init(String fileName, String fileSize, StorageType storageType) {
        this.name = fileName;
        this.size = fileSize;
        this.uploadDate = new Date();
        this.storageType = storageType;
    }


//    public FileEntity(String name, String size, String type, String imgUrl, String eTag, Date uploadDate, StorageType storageType, List<FileRecord> recordlist) {
//        this.name = name;
//        this.size = size;
//        this.type = type;
//        this.imgUrl = imgUrl;
//        this.eTag = eTag;
//        this.uploadDate = new Date();
//        this.storageType = storageType;
//        this.recordlist = recordlist;
//    }

    public void addFileRecord(String bizPath, String bizId) {
        FileRecord fileRecord=new FileRecord();
        fileRecord.setBizId(bizId);
        fileRecord.setBizPath(bizPath);
        fileRecord.setCreateTime(new Date());
        recordlist.add(fileRecord);
    }

    public void addUseRecord(FileRecord fileUseRecord) {
        recordlist.add(fileUseRecord);
    }

    public void removeUseRecord(String bizPath, String bizId) {
        List<FileRecord> list = recordlist.stream()
                .filter(p -> p.getBizId().equals(bizId) && p.getBizPath().equals(bizPath))
                .collect(Collectors.toList());
        log.info("清除文件大小："+list.size());
        recordlist.removeAll(list);
    }


    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String geteTag() {
        return eTag;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public List<FileRecord> getRecordlist() {
        return recordlist;
    }
}
