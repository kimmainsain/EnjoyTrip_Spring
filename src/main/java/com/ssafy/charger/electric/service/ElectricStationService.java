package com.ssafy.charger.electric.service;

import com.ssafy.charger.electric.dto.ElectricNearQuery;
import com.ssafy.charger.electric.dto.ElectricStation;

import java.util.List;

public interface ElectricStationService {
    List<ElectricStation> selectNearStations(ElectricNearQuery query);
}
