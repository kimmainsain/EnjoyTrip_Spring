package com.ssafy.charger.electric.controller;

import com.ssafy.charger.electric.dto.ElectricNearQuery;
import com.ssafy.charger.electric.dto.ElectricStation;
import com.ssafy.charger.electric.service.ElectricStationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/electric")
public class ElectricRestController {

    ElectricStationService electricStationService;

    @Autowired
    public ElectricRestController(ElectricStationService electricStationService){
        this.electricStationService = electricStationService;
    }

    private final String selectExample = "{\n" +
            "  \"attractionId\": [\n" +
            "    130057, 125408\n" +
            "  ],\n" +
            "  \"meters\": 5000\n" +
            "}";

    @ApiOperation(value = "근처 전기차 충전소 검색", notes = "검색하고자하는 관광지의 번호와 원하는 거리(미터)를 넣어 충전소 정보를 반환받습니다.\n" + selectExample)
    @PostMapping
    public ResponseEntity<List<ElectricStation>> selectNearStations(@RequestBody ElectricNearQuery query){
        List<ElectricStation> rst = electricStationService.selectNearStations(query);

        return new ResponseEntity<>(rst, HttpStatus.OK);
    }

}
