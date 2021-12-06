package com.geovis.jg.mb.controller;

import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.mb.model.MbYx;
import com.geovis.jg.mb.mapper.SzdqMbYxMapper;
import com.geovis.jg.mb.model.MbZbYx;
import com.geovis.jg.mb.service.SzdqMbYxService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/SzqdMbYxs")
@Api(tags = "影像相关接口")
public class SzdqMbYxController {

	@Resource
	private SzdqMbYxService szdqMbYxService;

	@Resource
	private SzdqMbYxMapper szdqMbYxMapper;

	@Value("${base.path}")
	private String basePath;

	@ApiOperation(value = "根据目标内码获取影像信息列表")
	@RequestMapping(value = "/queryYxByMbnm", method = RequestMethod.GET)
	public List<MbYx> queryYxidByMbnm(@RequestParam(value = "mbnm", required = true) String mbnm,
									  @RequestParam(value = "mc",required = false) String mc,
									  @RequestParam(value = "currentPage",required = false) Integer currentPage,
									  @RequestParam(value = "pageSize",required = false) String pageSize) throws Exception {
		return szdqMbYxService.queryYxidByMbnm(mbnm);
	}

	@ApiOperation(value = "根据影像yxid获取媒体信息")
	@RequestMapping(value = "/queryByYxid", method = RequestMethod.GET)
	public MbYx queryByYxid(String yxid) {
		return szdqMbYxMapper.queryByYxid(yxid);
	}

	@ApiOperation(value = "遥感影像删除", notes = "遥感影像删除")
	@RequestMapping(value = "/deleteYgyxById", method = RequestMethod.GET)
	public BaseResp deleteYgyxById(@RequestParam("yxid") String yxid) {

		MbYx mbyx = szdqMbYxMapper.queryByYxid(yxid);
		szdqMbYxMapper.deleteMbyx(mbyx.getMbnm(),yxid);
		String path = basePath + "/" + mbyx.getYxt();
		File file = new File(path.substring(0, path.lastIndexOf("/")));
		File[] files = file.listFiles();
		for (File f: files) {
			f.delete();
		}
		file.delete();

		return new BaseResp(200, "success");
	}


	@PostMapping("/yxFileUpload")
	@ApiOperation(value = "遥感影像上传单文件")
	public BaseResp yxFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("mbnm")String mbnm) throws IOException {

		if (file == null) {
			return  new BaseResp(500, "文件为空");
		}

		return szdqMbYxService.yxFileUpload(file,mbnm);
	}

	@PostMapping("/yxDataSave")
	@ApiOperation(value = "遥感影像表单保存")
	public BaseResp yxDataSave(@RequestBody MbYx yx) throws IOException {

		return szdqMbYxService.yxDataSave(yx);
	}


	@ApiOperation(value = "遥感临时文件删除", notes = "遥感影像删除-上传过程中出现问题或取消新增遥感影像")
	@RequestMapping(value = "/deleteYxTemp", method = RequestMethod.GET)
	public BaseResp deleteYxTemp(@RequestParam("path") String path) {

		File file = new File(path);
		file.delete();

		return new BaseResp(200, "success");
	}


	@PostMapping("/yxDataUpdate")
	@ApiOperation(value = "遥感影像表单修改")
	public BaseResp yxDataUpdate(@RequestBody MbYx yx) {
		return szdqMbYxService.yxDataUpdate(yx);
	}

	//********************影像识别相关*********************
	@PostMapping("/yxZbUpload")
	@ApiOperation(value = "影像识别--遥感影像上传单文件")
	public BaseResp yxZbUpload(@RequestParam("file") MultipartFile file) throws IOException {
		if (file == null) {
			return  new BaseResp(500, "文件为空");
		}
		return szdqMbYxService.yxZbUpload(file);
	}


	@PutMapping("/zbYxForMb")
	@ApiOperation(value = "影像整编识别--遥感影像关联目标接口")
	public BaseResp zbYxForMb(@RequestBody MbZbYx yx) {
		return szdqMbYxService.zbYxForMb(yx.getYxId(), yx.getMbnm());
	}

	@PutMapping("/zbYxRk")
	@ApiOperation(value = "影像整编识别--遥感影像整编信息入库")
	public BaseResp zbYxRk(@RequestBody MbZbYx yx) throws IOException {
		return szdqMbYxService.zbYxRk(yx);
	}

	@GetMapping("/getZbYxList")
	@ApiOperation(value = "影像整编识别--遥感影像整编信息查询")
	public PageInfo getZbYxList(@RequestParam(value = "yxMc",required = false) String yxMc,
								@RequestParam(value = "mbMc", required = false) String mbMc,
								@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
								@RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize) {
		return szdqMbYxService.getZbYxList(yxMc, mbMc, pageNum, pageSize);
	}

	@GetMapping("/getZbYxByZbYxId")
	@ApiOperation(value = "影像整编识别--根据影像id获取详情")
	public BaseResp getZbYxByZbYxId(@RequestParam(value = "zbYxId") String zbYxId) {
		return szdqMbYxService.getZbYxByZbYxId(zbYxId);
	}

	@DeleteMapping("/deleteZbYxByZbYxId")
	@ApiOperation(value = "影像整编识别--遥感影像整编信息删除")
	public BaseResp deleteZbYxByZbYxId(@RequestParam(value = "zbYxId") String zbYxId) {
		return szdqMbYxService.deleteZbYxByZbYxId(zbYxId);
	}





}
