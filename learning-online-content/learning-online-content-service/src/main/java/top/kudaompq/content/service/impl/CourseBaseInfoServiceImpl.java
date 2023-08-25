package top.kudaompq.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kudaompq.content.mapper.CourseBaseMapper;
import top.kudaompq.content.model.dto.AddCourseDto;
import top.kudaompq.content.model.dto.CourseBaseInfoDto;
import top.kudaompq.content.model.dto.QueryCourseParamsDto;
import top.kudaompq.content.model.po.CourseBase;
import top.kudaompq.content.service.CourseBaseInfoService;
import top.kudaompq.model.PageParams;
import top.kudaompq.model.PageResult;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description:
 * @author: kudaompq
 **/
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto) {
        //拼装查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //根据名称模糊查询,在sql中拼接 course_base.name like '%值%'
        queryWrapper.like(
                        StringUtils.isNotEmpty(courseParamsDto.getCourseName()),
                        CourseBase::getName,
                        courseParamsDto.getCourseName());
        //根据课程审核状态查询 course_base.audit_status = ?
        queryWrapper.eq(
                StringUtils.isNotEmpty(courseParamsDto.getAuditStatus()),
                CourseBase::getAuditStatus,
                courseParamsDto.getAuditStatus());
        //按课程发布状态查询
        queryWrapper.eq(
                StringUtils.isNotEmpty(courseParamsDto.getPublishStatus()),
                CourseBase::getStatus,
                courseParamsDto.getPublishStatus());
        //创建page分页参数对象，参数：当前页码，每页记录数
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        //开始进行分页查询
       courseBaseMapper.selectPage(page, queryWrapper);
        return  new PageResult<>(page);
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {
        if (StringUtils.isBlank(dto.getName())){
            throw new RuntimeException("课程名称为空");
        }
        if (StringUtils.isBlank(dto.getMt())) {
            throw new RuntimeException("课程分类为空");
        }
        if (StringUtils.isBlank(dto.getSt())) {
            throw new RuntimeException("课程分类为空");
        }
        if (StringUtils.isBlank(dto.getGrade())) {
            throw new RuntimeException("课程等级为空");
        }
        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new RuntimeException("教育模式为空");
        }
        if (StringUtils.isBlank(dto.getUsers())) {
            throw new RuntimeException("适应人群为空");
        }
        if (StringUtils.isBlank(dto.getCharge())) {
            throw new RuntimeException("收费规则为空");
        }
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(dto,courseBase);
        courseBase.setAuditStatus("202002");
        courseBase.setStatus("203001");
        courseBase.setCompanyId(companyId);
        courseBase.setCreateDate(LocalDateTime.now());
        int insert = courseBaseMapper.insert(courseBase);
        if (insert <= 0){
            throw new RuntimeException("新增课程基本信息失败");
        }

        //todo:向课程营销表保存课程营销信息
        //todo:查询课程基本信息及营销信息并返回
        return null;
    }
}
