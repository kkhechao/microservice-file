package com.zqkh.file.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * 资源详情DTO
 * @author 东来
 * @create 2018/2/3 0003
 */
@Getter
@Setter
@NoArgsConstructor
public class ResourceInfoDto {

    /**
     * 资源编号
     */
    private String id;

    /**
     * 资源链接地址
     */
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceInfoDto that = (ResourceInfoDto) o;
        return Objects.equals(id, that.id);
    }

    @Override

    public int hashCode() {

        return Objects.hash(id);
    }
}
