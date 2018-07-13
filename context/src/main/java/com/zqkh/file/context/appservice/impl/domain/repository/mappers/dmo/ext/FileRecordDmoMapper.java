package com.zqkh.file.context.appservice.impl.domain.repository.mappers.dmo.ext;

import com.zqkh.file.context.appservice.impl.domain.repository.mappers.dmo.FileRecordDmo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileRecordDmoMapper {
    int insert(FileRecordDmo record);

    int insertSelective(FileRecordDmo record);


    int batchInsert(@Param("FileRecordDmos") List<FileRecordDmo> fileUseRecordDmos, @Param("fileId") String fileId);

    List<FileRecordDmo> selectUseRecord(@Param("fileId") String fileId);

    int deleteUseRecord(@Param("fileId") String fileId);


}