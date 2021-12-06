package com.geovis.jg.mb.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.common.domain.Result;
import com.geovis.jg.mb.model.QbTjwjqkMbZmlImExcel;
import com.geovis.jg.mb.model.QbTjwjqkMbqkImExcel;
import com.geovis.jg.mb.model.*;
import com.geovis.jg.mb.service.QbTjwjqkMbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/qbTjwjqkMbs")
@Api(tags = "目标相关接口")
public class QbTjwjqkMbController {

    @Resource
    private QbTjwjqkMbService qbTjwjqkMbService;
	private static Logger logger = LoggerFactory.getLogger(QbTjwjqkMbController.class);

	@RequestMapping(value="/queryList",method= RequestMethod.GET)
	@ApiOperation(value = "查询目标信息", notes = "查询目标信息")
	public List<QbTjwjqkMb> queryList(@RequestParam(value = "mc",required = false) String mc){
		return qbTjwjqkMbService.queryList(mc);
	}

	@DeleteMapping(value="/deleteMb")
	@ApiOperation(value = "删除目标信息", notes = "删除目标信息")
	public BaseResp deleteMb(@RequestParam(value = "mbnm") String mbnm){
		return qbTjwjqkMbService.deleteMb(mbnm);
	}

	/**
	 * 导入目标信息
	 * 2020/04/13
	 * 张龙辉
	 */
	@RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
	@ApiOperation(value = "导入目标信息", notes = "导入目标信息")
	public JSONObject uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

		//转list
		List<QbTjwjqkMbqkImExcel> list = ExcelImportUtil.importExcel(file.getInputStream(), QbTjwjqkMbqkImExcel.class, new ImportParams());

		if (list == null && list.size() == 0) {
			return new JSONObject();
		}

		return qbTjwjqkMbService.uploadFile(list);
	}
	/**
	 * 导入目标总名录信息
	 * 2020/04/13
	 * 张龙辉
	 */
	@RequestMapping(value = "/uploadFileMbzml",method = RequestMethod.POST)
	@ApiOperation(value = "导入目标总名录信息", notes = "导入目标总名录信息")
	public Object uploadFileMbzml(@RequestParam("file") MultipartFile file) throws Exception {

		//转list
		List<QbTjwjqkMbZmlImExcel> list = ExcelImportUtil.importExcel(file.getInputStream(), QbTjwjqkMbZmlImExcel.class, new ImportParams());

		if (list == null && list.size() == 0) {
			return "无数据";
		}

		return qbTjwjqkMbService.uploadFileMbzml(list);
	}


	/**
	 * 导出目标信息
	 * 2020/04/13
	 * 张龙辉
	 */
	@RequestMapping(value = "/getMbDetailExcel",method = RequestMethod.GET)
	@ApiOperation(value = "导出目标信息", notes = "导出目标信息")
	public void getMbDetailExcel(HttpServletResponse response, QbTjwjqkMb qbTjwjqkMb) throws Exception {
		response.setHeader("content-Type", "application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");
		List<QbTjwjqkMbqkImExcel> mbDetailExcel = qbTjwjqkMbService.getMbDetailExcel(qbTjwjqkMb);
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("目标情况总信息","目标情况总信息"), QbTjwjqkMbqkImExcel.class, mbDetailExcel);
		workbook.write(response.getOutputStream());

	}
	/**
	 * 导出目标总名录信息
	 * 2020/04/13
	 * 张龙辉
	 */
	@RequestMapping(value = "/getMbZmlDetailExcel",method = RequestMethod.GET)
	@ApiOperation(value = "导出目标总名录信息", notes = "导出目标总名录信息")
	public void getMbZmlDetailExcel(HttpServletResponse response, QbTjwjqkMb qbTjwjqkMb) throws Exception {
		response.setHeader("content-Type", "application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");
		List<QbTjwjqkMbZmlImExcel> mbDetailExcel = qbTjwjqkMbService.getMbZmlDetailExcel(qbTjwjqkMb);
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("目标总名录信息","目标总名录信息"), QbTjwjqkMbZmlImExcel.class, mbDetailExcel);
		workbook.write(response.getOutputStream());
	}


	/**
	 * 保存总名录
	 * 2020/04/13
	 * 张龙辉
	 */
	@RequestMapping(value = "/saveZml",method = RequestMethod.POST)
	@ApiOperation(value = "保存总名录", notes = "保存总名录")
	public BaseResp saveZml(@RequestBody QbTjwjqkMbZmlImExcel qbTjwjqkMbZmlIm) {
		return qbTjwjqkMbService.saveZml(qbTjwjqkMbZmlIm);
	}



	/**
	 * 保存目标信息
	 * 2020/04/13
	 * 张龙辉
	 */
	@RequestMapping(value = "/saveMbqk",method = RequestMethod.POST)
	@ApiOperation(value = "保存目标信息", notes = "保存目标信息")
	public BaseResp saveMbqk(@RequestBody QbTjwjqkMbqkImExcel qbTjwjqkMbqkImExcel) {
		List<QbTjwjqkMbqkImExcel> list = new ArrayList<>();
		list.add(qbTjwjqkMbqkImExcel);
		if (list == null && list.size() == 0) {
			return new BaseResp(500,"无数据");
		}
		return qbTjwjqkMbService.saveMbqk(list);
	}




	/**
	 * 导出轨迹信息
	 * 2020/09/22
	 * 张龙辉
	 */
	@RequestMapping(value = "/getMbTsDetailExcel",method = RequestMethod.GET)
	@ApiOperation(value = "导出轨迹信息", notes = "导出轨迹信息")
	public void getMbTsDetailExcel(HttpServletResponse response,@RequestParam(value = "mbnm", required = false) String mbnm) throws Exception {
		response.setHeader("content-Type", "application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");
		List<MbTsExcel> mbTsExcel = qbTjwjqkMbService.getMbTsDetailExcel(mbnm);
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("目标轨迹信息","目标轨迹信息"), MbTsExcel.class, mbTsExcel);
		workbook.write(response.getOutputStream());
	}


	/**
	 * 导入轨迹信息
	 * 2020/09/22
	 * 张龙辉
	 */
	@RequestMapping(value = "/uploadMbTs",method = RequestMethod.POST)
	@ApiOperation(value = "导入轨迹信息", notes = "导入轨迹信息")
	public List<MbTsExcel> uploadMbTs(@RequestParam("file") MultipartFile file) throws Exception {

		//转list
		List<MbTsExcel> list = ExcelImportUtil.importExcel(file.getInputStream(), MbTsExcel.class, new ImportParams());

		if (list == null && list.size() == 0) {
			return null;
		}

		return qbTjwjqkMbService.uploadMbTs(list);
	}

	@RequestMapping(value = "/getLocalTxtFile",method = RequestMethod.GET)
	@ApiOperation(value = "读取本地txt文件")
	public BaseResp<String> getLocalTxtFile(){
		String fileName = "E:\\project\\zcnh\\testFile.txt";
		File file = new File(fileName);
		try {
			if (file.exists()){
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String fileContent;
				if((fileContent = bufferedReader.readLine())!=null) {
					logger.info("文件:{}",fileContent);
				}
				return new BaseResp<String>(fileContent,"success",200);

			}else return new BaseResp<String>("文件不存在","failed",500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BaseResp<String>("操作失败","failed",500);
	}
}
