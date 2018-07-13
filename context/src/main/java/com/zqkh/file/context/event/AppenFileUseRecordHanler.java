package com.zqkh.file.context.event;

import com.jovezhao.nest.ddd.event.EventHandler;
import com.jovezhao.nest.utils.SpringUtils;
import com.zqkh.file.context.appservice.inter.FileService;
import com.zqkh.file.eventdto.AppendFileUseRecordDto;


/**
 * 添加使用记录事件
 */
public class AppenFileUseRecordHanler implements EventHandler<AppendFileUseRecordDto> {

    private FileService fileService= SpringUtils.getInstance(FileService.class);


    @Override
    public String getEventName() {
      return   AppendFileUseRecordDto.APPEND;
    }

    @Override
    public Class<AppendFileUseRecordDto> getTClass() {
        return AppendFileUseRecordDto.class;
    }

    @Override
    public void handle(AppendFileUseRecordDto data) throws Exception {
        fileService.appendUsedRecord(data.getFileId(),data.getBizPath(),data.getBizId());
    }
}
