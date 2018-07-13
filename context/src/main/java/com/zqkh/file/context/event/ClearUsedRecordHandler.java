package com.zqkh.file.context.event;

import com.jovezhao.nest.ddd.event.EventHandler;
import com.jovezhao.nest.utils.SpringUtils;
import com.zqkh.file.context.appservice.inter.FileService;
import com.zqkh.file.eventdto.AppendFileUseRecordDto;
import com.zqkh.file.eventdto.ClearFileUseRecordDTO;

/**
 * 清除使用记录事件
 */
public class ClearUsedRecordHandler implements EventHandler<ClearFileUseRecordDTO> {

    private FileService fileService= SpringUtils.getInstance(FileService.class);


    @Override
    public String getEventName() {
        return   ClearFileUseRecordDTO.CLEAR;
    }

    @Override
    public Class<ClearFileUseRecordDTO> getTClass() {
        return ClearFileUseRecordDTO.class;
    }

    @Override
    public void handle(ClearFileUseRecordDTO data) throws Exception {
        fileService.removeUsedRecord(data.getFileId(),data.getBizPath(),data.getBizId());
    }
}
