package top.kudaompq.content.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kudaompq.content.mapper.CourseCategoryMapper;
import top.kudaompq.content.model.dto.CourseCategoryTreeDto;
import top.kudaompq.content.service.CourseCategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 课程分类模块 业务层
 * @author: kudaompq
 **/
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;


    /**
     * 课程分类树形结构查询
     * @param id
     * @return
     */
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> dtos = courseCategoryMapper.selectTreeNodes(id);
        // 过滤根节点后，将数据转变成map
        Map<String, CourseCategoryTreeDto> mapTemp = dtos.stream()
                .filter(item -> !id.equals(item.getId()))
                .collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        List<CourseCategoryTreeDto> categoryTreeDtos  = new ArrayList<>();
        // 依次遍历每个元素，排除根节点
        dtos.stream()
                .filter(item -> !id.equals(item.getId()))
                .forEach(item -> {
                    if (item.getParentid().equals(id)){
                        categoryTreeDtos.add(item);
                    }
                    // 找到当前的父节点
                    CourseCategoryTreeDto courseCategoryTreeDto = mapTemp.get(item.getParentid());
                    if (courseCategoryTreeDto != null){
                        if (courseCategoryTreeDto.getChildrenTreeNodes() == null){
                            courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<>());
                        }
                        // 往ChildrenNodes中添加子节点
                        courseCategoryTreeDto.getChildrenTreeNodes().add(item);
                    }
                });
        return categoryTreeDtos;
    }
}
