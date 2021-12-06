package com.geovis.jg.mb.service.impl;

//import com.dbs.epzh.mb.domain.QbTjwjqkMbDown;
//import com.dbs.epzh.mb.domain.QbTjwjqkMbMt;
//import com.dbs.epzh.mb.domain.QbTjwjqkMbqkDown;
//import com.dbs.epzh.mb.mapper.MbDownLoadMapper;
//import com.dbs.szdq.mb.domain.SzdqMbYx;
//import com.dbs.szdq.mb.mapper.SzdqMbYxMapper;
//import com.dbs.szdq.mb.service.MbDownUpLoadService;
import com.geovis.jg.mb.mapper.MbDownLoadMapper;
import com.geovis.jg.mb.mapper.SzdqMbYxMapper;
import com.geovis.jg.mb.model.QbTjwjqkMbDown;
import com.geovis.jg.mb.model.QbTjwjqkMbMt;
import com.geovis.jg.mb.model.QbTjwjqkMbqkDown;
import com.geovis.jg.mb.model.SzdqMbYx;
import com.geovis.jg.mb.service.MbDownUpLoadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unchecked")
@Service
public class MbDownUpLoadServiceImpl implements MbDownUpLoadService {

	@Resource
	private MbDownLoadMapper mapper;

	@Resource
	private SzdqMbYxMapper szdqMbYxMapper;


	@Override
	public List<String> downLoad(List<String> ids, String basePath) {

		List<String> list = mapper.getMbFjPath(ids);
		List<String> pList =new ArrayList<>();
		for (String s : list) {
			pList.add(basePath + s);
		}
		return pList;
	}

	@Override
	public List<SzdqMbYx> selectDownMbYx(List<String> ids) {
		return szdqMbYxMapper.selectDownMbYx(ids);
	}

	@Override
	public List<QbTjwjqkMbDown> selectDownMbInfo(List<String> ids) {
		return mapper.selectDownMbInfo(ids);
	}

	@Override
	public List<QbTjwjqkMbqkDown> selectDownMbJbqk(List<String> ids) {
		return mapper.selectDownMbJbqk(ids);
	}

	@Override
	public List<QbTjwjqkMbMt> selectDownMbFj(List<String> ids) {
		return mapper.selectDownMbFj(ids);
	}

	@Override
	public String getGjqdByNm(String gjdqnm) {

		return mapper.getGjqdByNm(gjdqnm);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUpLoadMb(List<QbTjwjqkMbDown> mb, List<QbTjwjqkMbqkDown> qk, List<QbTjwjqkMbMt> mt, List<SzdqMbYx> yx) {

		if (mb.size() > 0)  mapper.mbInsertOrUpdate(mb);
		if (qk.size() > 0) mapper.qkInsertOrUpdate(qk);
		if (mt.size() > 0) mapper.mtmbInsertOrUpdate(mt);
		if (yx.size() > 0) szdqMbYxMapper.yxInsertOrUpdate(yx);

	}
}
