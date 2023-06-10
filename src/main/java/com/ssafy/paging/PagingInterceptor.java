package com.ssafy.paging;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class PagingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String pageNum = request.getParameter("pageNum");
        if(pageNum == null){
            pageNum = "1";
        }

        String numOfRows = request.getParameter("numOfRows");
        if(numOfRows == null){
            numOfRows = "10";
        }

        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(numOfRows));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
