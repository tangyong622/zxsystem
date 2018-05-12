package com.zx.system.fileload;

import com.zx.system.entity.Constant;
import com.zx.system.ffmepg.ConvertVideo;
import com.zx.system.util.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.Random;


/**
 * Created by Administrator on 2018/5/11.
 */
@Controller
@RequestMapping("/file")
public class FileloadController {


    //上传
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String resumeurl = "";
            try {
                //重新生成文件名，避免乱码问题
                String filename=file.getOriginalFilename();
                String fName=null;
                if (filename.indexOf(".") >= 0) {
                    fName = filename.substring(filename.lastIndexOf("."), filename.length());
                }
                String path = String.valueOf(new Random().nextInt(100)).concat((fName));//拼接新文件名
                resumeurl = String.valueOf(new Date().getTime()).concat(path);
                File f = new File(Constant.UPLOAD_PATH_ORIGINAL);
                if (!f.exists()) {
                    f.mkdirs();
                }
                File f2 = new File(Constant.UPLOAD_PATH_FlV);
                if (!f2.exists()) {
                    f2.mkdirs();
                }
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(Constant.UPLOAD_PATH_ORIGINAL + new File(resumeurl)));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return ConvertVideo.getRun(Constant.UPLOAD_PATH_ORIGINAL+resumeurl,"E:\\demo\\ffmpeg",Constant.UPLOAD_PATH_FlV);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return new JsonResult(2, 0, "上传失败," + e.getMessage(), 0);
            } catch (IOException e) {
                e.printStackTrace();
                return new JsonResult(2, 0, "上传失败," + e.getMessage(), 0);
            } catch (Exception e) {
                e.printStackTrace();
                return new JsonResult(2, 0, "上传失败," + e.getMessage(), 0);
            }
        } else {
            return new JsonResult(2, "", "上传失败，因为文件是空的.", 0);
        }
    }



}

