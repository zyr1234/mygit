package com.geovis.jg.mb.controller;

import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.mb.model.ZzbzSQbMbzl;
import com.geovis.jg.mb.service.ZzbzSTyGjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/zzbzSQbMbzls")
@Api(tags = "目标种类相关接口")
public class ZzbzSQbMbzlController {

    @Resource
	private ZzbzSTyGjService zzbzSTyGjService;

    /**
	 * 根据目标种类名称查询目标种类内码
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryList", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据目标种类名称查询目标种类内码", notes = "根据目标种类名称查询目标种类内码")
	public List<ZzbzSQbMbzl> queryZlList(@RequestParam(value = "mc", required = false) String mc) {
		return zzbzSTyGjService.queryZlList(mc);
	}


	/**
	 *修改目标种类
	 ***/
	@PutMapping(value = "/update-body")
	@ApiOperation(value = "修改目标种类", notes = "修改目标种类")
	public BaseResp updateGjdq(@RequestBody ZzbzSQbMbzl t) {
		return zzbzSTyGjService.updateMbzl(t);
	}

	/**
	 *新增目标种类
	 ***/
	@PostMapping(value = "/add-body")
	@ApiOperation(value = "新增目标种类", notes = "新增目标种类")
	public BaseResp addGjdq(@RequestBody ZzbzSQbMbzl t) {
		return zzbzSTyGjService.addMbzl(t);
	}


	/**
	 *删除目标种类
	 ***/
	@DeleteMapping(value = "/{gjdqnm}")
	@ApiOperation(value = "删除目标种类", notes = "删除目标种类")
	public BaseResp deleteGjdq(@PathVariable String gjdqnm) {
		return zzbzSTyGjService.deleteMbzl(gjdqnm);
	}
}
