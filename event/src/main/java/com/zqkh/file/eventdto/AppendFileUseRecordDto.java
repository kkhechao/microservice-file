package com.zqkh.file.eventdto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AppendFileUseRecordDto implements Serializable {

        private String[] fileId;
        private String bizId;
        private String bizPath;
        private String option;
        /**
         * 添加使用记录
         */
        public static  String  APPEND="appendUseRecord";




}
