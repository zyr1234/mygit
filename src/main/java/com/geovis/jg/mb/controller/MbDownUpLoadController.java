package com.geovis.jg.mb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
//import com.dbs.common.util.GlobalConstants;
//import com.dbs.epzh.mb.domain.QbTjwjqkMbDown;
//import com.dbs.epzh.mb.domain.QbTjwjqkMbMt;
//import com.dbs.epzh.mb.domain.QbTjwjqkMbqkDown;
//import com.dbs.szdq.mb.domain.SzdqMbYx;
//import com.dbs.szdq.mb.service.MbDownUpLoadService;
import com.geovis.jg.mb.model.QbTjwjqkMbDown;
import com.geovis.jg.mb.model.QbTjwjqkMbMt;
import com.geovis.jg.mb.model.QbTjwjqkMbqkDown;
import com.geovis.jg.mb.model.SzdqMbYx;
import com.geovis.jg.mb.service.MbDownUpLoadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


@Controller
@CrossOrigin
@RequestMapping("/qtmb/down/up")
@Api(value = "目标打包迁移接口", description = "目标打包迁移接口")
public class MbDownUpLoadController {


    @Resource
    private MbDownUpLoadService downUpLoadService;

  //  private String basePath= GlobalConstants.BASEPATH;

   // private String tisPath= GlobalConstants.TIFPATH;

    @Value("${base.path}")
    private String basePath;//= GlobalConstants.BASEPATH;

    @Value("${base.tifpath}")
    private String tifPath;//= GlobalConstants.TIFPATH;

    private static final int BUFFER_SIZE = 2 * 1024;



    /**
     * 20210707新增：目标打包下载
     * **/
    @ApiOperation(value = "目标打包下载", notes = "目标打包下载")
    @RequestMapping(value = "/downLoad", method = RequestMethod.POST)
    @ResponseBody
    public String downLoad(@RequestBody List<String> ids) throws IOException {

        List<String> pathList = downUpLoadService.downLoad(ids, basePath);

        List<QbTjwjqkMbDown> mbList = downUpLoadService.selectDownMbInfo(ids);
        List<QbTjwjqkMbqkDown> qkList = downUpLoadService.selectDownMbJbqk(ids);
        List<QbTjwjqkMbMt> fjList = downUpLoadService.selectDownMbFj(ids);
        List<SzdqMbYx> yxList = downUpLoadService.selectDownMbYx(ids);

        //生成json文件
        jsonSave(mbList,"mb");
        jsonSave(qkList,"qk");
        jsonSave(fjList,"fj");
        jsonSave(yxList,"yx");

        //json文件路径
        String fileName = ids.get(0) + "_目标_目标打包.zip";
        pathList.add(basePath + "/mbInfoJson");
        toZip(pathList, basePath +  "/" + fileName, true);

        return tifPath + fileName;
    }

    /**
     * 20210709新增：下载已打包zip
     * **/
    @ApiOperation(value = "下载已打包zip", notes = "下载已打包zip")
    @RequestMapping(value = "/downZip", method = RequestMethod.POST)
    @ResponseBody
    public String downZip() {
        String zipPath = tifPath + "目标打包.zip";
        File zipFile = new File(zipPath);
        if (zipFile.isDirectory()) {
            throw new RuntimeException("无zip文件下载,请等待或重新打包");
        }
        return zipPath;
    }



