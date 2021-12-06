package com.geovis.jg.mb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.common.util.FileUtils;
import com.geovis.jg.mb.mapper.QbTjwjqkMbMtMapper;
import com.geovis.jg.mb.mapper.QbTjwjqkMbZmbMapper;
import com.geovis.jg.mb.mapper.SzdqMbYxMapper;
import com.geovis.jg.mb.model.QbTjwjqkMbZmlImExcel;
import com.geovis.jg.mb.model.QbTjwjqkMbqkImExcel;
import com.geovis.jg.mb.model.*;
import com.geovis.jg.mb.mapper.QbTjwjqkMbMapper;
import com.geovis.jg.mb.service.QbTjwjqkMbService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QbTjwjqkMbServiceImpl implements QbTjwjqkMbService {

	@Resource
	private QbTjwjqkMbMapper qbTjwjqkMbMapper;

	@Resource
	private SzdqMbYxMapper szdqMbYxMapper;

	@Resource
	private QbTjwjqkMbMtMapper qbTjwjqkMbMtMapper;

	@Resource
	private QbTjwjqkMbZmbMapper qbTjwjqkMbZmbMapper;

	@Value("${base.path}")
	private String basePath;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject uploadFile(List<QbTjwjqkMbqkImExcel> list) {

		//检查目标和内码
		List<QbTjwjqkMbqkImExcel> errorList = checkMbAndNm(list);

		if (CollectionUtils.isNotEmpty(list)) {
			//目标详情
			qbTjwjqkMbMapper.insertOrUpdate(list);
			//中心坐标点
			qbTjwjqkMbMapper.insertOrUpdateZxzb(list);

		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list",errorList);
		jsonObject.put("errorSize",errorList.size());
		jsonObject.put("successSize",list.size());
		jsonObject.put("allSize",list.size() + errorList.size());

		return jsonObject;
	}

	//@Override
	public List<QbTjwjqkMbqkImExcel> getMbDetailExcel(QbTjwjqkMb qbTjwjqkMb) {
		return qbTjwjqkMbMapper.getMbDetailExcel(qbTjwjqkMb);
	}

	//@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResp saveZml(QbTjwjqkMbZmlImExcel vo) {
		String mbnm = vo.getMbnm();
		QbTjwjqkMb mb = new QbTjwjqkMb();
		if (StringUtils.isBlank(mbnm)){

			mb.setGjdqnm(vo.getGjdqnm());
			mb.setMbmc(vo.getMbmc());
			mb.setSjsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			/*errorMessage.append("目标不存在,请前往**新增或导入目标;  ");*/
			Integer maxMbId = qbTjwjqkMbMapper.queryMaxMbId(mb);
			String gjdqnm = mb.getGjdqnm();
			if (null == maxMbId) {
				// 没有此国家地区内码
				mb.setMbnm(gjdqnm + "005" + String.format("%011d", 1));
			} else {
				// 已有此国家地区内码
				mb.setMbnm(gjdqnm + "005" + String.format("%011d", maxMbId + 1));
			}

			// 判断当前生成NM数据库是否已占用 如果占用 直接循环查询 至
			if (StringUtils.isNotBlank(mb.getMbnm())) {
				String addMbnm = mb.getMbnm();
				//QbTjwjqkMb tmb = this.selectByPrimaryKey(addMbnm);
				QbTjwjqkMb tmb = null;
				boolean isNotnull = false;
				if (null != tmb) {
					isNotnull = true;
					int i = (maxMbId == null ? 0 : maxMbId) + 1;
					while (isNotnull) {
						i++;
						addMbnm = mb.getGjdqnm() + "005" + String.format("%09d", i);
						//tmb = this.selectByPrimaryKey(addMbnm);
						if (null == tmb) {
							isNotnull = false;
						}
					}
					mb.setMbnm(addMbnm);
				}

			}
			vo.setMbnm(mb.getMbnm());
			//super.insertSelective(mb);
		} else {
			qbTjwjqkMbMapper.insertOrUpdateMbInfozml(vo.getMbmc() , vo.getIsMove(), mbnm);
			vo.setMbnm(mbnm);
		}
		List<QbTjwjqkMbZmlImExcel> list = new ArrayList<>();
		list.add(vo);
		if (CollectionUtils.isNotEmpty(list)) {

			//目标详情
			qbTjwjqkMbMapper.insertOrUpdateMbzml(list);

			//中心坐标点
			qbTjwjqkMbMapper.insertOrUpdateZxzbMbzml(list);

		}

		return new BaseResp<>(200);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResp saveMbqk(List<QbTjwjqkMbqkImExcel> list) {

		QbTjwjqkMbqkImExcel vo = list.get(0);
		String mbnm = vo.getMbnm();
		QbTjwjqkMb mb = new QbTjwjqkMb();
		if (StringUtils.isBlank(mbnm)){

			mb.setGjdqnm(vo.getGjdqnm());
			mb.setMbmc(vo.getMbmc());
			mb.setSjsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			/*errorMessage.append("目标不存在,请前往**新增或导入目标;  ");*/
			Integer maxMbId = qbTjwjqkMbMapper.queryMaxMbId(mb);
			String gjdqnm = mb.getGjdqnm();
			if (null == maxMbId) {
				// 没有此国家地区内码
				mb.setMbnm(gjdqnm + "005" + String.format("%011d", 1));
			} else {
				// 已有此国家地区内码
				mb.setMbnm(gjdqnm + "005" + String.format("%011d", maxMbId + 1));
			}

			// 判断当前生成NM数据库是否已占用 如果占用 直接循环查询 至
			if (StringUtils.isNotBlank(mb.getMbnm())) {
				String addMbnm = mb.getMbnm();
				//QbTjwjqkMb tmb = this.selectByPrimaryKey(addMbnm);
				QbTjwjqkMb tmb  = null;
				boolean isNotnull = false;
				if (null != tmb) {
					isNotnull = true;
					int i = maxMbId == null ? 0 : maxMbId + 1;
					while (isNotnull) {
						i++;
						addMbnm = mb.getGjdqnm() + "005" + String.format("%09d", i);
						//tmb = this.selectByPrimaryKey(addMbnm);
						if (null == tmb) {
							isNotnull = false;
						}
					}
					mb.setMbnm(addMbnm);
				}
			}
			vo.setMbnm(mb.getMbnm());
			qbTjwjqkMbMapper.insterMb(vo);
			//super.insertSelective(mb);
		} else {
			qbTjwjqkMbMapper.insertOrUpdateMbInfozml(vo.getMbmc() , vo.getIsMove(), mbnm);
			vo.setMbnm(mbnm);
		}
		if (CollectionUtils.isNotEmpty(list)) {
			//目标详情
			qbTjwjqkMbMapper.insertOrUpdate(list);
			//中心坐标点
			qbTjwjqkMbMapper.insertOrUpdateZxzb(list);
		}
		return new BaseResp<>(200,mb.getMbnm());
	}

	@Override
	public QbTjwjqkMbqk getMBInfoByMbnm(String mbnm) {
		return qbTjwjqkMbMapper.getMBInfoByMbnm(mbnm);
	}

	@Override
	public List<QbTjwjqkMb> queryList(String mc) {
		return qbTjwjqkMbMapper.queryList(mc);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResp deleteMb(String mbnm) {
		QbTjwjqkMbqk mbInfo = qbTjwjqkMbMapper.getMBInfoByMbnm(mbnm);
		//删除子目标及子目标中心点坐标
		List<QbTjwjqkMbZmb> zmbList = qbTjwjqkMbZmbMapper.getListByMbnm(mbnm);
		for (QbTjwjqkMbZmb zmb : zmbList) {
			qbTjwjqkMbZmbMapper.deleteZmbzb(zmb);
			qbTjwjqkMbZmbMapper.deleteZmb(zmb);
		}
		//删除目标
		qbTjwjqkMbMapper.deleteByMbnm(mbnm);
		//删除目标中心点坐标
		qbTjwjqkMbMapper.deleteZxdzbByMbnm(mbnm);
		//删除目标情况
		qbTjwjqkMbMapper.deleteMbqkByMbnm(mbnm);
		//删除目标轨迹
		qbTjwjqkMbMapper.deleteTsByMbnm(mbnm);

		//删除影像文件
		List<MbYx> yxList = szdqMbYxMapper.queryYxidByMbnm(mbnm);
		if (yxList != null && yxList.size() > 0) {
			FileUtils.deleteAllDirectory(basePath + "/yx/" + mbInfo.getGjdqmc() + "/" + mbInfo.getMbmc());
		}


		//删除媒体附件
		QbTjwjqkMbMt t = new QbTjwjqkMbMt();
		t.setMbnm(mbnm);
		List<QbTjwjqkMbMt> mtList = qbTjwjqkMbMtMapper.queryByMbnmMts(t);
		if (mtList != null && mtList.size() > 0) {
			FileUtils.deleteAllDirectory(basePath + "/mt/" + mbInfo.getGjdqmc() + "/" + mbInfo.getMbmc());
		}

		//删除影像记录
		szdqMbYxMapper.deleteMbyx(mbnm, null);
		//删除媒体记录
		qbTjwjqkMbMtMapper.deleteMtByMbnm(mbnm);

		return new BaseResp<>(200);
	}


	private List<QbTjwjqkMbZmlImExcel> checkMbZmlAndNm(List<QbTjwjqkMbZmlImExcel> list) {

		//获取种类内码集合
		List<QbTjwjqkMbqkSxDto> sxList = qbTjwjqkMbMapper.getAllZlMap();
		Map<String, String> zlMap = sxList.stream().collect(Collectors.toMap(QbTjwjqkMbqkSxDto::getSxmc, QbTjwjqkMbqkSxDto::getSxnm));

		//获取国家地区map集合
		sxList = qbTjwjqkMbMapper.getAllGjdqMap();
		Map<String, String> gjMap = sxList.stream().collect(Collectors.toMap(QbTjwjqkMbqkSxDto::getSxmc, QbTjwjqkMbqkSxDto::getSxnm));

		List<QbTjwjqkMbZmlImExcel> errorList = new ArrayList<>();
		for (QbTjwjqkMbZmlImExcel imExcel : list) {
			StringBuilder errorMessage = new StringBuilder();

			if (screenErrorListAndMessage(gjMap, imExcel.getGjdqnm(), errorMessage, "国家地区")) {
				imExcel.setGjdqnm(gjMap.get(imExcel.getGjdqnm()));
			}


			if (screenErrorListAndMessage(zlMap, imExcel.getMbzlnm(), errorMessage, "目标种类")){
				imExcel.setMbzlnm(zlMap.get(imExcel.getMbzlnm()));
			}

			if (StringUtils.isBlank(imExcel.getJdhzb() ) || StringUtils.isBlank(imExcel.getWdzzb())){
				errorMessage.append("经度或纬度为空;");
			}

			QbTjwjqkMb mb = new QbTjwjqkMb();
			boolean addMbFlag = false;
			//查看目标是否存在
			List<String> listMbnm = qbTjwjqkMbMapper.getMbnmByMbmc(imExcel.getMbmc());
			if (listMbnm.size() > 1) {
				errorMessage.append("目标名称存在多个,请检查;");
			}
			String mbnm = listMbnm.size() > 0 ? listMbnm.get(0) : null;
			imExcel.setMbnm(mbnm);
			if (StringUtils.isBlank(mbnm) && StringUtils.isBlank(errorMessage)) {

				mb.setGjdqnm(imExcel.getGjdqnm());
				mb.setMbmc(imExcel.getMbmc());
				String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				mb.setSjsj(format);
				/*errorMessage.append("目标不存在,请前往**新增或导入目标;  ");*/
				Integer maxMbId = qbTjwjqkMbMapper.queryMaxMbId(mb);
				String gjdqnm = mb.getGjdqnm();
				if (null == maxMbId) {
					// 没有此国家地区内码
					mb.setMbnm(gjdqnm + "005" + String.format("%011d", 1));
				} else {
					// 已有此国家地区内码
					mb.setMbnm(gjdqnm + "005" + String.format("%011d", maxMbId + 1));
				}

				// 判断当前生成NM数据库是否已占用 如果占用 直接循环查询 至
				if (StringUtils.isNotBlank(mb.getMbnm())) {
					String addMbnm = mb.getMbnm();
					//QbTjwjqkMb tmb = this.selectByPrimaryKey(addMbnm);
					QbTjwjqkMb tmb = null;
					boolean isNotnull = false;
					if (null != tmb) {
						isNotnull = true;
						int i = maxMbId == null ? 0 : maxMbId + 1;
						while (isNotnull) {
							i++;
							addMbnm = mb.getGjdqnm() + "005" + String.format("%09d", i);
							//tmb = this.selectByPrimaryKey(addMbnm);
							if (null == tmb) {
								isNotnull = false;
							}
						}
						mb.setMbnm(addMbnm);
					}
				}
				imExcel.setMbnm(mb.getMbnm());
				addMbFlag = true;
			}
			if (StringUtils.isNotBlank(errorMessage)) {
				imExcel.setErrorMessage(errorMessage.toString());
				errorList.add(imExcel);
				//list.remove(imExcel);
			} else {
				if (addMbFlag) {
					qbTjwjqkMbMapper.insterMbVo(mb);
					//super.insertSelective(mb);
				}
			}
		}
		list.removeAll(errorList);
		return errorList;
	}

	@Override
	public List<QbTjwjqkMbZmlImExcel> getMbZmlDetailExcel(QbTjwjqkMb qbTjwjqkMb) {
		List<QbTjwjqkMbqkImExcel> mbDetailExcel = qbTjwjqkMbMapper.getMbDetailExcel(qbTjwjqkMb);
		return JSON.parseArray(JSON.toJSONString(mbDetailExcel), QbTjwjqkMbZmlImExcel.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object uploadFileMbzml(List<QbTjwjqkMbZmlImExcel> list) {
		//检查目标和内码
		List<QbTjwjqkMbZmlImExcel> errorList = new ArrayList<>();
		errorList = checkMbZmlAndNm(list);
		if (CollectionUtils.isNotEmpty(list)) {
			//目标详情
			qbTjwjqkMbMapper.insertOrUpdateMbzml(list);
			//中心坐标点
			qbTjwjqkMbMapper.insertOrUpdateZxzbMbzml(list);

		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list",errorList);
		jsonObject.put("errorSize",errorList.size());
		jsonObject.put("successSize",list.size());
		jsonObject.put("allSize",list.size() + errorList.size());
		return jsonObject;
	}


	private List<QbTjwjqkMbqkImExcel> checkMbAndNm(List<QbTjwjqkMbqkImExcel> list) {

		//获取种类内码集合
		List<QbTjwjqkMbqkSxDto> sxList = qbTjwjqkMbMapper.getAllZlMap();
		Map<String, String> zlMap = sxList.stream().collect(Collectors.toMap(QbTjwjqkMbqkSxDto::getSxmc, QbTjwjqkMbqkSxDto::getSxnm));
		//获取等级map集合
		sxList = qbTjwjqkMbMapper.getAllDjMap();
		Map<String, String> djMap = sxList.stream().collect(Collectors.toMap(QbTjwjqkMbqkSxDto::getSxmc, QbTjwjqkMbqkSxDto::getSxnm));
		//获取敏感属性map集合
		sxList = qbTjwjqkMbMapper.getAllMgsxMap();
		Map<String, String> mgsxMap = sxList.stream().collect(Collectors.toMap(QbTjwjqkMbqkSxDto::getSxmc, QbTjwjqkMbqkSxDto::getSxnm));
		//获取情报单位map集合
		sxList = qbTjwjqkMbMapper.getAllQbdwMap();
		Map<String, String> qbdwMap = sxList.stream().collect(Collectors.toMap(QbTjwjqkMbqkSxDto::getSxmc, QbTjwjqkMbqkSxDto::getSxnm));
		//获取国家地区map集合
		sxList = qbTjwjqkMbMapper.getAllGjdqMap();
		Map<String, String> gjMap = sxList.stream().collect(Collectors.toMap(QbTjwjqkMbqkSxDto::getSxmc, QbTjwjqkMbqkSxDto::getSxnm));
		//获取目标状态map集合
		sxList = qbTjwjqkMbMapper.getAllmbztMap();
		Map<String, String> ztMap = sxList.stream().collect(Collectors.toMap(QbTjwjqkMbqkSxDto::getSxmc, QbTjwjqkMbqkSxDto::getSxnm));
		//获取坐标系map集合
		sxList = qbTjwjqkMbMapper.getAllZbxlxMap();
		Map<String, String> zbxlxMap = sxList.stream().collect(Collectors.toMap(QbTjwjqkMbqkSxDto::getSxmc, QbTjwjqkMbqkSxDto::getSxnm));

		List<QbTjwjqkMbqkImExcel> errorList = new ArrayList<>();
		for (QbTjwjqkMbqkImExcel imExcel : list) {
			String mc = imExcel.getMbmc();
			StringBuilder errorMessage = new StringBuilder();

			if (StringUtils.isBlank(imExcel.getMbmc())){
				imExcel.setMbmc(imExcel.getMbmc());
				errorMessage.append("目标名称" + "不存在或不合法;  ");
			}
			if (StringUtils.isBlank(imExcel.getJdhzb())){
				imExcel.setMbmc(imExcel.getMbmc());
				errorMessage.append("经度横坐标" + "不存在或不合法;  ");
			}
			if (StringUtils.isBlank(imExcel.getMbzlnm())){
				imExcel.setMbmc(imExcel.getMbmc());
				errorMessage.append("目标种类" + "不存在或不合法;  ");
			}
			if (screenErrorListAndMessage(gjMap, imExcel.getGjdqnm(), errorMessage, "国家地区")){
				imExcel.setGjdqnm(gjMap.get(imExcel.getGjdqnm()));
			}
			if (screenErrorListAndMessage(zlMap, imExcel.getMbzlnm(), errorMessage, "目标种类")){
				imExcel.setMbzlnm(zlMap.get(imExcel.getMbzlnm()));
			}
			if (screenErrorListAndMessage(djMap, imExcel.getMbdjnm(), errorMessage, "目标等级")){
				imExcel.setMbdjnm(djMap.get(imExcel.getMbdjnm()));
			}
			if (screenErrorListAndMessage(mgsxMap, imExcel.getMgsxnm(), errorMessage, "敏感属性")){
				imExcel.setMgsxnm(mgsxMap.get(imExcel.getMgsxnm()));
			}
			if (screenErrorListAndMessage(qbdwMap, imExcel.getQbdwnm(), errorMessage, "情报单位")){
				imExcel.setQbdwnm(qbdwMap.get(imExcel.getQbdwnm()));
			}

			if (screenErrorListAndMessage(ztMap, imExcel.getMbztnm(), errorMessage, "目标状态")){
				imExcel.setMbztnm(ztMap.get(imExcel.getMbztnm()));
			}
			if (screenErrorListAndMessage(zbxlxMap, imExcel.getZbxlxnm(), errorMessage, "坐标系类型")){
				imExcel.setZbxlxnm(zbxlxMap.get(imExcel.getZbxlxnm()));
			}

			QbTjwjqkMb mb = new QbTjwjqkMb();
			boolean addMbFlag = false;
			//查看目标是否存在
			List<String> listMbnm = qbTjwjqkMbMapper.getMbnmByMbmc(imExcel.getMbmc());
			if (listMbnm.size() > 1) {
				errorMessage.append("目标名称存在多个,请检查;");
			}
			String mbnm = listMbnm.size() > 0 ? listMbnm.get(0) : null;
			imExcel.setMbnm(mbnm);
			if (StringUtils.isBlank(mbnm) && StringUtils.isBlank(errorMessage)){

				mb.setGjdqnm(imExcel.getGjdqnm());
				mb.setMbmc(imExcel.getMbmc());
				mb.setSjsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(imExcel.getSjsj() == null ? new Date() : imExcel.getSjsj()));
				/*errorMessage.append("目标不存在,请前往**新增或导入目标;  ");*/
				Integer maxMbId = qbTjwjqkMbMapper.queryMaxMbId(mb);
				String gjdqnm = mb.getGjdqnm();
				if (null == maxMbId) {
					// 没有此国家地区内码
					mb.setMbnm(gjdqnm + "005" + String.format("%011d", 1));
				} else {
					// 已有此国家地区内码
					mb.setMbnm(gjdqnm + "005" + String.format("%011d", maxMbId + 1));
				}

				// 判断当前生成NM数据库是否已占用 如果占用 直接循环查询 至
				if (StringUtils.isNotBlank(mb.getMbnm())) {
					String addMbnm = mb.getMbnm();
					//QbTjwjqkMb tmb = this.selectByPrimaryKey(addMbnm);
					QbTjwjqkMb tmb = null;
					boolean isNotnull = false;
					if (null != tmb) {
						isNotnull = true;
						int i = maxMbId == null ? 0 : maxMbId + 1;
						while (isNotnull) {
							i++;
							addMbnm = mb.getGjdqnm() + "005" + String.format("%09d", i);
							//tmb = this.selectByPrimaryKey(addMbnm);
							if (null == tmb) {
								isNotnull = false;
							}
						}
						mb.setMbnm(addMbnm);
					}

				}
				imExcel.setMbnm(mb.getMbnm());
				addMbFlag = true;
			}


			if (StringUtils.isNotBlank(errorMessage)) {
				imExcel.setErrorMessage(errorMessage.toString());
				errorList.add(imExcel);
				//list.remove(imExcel);
			} else {
				if (addMbFlag) {
					//super.insertSelective(mb);
					qbTjwjqkMbMapper.insterMb(imExcel);
				}
			}
		}
		list.removeAll(errorList);
		return errorList;
	}


	@Override
	public List<MbTsExcel> getMbTsDetailExcel(String mbnm) {
		return qbTjwjqkMbMapper.getTsList(mbnm);
	}

	@Override
	public List<MbTsExcel> uploadMbTs(List<MbTsExcel> list) {
		List<MbTsExcel> errorList = new ArrayList<>();
		for (MbTsExcel ts : list) {
			StringBuilder errorMessage = new StringBuilder();
			List<String> listMbnm = qbTjwjqkMbMapper.getMbnmByMbmc(ts.getMbmc());
			if (listMbnm.size() > 1) {
				errorMessage.append("目标名称存在多个,请检查;");
			}
			if (listMbnm.size() < 1) {
				errorMessage.append("目标名称不存在,请检查;");
			}
			if (errorMessage.length() > 0) {
				ts.setErrorMessage(errorMessage.toString());
				errorList.add(ts);
			} else {
				ts.setMbnm(listMbnm.get(0));
				qbTjwjqkMbMapper.insterMbTs(ts);
			}
		}


		return errorList;
	}

	private boolean screenErrorListAndMessage(Map<String, String> zlMap, String imExcel,
											  StringBuilder errorMessage, String message) {
		if (StringUtils.isBlank(imExcel)) {
			return false;
		}

		if (StringUtils.isBlank(zlMap.get(imExcel))){
			errorMessage.append(message + "不存在或不合法;  ");
			return false;
			//list.remove(imExcel);
		} else {
			return true;
		}
	}
}
