package com.zqkh.file.context.uitls;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Date2Str {
    public static final String FORMAT= "yyyy-MM-dd";

    // 同步获取时间戳
    public static synchronized String getCurrentTimeStamp() {
        Random random = new Random();
        return String.valueOf(System.currentTimeMillis()+random.nextLong());
    }

    public static String getCurrentDate5() {

        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);

        String currentTime = formatter.format(date);

        return currentTime;
    }
}
