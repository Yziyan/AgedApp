package com.zcwxdqy.agedapp.controller;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zcwxdqy.agedapp.pojo.vo.result.CodeMsg;
import com.zcwxdqy.agedapp.pojo.vo.result.JsonVo;
import com.zcwxdqy.agedapp.util.JsonVos;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.function.Function;


/**
 * 公共的父类【删除、更新、添加】
 * @param <Po>： 用于数据库保存的
 * @param <ReqVo>：用于前端传参的
 */

@Validated
public abstract class BaseController<Po, ReqVo> {

    // 获取到 Mybatis plus的 Service
    protected abstract IService<Po> getService();

    protected abstract Function<ReqVo, Po> getFunction();


    /**
     * 后端验证：【放在参数名上面 】@NotBlank(message = "")
     * @param id：要删除记录的id字符串
     * @return ：删除成功or失败
     */
    @PostMapping("/remove")
    @ApiOperation("删除一条或者多条数据")
    public JsonVo remove( @NotBlank(message = "id不能为空") @RequestParam String id) {

        /*
        1、getService()是 Mybatis plus的
        2、removeByIds()可以删除多条或者一条
        3、Arrays.asList(id.split(",")))是将字符串用“ ， ” 切割转换成集合
         */
        if (getService().removeByIds(Arrays.asList(id.split(",")))) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.raise(CodeMsg.REMOVE_ERROR);
        }

    }

    /**
     * 后端验证：【若是一个 model,想要验证则】@Valid
     * @param reqVo：前端传过来的参数
     * @return ：保存\编辑 【成功 or 失败】
     */

    @PostMapping("/save")
    @ApiOperation("添加或者更新")
    public JsonVo save(@Valid @RequestBody ReqVo reqVo) {

        // 将 reqVo -> Po
        Po po = getFunction().apply(reqVo);
        if (getService().saveOrUpdate(po)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.raise(CodeMsg.SAVE_ERROR);
        }

    }


}
