package com.geovis.jg.mb.controller;

import com.geovis.jg.common.domain.BasePageResp;
import com.geovis.jg.common.domain.BaseResp;
import com.geovis.jg.common.domain.Page;
import com.geovis.jg.mb.model.*;
import com.geovis.jg.mb.service.QbTjwjqkMbqkService;
import com.geovis.jg.mb.utills.ListFindPage;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/qbTjwjqkMbqks")
@CrossOrigin
@Slf4j
@Api(tags = "目标情況相关接口")
public class QbTjwjqkMbqkController {

    @Resource
    private QbTjwjqkMbqkService qbTjwjqkMbqkService;

    @RequestMapping(value = "/mbqkInfo", method = RequestMethod.GET)
    @ApiOperation(value = "根据目标内码查询目标详情信息", notes = "根据目标内码查询目标详情信息")
	public QbTjwjqkMbqk mbqkInfo(@RequestParam("mbnm") String mbnm) {
		return qbTjwjqkMbqkService.mbqkInfo(mbnm);
	}
    
    
    @RequestMapping(value = "/pageAllInfo", method = RequestMethod.GET)
    @ApiOperation(value = "目标情况-分页", notes = "根据目标ID查询目标详情信息-分页")
    public Object pageAllInfo(QbTjwjqkMbqk record, Page page) {
        return qbTjwjqkMbqkService.pageAllInfo(record, page);
    }
    
    @RequestMapping(value = "/mbqkAllInfo", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部目标", notes = "获取全部目标")
    public List<QbTjwjqkMbqk> mbqkAllInfo() {
        return qbTjwjqkMbqkService.mbqkAllInfo();
    }

//    @RequestMapping(value = "/getMbqkAllInfo", method = RequestMethod.GET)
//    @ApiOperation(value = "目标情况-分页", notes = "情况详情")
//    public PageInfo getMbqkAllInfo(QbTjwjqkMbqk record,
//                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
//                                   @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize){
//        return qbTjwjqkMbqkService.getMbqkAllInfo(record, pageNum, pageSize);
//    }

    @RequestMapping(value = "/getMbqkAllInfo", method = RequestMethod.GET)
    @ApiOperation(value = "目标情况-分页", notes = "情况详情")
    public BasePageResp getMbqkAllInfo(QbTjwjqkMbqk record,
                                       @RequestParam(value = "start_date" , required = false) String startdate,
                                       @RequestParam(value = "end_date" , required = false) String enddate,
                                       @RequestParam(value = "cornerCoordinateA" , required = false) String cornerCoordinateA,
                                       @RequestParam(value = "cornerCoordinateB" , required = false) String cornerCoordinateB,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize) throws Exception {
        if (cornerCoordinateA==null&&cornerCoordinateB==null){
            PageInfo info = qbTjwjqkMbqkService.getMbqkAllInfo(record,startdate,enddate, pageNum, pageSize);
            return new BasePageResp(info.getList(),(int)info.getTotal());
        }else if (cornerCoordinateA!=null&&cornerCoordinateB!=null){
            double[] lons = new double[2];
            double[] lats = new double[2];
            lons[0] = Double.valueOf(cornerCoordinateA.split(",")[0]);
            lats[0] = Double.valueOf(cornerCoordinateA.split(",")[1]);
            lons[1] = Double.valueOf(cornerCoordinateB.split(",")[0]);
            lats[1] = Double.valueOf(cornerCoordinateB.split(",")[1]);
            List<QbTjwjqkMbqk> mbqks = qbTjwjqkMbqkService.mbqkAllInfo();
            List<QbTjwjqkMbqk> list = new ArrayList<>();
            for (QbTjwjqkMbqk mbqk : mbqks){
                if (mbqk.getJdhzb()!=null&&mbqk.getWdzzb()!=null){
                    String[] zb = {mbqk.getJdhzb(),mbqk.getWdzzb()};
                    if (checkOne(zb,lons,lats)){
                        list.add(mbqk);
                    }
                }
            }
            List<QbTjwjqkMbqk> pageList = new ListFindPage<>(list, pageSize).getPageList(pageNum);
            return new BasePageResp(pageList,list.size());
        }else {
            return new BasePageResp(400,"坐标数量错误！");
        }
    }


    @RequestMapping(value="/mbgjdqAllInfo", method= RequestMethod.GET)
    @ApiOperation(value = "国家地区级联查询", notes = "国家地区级联查询")
    public List<ZzbzSTyGj> mbgjdqAllInfo(@RequestParam(value = "nm", required = false) String nm) {
        return qbTjwjqkMbqkService.mbgjdqAllInfo(nm);
    }

    @RequestMapping(value="/mbzlAllInfoMyNm", method= RequestMethod.GET)
    @ApiOperation(value = "目标种类级联查询", notes = "目标种类级联查询")
    public List<ZzbzSQbMbzl> mbzlAllInfoMyNm(@RequestParam(value = "xh", required = false) String xh,
                                             @RequestParam(value = "cj", required = false) String cj) {
        return qbTjwjqkMbqkService.mbzlAllInfoMyNm(xh, cj);
    }

    @RequestMapping(value="/getMbMtlx", method= RequestMethod.GET)
    @ApiOperation(value = "目标媒体类型查询", notes = "目标媒体类型查询")
    public List<ZzbzSQbMtlx> getMbMtlx(@RequestParam(value = "mc", required = false) String mc) {
        return qbTjwjqkMbqkService.getMbMtlx(mc);
    }

    @RequestMapping(value="/getMbMtgs", method= RequestMethod.GET)
    @ApiOperation(value = "目标媒体格式查询", notes = "目标媒体格式查询")
    public List<ZzbzSQbMtgs> getMbMtgs(@RequestParam(value = "mc", required = false) String mc) {
        return qbTjwjqkMbqkService.getMbMtgs(mc);
    }

    @RequestMapping(value="/getMbZbx", method= RequestMethod.GET)
    @ApiOperation(value = "坐标系查询", notes = "坐标系查询")
    public List<ZzbzSQbZbxlx> getMbZbx(@RequestParam(value = "mc", required = false) String mc) {
        return qbTjwjqkMbqkService.getMbZbx(mc);
    }


    @RequestMapping(value="/getMbFjSx", method= RequestMethod.GET)
    @ApiOperation(value = "目标附加属性查询", notes = "目标附加属性")
    public BaseResp getMbFjSx(@RequestParam("mbnm") String mbnm) {
        return qbTjwjqkMbqkService.getMbFjSx(mbnm);
    }


    @RequestMapping(value="/mbFjSxChange", method= RequestMethod.POST)
    @ApiOperation(value = "目标附加属性新增或者修改", notes = "目标附加属性新增或者修改")
    public BaseResp mbFjSxChange(@RequestBody ExtensionJsonTab jsonTab) {
        return qbTjwjqkMbqkService.mbFjSxChange(jsonTab);
    }

    public boolean checkOne(String[] zbSplit,double[] lon, double[] lat) throws Exception{
        boolean flag = false;
        try {
            Double lonDouble = Double.valueOf(zbSplit[0]);
            Double latDouble = Double.valueOf(zbSplit[1]);
            if (InorNot(lonDouble,lon)&&InorNot(latDouble,lat)){
                flag = true;
            }
        }catch (Exception e){
            log.info("数据库数据有错");
        }
        return flag;
    }

    public boolean InorNot(Double zb , double[] data){
        boolean flag = false;
        Double datum = data[0];
        Double datum1 = data[1];
        if ((zb>=datum&&zb<=datum1)||(zb<=datum&&zb>=datum1)){
            flag = true;
        }
        return flag;
    }

}
