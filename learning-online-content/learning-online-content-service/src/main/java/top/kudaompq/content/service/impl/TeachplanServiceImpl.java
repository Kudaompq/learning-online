package top.kudaompq.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kudaompq.base.exception.LeaningOnlineException;
import top.kudaompq.content.mapper.TeachplanMapper;
import top.kudaompq.content.mapper.TeachplanMediaMapper;
import top.kudaompq.content.model.dto.SaveTeachplanDto;
import top.kudaompq.content.model.dto.TeachplanDto;
import top.kudaompq.content.model.po.Teachplan;
import top.kudaompq.content.model.po.TeachplanMedia;
import top.kudaompq.content.service.TeachplanService;

import java.util.List;

/**
 * @description: 课程计划服务层
 * @author: kudaompq
 **/
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;

    /**
     * 查找教学计划树状结构
     * @param courseId
     * @return
     */
    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    /**
     * 保存或修改课程计划
     * @param teachplanDto
     */
    @Transactional
    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {
        Long id = teachplanDto.getId();
        // 修改计划
        if (id != null){
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        }else {
            // 查询同父同等级的课程计划数量
            int count = getTeachplanCount(teachplanDto.getCourseId(),teachplanDto.getParentid());
            Teachplan teachplan = new Teachplan();
            teachplan.setOrderby(count);
            BeanUtils.copyProperties(teachplanDto,teachplan);
            teachplanMapper.insert(teachplan);
        }
    }

    @Transactional
    @Override
    public void deleteTeachplan(Long teachplanId) {
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if (teachplan == null) LeaningOnlineException.cast("该课程计划不存在");
        if (teachplan.getGrade() == 1){
            LambdaQueryWrapper<Teachplan> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Teachplan::getParentid,teachplanId);
            Integer count = teachplanMapper.selectCount(wrapper);
            if (count != 0){
                LeaningOnlineException.cast("课程计划信息还有子级信息，无法操作");
            }else {
                teachplanMapper.deleteById(teachplanId);
            }
        }else {
            int i = teachplanMapper.deleteById(teachplanId);
            if (i != 0){
                LambdaQueryWrapper<TeachplanMedia> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(TeachplanMedia::getTeachplanId,teachplanId);
                teachplanMediaMapper.delete(wrapper);
            }
        }
    }

    @Override
    public void moveSort(String moveType, Long planId) {
        Teachplan teachplan = teachplanMapper.selectById(planId);
        Long parentid = teachplan.getParentid();
        int orderNum = teachplan.getOrderby();
        if ("moveup".equals(moveType)){
            orderNum--;
        }else if ("movedown".equals(moveType)){
            orderNum++;
        }else {
            LeaningOnlineException.cast("移动动作出错");
        }
        LambdaQueryWrapper<Teachplan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teachplan::getParentid,parentid);
        wrapper.eq(Teachplan::getOrderby,orderNum);
        Teachplan teachplanObj = teachplanMapper.selectOne(wrapper);
        if (teachplanObj == null) LeaningOnlineException.cast("无法移动课程计划");
        // 交换排序
        int temp = teachplan.getOrderby();
        teachplan.setOrderby(teachplanObj.getOrderby());
        teachplanObj.setOrderby(temp);
        teachplanMapper.updateById(teachplan);
        teachplanMapper.updateById(teachplanObj);
    }

    /**
     * 获取最新的排序号
     * @param courseId 课程ID
     * @param parentid 父课程计划ID
     * @return
     */
    private int getTeachplanCount(Long courseId, Long parentid) {
        LambdaQueryWrapper<Teachplan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teachplan::getCourseId,courseId);
        wrapper.eq(Teachplan::getParentid,parentid);
        return teachplanMapper.selectCount(wrapper) + 1;
    }
}
