package com.zqkh.file.context.event;


import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventBus;
import com.jovezhao.nest.starter.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;


@AppService
public class FileApplicationRunner implements ApplicationRunner {

    @Autowired
    ChannelProvider channelProvider;

    @Override
    public void run(ApplicationArguments var1) throws Exception{

         EventBus.registerHandler(new AppenFileUseRecordHanler());

    }

}