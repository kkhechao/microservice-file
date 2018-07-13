package com.zqkh.file.context.appservice.impl.domain.repository.mappers;

import com.zqkh.file.context.appservice.impl.domain.repository.mappers.dmo.FileDmo;

public interface FileDmoMapper {
    int deleteByPrimaryKey(String id);

    int insert(FileDmo record);

    int insertSelective(FileDmo record);

    FileDmo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FileDmo record);

    int updateByPrimaryKey(FileDmo record);
}