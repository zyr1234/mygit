package com.geovis.jg.mb.mapper;

import com.geovis.jg.mb.model.MbYx;
import com.geovis.jg.mb.model.MbZbYx;
import com.geovis.jg.mb.model.QbTjwjqkMbqk;
import com.geovis.jg.mb.model.SzdqMbYx;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SzdqMbYxMapper {
	
	List<MbYx> queryYxidByMbnm(String mbnm);
	
	int insertMbyx(MbYx t);

	int insertMbZbyx(MbZbYx t);

	int deleteMbyx(@Param(value = "mbnm") String mbnm, @Param(value = "yxid") String yxid);

	int updateMbyx(MbYx t);

	MbYx queryByYxid(@Param(value = "yxid") String yxid);

	List<MbYx> getMbYxList(@Param(value = "mbnm") String mbnm, @Param(value = "yxId") String yxId);

	void zbYxForMb(@Param(value = "yxId") String yxId,@Param(value = "mbnm") String mbnm);

	void zbYxRk(@Param(value = "record")MbZbYx yx);

	List<MbZbYx> getZbYxList(@Param(value = "yxMc")String yxMc,@Param(value = "mbMc") String mbMc);

	void zbYxForYx(@Param(value = "id") Integer id);

	MbZbYx getZbYxByZbYxId(@Param(value = "zbYxId")String zbYxId);

	void deleteZbYxByZbYxId(@Param(value = "zbYxId")String zbYxId);

	//2021-11-23 zcnh
	List<SzdqMbYx> selectDownMbYx(@Param(value = "ids")List<String> ids);

	void yxInsertOrUpdate(@Param(value = "list") List<SzdqMbYx> list);
}
