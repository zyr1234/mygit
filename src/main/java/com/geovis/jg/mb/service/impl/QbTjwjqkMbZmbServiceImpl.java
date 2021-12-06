package com.geovis.jg.mb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.common.domain.Example;
import com.geovis.jg.common.mapper.BaseMapper;
import com.geovis.jg.common.service.impl.BaseServiceImpl;
import com.geovis.jg.common.util.StringUtils;
import com.geovis.jg.mb.model.MbTs;
import com.geovis.jg.mb.model.QbTjwjqkMbZmbExcel;
import com.geovis.jg.mb.model.QbTjwjqkMbZmb;
import com.geovis.jg.mb.mapper.QbTjwjqkMbMapper;
import com.geovis.jg.mb.mapper.QbTjwjqkMbZmbMapper;
import com.geovis.jg.mb.service.QbTjwjqkMbZmbService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QbTjwjqkMbZmbServiceImpl implements QbTjwjqkMbZmbService {

    @Resource
    private QbTjwjqkMbZmbMapper qbTjwjqkMbZmbMapper;
    @Resource
    private QbTjwjqkMbMapper qbTjwjqkMbMapper;
    
    @Override
    public List<QbTjwjqkMbZmb> queryZmbInfo(QbTjwjqkMbZmb t) throws Exception{
    	return qbTjwjqkMbZmbMapper.queryZmbInfo(t);
    }

    @Override
    public List<QbTjwjqkMbZmb> getListByMbnm(String mbnm) {
        List<QbTjwjqkMbZmb> list = qbTjwjqkMbZmbMapper.getListByMbnm(mbnm);
        return list;
    }

    @Override
    public PageInfo getListByMc(String mc, String fmc, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<QbTjwjqkMbZmb> list = qbTjwjqkMbZmbMapper.getListByMc(mc, fmc);
        PageInfo<QbTjwjqkMbZmb> info = new PageInfo<>(list);
        return info;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResp updateZmbInfo(QbTjwjqkMbZmb zmb) {

        if (zmb.getZmbnm() == null) {

            //新增
            qbTjwjqkMbZmbMapper.insertZmbInfo(zmb);
            if (StringUtils.isNotBlank(zmb.getJdhzb()) && StringUtils.isNotBlank(zmb.getJdhzb())) {
                qbTjwjqkMbZmbMapper.insertZmbZbSelective(zmb);
            }

        } else {
            //修改
            zmb.setZmblbnm("D01");
            qbTjwjqkMbZmbMapper.updateZmbByPrimaryKey(zmb);
            if (StringUtils.isNotBlank(zmb.getJdhzb()) && StringUtils.isNotBlank(zmb.getJdhzb())) {
                String zmbnm = qbTjwjqkMbZmbMapper.getZmbZbByZmbnm(zmb.getZmbnm());
                if (StringUtils.isBlank(zmbnm)) {
                    qbTjwjqkMbZmbMapper.insertZmbZbSelective(zmb);
                }

                qbTjwjqkMbZmbMapper.updateZmbZbByPrimaryKey(zmb);
            }
        }

        return new BaseResp<>(200,"success");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object uploadFileZMb(List<QbTjwjqkMbZmbExcel> list) {

        ArrayList errorList = new ArrayList<>();

        for(QbTjwjqkMbZmbExcel excel : list) {
            StringBuilder errorMessage = new StringBuilder();
            List<String> listMbnm = qbTjwjqkMbMapper.getMbnmByMbmc(excel.getMbmc());
            if (listMbnm.size() > 1) {
                errorMessage.append("目标名称存在多个,请检查;");
            }
            String mbnm = listMbnm.size() > 0 ? listMbnm.get(0) : null;
            if (mbnm == null) {
                errorMessage.append("目标不存在,请检查;");
            }
            if (StringUtils.isBlank(excel.getJdhzb()) || StringUtils.isBlank(excel.getWdzzb())) {
                errorMessage.append("经纬度为必填选项,请检查;");
            }
            if (StringUtils.isBlank(errorMessage)) {
                String zmbnm = qbTjwjqkMbZmbMapper.getZmbnmByZmbmc(excel.getZmbmc());
                QbTjwjqkMbZmb zmb = new QbTjwjqkMbZmb();
                BeanUtils.copyProperties(excel,zmb);
                zmb.setZmbnm(StringUtils.isBlank(zmbnm) ? null : Integer.valueOf(zmbnm));
                zmb.setMbnm(mbnm);
                zmb.setMc(excel.getZmbmc());
                updateZmbInfo(zmb);
            } else {
                excel.setErrorMessage(errorMessage.toString());
                errorList.add(excel);
            }


        }

        list.removeAll(errorList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",errorList);
        jsonObject.put("errorSize",errorList.size());
        jsonObject.put("successSize",list.size());
        jsonObject.put("allSize",list.size() + errorList.size());

        return jsonObject;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResp deleteZmb(QbTjwjqkMbZmb zmb) {
        qbTjwjqkMbZmbMapper.deleteZmbzb(zmb);
        qbTjwjqkMbZmbMapper.deleteZmb(zmb);
        return new BaseResp<>(200,"success");
    }

    @Override
    public List<QbTjwjqkMbZmbExcel> exportFileZMb(String mc, String fmc) {
        return qbTjwjqkMbZmbMapper.exportFileZMb(mc, fmc);
    }

    @Override
    public PageInfo getTsList(String mbnm, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MbTs> list = qbTjwjqkMbZmbMapper.getTsList(mbnm);
        PageInfo<MbTs> info = new PageInfo<>(list);
        return info;
    }
}
