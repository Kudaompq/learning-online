package top.kudaompq.content.service;

import top.kudaompq.content.model.po.CourseTeacher;

import java.util.List;

/**
 * @description: 课程教师 服务层
 * @author: kudaompq
 **/
public interface CourseTeacherService {


    List<CourseTeacher> queryCourseTeacher(Long courseId);

    CourseTeacher addCourseTeacher(CourseTeacher teacher,Long companyId);

    CourseTeacher updateCourseTeacher(CourseTeacher teacher, Long companyId);

    void deleteCourseTeacher(Long courseId, Long teacherId,Long companyId);
}
