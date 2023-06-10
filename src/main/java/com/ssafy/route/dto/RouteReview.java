package com.ssafy.route.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder (toBuilder = true)
public class RouteReview {
    private int reviewId;
    private int userNo;
    private int routeId;
    private int star;
    private String review;
}
