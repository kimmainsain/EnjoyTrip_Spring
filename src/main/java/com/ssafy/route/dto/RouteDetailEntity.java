package com.ssafy.route.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class RouteDetailEntity{
    private int id;
    private String title;
    private String memo;
    private int userNo;
    private String nickname;
    private Float star;
    private int detailId;
    private int routeSequence;
    private int attractionId;
}
