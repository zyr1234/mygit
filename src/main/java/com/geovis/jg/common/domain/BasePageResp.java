package com.geovis.jg.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BasePageResp<T> {
    private Integer code;
    private String message;
    private PageData<T> data;

    @Data
    public static class PageData<T>{
        private List<T> list;
        private Integer count;
    }

    public BasePageResp(List<T> list,int count){
        this.code=200;
        this.data = new PageData<>();
        this.data.setList(list);
        this.data.setCount(count);
    }
    public BasePageResp(Integer code,String message){
        this.code = code;
        this.message = message;
    }

}
