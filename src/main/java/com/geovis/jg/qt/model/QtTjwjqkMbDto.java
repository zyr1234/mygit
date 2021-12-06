package com.geovis.jg.qt.model;

import java.io.Serializable;


public class QtTjwjqkMbDto implements Serializable {

    private String mbnm;

    private String mbxh;

    private String mbmc;

    private String mbjc;
    
    private String sjsj;

    private String gxsj;


    /**
     * 国家地区内码
     */
    private String gjdqnm;

    /**
     * 国家地区名称
     */
    private String gjdqmc;

    /**
     * 目标种类内码
     */
    private String mbzlnm;

    /**
     * 目标种类内码
     */
    private String mbzlmc;

    /**
     * 经度/横坐标
     */
    private String jdhzb;

    /**
     * 高程
     */
    private String gc;

    /**
     * 纬度/纵坐标
     */
    private String wdzzb;

    private String isMove;

    public String getMove() {
        return isMove;
    }

    public void setMove(String move) {
        isMove = move;
    }

    public String getMbnm() {
        return mbnm;
    }

    public void setMbnm(String mbnm) {
        this.mbnm = mbnm;
    }

    public String getMbxh() {
        return mbxh;
    }

    public void setMbxh(String mbxh) {
        this.mbxh = mbxh;
    }

    public String getMbmc() {
        return mbmc;
    }

    public void setMbmc(String mbmc) {
        this.mbmc = mbmc;
    }

    public String getMbjc() {
        return mbjc;
    }

    public void setMbjc(String mbjc) {
        this.mbjc = mbjc;
    }

    public String getSjsj() {
        return sjsj;
    }

    public void setSjsj(String sjsj) {
        this.sjsj = sjsj;
    }

    public String getGxsj() {
        return gxsj;
    }

    public void setGxsj(String gxsj) {
        this.gxsj = gxsj;
    }

    public String getGjdqnm() {
        return gjdqnm;
    }

    public void setGjdqnm(String gjdqnm) {
        this.gjdqnm = gjdqnm;
    }

    public String getGjdqmc() {
        return gjdqmc;
    }

    public void setGjdqmc(String gjdqmc) {
        this.gjdqmc = gjdqmc;
    }

    public String getMbzlnm() {
        return mbzlnm;
    }

    public void setMbzlnm(String mbzlnm) {
        this.mbzlnm = mbzlnm;
    }

    public String getMbzlmc() {
        return mbzlmc;
    }

    public void setMbzlmc(String mbzlmc) {
        this.mbzlmc = mbzlmc;
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

    public String getGc() {
        return gc;
    }

    public void setGc(String gc) {
        this.gc = gc;
    }
}