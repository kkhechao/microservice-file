package com.zqkh.file.context.appservice.impl.domain.storage;

import java.util.Arrays;

public enum StorageType {

    Ali_OSS(1), Tx_COS(0);

    StorageType(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return this.value;
    }

    public static StorageType valueOf(int value) {
        return Arrays.stream(StorageType.values())
                .filter(p -> p.getValue() == value)
                .findFirst()
                .orElse(StorageType.Ali_OSS);
    }
}
