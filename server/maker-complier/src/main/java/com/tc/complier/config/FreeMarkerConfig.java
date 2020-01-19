package com.tc.complier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FreeMarkerConfig {

    @Bean
    public freemarker.template.Configuration freeMarkerConfiguration() throws IOException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_28);
        // 设置放置模板的页面路径,此处我在resources目录下新建了文件夹templates,所有模板文件都放在此目录下
        ClassPathResource classPathResource = new ClassPathResource("freemarker");
        configuration.setDirectoryForTemplateLoading(classPathResource.getFile());
        configuration.setDefaultEncoding("utf-8");
        configuration.setNumberFormat("#"); //避免出现“，”或者科学计数等
        return configuration;
    }
}