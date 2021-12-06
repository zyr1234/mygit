package com.geovis.jg.mb.service.impl;

import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.common.util.SSHRemoteUtils;
import com.geovis.jg.common.util.UUIDUtils;
import com.geovis.jg.mb.model.MbYx;
import com.geovis.jg.mb.mapper.SzdqMbYxMapper;
import com.geovis.jg.mb.model.MbZbYx;
import com.geovis.jg.mb.model.QbTjwjqkMbqk;
import com.geovis.jg.mb.service.QbTjwjqkMbService;
import com.geovis.jg.mb.service.SzdqMbYxService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class SzdqMbYxServiceImpl implements SzdqMbYxService {

	@Resource
	private SzdqMbYxMapper szdqMbYxMapper;

	@Resource
	private QbTjwjqkMbService qbTjwjqkMbService;

	@Value("${base.path}")
	private String basePath;

	@Value("${tifTojpg.ipAddress}")
	private String ipAddress;

	@Value("${tifTojpg.userName}")
	private String userName;

	@Value("${tifTojpg.password}")
	private String password;

	@Override
	public List<MbYx> queryYxidByMbnm(String mbnm) {
		List<MbYx> mbYxes;
		mbYxes =szdqMbYxMapper.queryYxidByMbnm(mbnm);
		return mbYxes;
	}

	@Override
	public BaseResp yxFileUpload(MultipartFile file, String mbnm) throws IOException {

//		//先放临时文件夹
//		String uuid = UUIDUtils.getUUID();
//		String path = basePath + "/temp/" + uuid + "/" + file.getOriginalFilename();
//		InputStream inputStream = file.getInputStream();
//		File baseFile = new File(path);
//		FileUtils.copyInputStreamToFile(inputStream, baseFile);
//		return new BaseResp(200 ,path);

		//先放临时文件夹
		String uuid = UUIDUtils.getUUID();
		//String yxtPath = basePath + "/temp/" + uuid + "/" + file.getOriginalFilename();

		QbTjwjqkMbqk mbInfo = qbTjwjqkMbService.getMBInfoByMbnm(mbnm);
		Calendar instance = Calendar.getInstance();

		String yxtPath = "/yx/" + mbInfo.getGjdqmc() + "/" + mbInfo.getMbmc() + "/" +
				+ instance.get(Calendar.YEAR) + "/"+ (instance.get(Calendar.MONTH)+1) + "/" + instance.get(Calendar.DATE) + "/" + uuid + "/" + file.getOriginalFilename();
        String absYxtPath =  basePath+yxtPath;
		InputStream inputStream = file.getInputStream();
		File baseFile = new File(absYxtPath);
		FileUtils.copyInputStreamToFile(inputStream, baseFile);
		String uuidFileName = yxtPath.substring(yxtPath.lastIndexOf("/") + 1);
		//连接服务器tif转jpg
		SSHRemoteUtils.getInstance().sshRemoteCallLogin(ipAddress, userName, password);
		SSHRemoteUtils.getInstance().execCommand("docker run --rm -v " + absYxtPath.substring(0, absYxtPath.lastIndexOf("/")) + ":/home osgeo/gdal:latest gdal_translate -of JPEG -outsize 1000 0 /home/" + uuidFileName + " /home/" + uuidFileName.replace(".tif",".jpg") + " -co WORLDFILE=NO");

		MbYx yx = new MbYx();
		yx.setSjsj(new Date());
		yx.setYxid(uuid);
		yx.setMbnm(mbnm);
		//yx.setYxt("/temp/" + uuid + "/" + file.getOriginalFilename());
		yx.setYxt(yxtPath);
		yx.setYxmc(uuidFileName);
		yx.setSlt(yx.getYxt().replace(".tif",".jpg"));
		//yx.setCjsj(new Date());
		//yx.setStatus(1);
		szdqMbYxMapper.insertMbyx(yx);

		return new BaseResp(200);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResp yxDataSave(MbYx yx) throws IOException {

		if (yx.getYxt() == null) {
			return  new BaseResp(500, "文件路径为空");
		}

		String uuidFileName = null;
		String path = null;
		String yxid = UUIDUtils.getUUID();
		try {
			String yxtPath = yx.getYxt();
			String uuidFilePath = yxtPath.substring(0,yxtPath.lastIndexOf("/"));
			uuidFileName = yxtPath.substring(yxtPath.lastIndexOf("/") + 1);
			//连接服务器tif转jpg
			SSHRemoteUtils.getInstance().sshRemoteCallLogin(ipAddress, userName, password);
			SSHRemoteUtils.getInstance().execCommand("docker run --rm -v " + uuidFilePath + ":/home osgeo/gdal:latest gdal_translate -of JPEG -outsize 1000 0 /home/" + uuidFileName + " /home/" + uuidFileName.replace(".tif",".jpg") + " -co WORLDFILE=NO");

			QbTjwjqkMbqk mbInfo = qbTjwjqkMbService.getMBInfoByMbnm(yx.getMbnm());
			Calendar instance = Calendar.getInstance();
			//根据客户的要求，这里可以添加 在yx目录后面添加/年/月/日  三层目录**//*

			path = basePath + "/yx/" + mbInfo.getGjdqmc() + "/" + mbInfo.getMbmc() + "/" +
					+ instance.get(Calendar.YEAR) + "/"+ (instance.get(Calendar.MONTH)+1) + "/" + instance.get(Calendar.DATE) + "/" + yxid + "/" + uuidFileName;

			File baseTifFile = new File(path);
			File tifFile = new File(yxtPath);
			FileUtils.copyFile(tifFile, baseTifFile);

			File baseJpgFile = new File(path.replace(".tif",".jpg"));
			File jpgFile = new File(yxtPath.replace(".tif",".jpg"));
			FileUtils.copyFile(jpgFile, baseJpgFile);

			//删除临时文件夹
			File[] tempFile = new File(uuidFilePath).listFiles();
			for (File file : tempFile) {
				file.delete();
			}
			new File(uuidFilePath).delete();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 将影像数据录入到数据库
		MbYx mbyxnew = new MbYx();
		mbyxnew.setMbnm(yx.getMbnm());
		mbyxnew.setSjsj(new Date());
		mbyxnew.setYxid(yxid);
		mbyxnew.setYxmc(uuidFileName);
		mbyxnew.setYxt(path.replace(basePath,""));
		mbyxnew.setSlt(mbyxnew.getYxt().replace(".tif",".jpg"));
		szdqMbYxMapper.insertMbyx(mbyxnew);

		return new BaseResp(200, "success");
	}

	@Override
	public BaseResp yxDataUpdate(MbYx yx) {
		szdqMbYxMapper.updateMbyx(yx);
		return new BaseResp(200);
	}

	@Override
	public BaseResp yxZbUpload(MultipartFile file) throws IOException {

		//先放临时文件夹
		String uuid = UUIDUtils.getUUID();
		String yxtPath = basePath + "/temp/" + uuid + "/" + file.getOriginalFilename();
		InputStream inputStream = file.getInputStream();
		File baseFile = new File(yxtPath);
		FileUtils.copyInputStreamToFile(inputStream, baseFile);
		String uuidFileName = yxtPath.substring(yxtPath.lastIndexOf("/") + 1);
		//连接服务器tif转jpg
		SSHRemoteUtils.getInstance().sshRemoteCallLogin(ipAddress, userName, password);
		SSHRemoteUtils.getInstance().execCommand("docker run --rm -v " + yxtPath.substring(0, yxtPath.lastIndexOf("/")) + ":/home osgeo/gdal:latest gdal_translate -of JPEG -outsize 1000 0 /home/" + uuidFileName + " /home/" + uuidFileName.replace(".tif",".jpg") + " -co WORLDFILE=NO");

		MbZbYx yx = new MbZbYx();
		yx.setSjsj(new Date());
		yx.setYxId(uuid);
		yx.setYxt("/temp/" + uuid + "/" + file.getOriginalFilename());
		yx.setYxmc(uuidFileName);
		yx.setSlt(yx.getYxt().replace(".tif",".jpg"));
		yx.setCjsj(new Date());
		yx.setStatus(1);
		szdqMbYxMapper.insertMbZbyx(yx);

		return new BaseResp(200);
	}

	@Override
	public BaseResp zbYxForMb(String yxId, String mbnm) {

		szdqMbYxMapper.zbYxForMb(yxId, mbnm);

		return new BaseResp(200);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResp zbYxRk(MbZbYx yx) throws IOException {

		if (yx.getYxt() == null) {
			return  new BaseResp(500, "文件路径为空");
		}

		String yxtPath = basePath + "/" + yx.getYxt();
		String uuidFilePath = yxtPath.substring(0,yxtPath.lastIndexOf("/"));
		String uuidFileName = yxtPath.substring(yxtPath.lastIndexOf("/") + 1);

		QbTjwjqkMbqk mbInfo = qbTjwjqkMbService.getMBInfoByMbnm(yx.getMbnm());

		Calendar instance = Calendar.getInstance();
		//根据客户的要求，这里可以添加 在yx目录后面添加/年/月/日  三层目录**//*
		String path = basePath + "/yx/" + mbInfo.getGjdqmc() + "/" + mbInfo.getMbmc() + "/"
				+ instance.get(Calendar.YEAR) + "/"+ (instance.get(Calendar.MONTH)+1) + "/" + instance.get(Calendar.DATE) + "/" + yx.getYxId() + "/" + uuidFileName;

		try {
			File baseTifFile = new File(path);
			File tifFile = new File(yxtPath);
			FileUtils.copyFile(tifFile, baseTifFile);

			File baseJpgFile = new File(path.replace(".tif",".jpg"));
			File jpgFile = new File(yxtPath.replace(".tif",".jpg"));
			FileUtils.copyFile(jpgFile, baseJpgFile);

			//删除临时文件夹
			File[] tempFile = new File(uuidFilePath).listFiles();
			for (File file : tempFile) {
				file.delete();
			}
			new File(uuidFilePath).delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		yx.setSjsj(new Date());
		yx.setYxmc(uuidFileName);
		yx.setYxt(path.replace(basePath, ""));
		yx.setSlt(yx.getYxt().replace(".tif",".jpg"));
		yx.setCjsj(new Date());
		yx.setStatus(0);
		yx.setZbType(0);
		szdqMbYxMapper.zbYxRk(yx);

		//将整编影像图入库
		szdqMbYxMapper.zbYxForYx(yx.getId());

		return new BaseResp(200);
	}

	@Override
	public PageInfo getZbYxList(String yxMc, String mbMc, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<MbZbYx> mbqkAllInfo = szdqMbYxMapper.getZbYxList(yxMc, mbMc);
		PageInfo info = new PageInfo<>(mbqkAllInfo);
		return info;
	}

	@Override
	public BaseResp getZbYxByZbYxId(String zbYxId) {
		MbZbYx yx = szdqMbYxMapper.getZbYxByZbYxId(zbYxId);
		return new BaseResp(200,yx);
	}

	@Override
	public BaseResp deleteZbYxByZbYxId(String zbYxId) {
		szdqMbYxMapper.deleteZbYxByZbYxId(zbYxId);
		return new BaseResp(200,"success");
	}

}
