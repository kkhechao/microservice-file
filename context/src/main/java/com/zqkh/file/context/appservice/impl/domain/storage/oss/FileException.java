package com.zqkh.file.context.appservice.impl.domain.storage.oss;

import com.jovezhao.nest.exception.CustomException;

/**
 * 图片异常
 */
public class FileException extends CustomException {


    public FileException(String message, Object... arguments) {
        super(OSSConstant.OSS_FILE_ISEMPTY, message, arguments);
    }
}



