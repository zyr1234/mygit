package com.geovis.jg.mb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="qb_tjwjqk_mb")
public class QbTjwjqkMb {
    private String mbnm;

    private String mbxh;

    private String mbmc;

    private String mbjc;

    private String sjsj; //数据库为Date，这里使用字符串

    private String gxsj;  //数据库为Date，这里使用字符串

    private String mgsxnm;

    private String mbztnm;

    private String qbdwnm;

    private String ssmbq;

    private String mbnms;

    private String mbmcs;

    private String pxh;

    private Integer count;

    private String metaid;
    /**
     * 开始时间
     */
    @Transient
    private String kssj;
    /**
     * 结束时间
     */
    @Transient
    private String jssj;
    /**
     * 国家地区内码
     */
    @Transient
    private String gjdqnm;

    /**
     * 国家地区名称
     */
    @Transient
    private String gjdqmc;

    /**
     * 目标种类内码
     */
    @Transient
    private String mbzlnm;

    /**
     * 目标种类内码
     */
    @Transient
    private String mbzlmc;

    /**
     * 数据来源
     */
    @Transient
    private String sjly;

    /**
     * 层级关系
     */
    @Transient
    private Integer level;
    @Transient
    private String sid;
    @Transient
    private String pid;


    /**
     * 子节点个数
     */
    @Transient
    private Integer childSize;

    private Integer isMove;
}