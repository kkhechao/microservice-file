package com.zqkh.file.context.configuration;

import com.jovezhao.nest.amq.AMQChannelProvider;
import com.jovezhao.nest.amq.AMQProviderConfig;
import com.jovezhao.nest.ddd.event.ChannelProvider;
import com.jovezhao.nest.ddd.event.EventConfigItem;
import com.zqkh.file.eventdto.AppendFileUseRecordDto;
import com.zqkh.file.eventdto.ClearFileUseRecordDTO;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class FileConfiguration {

    @Autowired
    CloudConfigProperties cloudConfigProperties;


    @Bean
    public DozerBeanMapper getMapper(ApplicationContext applicationContext) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList("dozer-mapper.xml"));
        applicationContext
                .getBeansOfType(BeanMappingBuilder.class)
                .forEach((k, v) -> {
                    mapper.addMapping(v);
                });
        return mapper;
    }


    @Bean
    public RestTemplate getRestTemplate(ApplicationContext applicationContext) {

        RestTemplate restTemplate = new RestTemplate();
        applicationContext.getBeansOfType(HttpMessageConverter.class)
                .forEach((p, q) -> {
            restTemplate.getMessageConverters().add(q);
        });
        return restTemplate;
    }

    @Bean
    public AMQProviderConfig getAMQProvider() {
        AMQProviderConfig providerConfig = new AMQProviderConfig();
        providerConfig.setSecretId(cloudConfigProperties.getAmq().getSecretId());
        providerConfig.setSecretKey(cloudConfigProperties.getAmq().getSecretKey());
        providerConfig.setEndpoint(cloudConfigProperties.getAmq().getEndpoint());
        return providerConfig;
    }

    @Bean
    public AMQChannelProvider getAMQChannelProvider(AMQProviderConfig providerConfig) {
        AMQChannelProvider channelProvider = new AMQChannelProvider(providerConfig);
        return channelProvider;
    }

    @Bean
    public EventConfigItem getEventConfigItemAppend(ChannelProvider channelProvider) {
        EventConfigItem eventConfigItem = new EventConfigItem();
        eventConfigItem.setEventName(AppendFileUseRecordDto.APPEND);
        eventConfigItem.setChannelProvider(channelProvider);
        return eventConfigItem;
    }

    @Bean
    public EventConfigItem getEventConfigItemClear(ChannelProvider channelProvider) {
        EventConfigItem eventConfigItem = new EventConfigItem();
        eventConfigItem.setEventName(ClearFileUseRecordDTO.CLEAR);
        eventConfigItem.setChannelProvider(channelProvider);
        return eventConfigItem;
    }


}
