package com.zx.system.util;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FfmepgUtil {

    /**
     * @param video:即视频路径；
     * @param newbasePath:转化后的视频存放路径
     * @param suffix:转化后视频的名字（截贞之后图片的名字）；
     * @return
     */
    //实现转码
    public static List<String> process(String video, String newbasePath, String suffix) {
        // 判断视频的类型
        int type = checkContentType(video);
        ArrayList<String> list = new ArrayList<String>();
        String StringUrl = null;
        //如果是ffmpeg可以转换的类型直接转码，否则先用mencoder转码成AVI
        if (type == 0) {
            System.out.println("直接将文件转为flv文件");
            StringUrl = processFLV(video, newbasePath, suffix);// 直接将文件转为fly文件
        } else if (type == 1) {
            //不是可解析的格式需转化为avi
            String avifilepath = processAVI(type, video);
            if (avifilepath == null)
                return list;// avi文件没有得到
            StringUrl = processFLV(avifilepath, newbasePath, suffix);// 将avi转为flv

        }

        //转码后视频地址
        String videoUrl = StringUrl + ".flv";

        //生成图片地址
        String pictureUrl = StringUrl + ".jpg";
        list.add(videoUrl);
        list.add(pictureUrl);
        return list;
    }


    //判断视频格式类型
    private static int checkContentType(String video) {
        String type = video.substring(video.lastIndexOf(".") + 1, video.length())
                .toLowerCase();
        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mpg")) {
            return 0;
        } else if (type.equals("wmv")) {
            return 0;
        } else if (type.equals("3gp")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("asf")) {
            return 0;
        } else if (type.equals("asx")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        }
        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if (type.equals("wmv9")) {
            return 1;
        } else if (type.equals("rm")) {
            return 1;
        } else if (type.equals("rmvb")) {
            return 1;
        }
        return 9;
    }

    // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
    private static String processFLV(String oldfilepath, String newbasePath, String suffix) {


        // 文件命名  D:/ffmpeg/ffmpeg.exe-->本地ffmpeg解压后存放地址
        Calendar c = Calendar.getInstance();
        String savename = String.valueOf(c.getTimeInMillis()) + Math.round(Math.random() * 100000);

        List<String> commend = new ArrayList<String>();

        //拼接ffmpeg的执行命令
        commend.add("D:/ffmpeg/ffmpeg.exe");
        commend.add("-i");
        commend.add(oldfilepath);
        commend.add("-ab");
        commend.add("56");
        commend.add("-ar");
        commend.add("22050");
        commend.add("-qscale");
        commend.add("4");
        commend.add("-r");
        commend.add("15");
        commend.add("-s");
        commend.add("600x500");
        commend.add(newbasePath + suffix + ".flv");

        //判断路径目录是否存在，不存在则创建

        File file = new File(newbasePath + suffix + ".flv");
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {

            //Runtime:介绍-对jvm进行操作通过getRuntime实例化，可获得jvm内存消耗；也可异步调用jvm实现所需操作；runtime.exec();
            Runtime runtime = Runtime.getRuntime();
            Process proce = null;
            //视频截图命令，封面图。  8是代表第8秒的时候截图
            String cmd = "";
            String cut = "    D:/ffmpeg/ffmpeg.exe   -i   "
                    + oldfilepath
                    + "   -y   -f   image2   -ss   4   -t   0.001   -s   600x500    " + newbasePath
                    + suffix + ".jpg";
            String cutCmd = cmd + cut;
            proce = runtime.exec(cutCmd);
            //调用线程命令进行转码
            ProcessBuilder builder = new ProcessBuilder(commend);
            builder.command(commend);
            builder.start();
            return newbasePath + suffix;
        } catch (Exception e) {
            e.printStackTrace();
            return "有误";
        }
    }

    //对不是ffmpeg可直接转码的文件类型,有需要的朋友可以操作下。本人在实践运用中没有对此进行处理；

    // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
    private static String processAVI(int type, String video) {
        List<String> commend = new ArrayList<String>();
        commend.add("D:\\FFmpeg\\mencoder");
        commend.add(video);
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
        commend.add("D:\\home\\111.avi");
        try {
            //调用线程命令启动转码
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.start();
            return "D:\\home\\222.avi";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
