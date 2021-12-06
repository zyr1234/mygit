package com.geovis.jg.mb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QbTjwjqkMbqk implements Serializable {
    private String mbnm;

    private String mbzlnm;

    private String mbdjnm;

    private String mgsxnm;

    private String qbdwnm;

    private String gjdqnm;

    private String mbztnm;

    private BigDecimal mbfwc;

    private BigDecimal mbfwk;

    private BigDecimal mbmj;

    private String mbzxdwd;

    private String ssmbq;

    private String nlgs;

    private String mbgk;

    private String zyqk;

    private String zcfb;

    private String jgxz;

    private String yhbw;

    private String dwzy;

    private String dxhj;

    private String fyss;

    private String zbsm;

    private BigDecimal dwjd;

    private Date fssj;

    private Date fszzsj;

    private Date fxsj;

    private Date fxzzsj;

    private String sjly;

    private Date sjsj;

    /**
     * 目标名称
     */
    @Transient
    private String mbmc;

    /**
     * 目标简称
     */
    @Transient
    private String mbjc;

    /**
     * 目标名称
     */
    @Transient
    private String mbxh;

    /**
     * 目标种类名称
     */
    @Transient
    private String mbzlmc;

    /**
     * 敏感属性名称
     */
    @Transient
    private String mgsxmc;

    /**
     * 情报单位名称
     */
    @Transient
    private String qbdwmc;

    /**
     * 国家地区名称
     */
    @Transient
    private String gjdqmc;
    @Transient
    private String disgjdqmc;
    @Transient
    private String dismbzlmc;
    /**
     * 目标等级名称
     */
    @Transient
    private String mbdjmc;

    /**
     * 目标状态名称
     */
    @Transient
    private String mbztmc;


    /**
     * 内码和列名对应
     */
    @Transient
    private Map<String, String> nmMap;

    @Transient
    private String jdhzb;

    @Transient
    private String wdzzb;

    @Transient
    private String gc;

    /**
     * 坐标系名称
     **/
    @Transient
    private String zbxmc;

    /**
     * 目标经度/横坐标
     **/
    @Transient
    private String lon;

    //气象水文
    private String qxsw;

    /**
     * 目标纬度/纵坐标
     **/
    @Transient
    private String lat;

    private Integer isMove;


    /**
     * 目标标识码
     **/
    private String mbIdentify;

    /**
     * 扩展字段Id
     **/
    private Long extensionJsonId;
}