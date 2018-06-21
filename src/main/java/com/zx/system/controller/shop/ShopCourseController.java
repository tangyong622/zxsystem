package com.zx.system.controller.shop;

import com.zx.system.service.CourseService;
import com.zx.system.util.JsonResult;
import com.zx.system.util.TokenUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/5/22/022.
 */
@RestController
@RequestMapping("/shop/course")
public class ShopCourseController {

    @Autowired
    private CourseService courseService;

    //查看首页banner
    @RequestMapping(value = "/findHeadList",method = RequestMethod.POST)
    @ApiOperation(value = "查看所有课程", notes = "查看所有课程")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = true, dataType = "String"),
    })
    public JsonResult findHeadList(String token){
        JsonResult result = TokenUtils.getCache(token);
        if(result.getCode() != 0){
            return result;
        }
        return courseService.getCourseHeadList();
    }

    //查看课程
    @RequestMapping(value = "/findList",method = RequestMethod.POST)
    @ApiOperation(value = "查看所有课程", notes = "查看所有课程")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = true, dataType = "String"),
    })
    public JsonResult findList(String token){
        JsonResult result = TokenUtils.getCache(token);
        if(result.getCode() != 0){
            return result;
        }
        return courseService.getCourseList();
    }

    //查看课程
    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    @ApiOperation(value = "查看课程详情", notes = "查看课程章节与视频")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "课程id", required = true, dataType = "String"),
    })
    public JsonResult getCourseDetail(String token,String id){
        JsonResult result = TokenUtils.getCache(token);
        if(result.getCode() != 0){
            return result;
        }
        return courseService.getCourseDetail(id);
    }
}
