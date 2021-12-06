package com.geovis.jg.mb.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MbTsExcel implements Serializable {

    private String mbnm;

    @Excel(name = "目标名称")
    private String mbmc;

    @Excel(name = "记录ID")
    private String jlid;

    @Excel(name = "侦照时间")
    private String zzsj;

    @Excel(name = "经度横坐标")
    private String jdhzb;

    @Excel(name = "纬度纵坐标")
    private String wdzzb;

    @Excel(name = "备注")
    private String bz;

    @Excel(name = "原始数据包")
    private String yssjb;

    @Excel(name = "数据时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    private Date sjsj;

    @Excel(name = "数据来源")
    private String sjly;

    private String errorMessage;
}