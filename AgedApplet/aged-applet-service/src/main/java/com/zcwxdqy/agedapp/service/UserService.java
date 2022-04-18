package com.zcwxdqy.agedapp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zcwxdqy.agedapp.pojo.po.User;
import com.zcwxdqy.agedapp.pojo.vo.request.LoginReqVo;
import com.zcwxdqy.agedapp.pojo.vo.result.DataJsonVo;
import com.zcwxdqy.agedapp.pojo.vo.result.LoginVo;
import org.springframework.transaction.annotation.Transactional;



@Transactional(readOnly = true)
public interface UserService extends IService<User> {

    @Transactional(readOnly = false)
    LoginVo login(LoginReqVo reqVo);
}
