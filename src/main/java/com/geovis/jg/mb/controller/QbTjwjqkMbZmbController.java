package com.geovis.jg.mb.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.mb.model.QbTjwjqkMbZmb;
import com.geovis.jg.mb.service.QbTjwjqkMbZmbService;
import com.geovis.jg.mb.model.QbTjwjqkMbZmbExcel;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/qbTjwjqkMbZmbs")
@Api(tags = "子目标相关接口")
public class QbTjwjqkMbZmbController {

    @Resource
    private QbTjwjqkMbZmbService qbTjwjqkMbZmbService;

    @RequestMapping(value = "/queryZmbInfo", method = RequestMethod.GET)
    @ApiOperation(value = "所属子目标信息的检索", notes = "所属子目标信息的检索")
    public List<QbTjwjqkMbZmb> queryZmbInfo(QbTjwjqkMbZmb t) throws Exception {
    	return qbTjwjqkMbZmbService.queryZmbInfo(t);
    }

    @RequestMapping(value = "/getListByMbnm", method = RequestMethod.GET)
    @ApiOperation(value = "查询子目标根据目标内码", notes = "查询子目标根据目标内码")
    public List<QbTjwjqkMbZmb> getListByMbnm(@RequestParam("mbnm") String mbnm) {
    	return qbTjwjqkMbZmbService.getListByMbnm(mbnm);
    }

    @RequestMapping(value = "/getListByMc", method = RequestMethod.GET)
    @ApiOperation(value = "查询子目标根据名称", notes = "查询子目标根据名称")
    public PageInfo getListByMc(@RequestParam(value = "mc",required = false) String mc,
                                @RequestParam(value = "fmc",required = false) String fmc,
                                @RequestParam(value = "pageNum",required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize",required = false, defaultValue = "10") Integer pageSize) {
    	return qbTjwjqkMbZmbService.getListByMc(mc, fmc, pageNum, pageSize);
    }

    @RequestMapping(value = "/updateZmbInfo", method = RequestMethod.POST)
    @ApiOperation(value = "新增修改子目标", notes = "新增修改子目标")
    public BaseResp updateZmbInfo(@RequestBody QbTjwjqkMbZmb zmb) {
    	return qbTjwjqkMbZmbService.updateZmbInfo(zmb);
    }

    @RequestMapping(value = "/deleteZmb", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除子目标", notes = "删除子目标")
    public BaseResp deleteZmb(QbTjwjqkMbZmb zmb) {
    	return qbTjwjqkMbZmbService.deleteZmb(zmb);
    }

    @RequestMapping(value = "/uploadFileZMb",method = RequestMethod.POST)
    @ApiOperation(value = "导入子目标信息", notes = "导入子目标信息")
    public Object uploadFileZMb(@RequestParam("file") MultipartFile file) throws Exception {
        List<QbTjwjqkMbZmbExcel> list = ExcelImportUtil.importExcel(file.getInputStream(), QbTjwjqkMbZmbExcel.class, new ImportParams());

        if (list == null && list.size() == 0) {
            return "无数据";
        }

        return qbTjwjqkMbZmbService.uploadFileZMb(list);
    }

    @RequestMapping(value = "/exportFileZMb",method = RequestMethod.GET)
    @ApiOperation(value = "导出子目标信息", notes = "导出子目标信息")
    public void exportFileZMb(HttpServletResponse response,
                              @RequestParam(value = "mc",required = false) String mc,
                              @RequestParam(value = "fmc",required = false) String fmc) throws Exception {

        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        List<QbTjwjqkMbZmbExcel> list = qbTjwjqkMbZmbService.exportFileZMb(mc, fmc);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("子目标","子目标"), QbTjwjqkMbZmbExcel.class, list);
        workbook.write(response.getOutputStream());
    }

    @RequestMapping(value = "/getTsList", method = RequestMethod.GET)
    @ApiOperation(value = "查询mb_ts", notes = "查询mb_ts")
    public PageInfo getTsList(@RequestParam(value = "pageNum",required = false, defaultValue = "0") Integer pageNum,
                              @RequestParam(value = "pageSize",required = false, defaultValue = "0") Integer pageSize,
                              @RequestParam(value = "mbnm",required = false) String mbnm) {
        return qbTjwjqkMbZmbService.getTsList(mbnm, pageNum, pageSize);
    }

}
