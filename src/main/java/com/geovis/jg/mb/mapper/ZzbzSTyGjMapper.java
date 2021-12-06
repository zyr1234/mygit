package com.geovis.jg.mb.mapper;

import com.geovis.jg.mb.model.ZzbzSQbMbzl;
import com.geovis.jg.mb.model.ZzbzSTyGj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZzbzSTyGjMapper{

	/**
	 * 根据国家地区内码查询国家名称
	 ***/
	List<ZzbzSTyGj> queryByGjdqnm(ZzbzSTyGj t);


	void updateGjdq(ZzbzSTyGj t);

	void addGjdq(ZzbzSTyGj t);

	void deleteGjdq(@Param("gjdqnm") String gjdqnm);

	List<ZzbzSQbMbzl> queryZlList(String mc);

	void updateMbzl(ZzbzSQbMbzl t);

	void addMbzl(ZzbzSQbMbzl t);

	void deleteMbzl(@Param("mbzlnm") String mbzlnm);
}