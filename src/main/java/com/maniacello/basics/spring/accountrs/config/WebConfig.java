package com.maniacello.basics.spring.accountrs.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.maniacello.basics.spring.accountrs.api",
    "com.maniacello.basics.spring.accountrs.service",
    "com.maniacello.basics.spring.accountrs"})
@EnableSpringDataWebSupport
public class WebConfig implements WebMvcConfigurer {

}
