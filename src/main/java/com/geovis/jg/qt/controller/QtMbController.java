package com.geovis.jg.qt.controller;

import com.geovis.jg.common.domain.GlobalConstants;
import com.geovis.jg.mb.model.*;
import com.geovis.jg.qt.model.QtMbQueryDto;
import com.geovis.jg.qt.model.QtTjwjqkMbDto;
import com.geovis.jg.qt.service.QtMbService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/qtmb")
@Api(tags = "qt球相关")
public class QtMbController {

    @Resource
    private QtMbService qtMbService;

    private String tifPath= GlobalConstants.TIFPATH;

    /**
     * 20210609新增：qt球获取目标列表
     * **/
    @ApiOperation(value = "qt球获取目标列表", notes = "qt球获取目标列表")
    @RequestMapping(value = "/getMbList", method = RequestMethod.GET)
    public List<QtTjwjqkMbDto> getMbList() {
        return qtMbService.getMbList();
    }


    /**
     * 20210609新增：qt球获取目标详情
     * **/
    @ApiOperation(value = "qt球根据mbnm获取目标详情", notes = "qt球根据mbnm获取目标详情")
    @RequestMapping(value = "/getMbQkList", method = RequestMethod.GET)
    public List<QbTjwjqkMbqk> getMbQkList(@RequestParam(value = "mbnm")String mbnm) {
        return qtMbService.mbqkAllInfoNew(mbnm);
    }


    /**
     * 20210609新增：qt球获取目标附件
     * **/
    @ApiOperation(value = "qt球获取目标附件", notes = "qt球获取目标附件")
    @RequestMapping(value = "/getMbAttachList", method = RequestMethod.GET)
    public List<QbTjwjqkMbMt> getMbAttachList(@RequestParam(value = "mbnm")String mbnm) {
        return qtMbService.getMbAttachList(mbnm);
    }


    /**
     * 20210609新增：qt球获取目标影像
     * **/
    @ApiOperation(value = "qt球获取目标影像", notes = "qt球获取目标影像")
    @RequestMapping(value = "/getMbYxList", method = RequestMethod.GET)
    public List<MbYx> getMbYxList(@RequestParam(value = "mbnm",required = false)String mbnm,
                                  @RequestParam(value = "yxId",required = false)String yxId) {
        return qtMbService.getMbYxList(mbnm,yxId,tifPath);
    }


    /**
     * 20210621新增：根据时间获取目标数量
     * **/
    @ApiOperation(value = "获取时间区间内目标新增和更新情况", notes = "获取时间区间内目标新增和更新情况")
    @RequestMapping(value = "/getMbCountByTime", method = RequestMethod.GET)
    public JSONObject getMbCountByTime(@RequestParam(value = "startTime",required = false)String startTime,
                                       @RequestParam(value = "endTime",required = false)String endTime) {
        return qtMbService.getMbCountByTime(startTime,endTime);
    }

    /**
     * 20210621新增：查询获取目标
     * **/
    @ApiOperation(value = "查询获取目标", notes = "查询获取目标")
    @RequestMapping(value = "/queryMbList", method = RequestMethod.GET)
    public PageInfo queryMbList(QtMbQueryDto queryDto) {
        return qtMbService.exactQueryMbList(queryDto);
    }


    /**
     * 20210015新增：空天图像--根据目标内码获取目标八大要素
     * **/
    @ApiOperation(value = "空天图像--根据目标内码获取目标八大要素", notes = "空天图像--根据目标内码获取目标八大要素")
    @RequestMapping(value = "/getMbListByMbnm", method = RequestMethod.GET)
    public QbTjwjqkMbqk getMbListByMbnm(@RequestParam(value = "mbnm")String mbnm) {
        return qtMbService.getMbListByMbnm(mbnm);
    }

    /**
     * 20210015新增：目标历史航迹点查询接口
     * **/
    @RequestMapping(value = "/getTsList", method = RequestMethod.GET)
    @ApiOperation(value = "空天图像--目标历史航迹点查询接口", notes = "空天图像--目标历史航迹点查询接口")
    public List<MbTs> getTsList(@RequestParam(value = "mbnm") String mbnm) {
        return qtMbService.getTsList(mbnm);
    }


}
