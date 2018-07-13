package com.zqkh.file.context.appservice.inter;

import java.util.Date;
import java.util.List;

public interface FileEntityQuery {

    List<String> unUsedFile(Date beforDateTime);
}
