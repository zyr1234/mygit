package com.geovis.jg.mb.mapper;

import com.geovis.jg.common.mapper.BaseMapper;
import com.geovis.jg.mb.model.MbTs;
import com.geovis.jg.mb.model.QbTjwjqkMbZmbExcel;
import com.geovis.jg.mb.model.QbTjwjqkMbZmb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QbTjwjqkMbZmbMapper extends BaseMapper<QbTjwjqkMbZmb> {

	List<QbTjwjqkMbZmb> queryZmbInfo(QbTjwjqkMbZmb t) throws Exception;

	List<QbTjwjqkMbZmb> getListByMbnm(@Param("mbnm") String mbnm);

	List<QbTjwjqkMbZmb> getListByMc(@Param("mc") String mc, @Param("fmc") String fmc);

	void insertZmbZbSelective(QbTjwjqkMbZmb zmb);

	void updateZmbZbByPrimaryKey(QbTjwjqkMbZmb zmb);

	void updateZmbByPrimaryKey(QbTjwjqkMbZmb zmb);

	String getZmbZbByZmbnm(@Param("zmbnm") Integer zmbnm);

	void insertZmbInfo(QbTjwjqkMbZmb zmb);

	int deleteZmb(QbTjwjqkMbZmb zmb);

	int deleteZmbzb(QbTjwjqkMbZmb zmb);

	String getZmbnmByZmbmc(@Param("zmbmc") String zmbmc);

	List<QbTjwjqkMbZmbExcel> exportFileZMb(@Param("mc") String mc, @Param("fmc") String fmc);

    List<MbTs> getTsList(@Param("mbnm") String mbnm);

}