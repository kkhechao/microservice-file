package com.zqkh.file.eventdto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class FileIndexDto {

    private String id;

    /**
     *  文件名
     */
    private String name;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 图片访问URL
     */
    private String imgUrl;
    /**
     * MD5
     */
    private String  eTag;
    /**
     * 上传时间
     */
    private Date uploadDate;


}
