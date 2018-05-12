package com.zx.system.ffmepg;

import com.zx.system.util.FileUtil;
import com.zx.system.util.JsonResult;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ConvertVideo {

    private static String inputPath = "";//待转码视频路径
    private static String outputPath = "";
    private static String ffmpegPath = "";

    public static void main(String[] args) {

        getPath("E:\\demo\\11111111111.mp4",
                "E:\\demo\\ffmpeg",
                "E:\\demo\\output");
        if (!checkfile(inputPath)) {
            System.out.println(inputPath + " is not file");
            return;
        }
            process();
    }

    private static void getPath(String input, String ffmpeg, String output) {
        inputPath = input;
        outputPath = output;
        ffmpegPath = ffmpeg;
    }

    public static JsonResult getRun(String inputPath, String ffmegPath, String outputPath) {
        System.out.println(inputPath + "  " + ffmegPath + " " + outputPath);
        getPath(inputPath, ffmegPath, outputPath);
        if (!checkfile(inputPath)) {
            System.out.println(inputPath + " is not file");
            return new JsonResult(400,"目标文件不存在");
        }
        return process();
    }


    private static JsonResult process() {
        int type = checkContentType();
        JsonResult status = null;
        if (type == 0) {
            System.out.println("直接将文件转换为flv文件");
            status = processFLV(inputPath);//直接将文件转为flv文件

        } else if (type == 1) {
            System.out.println("先转成avi");
            String avifilepath = processAVI(type);
            if (avifilepath == null) {

                return new JsonResult(400,"avi文件没有得到");//avi文件没有得到
            }
            status = processFLV(avifilepath);
        }
        return status;

    }

    private static int checkContentType() {
        String type = inputPath.substring(inputPath.lastIndexOf(".") + 1, inputPath.length()).toLowerCase();
        //ffmpeg能解难析的格式 ：（asx,asf,mpg,wmv,3gp,mp4,mov,avi,flv等）
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mpg")) {
            return 0;
        } else if (type.equals("asx")) {
            return 0;
        } else if (type.equals("asf")) {
            return 0;
        } else if (type.equals("wmv")) {
            return 0;
        } else if (type.equals("3gp")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("avi")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        }
        //对ffmpeg无法解析的文件格式（wmv9,rm,rmvb等）
        //可以先用别的工具（mencoder）转换为avi（ffmpeg能解析的）格式
        else if (type.equals("wmv9")) {
            return 1;
        } else if (type.equals("rm")) {
            return 1;
        } else if (type.equals("rmvb")) {
            return 1;
        }

        return 9;
    }

    public static boolean checkfile(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            return false;
        }
        return true;
    }

    //对ffmpeg无法解析匆文件格式（wmv9，rm,rmvb等，可以先用别的（moncoder）转换为avi）
    private static String processAVI(int type) {
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegPath + "\\mencoder");
        commend.add(inputPath);
        commend.add("-oac");
        commend.add("lavc");
        commend.add("-lavcopts");
        commend.add("acodec=mp3:abitrate=64");
        commend.add("-ovc");
        commend.add("xvid");
        commend.add("-xvidencopts");
        commend.add("bitrate=600");
        commend.add("-of");
        commend.add("avi");
        commend.add("-o");
        commend.add(outputPath + "\\a.avi");
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.start();
            return outputPath + "a.avi";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    //ffmpeg能解析格式：（asx,asf,mpg,wmv,3gp.mp4.mov,avi.flv等）
    private static JsonResult processFLV(String oldfilepath) {
        if (!checkfile(oldfilepath)) {
            FileUtil.delFile(inputPath);
            FileUtil.delFile(oldfilepath);
            System.out.println(oldfilepath + " is not file");
            return new JsonResult(400,"文件不存在");
        }
        //文件命名
        //Calendar c=Calendar.getInstance();
        //String savename=String.valueOf(c.getTimeInMillis())+Math.round(Math.random()*100000);
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegPath + "\\ffmpeg");
        commend.add("-i");
        commend.add(oldfilepath);
        commend.add("-ab");
        commend.add("56");
        commend.add("-ar");
        commend.add("22050");
        commend.add("-qscale");
        commend.add("8");
        commend.add("-r");
        commend.add("15");
        commend.add("-s");
        commend.add("480*272");
        String outurl = FileUtil.changeType(inputPath, "flv");
        commend.add(outurl);

        try {
            Process videoProcess = new ProcessBuilder(commend).redirectErrorStream(true).start();
            new ClearOutput(videoProcess.getErrorStream()).start();
            new ClearOutput(videoProcess.getInputStream()).start();
            videoProcess.waitFor();
            FileUtil.delFile(inputPath);
            FileUtil.delFile(oldfilepath);
            return new JsonResult(0,outurl,"上传成功",0);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(400,"上传压缩失败");
        }
    }


    static class ClearOutput extends Thread {

        InputStream in = null;

        public ClearOutput(InputStream in) {
            this.in = in;
        }

        public void run() {
            int len = -1;
            try {
                while ((len = in.read()) != -1) {
                    //System.out.println(len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}