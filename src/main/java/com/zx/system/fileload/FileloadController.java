package com.zx.system.fileload;

import com.zx.system.entity.Constant;
import com.zx.system.ffmepg.ConvertVideo;
import com.zx.system.util.FileUtil;
import com.zx.system.util.JsonResult;
import com.zx.system.util.StringUtils;
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


    //上传视频
    @RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadVideo(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String resumeurl = "";
            try {
                //重新生成文件名，避免乱码问题
                String filename = file.getOriginalFilename();
                String fName = null;
                if (filename.indexOf(".") >= 0) {
                    fName = filename.substring(filename.lastIndexOf("."), filename.length());
                }
                String path = String.valueOf(new Random().nextInt(100)).concat((fName));//拼接新文件名
                resumeurl = String.valueOf(new Date().getTime()).concat(path);
                File f = new File("static/video/");
                if (!f.exists()) {
                    f.mkdirs();
                }
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("static/video/" + new File(resumeurl)));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return new JsonResult("/video/" + resumeurl);
//                if (StringUtils.equals("flv", FileUtil.getType(fName))) {
//                    return new JsonResult("/video/" + resumeurl);
//                }
//                return ConvertVideo.getRun("static/video/" + resumeurl, Constant.VIDEO_FFMPEG);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return new JsonResult(400, 0, "上传失败," + e.getMessage(), 0);
            } catch (IOException e) {
                e.printStackTrace();
                return new JsonResult(400, 0, "上传失败," + e.getMessage(), 0);
            } catch (Exception e) {
                e.printStackTrace();
                return new JsonResult(400, 0, "上传失败," + e.getMessage(), 0);
            }
        } else {
            return new JsonResult(400, "", "上传失败，因为文件是空的.", 0);
        }
    }

    //上传图片文件
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String resumeurl = "";
            try {
                //重新生成文件名，避免乱码问题
                String filename = file.getOriginalFilename();
                String fName = null;
                if (filename.indexOf(".") >= 0) {
                    fName = filename.substring(filename.lastIndexOf("."), filename.length());
                }
                String path = String.valueOf(new Random().nextInt(100)).concat((fName));//拼接新文件名
                resumeurl = String.valueOf(new Date().getTime()).concat(path);
                File f = new File("static/file/");
                if (!f.exists()) {
                    f.mkdirs();
                }
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("static/file/" + new File(resumeurl)));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return new JsonResult("/file/"  + resumeurl);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return new JsonResult(400, 0, "上传失败," + e.getMessage(), 0);
            } catch (IOException e) {
                e.printStackTrace();
                return new JsonResult(400, 0, "上传失败," + e.getMessage(), 0);
            } catch (Exception e) {
                e.printStackTrace();
                return new JsonResult(400, 0, "上传失败," + e.getMessage(), 0);
            }
        } else {
            return new JsonResult(400, "", "上传失败，因为文件是空的.", 0);
        }
    }

    //删除线上文件
    @RequestMapping(value = "/fileDelete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult fileDelete(String path) {
        FileUtil.delFile("static/"+path);
        return new JsonResult(0, "删除成功");
    }


}

