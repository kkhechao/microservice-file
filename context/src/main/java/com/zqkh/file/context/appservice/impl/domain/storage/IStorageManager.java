package com.zqkh.file.context.appservice.impl.domain.storage;

public interface IStorageManager {
    public Storage getStorage(StorageType storageType);
    public Storage getStorageByName(String storageName);
}
