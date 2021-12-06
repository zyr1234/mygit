package com.geovis.jg.mb.mapper;

import com.geovis.jg.mb.model.QbTjwjqkMbZmlImExcel;
import com.geovis.jg.mb.model.QbTjwjqkMbqkImExcel;
import com.geovis.jg.mb.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QbTjwjqkMbMapper {

	Integer queryMaxMbId(QbTjwjqkMb mb);

	List<QbTjwjqkMbqkSxDto> getAllZlMap();

	List<QbTjwjqkMbqkSxDto> getAllDjMap();

	List<QbTjwjqkMbqkSxDto> getAllMgsxMap();

	List<QbTjwjqkMbqkSxDto> getAllQbdwMap();

	List<QbTjwjqkMbqkSxDto> getAllGjdqMap();

	List<QbTjwjqkMbqkSxDto> getAllmbztMap();

	List<QbTjwjqkMbqkSxDto> getAllZbxlxMap();

	List<String> getMbnmByMbmc(@Param(value = "mbmc") String mbmc);

	Integer insertOrUpdate(@Param(value = "list") List<QbTjwjqkMbqkImExcel> list);

	Integer insertOrUpdateZxzb(@Param(value = "list") List<QbTjwjqkMbqkImExcel> list);

	List<QbTjwjqkMbqkImExcel> getMbDetailExcel(QbTjwjqkMb qbTjwjqkMb);

	Integer insertOrUpdateMbzml(@Param(value = "list") List<QbTjwjqkMbZmlImExcel> list);

	Integer insertOrUpdateZxzbMbzml(@Param(value = "list") List<QbTjwjqkMbZmlImExcel> list);

	Integer insertOrUpdateMbInfozml(@Param(value = "mbmc") String mbmc,@Param(value = "isMove") Integer isMove, @Param(value = "mbnm") String mbnm);

	QbTjwjqkMbqk getMBInfoByMbnm(@Param(value = "mbnm") String mbnm);

	void insterMb(QbTjwjqkMbqkImExcel vo);

	void insterMbVo(QbTjwjqkMb vo);

    List<QbTjwjqkMb> queryList(@Param(value = "mc") String mc);

	void deleteByMbnm(@Param(value = "mbnm")String mbnm);

	void deleteZxdzbByMbnm(@Param(value = "mbnm") String mbnm);

	void deleteMbqkByMbnm(@Param(value = "mbnm")String mbnm);

	void deleteTsByMbnm(@Param(value = "mbnm") String mbnm);

    List<MbTsExcel> getTsList(@Param(value = "mbnm")String mbnm);

	void insterMbTs(@Param(value = "ts")MbTsExcel ts);
}