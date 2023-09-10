package top.kudaompq.content.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.content.model.po.CourseTeacher;
import top.kudaompq.content.service.CourseTeacherService;

import java.awt.image.VolatileImage;
import java.util.List;

/**
 * @description: 课程教师控制器
 * @author: kudaompq
 **/
@RestController
@Api(tags = "课程教师管理")
public class CourseTeacherController {

    @Autowired
    private CourseTeacherService teacherService;

    @ApiOperation("查询教师列表")
    @GetMapping("/courseTeacher/list/{courseId}")
    public List<CourseTeacher> list(@PathVariable Long courseId){
        return teacherService.queryCourseTeacher(courseId);
    }

    @ApiOperation("新增课程教师")
    @PostMapping("/courseTeacher")
    public CourseTeacher add(@RequestBody @Validated CourseTeacher teacher){
        Long companyId = 1232141425L;
        return teacherService.addCourseTeacher(teacher,companyId);
    }

    @ApiOperation("修改教师信息")
    @PutMapping("/courseTeacher")
    public CourseTeacher update(@RequestBody @Validated CourseTeacher teacher){
        Long companyId = 1232141425L;
        return teacherService.updateCourseTeacher(teacher,companyId);
    }

    @ApiOperation("删除教师")
    @DeleteMapping("/courseTeacher/course/{courseId}/{teacherId}")
    public void  delete(@PathVariable Long courseId,@PathVariable Long teacherId){
        Long companyId = 1232141425L;
        teacherService.deleteCourseTeacher(courseId,teacherId,companyId);

    }



}
