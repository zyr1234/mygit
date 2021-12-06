package com.geovis.jg.common.util;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.IOUtils;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CompressUtils {
    /**
     *
     * @param compressFile 压缩文件必须存在，为zip或rar
     * @param saveDir 存储路径，压缩包将直接解压在此目录
     * @return 解压后的文件绝对路径列表
     */
    public static List<File> decompress(File compressFile, File saveDir) throws IOException {
        if(!compressFile.exists()||!compressFile.isFile())
            throw new IOException("compressFile不存在或不是文件");
        if(!saveDir.exists() || !saveDir.isDirectory())
            throw new IOException("saveDir不存在或不是文件夹");

        String fileName = compressFile.getName().toLowerCase();
        if(fileName.endsWith(".zip")){
            List<File> result = new ArrayList<>();
            try(FileInputStream fileInputStream = new FileInputStream(compressFile);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream,4096);
                    ZipArchiveInputStream zais = new ZipArchiveInputStream(bufferedInputStream,
                            "GBK",false,true)){
                ZipArchiveEntry entry = null ;
                while((entry = zais.getNextZipEntry())!=null){
                    File tmp = new File(saveDir,entry.getName());
                    if (entry.isDirectory()) {
                        tmp.mkdirs();
                    }else{
                        try(FileOutputStream fileOutputStream = new FileOutputStream(tmp);
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream,4096)){
                            IOUtils.copy(zais,bufferedOutputStream);
                            result.add(tmp);
                        }
                    }
                }
            }
            return result;

        }else if(fileName.endsWith(".rar")){
            List<File> result = new ArrayList<>();
            try(FileInputStream fileInputStream = new FileInputStream(compressFile);
                Archive archive = new Archive(fileInputStream);){
                archive.getMainHeader().print();
                for(FileHeader header = archive.nextFileHeader();header!=null;header = archive.nextFileHeader()){
                    String tmpName = header.getFileNameW();
                    if(tmpName.isEmpty())
                        tmpName = header.getFileNameString();
                    File tmpFile = new File(saveDir,tmpName.trim());
                    if(header.isDirectory()){
                        tmpFile.mkdirs();
                    }else{
                        tmpFile.getParentFile().mkdirs();
                        try(FileOutputStream fileOutputStream = new FileOutputStream(tmpFile)){
                            archive.extractFile(header,fileOutputStream);
                            result.add(tmpFile);
                        }
                    }
                }
                return result;
            } catch (RarException e) {
                e.printStackTrace();
                throw new IOException("rar解析错误");
            }

        }else if(fileName.endsWith(".tar.gz") || fileName.endsWith(".tgz")) {
            List<File> result = new ArrayList<>();
            try(FileInputStream fileInputStream = new FileInputStream(compressFile);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream,4096);
                GzipCompressorInputStream gzipCompressorInputStream = new GzipCompressorInputStream(bufferedInputStream);
                TarArchiveInputStream taris = new TarArchiveInputStream(gzipCompressorInputStream,4096);){
                TarArchiveEntry entry = null;
                for(entry = taris.getNextTarEntry();entry!=null;entry=taris.getNextTarEntry()){
                    File tmpFile = new File(saveDir,entry.getName());
                    if(entry.isDirectory()){
                        tmpFile.mkdirs();
                    }else{
                        try(FileOutputStream outputStream = new FileOutputStream(tmpFile)){
                            IOUtils.copy(taris,outputStream);
                            result.add(tmpFile);
                        }
                    }
                }
            }
            return result;

        }else {
            throw new UnsupportedOperationException("暂未支持的格式："+compressFile.getName());
        }
    }

    public static String removeCompressSuffix(String fileName){
        if (fileName.endsWith(".zip")){
            return fileName.substring(0,fileName.lastIndexOf(".zip"));
        }else if (fileName.endsWith(".rar")){
            return fileName.substring(0,fileName.lastIndexOf(".rar"));
        }else if(fileName.endsWith(".tar.gz")){
            return fileName.substring(0,fileName.lastIndexOf(".tar.gz"));
        }else if(fileName.endsWith(".tgz")){
            return fileName.substring(0,fileName.lastIndexOf(".tgz"));
        }else{
            throw new UnsupportedOperationException("未知格式");
        }
    }

    public static File compress(@NotNull List<File> fileList, @NotNull File saveDir, @NotNull String saveName) throws IOException {
        File saveFile = new File(saveDir,saveName);
        if(saveFile.exists())
            throw new IOException("压缩文件错误：文件已存在"+saveFile.getAbsolutePath());
        try(ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(saveFile)){
            zaos.setUseZip64(Zip64Mode.AsNeeded);
            for(File i: fileList){
                ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(i,i.getName());
                zaos.putArchiveEntry(zipArchiveEntry);
                try(FileInputStream fis = new FileInputStream(i);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                ){
                    byte[] buffer = new byte[4096];
                    int len;
                    while((len=bis.read(buffer))!=-1){
                        zaos.write(buffer,0,len);
                    }
                }
                zaos.closeArchiveEntry();
            }
            zaos.finish();
        }
        return saveFile;
    }

    public static boolean isSupported(File f){
        return isSupported(f.getName());
    }

    public static boolean isSupported(String fn){
        return fn.endsWith(".tar.gz") || fn.endsWith(".zip") || fn.endsWith(".rar") || fn.endsWith(".tgz");
    }
}
