package top.kudaompq.content.service;

import top.kudaompq.content.model.dto.AddCourseDto;
import top.kudaompq.content.model.dto.CourseBaseInfoDto;
import top.kudaompq.content.model.dto.EditCourseDto;
import top.kudaompq.content.model.dto.QueryCourseParamsDto;
import top.kudaompq.content.model.po.CourseBase;
import top.kudaompq.base.model.PageParams;
import top.kudaompq.base.model.PageResult;

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


    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto);

    /**
     * 根据课程ID获取课程信息
     * @param courseId
     * @return
     */
    CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * 修改课程信息
     * @param companyId
     * @param dto
     * @return
     */
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);
}
