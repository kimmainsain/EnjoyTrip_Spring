package com.ssafy.route.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RouteSubObject {
    private int detailId;
    private int routeSequence;
    private int attractionId;
}
