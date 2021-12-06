package com.geovis.jg.mb.mapper;

//import com.dbs.common.mapper.BaseMapper;
//import com.dbs.epzh.mb.domain.QbTjwjqkMbDown;
//import com.dbs.epzh.mb.domain.QbTjwjqkMbMt;
//import com.dbs.epzh.mb.domain.QbTjwjqkMbqkDown;
import com.geovis.jg.mb.model.QbTjwjqkMbDown;
import com.geovis.jg.mb.model.QbTjwjqkMbMt;
import com.geovis.jg.mb.model.QbTjwjqkMbqkDown;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//public interface MbDownLoadMapper extends BaseMapper<QbTjwjqkMbMt> {
public interface MbDownLoadMapper {
	List<String> getMbFjPath(@Param(value = "ids") List<String> ids);

	List<QbTjwjqkMbDown> selectDownMbInfo(@Param(value = "ids") List<String> ids);

	List<QbTjwjqkMbqkDown> selectDownMbJbqk(@Param(value = "ids") List<String> ids);

	List<QbTjwjqkMbMt> selectDownMbFj(@Param(value = "ids") List<String> ids);

    String getGjqdByNm(@Param(value = "gjdqnm") String gjdqnm);

	void mbInsertOrUpdate(@Param(value = "list") List<QbTjwjqkMbDown> list);

	void qkInsertOrUpdate(@Param(value = "list") List<QbTjwjqkMbqkDown> list);

	void mtmbInsertOrUpdate(@Param(value = "list") List<QbTjwjqkMbMt> list);
}