package com.geovis.jg.mb.service;

import com.alibaba.fastjson.JSONObject;
import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.mb.model.*;

import java.util.List;

public interface QbTjwjqkMbService {

	JSONObject uploadFile(List<QbTjwjqkMbqkImExcel> list) ;

	List<QbTjwjqkMbqkImExcel> getMbDetailExcel(QbTjwjqkMb qbTjwjqkMb);

	Object uploadFileMbzml(List<QbTjwjqkMbZmlImExcel> list) ;

	List<QbTjwjqkMbZmlImExcel> getMbZmlDetailExcel(QbTjwjqkMb qbTjwjqkMb);

	BaseResp saveZml(QbTjwjqkMbZmlImExcel excel) ;

	BaseResp saveMbqk(List<QbTjwjqkMbqkImExcel> list) ;

	QbTjwjqkMbqk getMBInfoByMbnm(String mbnm);

    List<QbTjwjqkMb> queryList(String mc);

    BaseResp deleteMb(String mbnm);

	List<MbTsExcel> getMbTsDetailExcel(String mbnm);

	List<MbTsExcel> uploadMbTs(List<MbTsExcel> list);
}
