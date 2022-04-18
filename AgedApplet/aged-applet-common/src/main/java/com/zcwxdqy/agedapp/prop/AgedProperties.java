package com.zcwxdqy.agedapp.prop;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/*
    1、spring 启动后会自动装配yml文件里的配置
    2、别的地方要用到，调取这就行了
    eg：【 aged -> cfg -> corsOrigins 】

 */

@ConfigurationProperties("aged")
@Component
@Data
public class AgedProperties implements ApplicationContextAware {
    private Cfg cfg;
    private Upload upload;
    private static AgedProperties properties;

    public static AgedProperties get() {
        return properties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        properties = this;
    }


    // 跨域设置的路径配置
    @Data
    public static class Cfg {
        private String[] corsOrigins;
    }


    // 文件上传的配置
    @Data
    public static class Upload {
        private String basePath;
        private String uploadPath;
        private String imagePath;
        private String videoPath;

        public String getImageRelativePath() {
            return uploadPath + imagePath;
        }

        public String getVideoRelativePath() {
            return uploadPath + videoPath;
        }
    }
}
