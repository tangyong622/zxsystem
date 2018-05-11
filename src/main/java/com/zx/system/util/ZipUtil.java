package com.zx.system.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.LoggerFactory;

public class ZipUtil {
    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(ZipUtil.class);
    private static final int BUFFER = 2048;

    /**
     * 解压文件到指定路径
     *
     * @param filePath
     * @param upZipPath
     * @return 返回解压的文件集合
     */
    public static List<File> unZip(String filePath, String upZipPath) {
        List<File> list = new ArrayList<File>();
        int count = -1;
        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        // 生成指定的保存目录
        String savePath = upZipPath;
        if (!new File(savePath).exists()) {
            new File(savePath).mkdirs();
        }

        try {
            ZipFile zipFile = new ZipFile(filePath, "GBK");
            Enumeration enu = zipFile.getEntries();
            while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();
                if (zipEntry.isDirectory()) {
                    new File(savePath + "/" + zipEntry.getName()).mkdirs();
                    continue;
                }
                if (zipEntry.getName().indexOf("/") != -1) {
                    new File(savePath + "/" + zipEntry.getName().substring(0, zipEntry.getName().lastIndexOf("/"))).mkdirs();
                }
                is = zipFile.getInputStream(zipEntry);
                file = new File(savePath + "/" + zipEntry.getName());
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, BUFFER);

                byte buf[] = new byte[BUFFER];
                while ((count = is.read(buf)) > -1) {
                    bos.write(buf, 0, count);
                }

                bos.flush();
                fos.close();
                is.close();
                list.add(file);
            }

            zipFile.close();
            return list;
        } catch (IOException ioe) {
            log.error(ioe.getMessage());
            return list;
        }
    }

    /***
     * 将文件打成压缩包
     *
     * @param pathName 需打包的文件路径
     * @param zipfilename 压缩包名称
     */
    public static void listToZip(String pathName, String zipfilename) {
        FileInputStream is = null;
        File file = null;
        ZipOutputStream zos = null;
        try {
            if (StringUtils.isNotEmpty(pathName)) {
                String uri="D:/ZIP";
                File f = new File(uri);
                if(!f.exists()){
                    f.mkdirs();
                }
                zipfilename = uri + zipfilename;
                //创建zip文件输出流
                zos = new ZipOutputStream(new FileOutputStream(new File(
                        zipfilename)));
                zos.setEncoding("GBK");
                file = new File(pathName);
                if (file.exists()) {
                    //创建源文件输入流
                    is = new FileInputStream(file);
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    byte[] buf = new byte[BUFFER];
                    int length = -1;
                    while ((length = is.read(buf)) != -1) {
                        zos.write(buf, 0, length);
                        zos.flush();
                    }
                    zos.closeEntry();
                    is.close();
                } else {
                    System.out.println("源文件不存在");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}