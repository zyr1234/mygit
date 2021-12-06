package com.geovis.jg.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseResp<T> {
    private Integer code;
    private String message;
    private T data;

    public BaseResp(T data) {
        this.code = 200;
        this.data = data;
    }

    public BaseResp(T data,String message,Integer code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public BaseResp(Integer code , T data) {
        this.code = code;
        this.data = data;
    }

    public BaseResp() {
    }
}
