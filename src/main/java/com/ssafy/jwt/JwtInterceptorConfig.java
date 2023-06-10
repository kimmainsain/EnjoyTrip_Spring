package com.ssafy.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {

    JwtInterceptor jwtInterceptor;

    @Autowired
    public JwtInterceptorConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    final String[] userPaths = {
            "/api/user/drop", // 탈퇴
            "/api/user", // edit
            "/api/user/basicInfo"
    };

    final String[] boardPaths = {
            "/api/board/*", // regist, delete, edit
            "/api/board/*/*/*", // delete reply
            "/api/board/reply/*"
    };

    final String[] routePaths = {
            "/api/route", // insert, update, delete,
            "/api/route/review", // add, update, delete,
            "/api/route/bookmark", // make, remove,
    };

    // TODO 따로 권한 관리 해줘야함...
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns(userPaths)
                .addPathPatterns(boardPaths)
                .addPathPatterns(routePaths);
    }
}
