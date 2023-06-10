package com.ssafy.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AttractionAdjacentQuery {
    List<Integer> attractionIdList;
    int distInMeters;
}
