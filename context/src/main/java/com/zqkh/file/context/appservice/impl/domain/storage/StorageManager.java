//package com.zqkh.file.context.appservice.impl.domain.storage;
//
//import com.jovezhao.nest.utils.SpringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
///**
// * Created by zhaofujun on 2017/8/14.
// */
//@Component
//public class StorageManager implements IStorageManager {
//
//    @Value("${zqkh.file.defaultStorage}")
//    private String defaultStorage;
//
//    public Storage getDefaultStorage() {
//        return getStorageByName(defaultStorage);
//    }
//
//    private Storage getStorageByName(String storageName) {
//        return SpringUtils.getInstance(Storage.class, storageName);
//    }
//
//    @Override
//    public Storage getStorage(StorageType storageType) {
//        String storageName = null;
//        if (storageType.equals(StorageType.Ali_OSS))
//            storageName = "ossStorage";
//
//        return getStorageByName(storageName);
//    }
//}
