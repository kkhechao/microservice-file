package com.zqkh.file.context.appservice.impl.domain.repository.model;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.repository.IRepository;
import com.zqkh.file.context.appservice.impl.domain.FileEntity;
import com.zqkh.file.context.appservice.impl.domain.FileRecord;
import com.zqkh.file.context.appservice.impl.domain.repository.mappers.FileDmoMapper;
import com.zqkh.file.context.appservice.impl.domain.repository.mappers.dmo.FileDmo;
import com.zqkh.file.context.appservice.impl.domain.repository.mappers.dmo.FileRecordDmo;
import com.zqkh.file.context.appservice.impl.domain.repository.mappers.dmo.ext.FileRecordDmoMapper;
import com.zqkh.file.context.appservice.impl.domain.storage.oss.OSSConstant;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository("FileEntity_Repository")
public class FileRepository implements IRepository<FileEntity> {

    @Autowired
    FileDmoMapper ossFileDmoMapper;
    @Autowired
    FileRecordDmoMapper ossFileRecordDmoMapper;


    @Autowired
    private DozerBeanMapper beanMapper;

    @Override
    public FileEntity getEntityById(Identifier identifier, EntityLoader<FileEntity> entityLoader) {

        FileDmo fileDmo = ossFileDmoMapper.selectByPrimaryKey(identifier.toValue());
        if (fileDmo == null) {
            return null;
        }

        fileDmo.setImgUrl(OSSConstant.ossUrl+"/"+fileDmo.getId());

        FileEntity fileEntity = entityLoader.create(identifier);
        beanMapper.map(fileDmo,fileEntity);

        List<FileRecordDmo> fileUseRecordDmos = ossFileRecordDmoMapper.selectUseRecord(identifier.toValue());
        if (fileUseRecordDmos != null) {
            for (FileRecordDmo fileUseRecordDmo : fileUseRecordDmos) {
                FileRecord fileRecord = new FileRecord();
                fileRecord.setBizId(fileUseRecordDmo.getBizId());
                fileRecord.setBizPath(fileUseRecordDmo.getBizPath());
                fileRecord.setCreateTime(fileUseRecordDmo.getCreateTime());
                fileEntity.addUseRecord(fileRecord);
            }
        }
        return fileEntity;
    }

    @Override
    public void save(FileEntity fileEntity) {
        if (fileEntity == null) {
            return;
        }

        FileDmo dmo=beanMapper.map(fileEntity,FileDmo.class);

        if(ossFileDmoMapper.updateByPrimaryKey(dmo)==0){
            ossFileDmoMapper.insert(dmo);
        }
        ossFileRecordDmoMapper.deleteUseRecord(fileEntity.getId().toValue());
        List<FileRecord> fileUseRecords = fileEntity.getRecordlist();
        if (fileUseRecords != null && fileUseRecords.size() > 0) {
            List<FileRecordDmo> OssFileRecordDmos = fileUseRecords.stream()
                    .map(p -> beanMapper.map(p, FileRecordDmo.class))
                    .collect(Collectors.toList());
            ossFileRecordDmoMapper.batchInsert(OssFileRecordDmos,fileEntity.getId().toValue());

        }


    }

    @Override
    public void remove(FileEntity fileEntity) {
        ossFileDmoMapper.deleteByPrimaryKey(fileEntity.getId().toValue());
        ossFileRecordDmoMapper.deleteUseRecord(fileEntity.getId().toValue());

    }
}
