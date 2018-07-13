package com.zqkh.file.context.appservice.impl.domain.storage.oss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConstant {


//    public static  String ossUrl="http://jmscd.oss-cn-shenzhen.aliyuncs.com";
//    public static  String endpoint="http://oss-cn-shenzhen.aliyuncs.com";
//    public static  String accessKeyId="LTAIzPlpoSXgZxbc";
//    public static  String accessKeySecret="yErcdGnRM4q8nT94Sz8iOHxVB9HIw7";
//    //空间
//    public static  String  bucketName="jmscd";
//    public static  String bashFilePath="zqkh/cms/";
//
//    public static String callbackUrl="http://127.0.0.1:8099";
//
//    //文件存储目录
//    public static String filedir = "data/";
//
//    public static String defaultStorage="ossStorage";


    public static  String ossUrl;
    public static  String endpoint;
    public static  String accessKeyId;
    public static  String accessKeySecret;
    //空间
    public static  String  bucketName;
    public static  String bashFilePath;

    public static String callbackUrl;

    //文件存储目录
    public static String filedir;

    public static int OSS_FILE_ISEMPTY=003;


    public static String defaultStorage;



    @Value("${oss.url}")
    public  void setOssUrl(String ossUrl) {
        OSSConstant.ossUrl = ossUrl;
    }

    @Value("${oss.endpoint}")
    public  void setEndpoint(String endpoint) {
        OSSConstant.endpoint = endpoint;
    }

    @Value("${oss.accessKeyId}")
    public  void setAccessKeyId(String accessKeyId) {
        OSSConstant.accessKeyId = accessKeyId;
    }

    @Value("${oss.accessKeySecret}")
    public  void setAccessKeySecret(String accessKeySecret) {
        OSSConstant.accessKeySecret = accessKeySecret;
    }

    @Value("${oss.bucketName}")
    public  void setBucketName(String bucketName) {
        OSSConstant.bucketName = bucketName;
    }

    @Value("${oss.bashFilePath}")
    public  void setBashFilePath(String bashFilePath) {
        OSSConstant.bashFilePath = bashFilePath;
    }

    @Value("${oss.callbackUrl}")
    public  void setCallbackUrl(String callbackUrl) {
        OSSConstant.callbackUrl = callbackUrl;
    }

    @Value("${oss.filedir}")
    public  void setFiledir(String filedir) {
        OSSConstant.filedir = filedir;
    }

    @Value("${oss.defaultStorage}")
    public  void setDefaultStorage(String defaultStorage) {
        OSSConstant.defaultStorage = defaultStorage;
    }


}
