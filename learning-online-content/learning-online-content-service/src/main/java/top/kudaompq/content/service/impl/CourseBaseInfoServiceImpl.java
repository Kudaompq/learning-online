package top.kudaompq.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kudaompq.base.exception.LeaningOnlineException;
import top.kudaompq.content.mapper.*;
import top.kudaompq.content.model.dto.AddCourseDto;
import top.kudaompq.content.model.dto.CourseBaseInfoDto;
import top.kudaompq.content.model.dto.EditCourseDto;
import top.kudaompq.content.model.dto.QueryCourseParamsDto;
import top.kudaompq.content.model.po.*;
import top.kudaompq.content.service.CourseBaseInfoService;
import top.kudaompq.base.model.PageParams;
import top.kudaompq.base.model.PageResult;

import javax.xml.stream.events.DTD;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: kudaompq
 **/
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Autowired
    private CourseMarketMapper courseMarketMapper;

    @Autowired
    private CourseCategoryMapper categoryMapper;

    @Autowired
    private CourseTeacherMapper teacherMapper;

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanMediaMapper mediaMapper;


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
            throw new LeaningOnlineException("课程名称为空");
        }
        if (StringUtils.isBlank(dto.getMt())) {
            throw new LeaningOnlineException("课程分类为空");
        }
        if (StringUtils.isBlank(dto.getSt())) {
            throw new LeaningOnlineException("课程分类为空");
        }
        if (StringUtils.isBlank(dto.getGrade())) {
            throw new LeaningOnlineException("课程等级为空");
        }
        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new LeaningOnlineException("教育模式为空");
        }
        if (StringUtils.isBlank(dto.getUsers())) {
            throw new LeaningOnlineException("适应人群为空");
        }
        if (StringUtils.isBlank(dto.getCharge())) {
            throw new LeaningOnlineException("收费规则为空");
        }
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(dto,courseBase);
        courseBase.setAuditStatus("202002");
        courseBase.setStatus("203001");
        courseBase.setCompanyId(companyId);
        courseBase.setCreateDate(LocalDateTime.now());
        int insert = courseBaseMapper.insert(courseBase);
        if (insert <= 0){
            throw new LeaningOnlineException("新增课程基本信息失败");
        }

        // 向课程营销表保存课程营销信息
        // 课程营销信息
        CourseMarket courseMarket = new CourseMarket();
        // 新增成功后会获取ID
        Long courseId = courseBase.getId();
        BeanUtils.copyProperties(dto,courseMarket);
        courseMarket.setId(courseId);
        int i = saveCourseMarket(courseMarket);
        if (i <= 0){
            throw new LeaningOnlineException("保存课程营销信息失败");
        }
        // 查询课程基本信息及营销信息并返回
        return getCourseBaseInfo(courseId);
    }

    /**
     * 根据课程ID查询课程基本信息（基本和营销信息）
     * @param courseId
     * @return
     */
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) return null;
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        CourseBaseInfoDto dto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,dto);
        if (courseMarket != null){
            BeanUtils.copyProperties(courseMarket,dto);
        }
        // 查询分类名称
        CourseCategory st = categoryMapper.selectById(courseBase.getSt());
        dto.setStName(st.getName());
        CourseCategory mt = categoryMapper.selectById(courseBase.getMt());
        dto.setMtName(mt.getName());
        return dto;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {
        Long courseId = dto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null){
            LeaningOnlineException.cast("课程不存在");
        }
        // 校验本机构只能修改本机构的课程
        if (!courseBase.getCompanyId().equals(companyId)){
            LeaningOnlineException.cast("本机构只能修改本机构的课程");
        }
        // 封装基本信息
        BeanUtils.copyProperties(dto,courseBase);
        courseBase.setChangeDate(LocalDateTime.now());
        // 更新课程基本信息
        courseBaseMapper.updateById(courseBase);

        // 封装营销信息数据
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarket);
        saveCourseMarket(courseMarket);
        // 查询课程信息
        return this.getCourseBaseInfo(courseId);
    }

    @Transactional
    @Override
    public void deleteCourseById(Long courseId) {
        CourseBase course = courseBaseMapper.selectById(courseId);
        if (!course.getAuditStatus().equals("202002")){
            LeaningOnlineException.cast("只有未提交的课程可以被删除");
        }
        courseBaseMapper.deleteById(courseId);
        courseMarketMapper.deleteById(courseId);
        // 课程师资
        LambdaQueryWrapper<CourseTeacher> teacherWrapper = new LambdaQueryWrapper<>();
        teacherWrapper.eq(CourseTeacher::getCourseId,courseId);
        teacherMapper.delete(teacherWrapper);
        // 课程计划
        LambdaQueryWrapper<Teachplan> planWrapper = new LambdaQueryWrapper<>();
        planWrapper.eq(Teachplan::getCourseId,courseId);
        teachplanMapper.delete(planWrapper);
        LambdaQueryWrapper<TeachplanMedia> mediaWrapper = new LambdaQueryWrapper<>();
        mediaWrapper.eq(TeachplanMedia::getCourseId,courseId);
        mediaMapper.delete(mediaWrapper);
    }

    /**
     * 保存课程营销信息
     * @param courseMarket
     * @return
     */
    private int saveCourseMarket(CourseMarket courseMarket) {
        // 收费规则
        String charge = courseMarket.getCharge();
        if (StringUtils.isBlank(charge)){
            throw new LeaningOnlineException("收费规则没有选择");
        }
        // 收费规则为收费
        if (charge.equals("201001")){
            if (courseMarket.getPrice() == null ||
            courseMarket.getPrice().floatValue() <= 0){
                throw new LeaningOnlineException("课程为收费价格不能为空必须大于0");
            }
        }
        // 根据ID从课程营销表查询
        CourseMarket courseMarketObj = courseMarketMapper.selectById(courseMarket.getId());
        if (courseMarketObj == null){
            return courseMarketMapper.insert(courseMarket);
        }else {
            BeanUtils.copyProperties(courseMarket,courseMarketObj);
            courseMarketObj.setId(courseMarket.getId());
            return courseMarketMapper.updateById(courseMarketObj);
        }
    }
}
