package com.zqkh.file.context.appservice.impl.domain.storage;

import com.aliyun.oss.common.utils.BinaryUtil;
import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.StringIdentifier;
import com.jovezhao.nest.ddd.identifier.IdGeneratorStrategy;
import org.springframework.stereotype.Component;

@Component("md5")
public class MD5GeneratorStrategy implements IdGeneratorStrategy {

    @Override
    public Identifier generate(Class clazz, Object object) {
        byte[] fileBytes = (byte[]) object;
        // 设置上传MD5校验
        byte[] md5Bytes = BinaryUtil.calculateMd5(fileBytes);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return StringIdentifier.valueOf(hexValue.toString());
    }
}
