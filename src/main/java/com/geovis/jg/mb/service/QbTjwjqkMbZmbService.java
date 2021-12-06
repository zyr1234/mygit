package com.geovis.jg.mb.service;

import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.mb.model.QbTjwjqkMbZmbExcel;
import com.geovis.jg.mb.model.QbTjwjqkMbZmb;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface QbTjwjqkMbZmbService {
	
	List<QbTjwjqkMbZmb> queryZmbInfo(QbTjwjqkMbZmb t) throws Exception;

    List<QbTjwjqkMbZmb> getListByMbnm(String mbnm);

    PageInfo getListByMc(String mc, String fmc, Integer pageNum, Integer pageSize);

    BaseResp updateZmbInfo(QbTjwjqkMbZmb zmb);

    Object uploadFileZMb(List<QbTjwjqkMbZmbExcel> list);

    BaseResp deleteZmb(QbTjwjqkMbZmb zmb);

    List<QbTjwjqkMbZmbExcel> exportFileZMb(String mc, String fmc);

    PageInfo getTsList(String mc, Integer pageNum, Integer pageSize);
}
