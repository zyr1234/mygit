package com.geovis.jg.mb.mapper;


import com.geovis.jg.common.mapper.BaseMapper;
import com.geovis.jg.mb.model.*;
import com.geovis.jg.qt.model.QtMbQueryDto;
import com.geovis.jg.qt.model.QtTjwjqkMbDto;
import org.apache.ibatis.annotations.Param;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface QbTjwjqkMbqkMapper extends BaseMapper<QbTjwjqkMbqk> {

	QbTjwjqkMbqk mbqkInfo(@Param("mbnm") String mbnm);

	List<QbTjwjqkMbqk> listAllInfo(QbTjwjqkMbqk record);

	List<QbTjwjqkMbqk> mbqkAllInfo() ;

	List<QbTjwjqkMbqk> getMbqkAllInfo(@Param("record") QbTjwjqkMbqk record , @Param(("startdate")) String startdate,@Param("enddate") String enddate);

    List<ZzbzSTyGj> mbgjdqAllInfo(@Param(value = "nm") String nm);

	List<ZzbzSQbMbzl> mbzlAllInfoMyNm(@Param(value = "nm") String nm, @Param(value = "cj") String cj);

	List<QtTjwjqkMbDto> getMbList();

	List<QbTjwjqkMbqk> mbqkAllInfoFoQt(@Param(value = "mbId") String mbId);

	List<QbTjwjqkMbMt> getMbAttachList(@Param(value = "mbnm") String mbnm);

	JSONObject getMbCountByTime(@Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime);

	List<QbTjwjqkMbqk> exactQueryMbList(QtMbQueryDto queryDto);

	List<QbTjwjqkMbqk> likeQueryMbList(QtMbQueryDto queryDto);

    List<ZzbzSQbMtlx> getMbMtlx(@Param(value = "mc")String mc);

	List<ZzbzSQbMtgs> getMbMtgs(@Param(value = "mc")String mc);

	List<ZzbzSQbZbxlx> getMbZbx(@Param(value = "mc")String mc);

	QbTjwjqkMbqk getMbListByMbnm(@Param(value = "mbnm") String mbnm);

    ExtensionJsonTab getMbFjSx(@Param(value = "jsonId") Long jsonId);

	void insertMbFjSx(ExtensionJsonTab jsonTab);

	void updateJsonIdByNm(@Param(value = "mbnm")String mbnm, @Param(value = "jsonId") Long jsonId);

	void updateMbFjSx(ExtensionJsonTab jsonTab);

	void deleteMbFjSx(@Param(value = "jsonId")Long jsonId);

	QbTjwjqkMbqk getMbFjSxByMbnm(@Param(value = "mbnm") String mbnm);
}