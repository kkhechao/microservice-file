package com.zqkh.file.context.appservice.impl.domain.repository.mappers.dmo.ext;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface FileQueryMapper {

    List<String> selectUnusedFileByDateTime(@Param("beforDateTime") Date beforDateTime);
}
