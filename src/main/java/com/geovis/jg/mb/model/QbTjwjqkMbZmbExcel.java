package com.geovis.jg.mb.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

public class QbTjwjqkMbZmbExcel implements Serializable {


    private String mbnm;

    @Excel(name = "目标名称")
    private String mbmc;


    private String zmbnm;

    @Excel(name = "子目标名称")
    private String zmbmc;


    private String zmblbnm;


    private String sjzmbnm;


    private String mc;

    @Excel(name = "主要情况")
    private String zyqk;

    @Excel(name = "经度/横坐标")
    private String jdhzb;

    @Excel(name = "纬度/纵坐标")
    private String wdzzb;

    private String sjly;

    @Excel(name = "作战能力")
    private String zznl;

    private Date sjsj;

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getZmbmc() {
        return zmbmc;
    }

    public void setZmbmc(String zmbmc) {
        this.zmbmc = zmbmc;
    }

    public String getJdhzb() {
        return jdhzb;
    }

    public void setJdhzb(String jdhzb) {
        this.jdhzb = jdhzb;
    }

    public String getWdzzb() {
        return wdzzb;
    }

    public void setWdzzb(String wdzzb) {
        this.wdzzb = wdzzb;
    }

    public String getMbnm() {
        return mbnm;
    }

    public void setMbnm(String mbnm) {
        this.mbnm = mbnm;
    }

    public String getMbmc() {
        return mbmc;
    }

    public void setMbmc(String mbmc) {
        this.mbmc = mbmc;
    }

    public String getZmbnm() {
        return zmbnm;
    }

    public void setZmbnm(String zmbnm) {
        this.zmbnm = zmbnm;
    }

    public String getZmblbnm() {
        return zmblbnm;
    }

    public void setZmblbnm(String zmblbnm) {
        this.zmblbnm = zmblbnm;
    }

    public String getSjzmbnm() {
        return sjzmbnm;
    }

    public void setSjzmbnm(String sjzmbnm) {
        this.sjzmbnm = sjzmbnm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getZyqk() {
        return zyqk;
    }

    public void setZyqk(String zyqk) {
        this.zyqk = zyqk;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    public Date getSjsj() {
        return sjsj;
    }

    public void setSjsj(Date sjsj) {
        this.sjsj = sjsj;
    }

    public String getZznl() {
        return zznl;
    }

    public void setZznl(String zznl) {
        this.zznl = zznl;
    }
}