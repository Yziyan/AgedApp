package com.zcwxdqy.agedapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zcwxdqy.agedapp.pojo.po.Skill;
import com.zcwxdqy.agedapp.pojo.vo.request.page.SkillPageReqVo;
import com.zcwxdqy.agedapp.pojo.vo.response.SkillVo;
import com.zcwxdqy.agedapp.pojo.vo.result.PageVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * mybatis plus
 * 业务层：extends IService<Skill>
 *    若不是查询的方法，下面记得要覆盖@Transactional(readOnly = false)
 */

@Transactional(readOnly = true)
public interface SkillService extends IService<Skill> {


    // 分页查询
    PageVo<SkillVo> list(SkillPageReqVo query);



}
