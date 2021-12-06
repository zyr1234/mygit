package com.geovis.jg.mb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MbTs {
    private Integer id;

    private String jlid;

    private String mbnm;

    private String zzsj;

    private String jdhzb;

    private String wdzzb;

    private String bz;

    private String yssjb;

    private Date sjsj;

    private String sjly;
}