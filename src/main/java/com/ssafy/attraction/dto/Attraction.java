package com.ssafy.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attraction {
    private int contentId;
    private String title;
    private int contentTypeId;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String tel;
    private String firstImage;
    private int sidoCode;
    private int gugunCode;
    private double latitude;
    private double longitude;
    private String homepage;
    private String overview;
    private String telname;
}
