package com.ssafy.route.service;

import com.github.pagehelper.Page;
import com.ssafy.paging.PagingResult;
import com.ssafy.route.dto.*;
import com.ssafy.route.mapper.RouteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RouteServiceImpl implements RouteService {

    RouteMapper routeMapper;

    @Autowired
    public RouteServiceImpl(RouteMapper routeMapper){
        this.routeMapper = routeMapper;
    }


    @Override
    public PagingResult<RouteBasic> listBasicRoute() {
        Page<Integer> routeId = routeMapper.getBasicContentId();
        log.debug("attractionIds : {}", routeId);
        List<RouteBasic> rst = routeMapper.listBasicRoute(routeId);
        log.debug("rst : {}", rst);

        return new PagingResult<>(rst, routeId.getPageNum(), routeId.getPageSize(), routeId.getPages());
    }

    @Override
    public RouteDetail selectRoute(int routeId) {
        return new RouteDetail(routeMapper.selectRoute(routeId));
    }

    @Override
    public boolean insertRouteDetail(RouteDetail detail) {
        routeMapper.insertRouteDetail(detail);

        if(detail.getId() == 0)
            return false;

        return true;
    }

    @Override
    public boolean updateRouteDetail(RouteDetail detail, boolean isAdmin) {
        return routeMapper.updateRouteDetail(new RouteQueryWrapper(detail, isAdmin));
    }

    @Override
    public boolean deleteRoute(RouteDetail detail, boolean isAdmin) {
        return routeMapper.deleteRoute(new RouteQueryWrapper(detail, isAdmin));
    }

    @Override
    public boolean addReview(RouteReview review) {
        int rst = routeMapper.addReview(review);

        if(rst == 0) // multi query 아니여서 insert 결과가 나옴..
            return false;

        return true;
    }

    @Override
    public boolean updateReview(RouteReview review, boolean isAdmin) {
        int rst = routeMapper.updateReview(new RouteReviewQueryWrapper(review, isAdmin));

        if(rst == 0) // multi query 아님.. update 결과 수
            return false;

        return true;
    }

    @Override
    public boolean deleteReview(RouteReview review, boolean isAdmin) {
        int rst = routeMapper.deleteReview(new RouteReviewQueryWrapper(review, isAdmin));

        if(rst == 0) // 멀티쿼리 아니여서 delete 결과 수..
            return false;

        return true;
    }

    @Override
    public boolean addBookmark(int userNo, int routeId) {
        int rst = routeMapper.addBookmark(RouteBasic.builder().userNo(userNo).id(routeId).build());

        if(rst == 0) // 멀티쿼리 아니여서 insert 결과 수..
            return false;

        return true;
    }

    @Override
    public boolean removeBookmark(RouteBasic route) {
        int rst = routeMapper.removeBookmark(route);

        if(rst == 0) // 멀티쿼리 아니여서 delete 결과 수..
            return false;

        return true;
    }

    @Override
    public PagingResult<RouteBasic> listBookmark(int userNo) {
        Page<Integer> routeId = routeMapper.getBookmarkId(userNo);
        if(routeId.getResult().size() == 0){
            return new PagingResult<>(null, routeId.getPageNum(), routeId.getPageSize(), routeId.getPages());
        }

        List<RouteBasic> rst = routeMapper.listBasicRoute(routeId.getResult());

        return new PagingResult<>(rst, routeId.getPageNum(), routeId.getPageSize(), routeId.getPages());
    }

    @Override
    public Page<RouteReview> listReviewByRouteId(int routeId) {
        return routeMapper.listReviewByRouteId(routeId);
    }

    @Override
    public Page<RouteReview> listReviewByUserNo(int userNo) {
        return routeMapper.listReviewByUserNo(userNo);
    }

    @Override
    public boolean checkExistReviewByRouteIdUserNo(int routeId, int userNo) {
        return routeMapper.checkReviewByRouteIDUserNo(RouteReviewQueryWrapper.builder().userNo(userNo).routeId(routeId).build()) == 1 ? true : false;
    }

    @Override
    public boolean checkBookmark(String id, int routeId) {
        return routeMapper.checkBookmark(RouteReviewCheckWrapper.builder().id(id).routeId(routeId).build());
    }
}
