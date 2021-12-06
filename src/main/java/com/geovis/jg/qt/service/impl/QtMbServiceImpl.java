package com.geovis.jg.qt.service.impl;

import com.geovis.jg.mb.mapper.QbTjwjqkMbZmbMapper;
import com.geovis.jg.mb.model.*;
import com.geovis.jg.mb.mapper.QbTjwjqkMbqkMapper;
import com.geovis.jg.mb.mapper.SzdqMbYxMapper;
import com.geovis.jg.qt.model.QtMbQueryDto;
import com.geovis.jg.qt.model.QtTjwjqkMbDto;
import com.geovis.jg.qt.service.QtMbService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Service
public class QtMbServiceImpl implements QtMbService {

    @Resource
    private QbTjwjqkMbqkMapper qbTjwjqkMbqkMapper;

    @Resource
    private SzdqMbYxMapper szdqMbYxMapper;

    @Resource
    private QbTjwjqkMbZmbMapper qbTjwjqkMbZmbMapper;

    @Override
    public List<QbTjwjqkMbqk> mbqkAllInfoNew(String mbId) {
        return qbTjwjqkMbqkMapper.mbqkAllInfoFoQt(mbId);
    }

    @Override
    public List<QtTjwjqkMbDto> getMbList() {
        return qbTjwjqkMbqkMapper.getMbList();
    }

    @Override
    public List<QbTjwjqkMbMt> getMbAttachList(String mbnm) {

        return qbTjwjqkMbqkMapper.getMbAttachList(mbnm);
    }

    @Override
    public List<MbYx> getMbYxList(String mbnm, String yxId, String tifPath) {

        List<MbYx> list = szdqMbYxMapper.getMbYxList(mbnm,yxId);

        for (MbYx yx : list){
            yx.setYxt(tifPath + File.separator +  yx.getZjlj() + File.separator + yx.getYxt());
            yx.setMzt(tifPath + File.separator +  yx.getZjlj() + File.separator + yx.getMzt());
        }

        return list;
    }

    @Override
    public JSONObject getMbCountByTime(String startTime, String endTime) {

        JSONObject json = qbTjwjqkMbqkMapper.getMbCountByTime(startTime, endTime);


        return json;
    }

    @Override
    public PageInfo exactQueryMbList(QtMbQueryDto queryDto) {

        PageHelper.startPage(queryDto.getPageNumber() == null ? 0 : queryDto.getPageNumber(), queryDto.getPageSize() == null ? 0 : queryDto.getPageSize());
        List<QbTjwjqkMbqk> list = qbTjwjqkMbqkMapper.exactQueryMbList(queryDto);

        return new PageInfo<>(list);
    }

    @Override
    public QbTjwjqkMbqk getMbListByMbnm(String mbnm) {
        return qbTjwjqkMbqkMapper.getMbListByMbnm(mbnm);
    }

    @Override
    public List<MbTs> getTsList(String mbnm) {
        return qbTjwjqkMbZmbMapper.getTsList(mbnm);
    }

}
