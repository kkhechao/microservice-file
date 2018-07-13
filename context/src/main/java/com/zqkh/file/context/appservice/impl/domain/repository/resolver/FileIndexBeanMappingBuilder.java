package com.zqkh.file.context.appservice.impl.domain.repository.resolver;

import com.zqkh.file.context.appservice.impl.domain.repository.mappers.dmo.FileDmo;
import com.zqkh.file.context.appservice.impl.domain.repository.mappers.dmo.FileRecordDmo;
import com.zqkh.file.context.appservice.impl.domain.FileEntity;
import com.zqkh.file.context.appservice.impl.domain.FileRecord;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileIndexBeanMappingBuilder extends BeanMappingBuilder {
    @Override
    protected void configure() {
//        mapping(type(FileEntity.class).accessible(true), FileDmo.class)
////                .fields("name","name")
////                .fields("size","size")
////                .fields("type","type")
////                .fields("imgUrl","imgUrl")
////                .fields("eTag","eTag")
////                .fields("uploadDate","uploadDate")
//        ;


//        mapping(type(FileRecord.class).accessible(true), FileRecordDmo.class)
//                .fields("bizId","bizId")
//                .fields("bizPath","bizPath")
//
//                .fields("createTime","createTime")
        ;

    }
}
