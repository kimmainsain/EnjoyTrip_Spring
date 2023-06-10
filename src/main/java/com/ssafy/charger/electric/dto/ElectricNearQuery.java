package com.ssafy.charger.electric.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectricNearQuery {
    int meters;
    List<Integer> attractionId;
}
