package com.ssafy.route.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class RouteReviewQueryWrapper extends RouteReview{
    private boolean admin;

    public RouteReviewQueryWrapper(RouteReview review){
        super(review.toBuilder());
    }

    public RouteReviewQueryWrapper(RouteReview review, boolean admin){
        super(review.toBuilder());
        this.admin = admin;
    }
}
