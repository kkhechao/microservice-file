package com.zqkh.file.context.appservice.impl.domain.storage.oss;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.identifier.IdGenerator;
import com.zqkh.file.context.appservice.impl.domain.FileEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.*;


public class OSSStorageUtil {

    private static final Logger log = LoggerFactory.getLogger(OSSStorageUtil.class);

     private static OSSClient ossClient = DefaultOSSClient.getDefaultOSSClient();
    /**
     *  上传OSS服务器文件
     *  @Title: uploadFile
     *  @param multipartFile spring上传的文件
     *  @param remotePath oss服务器二级目录
     *  @throws Exception 设定文件
     *  @return String 返回类型 返回oss存放路径 还是返回外网地址？
     *
     * @throws
     */
    public static Map<String,Object> uploadFile(MultipartFile multipartFile, String remotePath) {

        if (multipartFile.isEmpty()) {
            return null;
        }
        // FileEntity file=new FileEntity();
        Map<String,Object> map=new HashMap<String,Object>();
        String remoteFilePath =null;
        String fileName=null;
        // 定义二级目录
        if (StringUtils.isBlank(remotePath)) {
            remotePath = "defaultRemotePath/";
        }
        try {
            //文件名称生成
            byte[] bytes = multipartFile.getBytes();
            Identifier identifier = IdGenerator.getInstance("md5").generate(FileEntity.class, bytes);


            // 流转换 将MultipartFile转换为oss所需的InputStream
            InputStream fileContent= multipartFile.getInputStream();
            String originalFilename = multipartFile.getOriginalFilename();
            String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            //获取文件名，对文件名做随机处理
            // fileName= Date2Str.getCurrentTimeStamp()+substring;
            fileName= identifier.toValue()+substring;
            // 获取ossClient
            OSSClient ossClient = DefaultOSSClient.getDefaultOSSClient();

            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            System.out.println(fileContent.available());
            objectMetadata.setContentLength(fileContent.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(contentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(OSSConstant.bucketName,  identifier.toValue(), fileContent, objectMetadata);

            // 获取ETAG 文件的MD5值
            String eTag = putResult.getETag();
            // String imgUrl = OSSStorageUtil.getUrl(identifier.toValue());
            String imgUrl = OSSConstant.ossUrl + "/" + identifier.toValue();

            map.put("eTag",eTag);
            map.put("fileName",fileName);
            map.put("fileSize",multipartFile.getSize());
            map.put("eTag",eTag);
            map.put("fileType",multipartFile.getContentType());
            map.put("imgUrl",imgUrl);
            // 关闭io流
            fileContent.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
        }finally {

            // 关闭OSSClient
            DefaultOSSClient.shutdownOSSClient();
        }
        return map;
    }

    // 下载文件
    public static void downloadFile(String key, String yourLocalFile)
            throws OSSException, ClientException, IOException {
        if (key.isEmpty()) {
            return ;
        }
        // 初始化OSSClient
        OSSClient ossClient =DefaultOSSClient.getDefaultOSSClient();
        OSSObject object = ossClient.getObject(OSSConstant.bucketName, key);
        // 获取ObjectMeta
        ObjectMetadata meta = object.getObjectMetadata();

        //下载到本地文件
        if(!StringUtils.isBlank(yourLocalFile)){
            ObjectMetadata objectData = ossClient.getObject(new GetObjectRequest(OSSConstant.bucketName, key),
                    new File(yourLocalFile));
        }else{
            // 获取Object的输入流
            InputStream objectContent = object.getObjectContent();
            //流下载
            displayTextInputStream(objectContent);
        }
        // 关闭OSSClient
        ossClient.shutdown();

    }

    /**
     *
     * @Title: deleteFile
     * @Description: 根据key删除OSS服务器上的文件
     * @param key 设定文件
     * @return void 返回类型 @throws
     */
    public static void deleteFile( String key) {
        OSSClient ossClient = DefaultOSSClient.getDefaultOSSClient();
        ossClient.deleteObject(OSSConstant.bucketName, key);
    }


    /**
     * @return void 返回类型 @throws
     * @Title: deleteFile
     * @Description: 删除多个文件 每次最多1000个
     */
    public static void deleteFiles(List<String> keys) {
        OSSClient ossClient = DefaultOSSClient.getDefaultOSSClient();
        ossClient.deleteObjects(new DeleteObjectsRequest(OSSConstant.bucketName).withKeys(keys));
    }


    /**
     * Description: 判断OSS服务文件上传时文件的contentType @Version1.0
     *
     * @param FilenameExtension
     *            文件后缀
     * @return String
     */
    public static String contentType(String FilenameExtension) {
        if (FilenameExtension.equals(".BMP") || FilenameExtension.equals(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equals(".GIF") || FilenameExtension.equals(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equals(".JPEG") || FilenameExtension.equals(".jpeg") || FilenameExtension.equals(".JPG")
                || FilenameExtension.equals(".jpg") || FilenameExtension.equals(".PNG")
                || FilenameExtension.equals(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equals(".HTML") || FilenameExtension.equals(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equals(".TXT") || FilenameExtension.equals(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equals(".VSD") || FilenameExtension.equals(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equals(".PPTX") || FilenameExtension.equals(".pptx") || FilenameExtension.equals(".PPT")
                || FilenameExtension.equals(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equals(".DOCX") || FilenameExtension.equals(".docx") || FilenameExtension.equals(".DOC")
                || FilenameExtension.equals(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equals(".XML") || FilenameExtension.equals(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equals(".apk") || FilenameExtension.equals(".APK")) {
            return "application/octet-stream";
        }
        return "text/html";
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null){break;}

        }

        reader.close();
    }

    public static void callbackTest() {

        OSSClient ossClient = new OSSClient(OSSConstant.endpoint, OSSConstant.accessKeyId, OSSConstant.accessKeySecret);

        try {
            String content = "Hello OSS";
            PutObjectRequest putObjectRequest = new PutObjectRequest(OSSConstant.bucketName, "key",
                    new ByteArrayInputStream(content.getBytes()));

            Callback callback = new Callback();
            callback.setCallbackUrl(OSSConstant.callbackUrl);
            callback.setCallbackHost("oss-cn-hangzhou.aliyuncs.com");
            callback.setCallbackBody("{\\\"bucket\\\":${bucket},\\\"object\\\":${object},"
                    + "\\\"mimeType\\\":${mimeType},\\\"size\\\":${size},"
                    + "\\\"my_var1\\\":${x:var1},\\\"my_var2\\\":${x:var2}}");
            callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
            callback.addCallbackVar("x:var1", "value1");
            callback.addCallbackVar("x:var2", "value2");
            putObjectRequest.setCallback(callback);

            PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
            byte[] buffer = new byte[1024];
            putObjectResult.getCallbackResponseBody().read(buffer);
            putObjectResult.getCallbackResponseBody().close();

        } catch (OSSException oe) {
            log.info("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.info("Error Message: " + oe.getErrorCode());
            log.info("Error Code:       " + oe.getErrorCode());
            log.info("Request ID:      " + oe.getRequestId());
            log.info("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            log.info("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.info("Error Message: " + ce.getMessage());
        }  catch (IOException ie) {
            log.info("Caught an IOException,"+ie.getMessage());
        }

        finally {
            ossClient.shutdown();
        }
    }

    private static String claimUploadId(String bucketName,String key) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(request);
        return result.getUploadId();
    }



    public static String uploadImg2Oss(MultipartFile file) {
        if (file.getSize() > 1024 * 1024*15) {
            throw new FileException("上传图片大小不能超过15M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            String ret = uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new FileException("图片上传失败");
        }
    }


    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public  static String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(contentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(OSSConstant.bucketName, fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public  static String getImgUrl(String fileUrl) {
        if (!org.springframework.util.StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return getUrl(split[split.length - 1]);
        }
        return null;
    }


    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public static String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600* 1000 * 24 * 365*10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(OSSConstant.bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }




}
