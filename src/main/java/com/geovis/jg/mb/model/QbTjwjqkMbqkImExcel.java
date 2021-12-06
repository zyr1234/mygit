package com.geovis.jg.mb.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QbTjwjqkMbqkImExcel implements Serializable{


    private String mbnm;

    @Excel(name = "目标名称")
    private String mbmc;

    @Excel(name = "目标种类")
    private String mbzlnm;

    //@Excel(name = "目标等级")
    private String mbdjnm;

    //@Excel(name = "敏感属性")
    private String mgsxnm;

    //@Excel(name = "情报单位")
    private String qbdwnm;

    @Excel(name = "国家地区")
    private String gjdqnm;

    //@Excel(name = "目标状态")
    private String mbztnm;

    //@Excel(name = "目标范围长")
    private Integer mbfwc;

   // @Excel(name = "目标范围宽")
    private Integer mbfwk;

    //@Excel(name = "目标面积")
    private Integer mbmj;

    //@Excel(name = "目标中心定位点")
    private String mbzxdwd;

    //@Excel(name = "所属目标区")
    private String ssmbq;

    @Excel(name = "目标概况")
    private String mbgk;

    //@Excel(name = "主要情况")
    private String zyqk;

    @Excel(name = "地位作用")
    private String dwzy;

    @Excel(name = "防御设施")
    private String fyss;

    @Excel(name = "组成分布")
    private String zcfb;

    @Excel(name = "气象水文")
    private String qxsw;

    @Excel(name = "地形环境")
    private String dxhj;

    @Excel(name = "结构性质")
    private String jgxz;

    @Excel(name = "要害部位")
    private String yhbw;

    //@Excel(name = "整编说明")
    private String zbsm;

    //@Excel(name = "定位精度")
    private Integer dwjd;

    //@Excel(name = "发生时间")
    private Date fssj;

    //@Excel(name = "发生作战时间")
    private Date fszzsj;

    //@Excel(name = "发现时间")
    private Date fxsj;

    //@Excel(name = "发现作战时间")
    private Date fxzzsj;

    //@Excel(name = "数据来源")
    private String sjly;

    //@Excel(name = "数据时间")
    private Date sjsj;

    //@Excel(name = "能力概述")
    private String nlgs;

    //@Excel(name = "坐标系类型")
    private String zbxlxnm;

    @Excel(name = "经度/横坐标")
    private String jdhzb;

    @Excel(name = "纬度/纵坐标")
    private String wdzzb;

    //@Excel(name = "高程")
    private Double gc;

    //@Excel(name = "点位误差")
    private Double dwwc;

    //@Excel(name = "高误差")
    private Double gwc;

    private Integer isMove;

    private String errorMessage;

}