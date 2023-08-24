package top.kudaompq.content.service;

import top.kudaompq.content.model.dto.QueryCourseParamsDto;
import top.kudaompq.content.model.po.CourseBase;
import top.kudaompq.model.PageParams;
import top.kudaompq.model.PageResult;

/**
 * @description: 课程管理服务类
 * @author: kudaompq
 **/
public interface CourseBaseInfoService {

    /**
     * 课程分页查询
     * @param pageParams 分页查询参数
     * @param courseParamsDto 查询条件
     * @return 查询结果
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto);

}
