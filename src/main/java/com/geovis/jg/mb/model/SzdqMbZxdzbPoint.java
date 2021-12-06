package com.geovis.jg.mb.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.awt.*;

@Table(name = "mb_zxdzb_point")
public class SzdqMbZxdzbPoint {

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "mbnm")
	private String mbnm;
	
	@Column(name = "zbxlxnm")
	private String zbxlxnm;

	@Column(name = "jdhzb")
	private String jdhzb;

	@Column(name = "wdzzb")
	private String wdzzb;
	
	@Column(name = "zbpnt")
	private Point zbpnt;

	@Column(name = "gc")
	private Double gc;

	@Column(name = "dwwc")
	private Double dwwc;

	@Column(name = "gwc")
	private Double gwc;
	
	@Transient
	private String polyzbs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMbnm() {
		return mbnm;
	}

	public void setMbnm(String mbnm) {
		this.mbnm = mbnm;
	}

	public String getZbxlxnm() {
		return zbxlxnm;
	}

	public void setZbxlxnm(String zbxlxnm) {
		this.zbxlxnm = zbxlxnm;
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

	public Point getZbpnt() {
		return zbpnt;
	}

	public void setZbpnt(Point zbpnt) {
		this.zbpnt = zbpnt;
	}

	public Double getGc() {
		return gc;
	}

	public void setGc(Double gc) {
		this.gc = gc;
	}

	public Double getDwwc() {
		return dwwc;
	}

	public void setDwwc(Double dwwc) {
		this.dwwc = dwwc;
	}

	public Double getGwc() {
		return gwc;
	}

	public void setGwc(Double gwc) {
		this.gwc = gwc;
	}

	public String getPolyzbs() {
		return polyzbs;
	}

	public void setPolyzbs(String polyzbs) {
		this.polyzbs = polyzbs;
	}
	
	
}