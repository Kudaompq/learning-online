package top.kudaompq.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kudaompq.content.mapper.CourseTeacherMapper;
import top.kudaompq.content.model.po.CourseTeacher;
import top.kudaompq.content.model.po.Teachplan;
import top.kudaompq.content.service.CourseTeacherService;

import java.util.List;

/**
 * @description: 课程教师 服务层
 * @author: kudaompq
 **/
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Autowired
    private CourseTeacherMapper teacherMapper;

    @Override
    public List<CourseTeacher> queryCourseTeacher(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseTeacher::getCourseId,courseId);
        return teacherMapper.selectList(wrapper);
    }


    @Override
    public CourseTeacher addCourseTeacher(CourseTeacher teacher,Long companyId) {
        teacherMapper.insert(teacher);
        return teacherMapper.selectById(teacher.getId());
    }

    @Override
    public CourseTeacher updateCourseTeacher(CourseTeacher teacher, Long companyId) {
        CourseTeacher courseTeacher = teacherMapper.selectById(teacher.getId());
        BeanUtils.copyProperties(teacher,courseTeacher);
        teacherMapper.updateById(courseTeacher);
        return teacherMapper.selectById(teacher.getId());
    }

    @Override
    public void deleteCourseTeacher(Long courseId, Long teacherId,Long companyId) {
        LambdaQueryWrapper<CourseTeacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseTeacher::getCourseId,courseId);
        wrapper.eq(CourseTeacher::getId,teacherId);
        teacherMapper.delete(wrapper);
    }
}
