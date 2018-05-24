package com.zx.system.service;

import com.zx.system.entity.Video;
import com.zx.system.mapper.VideoMapper;
import com.zx.system.util.FileUtil;
import com.zx.system.util.JsonResult;
import com.zx.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/5/13/013.
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
            video.setPage((page - 1) * limit);
            video.setLimit(limit);
            return new JsonResult(0, videoMapper.findList(video), "视频列表", count);
        }
        return new JsonResult(400, "暂无数据");
    }

    //编辑视频
    public JsonResult form(Video video) {
        if (StringUtils.isEmpty(video.getId())) {//新增
            video.insert();
            videoMapper.insert(video);
        } else {
            Video old = (Video)videoMapper.get(video.getId());
            if(!StringUtils.equals(old.getUrl(),video.getUrl())){
                FileUtil.delFile("static" + old.getUrl());
            }
            video.update();
            videoMapper.update(video);
        }
        return new JsonResult(0, "编辑视频成功");
    }

    //删除视频
    public JsonResult delete(Video video) {
        FileUtil.delFile("static" + video.getUrl());
        videoMapper.delete(video.getId());
        return new JsonResult(0, "删除视频成功");
    }
}
