package com.zqkh.file.context.appservice.impl.domain;

import com.jovezhao.nest.ddd.ValueObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Setter
@Getter
@ToString
public class FileRecord extends ValueObject {
    /**
     * 业务编号
     */
    private String bizId;
    /**
     * 业务路径
     */
    private String bizPath;


    /**
     * 创建时间
     */
    private Date createTime;
}
