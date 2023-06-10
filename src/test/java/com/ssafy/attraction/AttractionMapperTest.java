package com.ssafy.attraction;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssafy.attraction.dto.Attraction;
import com.ssafy.attraction.dto.AttractionAdjacentQuery;
import com.ssafy.attraction.dto.AttractionKeywordSearchDto;
import com.ssafy.attraction.mapper.AttractionMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class AttractionMapperTest {
    @Autowired
    AttractionMapper attractionMapper;

    @Test
    public void selectTest() {
        Attraction attraction = attractionMapper.select(125406);

        assertNotNull(attraction);
        assertEquals("비슬산자연휴양림", attraction.getTitle());
        assertEquals("http://tong.visitkorea.or.kr/cms/resource/62/219162_image2_1.jpg", attraction.getFirstImage());
    }

    @Test
    public void listTest() {
        AttractionKeywordSearchDto option = new AttractionKeywordSearchDto();
        PageHelper.startPage(1, 100000);
        Page<Attraction> list = attractionMapper.list(option);
        assertEquals(35844, list.size());

        option.setKeyword("해운대");
        list = attractionMapper.list(option);

        assertEquals(36, list.size());

        PageHelper.startPage(1, 10);
        option = new AttractionKeywordSearchDto();
        option.setKeyword("해운대");
        list = attractionMapper.list(option);
        assertEquals(10, list.size());

        PageInfo<Attraction> info = list.toPageInfo();
        log.debug("page info : {}", info);
        log.debug("page info -> list : {}", info.getList());

        PageHelper.startPage(1, 10000);
        option = AttractionKeywordSearchDto.builder().keyword("수목원").build();
        list = attractionMapper.list(option);
        assertEquals(80, list.size());
    }

    @Test
    public void distTest(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(125266);

        AttractionAdjacentQuery query = AttractionAdjacentQuery.builder().attractionIdList(list).distInMeters(10000).build();
        List<Attraction> rst = attractionMapper.listAdjacentAttractions(query);
        assertEquals(168, rst.size());

        list = new ArrayList<>();
        list.add(125408);
        list.add(125417);
        query = AttractionAdjacentQuery.builder().attractionIdList(list).distInMeters(5000).build();
        rst = attractionMapper.listAdjacentAttractions(query);
        assertEquals(34, rst.size());

        list.add(130057);
        query = AttractionAdjacentQuery.builder().attractionIdList(list).distInMeters(5000).build();
        rst = attractionMapper.listAdjacentAttractions(query);
        assertEquals(36, rst.size());
    }
}