    /**
     * 20210709新增：目标打包上传
     * **/
    @ApiOperation(value = "目标打包上传", notes = "目标打包上传")
    @RequestMapping(value = "/upLoad", method = RequestMethod.POST)
    @ResponseBody
    public String upLoad(@RequestParam("file") MultipartFile file) throws IOException {

        //文件保存解压
        String zipTemPath = basePath + "/zipTemp/";
        File zipFile = new File(zipTemPath);
        if (!zipFile.exists()) {
            zipFile.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        String outFilePath = zipTemPath + fileName;
        InputStream inputStream = file.getInputStream();
        // 将文件写入硬盘
        FileUtils.copyInputStreamToFile(inputStream, new File(outFilePath));
        //unZip(new File(outFilePath),zipTemPath);
        //decompress(outFilePath, zipTemPath);
        unZip(new File(outFilePath),zipTemPath);
        inputStream.close();

        //读取json文件
        List<QbTjwjqkMbMt> mt = getJson(new File(zipTemPath + "/mbInfoJson/fj.json"), QbTjwjqkMbMt.class);
        List<QbTjwjqkMbDown> mb = getJson(new File(zipTemPath + "/mbInfoJson/mb.json"), QbTjwjqkMbDown.class);
        List<QbTjwjqkMbqkDown> qk = getJson(new File(zipTemPath + "/mbInfoJson/qk.json"), QbTjwjqkMbqkDown.class);
        List<SzdqMbYx> yx = getJson(new File(zipTemPath + "/mbInfoJson/yx.json"), SzdqMbYx.class);

        for (QbTjwjqkMbqkDown mbqk : qk) {

            if (StringUtils.isBlank(mbqk.getGjdqnm())){
                continue;
            }
            //查询目标国家地区
            String gj = downUpLoadService.getGjqdByNm(mbqk.getGjdqnm());
            if (StringUtils.isBlank(gj)){
                continue;
            }
            File oldFile = new File(basePath + "/" + gj + "/" + mbqk.getMbmc() + "/");
            if (!oldFile.exists()) {
                oldFile.mkdirs();
            }
            FileUtils.copyDirectory(new File(basePath + "/zipTemp/" + mbqk.getMbmc() + "/"), oldFile);
        }
        File tempFile = new File(basePath + "/zipTemp/");
        if (tempFile.exists()) {
            FileUtils.deleteDirectory(tempFile);
        }

        downUpLoadService.saveUpLoadMb(mb, qk, mt, yx);

        return "success";
    }





    /**
     * @param srcDir
     * @param outDir
     * @param keep
     * @throws Exception
     */
    public void toZip(List<String> srcDir, String outDir, Boolean keep) throws IOException{

        File f = new File(outDir);
        OutputStream out = new FileOutputStream(f);
        org.apache.tools.zip.ZipOutputStream zos = null;
        try {
            zos = new org.apache.tools.zip.ZipOutputStream(out);
            ArrayList<File> sourceList = new ArrayList<>();
            for (String dir : srcDir) {
                File file = new File(dir);
                sourceList.add(file);
            }
            compress(sourceList, zos, keep);
        } catch (Exception e) {
        } finally {
            if (zos != null) {
                zos.close();
            }
        }


    }

    private void compress(List<File> sourceList, org.apache.tools.zip.ZipOutputStream zos, Boolean keep) throws IOException {

        byte[] buf = new byte[BUFFER_SIZE];
        for (File file : sourceList) {
            String name = file.getName().replace("data","");
            if (file.isFile()) {
                zos.putNextEntry(new org.apache.tools.zip.ZipEntry(name));
                int len;
                FileInputStream in = new FileInputStream(file);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            } else {
                File[] files = file.listFiles();
                if (files == null || files.length == 0) {
                    if (keep) {
                        zos.putNextEntry(new org.apache.tools.zip.ZipEntry(name + "\\"));
                        zos.closeEntry();
                    }

                } else {

                    for(File f : files){
                        if (keep) {
                            compress(f, zos, name + "\\" + f.getName(), keep);
                        } else {
                            compress(f, zos, f.getName(), keep);
                        }
                    }
                }
            }
        }
    }

    private void compress(File sourceFile, ZipOutputStream zos, String name, Boolean keep) throws IOException {

        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] files = sourceFile.listFiles();
            if (files == null || files.length == 0) {
                if (keep) {
                    zos.putNextEntry(new ZipEntry(name + "\\"));
                    zos.closeEntry();
                }
            } else {
                for (File f : files) {
                    if (keep) {
                        compress(f, zos, name + "\\" + f.getName(), keep);
                    } else {
                        compress(f, zos, f.getName(), keep);
                    }
                }
            }
        }
    }

    public void decompress(String zipPath, String parent) throws IOException {

        ZipInputStream zin = new ZipInputStream(new FileInputStream(zipPath), Charset.forName("UTF-8"));
        BufferedInputStream bin = new BufferedInputStream(zin);
        java.util.zip.ZipEntry entry;
        while ((entry = zin.getNextEntry()) != null && !entry.isDirectory()) {
            File file = new File(parent, entry.getName());
            if (!file.exists()) {
                 (new File(file.getParent())).mkdirs();
            }
            FileOutputStream out = new FileOutputStream(file);
            BufferedOutputStream bout = new BufferedOutputStream(out);
            int b;
            while ((b = bin.read()) != -1) {
                bout.write(b);
            }
            bout.close();
            out.close();
        }
        bin.close();
        zin.close();
    }



    private void jsonSave(List list, String name) throws IOException {
        File tmpDir = new File(basePath + "/mbInfoJson");
        tmpDir.mkdirs();
        tmpDir = new File(tmpDir,name + ".json");
        FileUtils.write(tmpDir, JSON.toJSONString(list), String.valueOf(Charset.forName("UTF-8")));
    }

    private List getJson(File file, Class cla) throws IOException {
        return JSONArray.parseArray(FileUtils.readFileToString(file, "UTF-8"),cla);
    }

    public void unZip(File file, String path) {
        if (!file.exists()) {
            throw new RuntimeException("文件异常");
        }
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                java.util.zip.ZipEntry entry = (java.util.zip.ZipEntry) entries.nextElement();

                if (entry.isDirectory()) {
                    new File(path + "/" + entry.getName()).mkdirs();
                } else {
                    File tarFile = new File(path + "/" + entry.getName());
                    if (!tarFile.getParentFile().exists()) {
                        tarFile.getParentFile().mkdirs();
                    }
                    tarFile.createNewFile();
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(tarFile);
                    int len;
                    byte[] buf = new byte[BUFFER_SIZE];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0 , len);
                    }
                    fos.close();
                    is.close();

                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
