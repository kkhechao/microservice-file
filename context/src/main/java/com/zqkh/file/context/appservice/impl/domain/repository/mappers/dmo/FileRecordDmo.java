package com.zqkh.file.context.appservice.impl.domain.repository.mappers.dmo;

import java.util.Date;

public class FileRecordDmo {
    private String fileId;

    private String bizId;

    private String bizPath;

    private Date createTime;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId == null ? null : bizId.trim();
    }

    public String getBizPath() {
        return bizPath;
    }

    public void setBizPath(String bizPath) {
        this.bizPath = bizPath == null ? null : bizPath.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}