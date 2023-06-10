package com.ssafy.route;

import com.ssafy.route.dto.*;
import com.ssafy.route.service.RouteService;
import com.ssafy.route.service.RouteServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class RouteServiceTest {
    @Autowired
    RouteService routeService;
    @Autowired
    SqlSession sqlSession;

//    @Test
//    public void basicSelectTest(){
//        List<RouteBasic> list = routeService.listBasicRoute().getResult();
//
//        assertEquals(1, list.size());
//    }

    @Test
    public void selectTest(){
        RouteDetail detail = routeService.selectRoute(9);
        assertEquals(4, detail.getData().size());
        assertEquals("test", detail.getTitle());
        assertEquals("관리자", detail.getNickname());
        assertEquals(125266, detail.getData().get(3).getAttractionId());
    }

    @Test
    @Transactional
    public void insertAndUpdateRouteDetailTest(){
        RouteDetail detail = RouteDetail.builder().userNo(2).title("ssafy 여행~~").memo("재미있는 여행~~").build();
        RouteSubObject subObject1 = RouteSubObject.builder().routeSequence(1).attractionId(125452).build();
        RouteSubObject subObject2 = RouteSubObject.builder().routeSequence(2).attractionId(125462).build();
        RouteSubObject subObject3 = RouteSubObject.builder().routeSequence(3).attractionId(125471).build();
        RouteSubObject subObject4 = RouteSubObject.builder().routeSequence(4).attractionId(125492).build();

        HashMap<Integer, RouteSubObject> data = new HashMap<>();
        data.put(1, subObject1);
        data.put(2, subObject2);
        data.put(3, subObject3);
        data.put(4, subObject4);
        detail.setData(data);

        boolean rst = routeService.insertRouteDetail(detail);

        assertEquals(true, rst);
        sqlSession.clearCache();

        RouteDetail detail1 = routeService.selectRoute(detail.getId());
        assertEquals(4, detail1.getData().size());
        assertEquals(125492, detail1.getData().get(4).getAttractionId());
        assertEquals(2, detail1.getUserNo());

        sqlSession.clearCache();

        data.remove(4);

        detail.setUserNo(3);
        rst = routeService.updateRouteDetail(detail, false);
        assertEquals(false, rst);
        sqlSession.clearCache();
        rst = routeService.updateRouteDetail(detail, true);
        assertEquals(true, rst);
        sqlSession.clearCache();

        detail1 = routeService.selectRoute(detail.getId());
        assertEquals(3, detail1.getData().size());
    }

    @Test
    @Transactional
    public void insertAndUpdateRouteDetailTest2(){
        RouteDetail detail = RouteDetail.builder().userNo(2).title("ssafy 여행~~").memo("재미있는 여행~~").build();
        RouteSubObject subObject1 = RouteSubObject.builder().routeSequence(1).attractionId(125452).build();
        RouteSubObject subObject2 = RouteSubObject.builder().routeSequence(2).attractionId(125462).build();
        RouteSubObject subObject3 = RouteSubObject.builder().routeSequence(3).attractionId(125471).build();
        RouteSubObject subObject4 = RouteSubObject.builder().routeSequence(4).attractionId(125492).build();

        HashMap<Integer, RouteSubObject> data = new HashMap<>();
        data.put(1, subObject1);
        data.put(2, subObject2);
        data.put(3, subObject3);
        data.put(4, subObject4);
        detail.setData(data);

        boolean rst = routeService.insertRouteDetail(detail);

        assertEquals(true, rst);
        sqlSession.clearCache();

        data.remove(4);
        rst = routeService.updateRouteDetail(detail, false);
        assertEquals(true, rst);
        sqlSession.clearCache();

        RouteDetail detail1 = routeService.selectRoute(detail.getId());
        assertEquals(3, detail1.getData().size());
    }

    @Test
    @Transactional
    public void deletionRouteDetailTest(){
        RouteDetail detail = RouteDetail.builder().userNo(2).title("ssafy 여행~~").memo("재미있는 여행~~").build();
        RouteSubObject subObject1 = RouteSubObject.builder().routeSequence(1).attractionId(125452).build();
        RouteSubObject subObject2 = RouteSubObject.builder().routeSequence(2).attractionId(125462).build();
        RouteSubObject subObject3 = RouteSubObject.builder().routeSequence(3).attractionId(125471).build();
        RouteSubObject subObject4 = RouteSubObject.builder().routeSequence(4).attractionId(125492).build();

        HashMap<Integer, RouteSubObject> data = new HashMap<>();
        data.put(1, subObject1);
        data.put(2, subObject2);
        data.put(3, subObject3);
        data.put(4, subObject4);
        detail.setData(data);

        routeService.insertRouteDetail(detail);

        RouteDetail detail1 = RouteDetail.builder().userNo(3).id(detail.getId()).build();
        sqlSession.clearCache();
        boolean rst = routeService.deleteRoute(detail1, false);
        assertEquals(false, rst);
        sqlSession.clearCache();
        detail1.setUserNo(2);
        rst = routeService.deleteRoute(detail1, false);
        assertEquals(true, rst);
    }

    @Test
    @Transactional
    public void deletionRouteDetailTest2(){
        RouteDetail detail = RouteDetail.builder().userNo(2).title("ssafy 여행~~").memo("재미있는 여행~~").build();
        RouteSubObject subObject1 = RouteSubObject.builder().routeSequence(1).attractionId(125452).build();
        RouteSubObject subObject2 = RouteSubObject.builder().routeSequence(2).attractionId(125462).build();
        RouteSubObject subObject3 = RouteSubObject.builder().routeSequence(3).attractionId(125471).build();
        RouteSubObject subObject4 = RouteSubObject.builder().routeSequence(4).attractionId(125492).build();

        HashMap<Integer, RouteSubObject> data = new HashMap<>();
        data.put(1, subObject1);
        data.put(2, subObject2);
        data.put(3, subObject3);
        data.put(4, subObject4);
        detail.setData(data);

        routeService.insertRouteDetail(detail);

        RouteDetail detail1 = RouteDetail.builder().userNo(3).id(detail.getId()).build();
        sqlSession.clearCache();
        boolean rst = routeService.deleteRoute(detail1, true);
        assertEquals(true, rst);
    }

    @Test
    @Transactional
    public void deletionRouteDetailTest3(){
        RouteDetail detail = RouteDetail.builder().userNo(2).title("ssafy 여행~~").memo("재미있는 여행~~").build();
        RouteSubObject subObject1 = RouteSubObject.builder().routeSequence(1).attractionId(125452).build();
        RouteSubObject subObject2 = RouteSubObject.builder().routeSequence(2).attractionId(125462).build();
        RouteSubObject subObject3 = RouteSubObject.builder().routeSequence(3).attractionId(125471).build();
        RouteSubObject subObject4 = RouteSubObject.builder().routeSequence(4).attractionId(125492).build();

        HashMap<Integer, RouteSubObject> data = new HashMap<>();
        data.put(1, subObject1);
        data.put(2, subObject2);
        data.put(3, subObject3);
        data.put(4, subObject4);
        detail.setData(data);

        routeService.insertRouteDetail(detail);

        RouteDetail detail1 = RouteDetail.builder().userNo(2).id(detail.getId()).build();
        sqlSession.clearCache();
        boolean rst = routeService.deleteRoute(detail1, true);
        assertEquals(true, rst);
    }

    @Test
    @Transactional
    public void addReviewTest(){
        RouteReview review = RouteReview.builder().routeId(9).userNo(1).star(5).review("좋아요~~").build();
        RouteReview review2 = RouteReview.builder().routeId(9).userNo(2).star(10).review("짱짱").build();

        routeService.addReview(review);
        routeService.addReview(review2);

        RouteDetail route = routeService.selectRoute(9);
        log.debug("route : {}", route);

        assertEquals(7.5f, route.getStar());
    }

    @Test
    @Transactional
    public void updateReviewTest(){
        RouteReview review = RouteReview.builder().routeId(9).userNo(1).star(5).review("좋아요~~").build();
        RouteReview review2 = RouteReview.builder().routeId(9).userNo(2).star(10).review("짱짱").build();

        routeService.addReview(review);
        routeService.addReview(review2);

        RouteReview review3 = RouteReview.builder().reviewId(review.getReviewId()).userNo(3).star(4).review("not bad..").build();
        boolean rst = routeService.updateReview(review3, false);
        log.debug("rst : {}", rst);
        assertEquals(false, rst);

        review3.setUserNo(1);

        rst = routeService.updateReview(review3, false);
        assertEquals(true, rst);
    }

    @Test
    @Transactional
    public void updateReviewTest2(){
        RouteReview review = RouteReview.builder().routeId(9).userNo(1).star(5).review("좋아요~~").build();
        RouteReview review2 = RouteReview.builder().routeId(9).userNo(2).star(10).review("짱짱").build();

        routeService.addReview(review);
        routeService.addReview(review2);

        RouteReview review3 = RouteReview.builder().reviewId(review.getReviewId()).userNo(3).star(4).review("not bad..").build();
        boolean rst = routeService.updateReview(review3, true);
        log.debug("rst : {}", rst);
        assertEquals(true, rst);
    }

    @Test
    @Transactional
    public void deleteReviewTest(){
        RouteReview review = RouteReview.builder().routeId(9).userNo(1).star(5).review("좋아요~~").build();
        RouteReview review2 = RouteReview.builder().routeId(9).userNo(2).star(10).review("짱짱").build();

        routeService.addReview(review);
        routeService.addReview(review2);
        sqlSession.clearCache();

        review2.setUserNo(3);
        assertEquals(false, routeService.deleteReview(review2, false));
        sqlSession.clearCache();
        assertEquals(true, routeService.deleteReview(review2, true));
        sqlSession.clearCache();

        assertEquals(true, routeService.deleteReview(review, false));
    }

//    @Test
//    @Transactional
//    public void bookmarkTest(){
//        boolean rst = routeService.addBookmark(1, 9);
//        assertEquals(true, rst);
//        sqlSession.clearCache();
//        assertThrows(Exception.class, ()->{
//            routeService.addBookmark(1, 9);
//        });
//        sqlSession.clearCache();
//
//        rst = routeService.addBookmark(3, 9);
//        assertEquals(true, rst);
//
//        assertEquals(1, routeService.listBookmark(1).getResult().size());
//        assertEquals(0, routeService.listBookmark(2).getResult().size());
//
//        rst = routeService.removeBookmark(RouteBasic.builder().userNo(1).id(9).build());
//        assertEquals(true, rst);
//        rst = routeService.removeBookmark(RouteBasic.builder().userNo(2).id(9).build());
//        assertEquals(false, rst);
//    }
}
