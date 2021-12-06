package com.geovis.jg.mb.service;

import com.alibaba.fastjson.JSONObject;
import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.common.domain.Page;
import com.geovis.jg.mb.model.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface QbTjwjqkMbqkService {

	QbTjwjqkMbqk mbqkInfo(String mbnm);

	Page pageAllInfo(QbTjwjqkMbqk record, Page page) ;

	List<QbTjwjqkMbqk> mbqkAllInfo() ;

	PageInfo getMbqkAllInfo(QbTjwjqkMbqk record,String startdate,String enddate,  Integer pageNum, Integer pageSize);

    List<ZzbzSTyGj> mbgjdqAllInfo(String nm);

	List<ZzbzSQbMbzl> mbzlAllInfoMyNm(String nm, String cj);

    List<ZzbzSQbMtlx> getMbMtlx(String mc);

	List<ZzbzSQbMtgs> getMbMtgs(String mc);

    List<ZzbzSQbZbxlx> getMbZbx(String mc);

	BaseResp getMbFjSx(String mbnm);

	BaseResp mbFjSxChange(ExtensionJsonTab jsonTab);
}
