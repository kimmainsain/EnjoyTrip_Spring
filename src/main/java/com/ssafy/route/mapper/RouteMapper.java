package com.ssafy.route.mapper;

import com.github.pagehelper.Page;
import com.ssafy.route.dto.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RouteMapper {
    Page<Integer> getBasicContentId();
    List<RouteBasic> listBasicRoute(List<Integer> routeId);
    List<RouteDetailEntity> selectRoute(int routeId);

    int insertRouteDetail(RouteDetail detail);
    boolean updateRouteDetail(RouteQueryWrapper query);
    boolean deleteRoute(RouteQueryWrapper query);

    int addReview(RouteReview review);
    int updateReview(RouteReviewQueryWrapper query);
    int deleteReview(RouteReviewQueryWrapper query);
    Page<RouteReview> listReviewByRouteId(int routeId);
    Page<RouteReview> listReviewByUserNo(int userNo);

    int checkReviewByRouteIDUserNo(RouteReviewQueryWrapper query);


    // 원래는 따로 dto 만들어야하는데, 그냥 재활용하자..
    int addBookmark(RouteBasic route);
    int removeBookmark(RouteBasic route);
    Page<Integer> getBookmarkId(int userNo);

    boolean checkBookmark(RouteReviewCheckWrapper query);
}
