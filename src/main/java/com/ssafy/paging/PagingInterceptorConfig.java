package com.ssafy.paging;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PagingInterceptorConfig implements WebMvcConfigurer {
    PagingInterceptor pagingInterceptor;

    public PagingInterceptorConfig(PagingInterceptor pagingInterceptor){
        this.pagingInterceptor = pagingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pagingInterceptor).addPathPatterns("/**/paging");
    }
}
