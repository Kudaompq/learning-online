package top.kudaompq.content.service;

import top.kudaompq.content.model.dto.SaveTeachplanDto;
import top.kudaompq.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @description: 课程计划服务层
 * @author: kudaompq
 **/
public interface TeachplanService {

    List<TeachplanDto> findTeachplanTree(Long courseId);

    void saveTeachplan(SaveTeachplanDto teachplanDto);

    void deleteTeachplan(Long teachplanId);

    void moveSort(String moveType, Long planId);
}
