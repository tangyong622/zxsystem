package com.zx.system.service;

import com.zx.system.entity.Video;
import com.zx.system.mapper.VideoMapper;
import com.zx.system.util.FileUtil;
import com.zx.system.util.JsonResult;
import com.zx.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018/5/20/020.
 */
@Service
public class VideoService {

    @Autowired
    private VideoMapper videoMapper;

    //视频列表
    public JsonResult findList(Video video) {
        //查看总数
        int count = videoMapper.findListCount(video);
        if (count > 0) {
            Integer page = video.getPage();
            if (page == null) {
                page = 1;
            }
            Integer limit = video.getLimit();
            if (limit == null) {
                limit = 10;
            }
            video.setPage((page - 1) * 30);
            return new JsonResult(0, videoMapper.findList(video), "视频列表", count);
        }
        return new JsonResult(400, "暂无数据");
    }

    //编辑视频
    @Transactional
    public JsonResult form(Video video) {
        if (StringUtils.isEmpty(video.getId())) {//新增
            video.insert();
            videoMapper.insert(video);
        } else {
            video.update();
            Video old = videoMapper.get(video.getId());
            if (!StringUtils.equals(video.getBigImg(), old.getBigImg())) {//更换大图，删除原来的图片
                FileUtil.delFile("static/" + old.getBigImg());
            }
            if (!StringUtils.equals(video.getImg(), old.getImg())) {//更换封面，删除原来的图片
                FileUtil.delFile("static/" + old.getImg());
            }
            if (!StringUtils.equals(video.getUrl(), old.getUrl())) {//更换视频，删除原来的图片
                FileUtil.delFile("static/" + old.getUrl());
            }
            videoMapper.update(video);
        }
        return new JsonResult(0, "编辑视频成功");
    }

    //删除视频
    public JsonResult delete(Video video) {
        videoMapper.delete(video.getId());
        FileUtil.delFile("static" + video.getBigImg());
        FileUtil.delFile("static" + video.getImg());
        FileUtil.delFile("static" + video.getUrl());
        return new JsonResult(0, "删除视频成功");
    }


}
