package com.ssafy.route.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class RouteBasic {
    private int id;
    private String title;
    private String memo;
    private int userNo;
    private String nickname;
    private Float star;
    private String img;
}
