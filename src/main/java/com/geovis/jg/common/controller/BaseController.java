package com.geovis.jg.common.controller;

//import com.geovis.jg.qqzdmb.config.YmlParam;
//import com.geovis.jg.common.config.YmlParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/qqzdmb/api")
public class BaseController {
    /*@Autowired
    YmlParam ymlParam;*/
    @InitBinder
    public void initBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class,new CustomDateEditor(dateFormat,true));
    }
    /**
     * 检查参数
     * @return 没有错误返回null
     * @author Morris
     * @date 2020/9/8 21:37
     */
    protected String checkParamError(BindingResult result){
        if(result.hasErrors()){
            StringBuilder errors = new StringBuilder();
            for(FieldError i:result.getFieldErrors()){
                errors.append(i.getDefaultMessage()).append("\n");
            }
            return errors.toString();
        }
        return null;
    }

    /**
     * controller下载通用处理
     * @author Morris
     * @date 2020/6/17 21:05
     */
    protected void downloadProcess(HttpServletResponse resp, File tmp) throws IOException {
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(tmp.getName(),"utf-8"));
        try(FileInputStream fis = new FileInputStream(tmp);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = resp.getOutputStream();
        ){
            int i;
            byte[] buffer = new byte[1024];
            while((i=bis.read(buffer))!=-1){
                os.write(buffer,0,i);
            }
        }
    }
/////////////////////////////////////////////////////统一下载//////////////////////////////////////////////////////////

    /*@GetMapping(value = "/download/")
    @ApiOperation(value = "统一下载", notes = "hahah")
    public void swqbYx(HttpServletResponse resp,@RequestParam String path) throws IOException {
        File f = new File(ymlParam.getFileBaseDir()+"/"+path);
        if(!f.exists())
            throw new RuntimeException("文件不存在"+f.getAbsolutePath());
        downloadProcess(resp,f);
    }*/
}
