package top.kudaompq.content.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kudaompq.content.model.dto.QueryCourseParamsDto;
import top.kudaompq.content.model.po.CourseBase;
import top.kudaompq.content.service.CourseBaseInfoService;
import top.kudaompq.model.PageParams;
import top.kudaompq.model.PageResult;

/**
 * @description:
 * @author: kudaompq
 **/

@RestController
@Api(tags = "课程信息管理接口")
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams,
                                       @RequestBody(required = false) QueryCourseParamsDto queryCourseParams){
        return courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParams);
    }
}
