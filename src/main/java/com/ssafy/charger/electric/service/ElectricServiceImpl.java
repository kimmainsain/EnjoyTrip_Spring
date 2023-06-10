package com.ssafy.charger.electric.service;

import com.ssafy.charger.electric.dto.ElectricNearQuery;
import com.ssafy.charger.electric.dto.ElectricStation;
import com.ssafy.charger.electric.mapper.ElectricMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElectricServiceImpl implements ElectricStationService{
    @Autowired
    ElectricMapper electricMapper;


    @Override
    public List<ElectricStation> selectNearStations(ElectricNearQuery query) {
        List<Integer> stationNo = electricMapper.selectNearStationNo(query);

        List<ElectricStation> rst = new ArrayList<>(stationNo.size() + 1);

        for(int i = 0; i < stationNo.size(); ++i){
            rst.add(new ElectricStation(electricMapper.selectChargers(stationNo.get(i))));
        }

        return rst;
    }
}
