package com.geovis.jg.mb.service;

/*import com.dbs.epzh.mb.domain.QbTjwjqkMbDown;
import com.dbs.epzh.mb.domain.QbTjwjqkMbMt;
import com.dbs.epzh.mb.domain.QbTjwjqkMbqkDown;
import com.dbs.szdq.mb.domain.SzdqMbYx;*/
import com.geovis.jg.mb.model.QbTjwjqkMbDown;
import com.geovis.jg.mb.model.QbTjwjqkMbMt;
import com.geovis.jg.mb.model.QbTjwjqkMbqkDown;
import com.geovis.jg.mb.model.SzdqMbYx;

import java.util.List;

public interface MbDownUpLoadService {

	List<String> downLoad(List<String> ids, String basePath);

	List<SzdqMbYx> selectDownMbYx(List<String> ids);

	List<QbTjwjqkMbDown> selectDownMbInfo(List<String> ids);

	List<QbTjwjqkMbqkDown> selectDownMbJbqk(List<String> ids);

	List<QbTjwjqkMbMt> selectDownMbFj(List<String> ids);

	String getGjqdByNm(String gjdqnm);

	void saveUpLoadMb(List<QbTjwjqkMbDown> mb, List<QbTjwjqkMbqkDown> qk, List<QbTjwjqkMbMt> mt, List<SzdqMbYx> yx);
}
