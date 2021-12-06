package com.geovis.jg.mb.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "mb_yx")
public class SzdqMbYx {

	@Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MBNM")
    private String mbnm;
    
    /*
     * 影像ID
     */
    @Column(name = "YXID")
    private String yxid;
    
    @Column(name = "YXMC")
    private String yxmc;
    
    @Column(name = "SJSJ")
    private Date sjsj;

    @Column(name  = "UPDATE_TIME")
    private String update_time;
    
    /*
     * 中间相对路径
     */
    @Column(name  = "ZJLJ")
    private String zjlj;
    
    /*
     * 缩略图
     */
    @Column(name  = "SLT")
    private String slt;
    
    /*
     * 影像图
     */
    @Column(name  = "YXT")
    private String yxt;
    
    /*
     * 拇指图
     */
    @Column(name  = "MZT")
    private String mzt;

    /*
     * RST图
     */
    @Column(name  = "CJT")
    private String cjt;

    /*
     * 侦查时间
     */
    @Column(name  = "ZCSJ")
    private String zcsj;
    
    /*
     * rst是否存在flag
     */
    private String cjtexistflag;
    
    
	public SzdqMbYx() {
		super();
	}

	public SzdqMbYx(Integer id, String mbnm, String yxid, String yxmc, Date sjsj, String update_time, String cjt,
                    String zcsj) {
		super();
		this.id = id;
		this.mbnm = mbnm;
		this.yxid = yxid;
		this.yxmc = yxmc;
		this.sjsj = sjsj;
		this.update_time = update_time;
		this.cjt = cjt;
		this.zcsj = zcsj;
	}

	public SzdqMbYx(Integer id, String mbnm, String yxid, String yxmc, Date sjsj, String update_time, String zjlj,
                    String slt, String yxt, String mzt, String cjt, String zcsj, String cjtexistflag) {
		super();
		this.id = id;
		this.mbnm = mbnm;
		this.yxid = yxid;
		this.yxmc = yxmc;
		this.sjsj = sjsj;
		this.update_time = update_time;
		this.zjlj = zjlj;
		this.slt = slt;
		this.yxt = yxt;
		this.mzt = mzt;
		this.cjt = cjt;
		this.zcsj = zcsj;
		this.cjtexistflag = cjtexistflag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMbnm() {
		return mbnm;
	}

	public void setMbnm(String mbnm) {
		this.mbnm = mbnm;
	}

	public String getYxid() {
		return yxid;
	}

	public void setYxid(String yxid) {
		this.yxid = yxid;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getYxmc() {
		return yxmc;
	}

	public void setYxmc(String yxmc) {
		this.yxmc = yxmc;
	}

	public Date getSjsj() {
		return sjsj;
	}

	public void setSjsj(Date sjsj) {
		this.sjsj = sjsj;
	}

	public String getZjlj() {
		return zjlj;
	}

	public void setZjlj(String zjlj) {
		this.zjlj = zjlj;
	}

	public String getSlt() {
		return slt;
	}

	public void setSlt(String slt) {
		this.slt = slt;
	}

	public String getYxt() {
		return yxt;
	}

	public void setYxt(String yxt) {
		this.yxt = yxt;
	}

	public String getMzt() {
		return mzt;
	}

	public void setMzt(String mzt) {
		this.mzt = mzt;
	}

	public String getCjt() {
		return cjt;
	}

	public void setCjt(String cjt) {
		this.cjt = cjt;
		if(cjt!=null){
			this.cjtexistflag="true";
		}else{
			this.cjtexistflag="false";
		}
	}

	public String getZcsj() {
		return zcsj;
	}

	public void setZcsj(String zcsj) {
		this.zcsj = zcsj;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCjtexistflag() {
		return cjtexistflag;
	}

	public void setCjtexistflag(String cjtexistflag) {
		this.cjtexistflag = cjtexistflag;
	}
        
}
