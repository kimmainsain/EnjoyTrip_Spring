package com.ssafy.route;

import com.github.pagehelper.Page;
import com.ssafy.route.dto.*;
import com.ssafy.route.mapper.RouteMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class RouteMapperTest {

    @Autowired
    RouteMapper routeMapper;

    @Autowired
    SqlSession sqlSession;

    @Test
    @Transactional
    public void insertTest(){
        RouteDetail detail =
                RouteDetail.builder().title("test").memo("테스트").userNo(1).build();
        RouteSubObject sub1 = RouteSubObject.builder().routeSequence(1).attractionId(125266).build();
        RouteSubObject sub2 = RouteSubObject.builder().routeSequence(2).attractionId(125405).build();
        RouteSubObject sub3 = RouteSubObject.builder().routeSequence(3).attractionId(125406).build();

        detail.setData(new HashMap<>());
        detail.getData().put(1, sub1);
        detail.getData().put(2, sub2);
        detail.getData().put(3, sub3);

        int rst = routeMapper.insertRouteDetail(detail);

        log.debug("rst count : {}", rst);
    }

//    @Test
//    public void basicSelectTest(){
//        Page<RouteBasic> page = routeMapper.listBasicRoute();
//        assertEquals(1, page.getResult().size());
//        log.debug("data : {}", page.getResult());
//    }

    @Test
    public void detailSelectTest(){
        List<RouteDetailEntity> rst = routeMapper.selectRoute(9);
        assertEquals(4, rst.size());
        log.debug("rst : {}", rst);

        RouteDetail detail = new RouteDetail(rst);
        log.debug("to detail : {}", rst);
        assertEquals(4, detail.getData().size());
    }

    @Test
    @Transactional
    public void updateDetailTest(){
        RouteDetail detail = RouteDetail.builder().id(9).userNo(1).build();
        RouteSubObject sub1 = RouteSubObject.builder().detailId(10).routeSequence(1).attractionId(125458).build();
        RouteSubObject sub2 = RouteSubObject.builder().detailId(11).routeSequence(3).attractionId(125459).build();
        RouteSubObject sub3 = RouteSubObject.builder().detailId(12).routeSequence(2).attractionId(125460).build();
        RouteSubObject sub4 = RouteSubObject.builder().detailId(13).routeSequence(4).attractionId(125461).build();
        RouteSubObject sub5 = RouteSubObject.builder().routeSequence(5).attractionId(125462).build();
        detail.setData(new HashMap<>());
        detail.getData().put(1, sub1);
        detail.getData().put(2, sub2);
        detail.getData().put(3, sub3);
        detail.getData().put(4, sub4);
        detail.getData().put(5, sub5);

        RouteQueryWrapper query = new RouteQueryWrapper(detail);

        boolean rst = routeMapper.updateRouteDetail(query);
        log.debug("rst cnt : {}", rst);

        sqlSession.clearCache();

        List<RouteDetailEntity> list  = routeMapper.selectRoute(9);
        log.debug("list : {}", list);
        assertEquals(5, list.size());
        assertEquals(125459, list.get(2).getAttractionId());

    }

    @Test
    @Transactional
    public void deleteTest(){
        boolean rst = routeMapper.deleteRoute(new RouteQueryWrapper(RouteDetail.builder().id(9).userNo(2).build(), false));

        assertEquals(false, rst);

        sqlSession.clearCache();
        rst = routeMapper.deleteRoute(new RouteQueryWrapper(RouteDetail.builder().id(9).userNo(1).build(), false));

        assertEquals(true, rst);
        log.debug("rst : {}", rst);
    }

    @Test
    public void addReviewTest(){
        RouteReview review = RouteReview.builder().routeId(9).userNo(1).star(5).review("좋아요~~").build();
        RouteReview review2 = RouteReview.builder().routeId(9).userNo(2).star(10).review("짱짱").build();

        routeMapper.addReview(review);
        routeMapper.addReview(review2);
        RouteDetail route = new RouteDetail(routeMapper.selectRoute(9));
        log.debug("route : {}", route);

        assertEquals(7.5f, route.getStar());
    }

    @Test
    @Transactional
    public void updateReviewTest(){
        RouteReview review = RouteReview.builder().routeId(9).userNo(1).star(5).review("좋아요~~").build();
        RouteReview review2 = RouteReview.builder().routeId(9).userNo(2).star(10).review("짱짱").build();

        routeMapper.addReview(review);
        routeMapper.addReview(review2);

        RouteReview review3 = RouteReview.builder().reviewId(review.getReviewId()).userNo(1).star(4).review("not bad..").build();
        int rst = routeMapper.updateReview(new RouteReviewQueryWrapper(review3));
        log.debug("rst : {}", rst);

        RouteDetail route = new RouteDetail(routeMapper.selectRoute(9));
        log.debug("route : {}", route);

        assertEquals(7, route.getStar());
    }

    @Test
    @Transactional
    public void deleteReviewTest(){
        RouteReview review = RouteReview.builder().routeId(9).userNo(1).star(5).review("좋아요~~").build();
        RouteReview review2 = RouteReview.builder().routeId(9).userNo(2).star(10).review("짱짱").build();

        routeMapper.addReview(review);
        routeMapper.addReview(review2);

        int rst = routeMapper.deleteReview(new RouteReviewQueryWrapper(review));
        log.debug("rst : {}", rst);

        RouteDetail route = new RouteDetail(routeMapper.selectRoute(9));
        log.debug("route : {}", route);

        assertEquals(10, route.getStar());
    }

//    @Test
//    @Transactional
//    public void bookmarkTest(){
//        RouteBasic bookmark = RouteBasic.builder().userNo(2).id(9).build();
//        int rst = routeMapper.addBookmark(bookmark);
//        log.debug("rst : {}", rst);
//
//        Page<RouteBasic> page = routeMapper.listBookmark(2);
//        assertEquals(1, page.getResult().size());
//        assertEquals(9, page.getResult().get(0).getId());
//
//        RouteDetail detail = new RouteDetail(routeMapper.selectRoute(page.getResult().get(0).getId()));
//        log.debug("detail : {}", detail);
//        sqlSession.clearCache();
//
//        rst = routeMapper.removeBookmark(bookmark);
//        log.debug("rst : {}", rst);
//
//        page = routeMapper.listBookmark(2);
//        assertEquals(0, page.getResult().size());
//    }
}
