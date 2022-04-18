package com.zcwxdqy.agedapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcwxdqy.agedapp.enhance.MpLambdaQueryWrapper;
import com.zcwxdqy.agedapp.enhance.MpPage;
import com.zcwxdqy.agedapp.mapStruct.MapStructs;
import com.zcwxdqy.agedapp.mapper.SkillMapper;
import com.zcwxdqy.agedapp.pojo.po.Skill;
import com.zcwxdqy.agedapp.pojo.vo.request.page.SkillPageReqVo;
import com.zcwxdqy.agedapp.pojo.vo.response.SkillVo;
import com.zcwxdqy.agedapp.pojo.vo.result.PageVo;
import com.zcwxdqy.agedapp.service.SkillService;
import org.springframework.stereotype.Service;


/**
 * Mybatis plus
 * Service实现类继承extends ServiceImpl<SkillMapper, Skill>
 * SkillMapper：对应的mapper
 * Skill：对应的实体类po对象
 * 本类的mapper就不用写了，默认会带很多方法
 */

@Service
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements SkillService {


    /**
     * 分页查询
     * @param query：查询条件
     * @return  PageVo<SkillVo>
     */
    @Override
    public PageVo<SkillVo> list(SkillPageReqVo query) {

        // 查询条件
        MpLambdaQueryWrapper<Skill> wrapper = new MpLambdaQueryWrapper<>();

        // 根据关键字查询
        wrapper.like(query.getKeyword(), Skill::getName, Skill::getLevel);

        // 按照id降序排列
        wrapper.orderByDesc(Skill::getId);


        // 分页查询：baseMapper【Mybatis plus提供的】
        return baseMapper
                .selectPage(new MpPage<>(query), wrapper)
                .buildVo(MapStructs.INSTANCE::po2vo);

    }

}
