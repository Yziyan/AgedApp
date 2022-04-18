package com.zcwxdqy.agedapp.controller;


import com.zcwxdqy.agedapp.service.SkillService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testWx")
@Api("测试测试")
public class TestWxController {


    @Autowired
    private SkillService service;

    @GetMapping("/test")
    public String test() {
        return service.list() + "____微信小程序";
    }




}
