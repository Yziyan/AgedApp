package com.zcwxdqy.agedapp.controller;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wf.captcha.utils.CaptchaUtil;
import com.zcwxdqy.agedapp.cache.Caches;
import com.zcwxdqy.agedapp.mapStruct.MapStructs;
import com.zcwxdqy.agedapp.pojo.po.User;
import com.zcwxdqy.agedapp.pojo.vo.request.LoginReqVo;
import com.zcwxdqy.agedapp.pojo.vo.request.save.UserReqVo;
import com.zcwxdqy.agedapp.pojo.vo.response.UserVo;
import com.zcwxdqy.agedapp.pojo.vo.result.CodeMsg;
import com.zcwxdqy.agedapp.pojo.vo.result.DataJsonVo;
import com.zcwxdqy.agedapp.pojo.vo.result.JsonVo;
import com.zcwxdqy.agedapp.pojo.vo.result.LoginVo;
import com.zcwxdqy.agedapp.service.UserService;
import com.zcwxdqy.agedapp.shiro.TokenFilter;
import com.zcwxdqy.agedapp.util.JsonVos;
import com.zcwxdqy.agedapp.util.Streams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/users")
@Api(tags = "用户")
@Validated
public class UserController extends BaseController<User, UserReqVo> {


    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @ApiOperation("查询所有的用户")
    public DataJsonVo<List<UserVo>> list() {
        List<UserVo> userVos = Streams.map(userService.list(), MapStructs.INSTANCE::po2vo);
        return JsonVos.ok(userVos, "查询成功");
    }

    @GetMapping("/captcha")
    @ApiOperation("生成验证码")
    public void captcha(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }


    @PostMapping("/login")
    @ApiOperation("登录")
    public DataJsonVo<LoginVo> login(@Valid @RequestBody LoginReqVo reqVo, HttpServletRequest request) {
        // 验证码是否正确
        System.out.println(request.getSession().getAttribute("captcha"));
        if (CaptchaUtil.ver(reqVo.getCaptcha(), request)) {
            return JsonVos.ok(userService.login(reqVo));
        }
        return JsonVos.raise(CodeMsg.WRONG_CAPTCHA);
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public JsonVo logout(@RequestHeader(TokenFilter.HEADER_TOKEN) String token) {
        // 清空缓存中的token就可以了
        Caches.remove(token);
        return JsonVos.ok();
    }


    @Override
    protected IService<User> getService() {
        return userService;
    }

    @Override
    protected Function<UserReqVo, User> getFunction() {
        // 将 ReqVo -> Po
        return MapStructs.INSTANCE::reqVo2po;
    }
}
