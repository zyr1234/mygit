package com.geovis.jg.mb.utills;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * list分页
 * @author liufeng
 * @data 2021/10/30
 */

@Slf4j
public class ListFindPage<T> {

    /**
     * 每页条数
     */
    private int pageSize;


    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 分页的list
     */
    private  List<T> data;


    public ListFindPage(List<T> data , int pageSize){
        if (data == null || data.isEmpty()){
            log.info("集合为空");
        }
        this.data = data;
        this.pageSize = pageSize;
        this.pageCount = data.size()/pageSize;
        if (data.size()%pageSize!=0){
            this.pageCount++;
        }
    }

    public List<T> getPageList(int pageNum){
        int fromIndex = (pageNum - 1) * pageSize;
        if (fromIndex >= data.size()){
            return Collections.emptyList();
        }
        int toIndex = pageNum * pageSize;
        if (toIndex >= data.size()){
            toIndex = data.size();
        }
        return data.subList(fromIndex,toIndex);
    }
}
