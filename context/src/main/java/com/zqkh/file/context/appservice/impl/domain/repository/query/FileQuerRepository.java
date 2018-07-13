package com.zqkh.file.context.appservice.impl.domain.repository.query;

import com.zqkh.file.context.appservice.impl.domain.repository.mappers.dmo.ext.FileQueryMapper;
import com.zqkh.file.context.appservice.inter.FileEntityQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class FileQuerRepository implements FileEntityQuery {
    @Autowired
    FileQueryMapper fileQueryMapper;


    @Override
    public List<String> unUsedFile(Date beforDateTime) {

        return fileQueryMapper.selectUnusedFileByDateTime(beforDateTime);
    }
}
