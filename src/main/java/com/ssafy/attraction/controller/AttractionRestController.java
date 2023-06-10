package com.ssafy.attraction.controller;

import com.github.pagehelper.Page;
import com.ssafy.attraction.dto.Attraction;
import com.ssafy.attraction.dto.AttractionAdjacentQuery;
import com.ssafy.attraction.dto.AttractionKeywordSearchDto;
import com.ssafy.attraction.service.AttractionService;
import com.ssafy.paging.PagingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/attraction")
@CrossOrigin("*")
@Api(tags = {"관광지"})
public class AttractionRestController {

    AttractionService attractionService;

    @Autowired
    public AttractionRestController(AttractionService attractionService){
        this.attractionService = attractionService;
    }

    @GetMapping("/list/paging")
    public ResponseEntity<PagingResult<Attraction>> list(@ModelAttribute AttractionKeywordSearchDto attractionKeywordSearchDto) {
        log.debug("param : {}", attractionKeywordSearchDto);
        Page<Attraction> page = attractionService.list(attractionKeywordSearchDto);

        return new ResponseEntity<>(new PagingResult<>(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Attraction select(@PathVariable("id") int contentId){
        return attractionService.select(contentId);
    }

    @ApiOperation(value = "근처 관광지 조회", notes = "기준이 되는 관광지 아이디와 반경(미터)를 넣어서 기준점의 반경 내의 관광지를 반환받는다.")
    @PostMapping("/adjacent")
    public ResponseEntity<List<Attraction>> getAdjacent(@RequestBody AttractionAdjacentQuery query){
        return new ResponseEntity<>(attractionService.listAdjacentAttractions(query), HttpStatus.OK);
    }

}
