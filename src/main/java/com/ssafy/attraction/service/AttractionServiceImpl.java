package com.ssafy.attraction.service;

import com.github.pagehelper.Page;
import com.ssafy.attraction.dto.Attraction;
import com.ssafy.attraction.dto.AttractionAdjacentQuery;
import com.ssafy.attraction.dto.AttractionKeywordSearchDto;
import com.ssafy.attraction.mapper.AttractionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionServiceImpl implements AttractionService{

    AttractionMapper attractionMapper;

    @Autowired
    public AttractionServiceImpl(AttractionMapper attractionMapper){
        this.attractionMapper = attractionMapper;
    }

    @Override
    public Attraction select(int contentId) {
        return attractionMapper.select(contentId);
    }

    @Override
    public Page<Attraction> list(AttractionKeywordSearchDto option) {
        return attractionMapper.list(option);
    }

    @Override
    public List<Attraction> listAdjacentAttractions(AttractionAdjacentQuery query) {
        return attractionMapper.listAdjacentAttractions(query);
    }
}
