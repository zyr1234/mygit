package com.geovis.jg.mb.mapper;


import com.geovis.jg.mb.model.QbTjwjqkMbMt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QbTjwjqkMbMtMapper  {
	List<QbTjwjqkMbMt> queryByMbnmMts(QbTjwjqkMbMt t)  ;

	QbTjwjqkMbMt selectByMtnm(Integer mtnm);

	/**
	 *20190110新增
	 * **/
	List<QbTjwjqkMbMt> queryByMtlxmc(String mtlxmc)  ;

	int deleteByMtnm(@Param(value = "mtnm") Integer mtnm);

	int deleteMtByMbnm(@Param(value = "mbnm") String mbnm);

	void insertMbMt(QbTjwjqkMbMt mt);

	void mtFileUpdate(QbTjwjqkMbMt mt);
}