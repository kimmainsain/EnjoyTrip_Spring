package com.ssafy.charger.electric.service;

import com.ssafy.charger.electric.dto.ElectricChargingEntity;
import com.ssafy.charger.electric.dto.ElectricStation;
import com.ssafy.charger.electric.mapper.ElectricMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;

@Service
@Slf4j
public class ElectricDataGoService {
    //https://apis.data.go.kr/B552584/EvCharger/getChargerInfo?serviceKey=&pageNo=1&numOfRows=10&zcode=11&dataType=JSON
    private final String baseURL = "https://apis.data.go.kr/B552584/EvCharger/getChargerInfo";
    private final String serviceKey = "dRn0wpwE0u%2BrLRUP27Ygd8kJQzVl3HMwKbRV0H%2Fid2eDRO1MT0u0S3kvlYg5f3%2B4%2BDgBwq6BtqTPe86e9dGDlg%3D%3D";
    private final DefaultUriBuilderFactory factory;

    @Autowired
    private ElectricMapper mapper;

    public ElectricDataGoService(){
        factory = new DefaultUriBuilderFactory(baseURL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
    }

    public void fetch(String code) throws Exception{
        int numOfRows = 200;
        int pageNo = 1;
        long total = Long.MAX_VALUE;

        do {
            URI uri = factory.builder().queryParam("serviceKey", serviceKey)
                    .queryParam("numOfRows", numOfRows).queryParam("pageNo", pageNo)
                    .queryParam("zcode", code).queryParam("dataType", "JSON").build();

            String body = WebClient.create().get().uri(uri).retrieve().bodyToMono(String.class).block();

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(body);
            JSONArray items = (JSONArray) ((JSONObject)object.get("items")).get("item");
            ArrayList<ElectricChargingEntity> list = new ArrayList<>();
            for(int i = 0; i < items.size(); ++i){
                JSONObject item = (JSONObject) items.get(i);
                ElectricChargingEntity entity = ElectricChargingEntity.builder()
                        .statId((String) item.get("statId")).statNm((String) item.get("statNm")).addr((String) item.get("addr"))
                        .location((String) item.get("location")).lat((String) item.get("lat")).lng((String) item.get("lng"))
                        .chgerId((String) item.get("chgerId")).chgerType((String) item.get("chgerType")).build();

                list.add(entity);
            }

            mapper.insertStation(list);

            total = (long) object.get("totalCount");

            log.debug("done : {}", pageNo);
        } while(Math.ceil(total / numOfRows) + 1 >= pageNo++);
    }
}
