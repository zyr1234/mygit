package com.geovis.jg.mb.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QbTjwjqkMbZmlImExcel implements Serializable{


    private String mbnm;

    @Excel(name = "目标名称")
    private String mbmc;

    @Excel(name = "目标种类")
    private String mbzlnm;

    @Excel(name = "国家地区")
    private String gjdqnm;

    @Excel(name = "经度/横坐标")
    private String jdhzb;

    @Excel(name = "纬度/纵坐标")
    private String wdzzb;

    @Excel(name = "目标属性")
    private Integer isMove;

    private String errorMessage;

}