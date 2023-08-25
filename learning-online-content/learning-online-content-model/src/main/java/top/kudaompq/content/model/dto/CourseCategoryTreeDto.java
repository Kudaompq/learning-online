package top.kudaompq.content.model.dto;

import lombok.Data;
import top.kudaompq.content.model.po.CourseCategory;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: kudaompq
 **/
@Data
public class CourseCategoryTreeDto  extends CourseCategory implements Serializable {

    List<CourseCategoryTreeDto> childrenTreeNodes;
}
