package com.zqkh.file.feign;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

public class FileFeignUtils {


    public static FileFeign getFileFeignClient(DiscoveryClient discoveryClient) throws MalformedURLException {

        URI uri = null;
        List<ServiceInstance> listClient = discoveryClient.getInstances("MICROSERVICE-FILE-CONTEXT");
        if (listClient != null && listClient.size() > 0) {
            uri = listClient.get(0).getUri();
        }

        Feign.Builder encoder = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new FeignSpringFormEncoder());

        return encoder.target(FileFeign.class, uri.toURL().toString());
    }
}
