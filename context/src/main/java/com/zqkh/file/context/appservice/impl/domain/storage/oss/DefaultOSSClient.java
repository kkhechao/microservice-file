package com.zqkh.file.context.appservice.impl.domain.storage.oss;

import com.aliyun.oss.OSSClient;

public class DefaultOSSClient {


    private static OSSClient client = new OSSClient(OSSConstant.endpoint, OSSConstant.accessKeyId, OSSConstant.accessKeySecret,DefaultClientConfiguration.getDefalutClientConfig());

    private DefaultOSSClient() {

    }

    public static OSSClient getDefaultOSSClient(){
        if(client == null){
            client = new OSSClient(OSSConstant.endpoint, OSSConstant.accessKeyId, OSSConstant.accessKeySecret,DefaultClientConfiguration.getDefalutClientConfig());
        }
        return client;
    }

    public static void shutdownOSSClient(){
        client.shutdown();
        client = null;
    }
}
