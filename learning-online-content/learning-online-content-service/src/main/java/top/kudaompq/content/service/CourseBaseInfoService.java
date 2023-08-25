package top.kudaompq.content.service;

import top.kudaompq.content.model.dto.AddCourseDto;
import top.kudaompq.content.model.dto.CourseBaseInfoDto;
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

    /**
     * @description 添加课程基本信息
     * @param companyId  教学机构id
     * @param dto  课程基本信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author Mr.M
     * @date 2022/9/7 17:51
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto);
}
