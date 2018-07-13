package com.zqkh.file.context.appservice.impl.domain.storage.oss;

import com.jovezhao.nest.utils.SpringUtils;

import com.zqkh.file.context.appservice.impl.domain.storage.IStorageManager;
import com.zqkh.file.context.appservice.impl.domain.storage.Storage;
import com.zqkh.file.context.appservice.impl.domain.storage.StorageType;
import org.springframework.stereotype.Component;

@Component
public class OssStorageManager implements IStorageManager {

    @Override
    public Storage getStorage(StorageType storageType) {
        String storageName = null;
        if (storageType.equals(StorageType.Ali_OSS))
            storageName = "ossStorage";

        return getStorageByName(storageName);
    }

    @Override
    public Storage getStorageByName(String storageName) {
        return SpringUtils.getInstance(Storage.class, storageName);
    }
}
