package com.ssafy.route.service;

import com.github.pagehelper.Page;
import com.ssafy.paging.PagingResult;
import com.ssafy.route.dto.RouteBasic;
import com.ssafy.route.dto.RouteDetail;
import com.ssafy.route.dto.RouteReview;

public interface RouteService {
    PagingResult<RouteBasic> listBasicRoute();
    RouteDetail selectRoute(int routeId);
    boolean insertRouteDetail(RouteDetail detail);
    boolean updateRouteDetail(RouteDetail detail, boolean isAdmin);
    boolean deleteRoute(RouteDetail detail, boolean isAdmin);

    boolean addReview(RouteReview review);
    boolean updateReview(RouteReview review, boolean isAdmin);
    boolean deleteReview(RouteReview review, boolean isAdmin);
    Page<RouteReview> listReviewByRouteId(int routeId);
    Page<RouteReview> listReviewByUserNo(int userNo);

    boolean checkExistReviewByRouteIdUserNo(int routeId, int userNo);


    boolean addBookmark(int userNo, int routeId);
    boolean removeBookmark(RouteBasic route);
    PagingResult<RouteBasic> listBookmark(int userNo);
    boolean checkBookmark(String id, int routeId);
}
