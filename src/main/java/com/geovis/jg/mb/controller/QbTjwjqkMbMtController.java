package com.geovis.jg.mb.controller;

import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.common.domain.Page;
import com.geovis.jg.mb.model.QbTjwjqkMbMt;
import com.geovis.jg.mb.service.QbTjwjqkMbMtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/api/qbTjwjqkMbMts")
@CrossOrigin
@Api(tags = "媒体附件信息相关接口")
public class QbTjwjqkMbMtController  {

	@Resource
	private QbTjwjqkMbMtService qbTjwjqkMbMtService;


	/**
	 * 获取目标的媒体信息 新增分页查询2018052
	 *
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "获取媒体信息列表")
	@RequestMapping(value = "/queryByMbnmMt", method = RequestMethod.GET)
	public Page queryByMbnmMtsNew(@RequestParam(value = "mbnm", required = false) String mbnm,
								  @RequestParam(value = "mc", required = false) String mc,
								  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
								  @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize) {
		QbTjwjqkMbMt t = new QbTjwjqkMbMt();
		t.setMbnm(mbnm);
		t.setWjm(mc);
		Page page = new Page();
		page.setSort("mtnm");
		page.setPage(Integer.valueOf(pageNum));
		page.setRows(Integer.valueOf(pageSize));
		page = qbTjwjqkMbMtService.queryByMbnmMts(t, page);
		return page;

	}

	@ApiOperation(value = "根据媒体信息内码获取媒体详细信息")
	@RequestMapping(value = "/getMtdxByMtnm", method = RequestMethod.GET)
	public QbTjwjqkMbMt getMtdxByMtnm(Integer mtnm) {
		return qbTjwjqkMbMtService.selectByMtnm(mtnm);
	}


	@PostMapping("/upload")
	@ApiOperation(value = "上传文件")
	public BaseResp mtFileUpload(QbTjwjqkMbMt mt, @RequestParam("file") MultipartFile file) throws IOException {

		return qbTjwjqkMbMtService.mtFileUpload(file, mt);
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "删除文件")
	public BaseResp<Integer> mtFileDelete(@RequestParam("mtnm") Integer mtnm) {

		return qbTjwjqkMbMtService.mtFileDelete(mtnm);
	}


	@PostMapping("/update")
	@ApiOperation(value = "修改文件")
	public BaseResp mtFileUpdate(@RequestBody QbTjwjqkMbMt mt) {

		return qbTjwjqkMbMtService.mtFileUpdate(mt);
	}

}
