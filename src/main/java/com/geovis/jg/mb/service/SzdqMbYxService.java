package com.geovis.jg.mb.service;

import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.mb.model.MbYx;
import com.geovis.jg.mb.model.MbZbYx;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface SzdqMbYxService {
	
	List<MbYx> queryYxidByMbnm(String mbnm) throws Exception;

    BaseResp yxFileUpload(MultipartFile file, String mbnm) throws IOException;

	BaseResp yxDataSave(MbYx yx) throws IOException;

	BaseResp yxDataUpdate(MbYx yx);

	BaseResp yxZbUpload(MultipartFile file) throws IOException;

	BaseResp zbYxForMb(String yxId, String mbnm);

	BaseResp zbYxRk(MbZbYx yx) throws IOException;

	PageInfo getZbYxList(String yxMc, String mbMc, Integer pageNum, Integer pageSize);

	BaseResp getZbYxByZbYxId(String zbYxId);

	BaseResp deleteZbYxByZbYxId(String zbYxId);
}
