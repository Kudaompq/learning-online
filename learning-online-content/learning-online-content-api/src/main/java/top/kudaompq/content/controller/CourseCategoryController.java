package top.kudaompq.content.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kudaompq.content.model.dto.CourseCategoryTreeDto;
import top.kudaompq.content.service.CourseCategoryService;

import java.util.List;

/**
 * @description: 课程分类控制器
 * @author: kudaompq
 **/


@Slf4j
@Api(tags = "课程分类管理接口")
@RestController
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService courseCategoryService;

    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        return courseCategoryService.queryTreeNodes("1");
    }

}
