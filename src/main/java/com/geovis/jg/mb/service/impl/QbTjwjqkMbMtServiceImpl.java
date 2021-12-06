package com.geovis.jg.mb.service.impl;

import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.common.domain.Page;
import com.geovis.jg.common.util.UUIDUtils;
import com.geovis.jg.mb.mapper.QbTjwjqkMbMtMapper;
import com.geovis.jg.mb.model.QbTjwjqkMbMt;
import com.geovis.jg.mb.model.QbTjwjqkMbqk;
import com.geovis.jg.mb.service.QbTjwjqkMbMtService;
import com.geovis.jg.mb.service.QbTjwjqkMbService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

@Service
public class QbTjwjqkMbMtServiceImpl implements QbTjwjqkMbMtService {

    @Resource
    private QbTjwjqkMbMtMapper qbTjwjqkMbMtMapper;

	@Resource
	private QbTjwjqkMbService qbTjwjqkMbService;

	@Value("${base.path}")
	private String basePath;

	public Page queryByMbnmMts(QbTjwjqkMbMt t, Page page) {
		
		com.github.pagehelper.Page p = null;
		int pageNum = null != page.getPage() ? page.getPage() : page.getStart() / page.getLength();
		int pageSize = null != page.getRows() ? Integer.parseInt(String.valueOf(page.getRows())) : page.getLength();
		p = PageHelper.startPage(pageNum, pageSize);
		List<QbTjwjqkMbMt> mts = qbTjwjqkMbMtMapper.queryByMbnmMts(t);
		page.setContent(mts);
		if (null != p){
			page.setTotal(p.getTotal());
			page.setPage(p.getPageNum());
			page.setRows(p.getPageSize());
			page.setPageNum(p.getPages());
		}
		return page;
	}



    public QbTjwjqkMbMt selectByMtnm(Integer mtnm) {
    	return qbTjwjqkMbMtMapper.selectByMtnm(mtnm);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResp mtFileUpload(MultipartFile file, QbTjwjqkMbMt mt) throws IOException {

		QbTjwjqkMbqk mbInfo = qbTjwjqkMbService.getMBInfoByMbnm(mt.getMbnm());

		/** 根据客户的要求，这里可以添加 在yx目录后面添加/年/月/日  三层目录**/
		Calendar instance = Calendar.getInstance();
		String path = "/mt" + "/" + mbInfo.getGjdqmc() + "/" + mbInfo.getMbmc()
				+ "/" + instance.get(Calendar.YEAR) + "/"+ (instance.get(Calendar.MONTH)+1) + "/" + instance.get(Calendar.DATE) + "/"  + UUIDUtils.getUUID() + "/" + file.getOriginalFilename();
    	mt.setPath(path);
		mt.setWjm(file.getOriginalFilename());
		File baseFile = new File(basePath+path);//basePath是配置的路径前缀；path是数据库中存储的相对路径
		InputStream inputStream = file.getInputStream();
		FileUtils.copyInputStreamToFile(inputStream, baseFile);
		inputStream.close();
        qbTjwjqkMbMtMapper.insertMbMt(mt);
    	return new BaseResp(null,"操作成功",200);
	}

	@Override
	public BaseResp<Integer> mtFileDelete(Integer mtnm) {
		QbTjwjqkMbMt mt = qbTjwjqkMbMtMapper.selectByMtnm(mtnm);
		String path = basePath + "/" + mt.getPath();
		File file = new File(path.substring(0, path.lastIndexOf("/")));
		File[] files = file.listFiles();
		for (File f: files) {
			f.delete();
		}
		file.delete();

		qbTjwjqkMbMtMapper.deleteByMtnm(mtnm);

		return new BaseResp(200,"success");
	}

	@Override
	public BaseResp mtFileUpdate(QbTjwjqkMbMt mt){
		qbTjwjqkMbMtMapper.mtFileUpdate(mt);

		return new BaseResp(200,"success");
	}

	/**
	 * 20190110新增：根据媒体类型名称分页查询媒体基本信息（不包含mtdx字段）
	 * **/
    public Page queryByMtlxmc(String mtlxmc,Page page) throws Exception{
    	com.github.pagehelper.Page p = null;
		int pageNum = null != page.getPage() ? page.getPage() : page.getStart() / page.getLength();
		int pageSize = null != page.getRows() ? Integer.parseInt(String.valueOf(page.getRows())) : page.getLength();
		p = PageHelper.startPage(pageNum, pageSize);
		List<QbTjwjqkMbMt> list = qbTjwjqkMbMtMapper.queryByMtlxmc(mtlxmc);
		page.setContent(list);
		if (null != p){
			page.setTotal(p.getTotal());
			page.setPage(p.getPageNum());
			page.setRows(p.getPageSize());
			page.setPageNum(p.getPages());
		}
		return page;
    }
}
