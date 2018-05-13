package com.zx.system.util;

import java.io.File;

/**
 * Created by Administrator on 2018/5/12.
 */
public class FileUtil {

    //获取文件后缀名
    public static String getType(String fileName){
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    //改文件后缀名
    public static String changeType(String fileName,String type){

        return fileName.substring(0,fileName.lastIndexOf(".")+1)+type;
    }

    //删除文件
    public static void delFile(String path){
        File file=new File(path);
        if(file.isFile()){
            file.delete();
        }
    }
}
