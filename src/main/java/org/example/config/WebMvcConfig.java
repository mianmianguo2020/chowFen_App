package org.example.config;

/**
 * @author Christy Guo
 * @date 2023-03-27 10:31 PM
 */


import lombok.extern.slf4j.Slf4j;
import org.example.common.JacksonObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("静态资源开始映射...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/"); // 后台
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");     // 前台
    }


    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info ("Use a custom message converter");
        // Create a message converter object
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        // Set up the object converter, and the underlying layer uses Jackson to convert Java objects to json
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        // Append the above message converter object to the transformer collection of the MVC framework
        converters.add(0,messageConverter);
    }


}
