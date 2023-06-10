package com.ssafy.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AttractionSearchDto {
    String contentTypeId;
    String sidoCode;
    String gugunCode;
}
