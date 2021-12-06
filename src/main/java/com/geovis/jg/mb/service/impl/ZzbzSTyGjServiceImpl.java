package com.geovis.jg.mb.service.impl;

import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.mb.mapper.ZzbzSTyGjMapper;
import com.geovis.jg.mb.model.ZzbzSQbMbzl;
import com.geovis.jg.mb.model.ZzbzSTyGj;
import com.geovis.jg.mb.service.ZzbzSTyGjService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ZzbzSTyGjServiceImpl implements ZzbzSTyGjService {

    @Resource
    private ZzbzSTyGjMapper zzbzSTyGjMapper;

	@Override
	public List<ZzbzSTyGj> queryByGjdqnm(ZzbzSTyGj t){
		return zzbzSTyGjMapper.queryByGjdqnm(t);
	}

	@Override
	public BaseResp updateGjdq(ZzbzSTyGj t) {
		zzbzSTyGjMapper.updateGjdq(t);
		return new BaseResp(200);
	}

	@Override
	public BaseResp addGjdq(ZzbzSTyGj t) {
		zzbzSTyGjMapper.addGjdq(t);
		return new BaseResp(200);
	}

	@Override
	public BaseResp deleteGjdq(String gjdqnm) {
		zzbzSTyGjMapper.deleteGjdq(gjdqnm);
		return new BaseResp(200);
	}

	@Override
	public List<ZzbzSQbMbzl> queryZlList(String mc) {
		return zzbzSTyGjMapper.queryZlList(mc);
	}

	@Override
	public BaseResp updateMbzl(ZzbzSQbMbzl t) {
		zzbzSTyGjMapper.updateMbzl(t);
		return new BaseResp(200);
	}

	@Override
	public BaseResp addMbzl(ZzbzSQbMbzl t) {
		zzbzSTyGjMapper.addMbzl(t);
		return new BaseResp(200);
	}

	@Override
	public BaseResp deleteMbzl(String gjdqnm) {
		zzbzSTyGjMapper.deleteMbzl(gjdqnm);
		return new BaseResp(200);
	}

}
