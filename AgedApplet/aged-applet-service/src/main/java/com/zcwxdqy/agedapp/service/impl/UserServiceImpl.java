package com.zcwxdqy.agedapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcwxdqy.agedapp.cache.Caches;
import com.zcwxdqy.agedapp.enhance.MpLambdaQueryWrapper;
import com.zcwxdqy.agedapp.mapStruct.MapStructs;
import com.zcwxdqy.agedapp.mapper.UserMapper;
import com.zcwxdqy.agedapp.pojo.po.User;
import com.zcwxdqy.agedapp.pojo.vo.request.LoginReqVo;
import com.zcwxdqy.agedapp.pojo.vo.result.CodeMsg;
import com.zcwxdqy.agedapp.pojo.vo.result.LoginVo;
import com.zcwxdqy.agedapp.service.UserService;
import com.zcwxdqy.agedapp.util.Constants;
import com.zcwxdqy.agedapp.util.JsonVos;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public LoginVo login(LoginReqVo reqVo) {

        // 通过邮箱查询user
        MpLambdaQueryWrapper<User> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, reqVo.getEmail());
        User userPo = baseMapper.selectOne(wrapper);

        if (userPo == null) {
            return JsonVos.raise(CodeMsg.WRONG_USERNAME);
        }

        if (!userPo.getPassword().equals(reqVo.getPassword())) {
            return JsonVos.raise(CodeMsg.WRONG_PASSWORD);
        }

        if (userPo.getState() == Constants.UserStatus.UNABLE) {
            return JsonVos.raise(CodeMsg.USER_LOCKED);
        }

        // 更新登录时间
        userPo.setLoginTime(new Date());
        baseMapper.updateById(userPo);

        // 生成Token
        String token = UUID.randomUUID().toString();

        // 将对象其放入缓存中
        Caches.putToken(token, userPo);

        // 将 po -> vo
        LoginVo vo = MapStructs.INSTANCE.po2loginVo(userPo);
        vo.setToken(token);


        return vo;
    }
}
