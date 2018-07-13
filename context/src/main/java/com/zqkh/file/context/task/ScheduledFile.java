//package com.zqkh.file.context.task;
//
//import com.zqkh.file.context.appservice.inter.FileService;
//import lombok.extern.log4j.Log4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Calendar;
//import java.util.Date;
//
///**
// * 清除未使用的文件记录
// */
//@Component
//@Log4j
//public class ScheduledFile {
//
//    private static final String LOCK_NO = "redis_distribution_lock_no_";
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Autowired
//    FileService fileService;
//
//
////    @Scheduled(cron="0/5 * * * * ?")
////    public void executeFileDeleteTask1() {
////
////        RedisLockImpl redisLock = new RedisLockImpl(redisTemplate);
////        String name=Thread.currentThread().getName();
////        //加锁时间
////        Long lockTime;
////        if ((lockTime = redisLock.lock((LOCK_NO+1)+"",name ))!=null){
////            //开始执行任务
////            log.info(name + "任务执行中"+(i++));
////            Calendar calendar = Calendar.getInstance();
////            calendar.set(Calendar.HOUR_OF_DAY, 0);
////            calendar.set(Calendar.MINUTE, 0);
////            calendar.set(Calendar.SECOND, 0);
////
////        }
////    }
//
//
//   // @Scheduled(cron="0 0 0 * * ?")
//    public void executeFileDeleteTask() {
//        //开始执行任务
//        log.info("自身清理未文件任务执行中");
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.add(Calendar.DAY_OF_MONTH, 1);
//        Date date=calendar.getTime();
//        fileService.clearUnusedFiles(date);
//        log.info("自身清理未文件任务完毕");
//
//    }
//
//}
//
