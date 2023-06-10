package com.ssafy.charger.electric.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ElectricChargingEntity extends ElectricStation {
    private String chgerId;
    private String chgerType;
}
