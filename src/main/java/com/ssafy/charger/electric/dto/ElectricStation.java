package com.ssafy.charger.electric.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class ElectricStation {
    private String statId;
    private String statNm;
    private String addr;
    private String location;
    private String lat;
    private String lng;

    private List<ElectricCharger> chargers;

    public ElectricStation(List<ElectricChargingEntity> list){
        ElectricChargingEntity elem = list.get(0);
        this.statId = elem.getStatId();
        this.statNm = elem.getStatNm();
        this.lat = elem.getLat();
        this.lng = elem.getLng();
        this.addr = elem.getAddr();
        this.location = elem.getLocation();
        this.chargers = new ArrayList<>();

        for(int i = 0; i < list.size(); ++i){
            ElectricChargingEntity item = list.get(i);
            chargers.add(new ElectricCharger(item.getChgerId(), item.getChgerType()));
        }
    }
}
