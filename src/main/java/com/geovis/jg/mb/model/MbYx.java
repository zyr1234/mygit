package com.geovis.jg.mb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MbYx {
    private Integer id;

    private String mbnm;

    private String yxid;

    private String yxmc;

    private String updateTime;

    private Date sjsj;

    private String zjlj;

    private String slt;

    private String yxt;

    private String mzt;

    private String cjt;

    private String zcsj;

    private String yyxid;

    private Date rectime;

    private Integer zbxlxnm;

    private String ygtmc;

    private String ptmc;

    private String cgqlx;

    private String txcpdj;

    private Long txhs;

    private Long txls;

    private BigDecimal xyfbl;

    private String cxbd;

    private Integer txws;

    private String dtty;

    private String txjzxjd;

    private String txjzxwd;

    private String txzsjjd;

    private String txzsjwd;

    private String txyxjjd;

    private String txyxjwd;

    private BigDecimal dwwc;

    private Date sysj;

    private String zzdw;

    private Date zzrq;

    private String msxx;
    
    /*
     * rst是否存在flag
     */
    private String cjtexistflag;
    
    
//	public SzdqMbYx() {
//		super();
//	}

//	public SzdqMbYx(Integer id, String mbnm, String yxid, String yxmc, Date sjsj, String update_time, String cjt,
//			String zcsj) {
//		super();
//		this.id = id;
//		this.mbnm = mbnm;
//		this.yxid = yxid;
//		this.yxmc = yxmc;
//		this.sjsj = sjsj;
//		this.update_time = update_time;
//		this.cjt = cjt;
//		this.zcsj = zcsj;
//	}
//
//	public SzdqMbYx(Integer id, String mbnm, String yxid, String yxmc, Date sjsj, String update_time, String zjlj,
//			String slt, String yxt, String mzt, String cjt, String zcsj, String cjtexistflag) {
//		super();
//		this.id = id;
//		this.mbnm = mbnm;
//		this.yxid = yxid;
//		this.yxmc = yxmc;
//		this.sjsj = sjsj;
//		this.update_time = update_time;
//		this.zjlj = zjlj;
//		this.slt = slt;
//		this.yxt = yxt;
//		this.mzt = mzt;
//		this.cjt = cjt;
//		this.zcsj = zcsj;
//		this.cjtexistflag = cjtexistflag;
//	}


        
}
