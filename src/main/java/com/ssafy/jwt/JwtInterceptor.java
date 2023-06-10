package com.ssafy.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ssafy.jwt.JwtUtil.TOKEN_RESULT.*;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    JwtUtil jwtUtil;

    @Autowired
    public JwtInterceptor(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	log.debug("path : {} {}", request.getServletPath(), request.getMethod());
    	if(request.getMethod().equals(HttpMethod.OPTIONS.name()))
    		return true;

        // 회원가입 예외....
        if(request.getServletPath().equals("/api/user") && request.getMethod().equals(HttpMethod.POST.name()))
            return true;

        if(request.getServletPath().equals("/api/search"))
            return true;

        if(request.getServletPath().contains("reply") && request.getMethod().equals(HttpMethod.GET.name()))
            return true;

        String jwt = request.getHeader("jwt");

        log.debug("method : ", request.getMethod());
        log.debug("jwt interceptor : jwt : {}", jwt);

        JwtUtil.JwtResult validation = jwtUtil.validate(jwt);

        switch (validation.getResult()){
            case EXPIRED:
                response.addHeader("code", EXPIRED.toString());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;

            case NOT_TOKEN:
                response.addHeader("code", NOT_TOKEN.toString());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;

            case UNAUTHORIZED:
                response.addHeader("code", UNAUTHORIZED.toString());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
        }


        request.setAttribute("jwtId", validation.getId());

        if(validation.getAdmin() != null && validation.getAdmin() == true)
            request.setAttribute("admin", true);
        else request.setAttribute("admin", false);


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
