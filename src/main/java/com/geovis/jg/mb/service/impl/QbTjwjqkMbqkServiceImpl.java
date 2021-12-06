package com.geovis.jg.mb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.common.domain.Page;
import com.geovis.jg.mb.model.*;
import com.geovis.jg.mb.mapper.QbTjwjqkMbqkMapper;
import com.geovis.jg.mb.service.QbTjwjqkMbqkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QbTjwjqkMbqkServiceImpl implements QbTjwjqkMbqkService {

    @Resource
    private QbTjwjqkMbqkMapper qbTjwjqkMbqkMapper;

	@Override
	public QbTjwjqkMbqk mbqkInfo(String mbnm) {
		QbTjwjqkMbqk tmb = qbTjwjqkMbqkMapper.mbqkInfo(mbnm);
		return tmb;
	}


    public Page pageAllInfo(QbTjwjqkMbqk record, Page page) {
        com.github.pagehelper.Page p = null;
        if(null != page.getStart() && null != page.getLength()) {
            int pageNum = null!=page.getPage()?page.getPage():page.getStart()/page.getLength();
            int pageSize = null!=page.getRows()?Integer.parseInt(String.valueOf(page.getRows())):page.getLength();
            p = PageHelper.startPage(pageNum, pageSize);
        }
        page.setRows(qbTjwjqkMbqkMapper.listAllInfo(record));
        if(null != p)
            page.setTotal(p.getTotal());
        return page;
    }


	@Override
	public List<QbTjwjqkMbqk> mbqkAllInfo() {
    	return qbTjwjqkMbqkMapper.mbqkAllInfo();
    }


    @Override
    public PageInfo getMbqkAllInfo(QbTjwjqkMbqk record,String startdate,String enddate,  Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<QbTjwjqkMbqk> mbqkAllInfo = qbTjwjqkMbqkMapper.getMbqkAllInfo(record,startdate,enddate);
        PageInfo info = new PageInfo<>(mbqkAllInfo);
        return info;
    }

//    @Override
//    public PageInfo getMbqkAllInfo(QbTjwjqkMbqk record, Integer pageNum, Integer pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<QbTjwjqkMbqk> mbqkAllInfo = qbTjwjqkMbqkMapper.getMbqkAllInfo(record);
//        PageInfo info = new PageInfo<>(mbqkAllInfo);
//        return info;
//    }

    @Override
    public List<ZzbzSTyGj> mbgjdqAllInfo(String nm) {
        return qbTjwjqkMbqkMapper.mbgjdqAllInfo(nm);
    }

    @Override
    public List<ZzbzSQbMbzl> mbzlAllInfoMyNm(String nm, String cj) {
        return qbTjwjqkMbqkMapper.mbzlAllInfoMyNm(nm, cj);
    }

    @Override
    public List<ZzbzSQbMtlx> getMbMtlx(String mc) {
        return qbTjwjqkMbqkMapper.getMbMtlx(mc);
    }

    @Override
    public List<ZzbzSQbMtgs> getMbMtgs(String mc) {
        return qbTjwjqkMbqkMapper.getMbMtgs(mc);
    }

    @Override
    public List<ZzbzSQbZbxlx> getMbZbx(String mc) {
        return qbTjwjqkMbqkMapper.getMbZbx(mc);
    }

    @Override
    public BaseResp getMbFjSx(String mbnm) {
        QbTjwjqkMbqk tmb = qbTjwjqkMbqkMapper.getMbFjSxByMbnm(mbnm);
        if (tmb == null || tmb.getExtensionJsonId() == null) {
            return new BaseResp(null,"无数据",200);
        }

        ExtensionJsonTab jsonTab = qbTjwjqkMbqkMapper.getMbFjSx(tmb.getExtensionJsonId());
        List<ExtensionJson> extensionJsons = JSONArray.parseArray(jsonTab.getJsonListString(), ExtensionJson.class);
        jsonTab.setJsonList(extensionJsons);
        return new BaseResp(jsonTab,"成功",200);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResp mbFjSxChange(ExtensionJsonTab jsonTab) {

        QbTjwjqkMbqk tmb = qbTjwjqkMbqkMapper.getMbFjSxByMbnm(jsonTab.getMbnm());
        jsonTab.setJsonListString(JSON.toJSONString(jsonTab.getJsonList()));

	    if (tmb.getExtensionJsonId() == null) {
	        if (jsonTab.getJsonList() == null || jsonTab.getJsonList().size() == 0) {
                return new BaseResp(null,"无数据操作",200);
            }
	        //新增
            jsonTab.setType(new Byte("1"));
            qbTjwjqkMbqkMapper.insertMbFjSx(jsonTab);
            qbTjwjqkMbqkMapper.updateJsonIdByNm(jsonTab.getMbnm(), jsonTab.getId());
        } else {
            jsonTab.setId(tmb.getExtensionJsonId());
            if (jsonTab.getJsonList() == null || jsonTab.getJsonList().size() == 0) {
                //删除
                qbTjwjqkMbqkMapper.updateJsonIdByNm(jsonTab.getMbnm(), null);
                qbTjwjqkMbqkMapper.deleteMbFjSx(tmb.getExtensionJsonId());
            } else {
                //修改
                qbTjwjqkMbqkMapper.updateMbFjSx(jsonTab);
            }

        }

	    return new BaseResp(null,"操作成功",200);
    }
}
