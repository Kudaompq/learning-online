package top.kudaompq.content.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.content.model.dto.SaveTeachplanDto;
import top.kudaompq.content.model.dto.TeachplanDto;
import top.kudaompq.content.service.TeachplanService;

import java.util.List;

/**
 * @description:
 * @author: kudaompq
 **/

@RestController
@Api(tags = "课程计划编辑接口")
public class TeachplanController {

    @Autowired
    private TeachplanService teachplanService;

    @ApiOperation("查询课程计划树形结构")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){
        return teachplanService.findTeachplanTree(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan( @RequestBody SaveTeachplanDto teachplan){
        teachplanService.saveTeachplan(teachplan);
    }


    @ApiOperation("课程计划删除")
    @DeleteMapping("/teachplan/{teachplanId}")
    public void delete(@PathVariable Long teachplanId){
        teachplanService.deleteTeachplan(teachplanId);
    }

    @ApiOperation("课程计划更换顺序")
    @PostMapping("/teachplan/{moveType}/{planId}")
    public void move(@PathVariable(required = true) String moveType,@PathVariable Long planId){
        teachplanService.moveSort(moveType,planId);
    }
}
