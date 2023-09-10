package top.kudaompq.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.kudaompq.content.model.dto.TeachplanDto;
import top.kudaompq.content.model.po.Teachplan;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author kudaompq
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {

    /**
     * 查询课程计划，组成树形结构
     * @param courseId
     * @return
     */
    List<TeachplanDto> selectTreeNodes(Long courseId);

}
