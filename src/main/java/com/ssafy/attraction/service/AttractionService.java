package com.ssafy.attraction.service;

import com.github.pagehelper.Page;
import com.ssafy.attraction.dto.Attraction;
import com.ssafy.attraction.dto.AttractionAdjacentQuery;
import com.ssafy.attraction.dto.AttractionKeywordSearchDto;

import java.util.List;

public interface AttractionService {
    Attraction select(int contentId);
    Page<Attraction> list(AttractionKeywordSearchDto option); // Browser에서 해당 dto에 맞게 그냥 requestbody에 담으면, 바로 wrapping 될 듯...

    List<Attraction> listAdjacentAttractions(AttractionAdjacentQuery query);
}
