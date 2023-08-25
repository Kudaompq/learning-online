package top.kudaompq.content.service;

import top.kudaompq.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @description: 课程分类模块 服务层
 * @author: kudaompq
 **/
public interface CourseCategoryService {

    /**
     * 课程分类树形结构查询
     * @param id
     * @return
     */
    List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
