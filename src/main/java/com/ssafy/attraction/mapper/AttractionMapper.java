package com.ssafy.attraction.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ssafy.attraction.dto.Attraction;
import com.ssafy.attraction.dto.AttractionAdjacentQuery;
import com.ssafy.attraction.dto.AttractionKeywordSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttractionMapper {
    Attraction select(int contentId);
    Page<Attraction> list(AttractionKeywordSearchDto option);

    List<Attraction> listAdjacentAttractions(AttractionAdjacentQuery query);
}
