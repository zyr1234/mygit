package com.geovis.jg.mb.service;

import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.common.domain.Page;
import com.geovis.jg.mb.model.QbTjwjqkMbMt;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface QbTjwjqkMbMtService {
	
	Page queryByMbnmMts(QbTjwjqkMbMt t, Page page) ;

	QbTjwjqkMbMt selectByMtnm(Integer mtnm) ;

    BaseResp mtFileUpload(MultipartFile file, QbTjwjqkMbMt mt) throws IOException;

	BaseResp<Integer> mtFileDelete(Integer mtnm);

	BaseResp mtFileUpdate(QbTjwjqkMbMt mt);
}
