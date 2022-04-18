package com.zcwxdqy.agedapp.shiro;


import com.zcwxdqy.agedapp.filter.ErrorFilter;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroCfg {

    @Bean
    public Realm realm() {
        return new TokenRealm(new TokenMatcher());
    }


    /**
     * ShiroFilterFactoryBean：用来告诉Shiro如何进行拦截
     * 1、拦截哪些Url
     * 2、每个Url需要经过哪些filter
     * 3、shiroFilterFactoryBean【方法名必须是这个】
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(Realm realm) {

        ShiroFilterFactoryBean filterBean = new ShiroFilterFactoryBean();
        // 安全管理器【并且告诉使用什么realm】
        filterBean.setSecurityManager(new DefaultWebSecurityManager(realm));

        // 添加一些自定义 Filter
        Map<String, Filter> filters = new HashMap<>();
        filters.put("token", new TokenFilter());
        filterBean.setFilters(filters);



        /*
        1、设置URL如何拦截
        2、使用LinkedHashMap 是为了让Shiro按顺序遍历，也就是写到前面的，会优先进入
        3、map里放的 key 【是拦截的url】； value 【是使用上面内置的过滤器】
        4、anon 【匿名访问】指什么都可以通过【一般用来设置不需要拦截的请求】

         */
        Map<String, String> urlMap = new LinkedHashMap<>();
        // 放行验证码
        urlMap.put("/users/captcha", "anon");

        // 放行登录
        urlMap.put("/users/login", "anon");

        // 放行swagger文档
        urlMap.put("/swagger*/**", "anon");
        urlMap.put("/v2/api-docs/**", "anon");

        // 放行处理Filter内部的异常的请求
        urlMap.put(ErrorFilter.ERROR_URI, "anon");

        // 其他请求使用自定义的Filter
        urlMap.put("/**", "token");


        filterBean.setFilterChainDefinitionMap(urlMap);

        return filterBean;

    }

}
