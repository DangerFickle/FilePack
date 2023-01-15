package com.belongme.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @Title: R
 * @ProjectName FilePack
 * @Description: TODO
 * @Author DengChao
 * @Date 2023/1/1312:51
 */
@Data
@AllArgsConstructor
public class R {
    private Integer status;

    private String message;

    private Map<String, Object> data;

    public R(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
