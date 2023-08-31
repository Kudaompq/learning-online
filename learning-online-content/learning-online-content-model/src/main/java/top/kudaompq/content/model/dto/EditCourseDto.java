package top.kudaompq.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @description:
 * @author: kudaompq
 **/
@Data
@ApiOperation(value = "EditCourseDto",tags = "修改课程基本信息")
public class EditCourseDto extends AddCourseDto{

    @ApiModelProperty(value = "课程id", required = true)
    private Long id;
}
