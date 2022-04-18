package com.zcwxdqy.agedapp.cfg;

import com.zcwxdqy.agedapp.shiro.TokenFilter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 *  Swagger接口文档的配置
 *  访问地址: Url + swagger-ui/index.html
 */

@Configuration
public class SwaggerCfg implements InitializingBean {
    @Autowired
    private Environment environment;
    private boolean enable;


    @Bean
    public Docket sysDocket() {
        return groupDocket(
                "01_用户",                          // 分组模块
                "/user.*",                         // 正则表达式，想要的模块。
                "用户模块文档",                     // 模块标题
                "登录，注册，修改信息");       // 描述信息
    }

    @Bean
    public Docket skillDocket() {
        return groupDocket(
                "02_技巧模块",
                "/skill.*",
                "掌握技巧模块",
                "查询，修改，xx，xxx");
    }

    @Bean
    public Docket metadataDocket() {
        return groupDocket(
                "03_学习模块",
                "/study.*",
                "学习模块文档",
                "推荐视频，发布视频，xxx，xxx");
    }
    @Bean
    public Docket examDocket() {
        return groupDocket(
                "04_xxx",
                "/xxx.*",
                "xxx模块文档",
                "xxx，xxx");
    }


    private Docket groupDocket(String group,             // 分组
                               String regex,             // 哪些会被生成
                               String title,             // 模块标题
                               String description) {     // 描述信息
        return basicDocket()
                .groupName(group)
                .apiInfo(apiInfo(title, description))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex(regex))
                .build();
    }

    private Docket basicDocket() {
        // 配置全局swagger的token
        RequestParameter token = new RequestParameterBuilder()
                .name(TokenFilter.HEADER_TOKEN)
                .description("用户登录令牌")
                .in(ParameterType.HEADER)
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .globalRequestParameters(List.of(token))
                .ignoredParameterTypes(
                        HttpSession.class,
                        HttpServletRequest.class,
                        HttpServletResponse.class)
                .enable(enable);
    }

    private ApiInfo apiInfo(String title, String description) {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version("1.0.0")
                .build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        enable = environment.acceptsProfiles(Profiles.of("dev", "test"));
    }

}
