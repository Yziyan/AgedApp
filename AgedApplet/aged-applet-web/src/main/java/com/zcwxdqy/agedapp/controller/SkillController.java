package com.zcwxdqy.agedapp.controller;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zcwxdqy.agedapp.mapStruct.MapStructs;
import com.zcwxdqy.agedapp.pojo.po.Skill;
import com.zcwxdqy.agedapp.pojo.vo.request.page.SkillPageReqVo;
import com.zcwxdqy.agedapp.pojo.vo.request.save.SkillReqVo;
import com.zcwxdqy.agedapp.pojo.vo.response.SkillVo;
import com.zcwxdqy.agedapp.pojo.vo.result.DataJsonVo;
import com.zcwxdqy.agedapp.pojo.vo.result.PageJsonVo;
import com.zcwxdqy.agedapp.service.SkillService;
import com.zcwxdqy.agedapp.util.JsonVos;
import com.zcwxdqy.agedapp.util.Streams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/skills")
@Api(tags = "技巧")
public class SkillController extends BaseController<Skill, SkillReqVo> {


    @Autowired
    private SkillService skillService;

    @GetMapping("/list")
    @ApiOperation("查询所有的技巧")
    public DataJsonVo<List<SkillVo>> list() {

        List<SkillVo> skillVos = Streams.map(skillService.list(), MapStructs.INSTANCE::po2vo);
        return JsonVos.ok(skillVos, "查询成功");
    }

    @GetMapping
    @ApiOperation("分页查询")
    public PageJsonVo<SkillVo> list(SkillPageReqVo query) {
        return JsonVos.ok(skillService.list(query));
    }


    @Override
    protected IService<Skill> getService() {
        return skillService;
    }

    @Override
    protected Function<SkillReqVo, Skill> getFunction() {
        // 将 ReqVo -> Po
        return MapStructs.INSTANCE::reqVo2po;
    }
}
