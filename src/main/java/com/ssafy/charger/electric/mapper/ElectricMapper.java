package com.ssafy.charger.electric.mapper;

import com.ssafy.charger.electric.dto.ElectricChargingEntity;
import com.ssafy.charger.electric.dto.ElectricNearQuery;
import com.ssafy.charger.electric.dto.ElectricStation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ElectricMapper {
    List<ElectricChargingEntity> selectChargers(Integer stNo);

    void insertStation(List<ElectricChargingEntity> entity);

    List<Integer> selectNearStationNo(ElectricNearQuery query);
}
