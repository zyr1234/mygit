package com.geovis.jg.qt.service;

import com.geovis.jg.mb.model.*;
import com.geovis.jg.qt.model.QtMbQueryDto;
import com.geovis.jg.qt.model.QtTjwjqkMbDto;
import com.github.pagehelper.PageInfo;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface QtMbService {
    List<QbTjwjqkMbqk> mbqkAllInfoNew(String mbId);

    List<QtTjwjqkMbDto> getMbList();

    List<QbTjwjqkMbMt> getMbAttachList(String mbnm);

    List<MbYx> getMbYxList(String mbnm, String yxId, String tifPath);

    JSONObject getMbCountByTime(String startTime, String endTime);

    PageInfo exactQueryMbList(QtMbQueryDto queryDto);

    QbTjwjqkMbqk getMbListByMbnm(String mbnm);

    List<MbTs> getTsList(String mbnm);
}
