package com.zcwxdqy.agedapp.pojo.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginReqVo {


    @NotBlank
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @NotBlank
    @ApiModelProperty(value = "验证码", required = true)
    private String captcha;
}
