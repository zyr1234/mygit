package com.geovis.jg.mb.controller;

import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.mb.model.ZzbzSTyGj;
import com.geovis.jg.mb.service.ZzbzSTyGjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/zzbzSTyGjs")
@Api(tags = "目标国家地区相关接口")
public class ZzbzSTyGjController {

    @Resource
    private ZzbzSTyGjService zzbzSTyGjService;

    @RequestMapping(value = "/queryByGjdqnm",method = RequestMethod.GET)
    @ApiOperation(value = "查询全部国家地区", notes = "查询全部国家地区")
    public List<ZzbzSTyGj> queryByGjdqnm(ZzbzSTyGj t) {
		return zzbzSTyGjService.queryByGjdqnm(t);
	}

    @PutMapping(value = "/update-body")
    @ApiOperation(value = "修改国家地区", notes = "修改国家地区")
    public BaseResp updateGjdq(@RequestBody ZzbzSTyGj t) {
        return zzbzSTyGjService.updateGjdq(t);
    }

    @PostMapping(value = "/add-body")
    @ApiOperation(value = "新增国家地区", notes = "新增国家地区")
    public BaseResp addGjdq(@RequestBody ZzbzSTyGj t) {
        return zzbzSTyGjService.addGjdq(t);
    }

    @DeleteMapping(value = "/{gjdqnm}")
    @ApiOperation(value = "删除国家地区", notes = "删除国家地区")
    public BaseResp deleteGjdq(@PathVariable String gjdqnm) {
        return zzbzSTyGjService.deleteGjdq(gjdqnm);
    }
    
}
