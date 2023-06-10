package com.ssafy.route.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class RouteQueryWrapper extends RouteDetail{
    private boolean admin;

    public RouteQueryWrapper(RouteDetail detail){
        super(detail.toBuilder());
    }

    public RouteQueryWrapper(RouteDetail detail, boolean admin){
        super(detail.toBuilder());

        this.admin = admin;
    }
}
