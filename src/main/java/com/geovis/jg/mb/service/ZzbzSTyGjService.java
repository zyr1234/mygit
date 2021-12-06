package com.geovis.jg.mb.service;

import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.mb.model.ZzbzSQbMbzl;
import com.geovis.jg.mb.model.ZzbzSTyGj;

import java.util.List;

public interface ZzbzSTyGjService {

	List<ZzbzSTyGj> queryByGjdqnm(ZzbzSTyGj t);

	BaseResp updateGjdq(ZzbzSTyGj t);

	BaseResp addGjdq(ZzbzSTyGj t);

	BaseResp deleteGjdq(String gjdqnm);

	List<ZzbzSQbMbzl> queryZlList(String mc);

	BaseResp updateMbzl(ZzbzSQbMbzl t);

	BaseResp addMbzl(ZzbzSQbMbzl t);

	BaseResp deleteMbzl(String gjdqnm);
}
