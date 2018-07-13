package com.zqkh.file.eventdto;

import java.io.Serializable;

public class ClearFileUseRecordDTO implements Serializable {

    private String[] fileId;
    private String bizId;
    private String bizPath;
    private String option;

    /**
     * 清除使用记录
     */
    public static  String CLEAR="clearUnusedrecord";

    public String[] getFileId() {
        return fileId;
    }

    public void setFileId(String[] fileId) {
        this.fileId = fileId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizPath() {
        return bizPath;
    }

    public void setBizPath(String bizPath) {
        this.bizPath = bizPath;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
