package com.ssafy.route.controller;

import com.github.pagehelper.Page;
import com.ssafy.paging.PagingResult;
import com.ssafy.route.dto.RouteBasic;
import com.ssafy.route.dto.RouteDetail;
import com.ssafy.route.dto.RouteReview;
import com.ssafy.route.service.RouteService;
import com.ssafy.user.dto.User;
import com.ssafy.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/route")
@CrossOrigin("*")
@Api(tags = "여행경로")
public class RouteRestController {
    RouteService routeService;
    UserService userService;

    @Autowired
    public RouteRestController(RouteService routeService, UserService userService){
        this.routeService = routeService;
        this.userService = userService;
    }

    @GetMapping("/paging")
    public ResponseEntity<PagingResult<RouteBasic>> listBasicRoutes(){
        return new ResponseEntity<>(routeService.listBasicRoute(), HttpStatus.OK);
    }

    @ApiOperation(value = "경로 선택", notes = "경로 번호를 넘겨서 경로에 대한 정보를 얻어옵니다.")
    @GetMapping("/{routeId}")
    public ResponseEntity<RouteDetail> selectRoute(@ApiParam(value = "경로 번호") @PathVariable("routeId") int routeId){
        RouteDetail detail = routeService.selectRoute(routeId);

        if(detail == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    private static final String routeInsertExample = "{\n" +
            "  \"data\": {\n" +
            "    \"1\": {\n" +
            "      \"attractionId\": 125431,\n" +
            "      \"routeSequence\": 1\n" +
            "    },\n" +
            "    \"2\": {\n" +
            "      \"attractionId\": 125434,\n" +
            "      \"routeSequence\": 2\n" +
            "    }\n" +
            "  },\n" +
            "  \"memo\": \"짱 쉬운 REST 경로~~\",\n" +
            "  \"title\": \"Rest\"\n" +
            "}";

    @ApiOperation(value = "경로 추가", notes = "jwt 토큰과 경로 정보(제목, 메모, 데이터(순서 : {관광지번호, 순서}})를 받아 입력하고, 입력된 경로의 아이디를 반환합니다.\n" + routeInsertExample)
    @PostMapping
    public ResponseEntity<Integer> insertRoute(@RequestBody RouteDetail detail, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        User user = userService.select(jwtId);
        detail.setUserNo(user.getNo());

        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        boolean rst = routeService.insertRouteDetail(detail);

        if(rst)
            return new ResponseEntity<>(detail.getId(), HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    private static final String updateRouteExample = "{\n" +
            "  \"id\": 18,\n" +
            "  \"title\": \"Rest\",\n" +
            "  \"memo\": \"짱 쉬운 REST 수정~~\",\n" +
            "  \"data\": {\n" +
            "    \"1\": {\n" +
            "      \"detailId\": 101,\n" +
            "      \"routeSequence\": 1,\n" +
            "      \"attractionId\": 125850\n" +
            "    },\n" +
            "    \"2\": {\n" +
            "      \"detailId\": 102,\n" +
            "      \"routeSequence\": 2,\n" +
            "      \"attractionId\": 125854\n" +
            "    },\n" +
            "    \"3\": {\n" +
            "      \"detailId\": 103,\n" +
            "      \"routeSequence\": 3,\n" +
            "      \"attractionId\": 125476\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @ApiOperation(value = "경로 수정", notes = "jwt 토큰과 경로 정보(경로 아이디, 제목, 메모, 경로 데이터)를 받아 수정합니다.\n" + updateRouteExample)
    @PutMapping
    public ResponseEntity<Boolean> updateRoute(@RequestBody RouteDetail detail, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        User user = userService.select(jwtId);

        if(user == null)
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

        boolean rst = routeService.updateRouteDetail(detail, user.isAdmin());

        if(rst)
            return new ResponseEntity<>(true, HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "경로 삭제", notes = "jwt 토큰과 지우고자하는 경로의 번호를 받아 삭제합니다. {  \"id\": 13}")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteRoute(@RequestParam int routeId, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        User user = userService.select(jwtId);

        if(user == null)
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

        boolean rst = routeService.deleteRoute(RouteDetail.builder().id(routeId).userNo(user.getNo()).build(), user.isAdmin());

        if(rst)
            return new ResponseEntity<>(true, HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    private static final String addReviewExample = "{\n" +
            "  \"review\": \"재미있어요~~\",\n" +
            "  \"routeId\": 30,\n" +
            "  \"star\": 10\n" +
            "}";

    @ApiOperation(value = "리뷰 등록", notes = "jwt 토큰과 리뷰하고자 하는 경로의 아이디, 리뷰 내용, 별점을 넘긴다.\n"+addReviewExample)
    @PostMapping("/review")
    public ResponseEntity<Boolean> addReview(@RequestBody RouteReview review, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        User user = userService.select(jwtId);
        review.setUserNo(user.getNo());

        if(user == null)
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

        boolean rst = routeService.addReview(review);

        if(rst)
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/review")
    public ResponseEntity<Boolean> updateReview(@RequestBody RouteReview review, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        User user = userService.select(jwtId);
        review.setUserNo(user.getNo());

        if(user == null)
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

        boolean rst = routeService.updateReview(review, user.isAdmin());

        if(rst)
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/review")
    public ResponseEntity<Boolean> deleteReview(@RequestParam int reviewId, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        User user = userService.select(jwtId);

        if(user == null)
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

        boolean rst = routeService.deleteReview(RouteReview.builder().reviewId(reviewId).userNo(user.getNo()).build(), user.isAdmin());

        if(rst)
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "즐겨찾기 추가", notes = "jwt 토큰과 경로의 번호를 넘겨서 즐겨찾기에 추가합니다.\n{  \"id\": 31 }")
    @PostMapping("/bookmark")
    public ResponseEntity<Boolean> makeBookmark(@RequestBody RouteBasic route, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        User user = userService.select(jwtId);

        if(user == null)
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

        boolean rst = routeService.addBookmark(user.getNo(), route.getId());

        if(rst)
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "즐겨찾기 제거", notes = "jwt 토큰과 경로의 번호를 넘겨서 즐겨찾기에서 제거합니다.\n{  \"id\": 31 }")
    @DeleteMapping("/bookmark")
    public ResponseEntity<Boolean> removeBookmark(@RequestParam int routeId, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        User user = userService.select(jwtId);

        if(user == null)
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

        boolean rst = routeService.removeBookmark(RouteBasic.builder().id(routeId).userNo(user.getNo()).build());

        if(rst)
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "즐겨찾기 리스트", notes = "즐겨찾기 목록을 페이징 처리하여 가져옵니다.")
    @GetMapping("/bookmark/paging")
    public ResponseEntity<PagingResult<RouteBasic>> listBookmark(@RequestParam int userNo, @ApiParam(value = "현재 페이지") @RequestParam(required = false) Integer pageNum, @ApiParam(value = "페이지당 수") @RequestParam(required = false) Integer numOfRows){
        return new ResponseEntity<>(routeService.listBookmark(userNo), HttpStatus.OK);
    }

    /*
        Page<RouteReview> listReview(RouteReview review);
    Page<RouteReview> listReview(int userNo);
     */

    @GetMapping("/review/user/paging")
    public ResponseEntity<PagingResult<RouteReview>> listReviewByUserNo(@RequestParam int userNo, @ApiParam(value = "현재 페이지") @RequestParam(required = false) Integer pageNum, @ApiParam(value = "페이지당 수") @RequestParam(required = false) Integer numOfRows){
        Page<RouteReview> page = routeService.listReviewByUserNo(userNo);

        return new ResponseEntity<>(new PagingResult<>(page), HttpStatus.OK);
    }

    @GetMapping("/review/route/paging")
    public ResponseEntity<PagingResult<RouteReview>> listReviewByRouteId(@RequestParam int routeId, @ApiParam(value = "현재 페이지") @RequestParam(required = false) Integer pageNum, @ApiParam(value = "페이지당 수") @RequestParam(required = false) Integer numOfRows){
        Page<RouteReview> page = routeService.listReviewByRouteId(routeId);

        return new ResponseEntity<>(new PagingResult<>(page), HttpStatus.OK);
    }

    @GetMapping("/review/check")
    public ResponseEntity<Boolean> checkExist(@RequestParam int routeId, @RequestParam int userNo){
        return new ResponseEntity<>(routeService.checkExistReviewByRouteIdUserNo(routeId, userNo), HttpStatus.OK);
    }

    @GetMapping("/bookmark")
    public ResponseEntity<Boolean> checkBookmarkExist(@RequestParam int routeId, @ApiParam(hidden = true) @RequestAttribute String jwtId){
        return new ResponseEntity<>(routeService.checkBookmark(jwtId, routeId), HttpStatus.OK);
    }
}
