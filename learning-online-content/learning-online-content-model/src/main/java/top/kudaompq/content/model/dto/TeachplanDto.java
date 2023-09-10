package top.kudaompq.content.model.dto;

import lombok.Data;
import lombok.ToString;
import top.kudaompq.content.model.po.Teachplan;
import top.kudaompq.content.model.po.TeachplanMedia;

import java.util.List;

/**
 * @description: 课程计划树形结构DTO
 * @author: kudaompq
 **/
@Data
@ToString
public class TeachplanDto extends Teachplan{

    /**
     * 课程计划关联的媒资信息
     */
    private TeachplanMedia teachplanMedia;

    private List<TeachplanDto> teachPlanTreeNodes;
}
