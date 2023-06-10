package com.ssafy.charger.electric;

import com.ssafy.charger.electric.dto.ElectricNearQuery;
import com.ssafy.charger.electric.dto.ElectricStation;
import com.ssafy.charger.electric.mapper.ElectricMapper;
import com.ssafy.charger.electric.service.ElectricDataGoService;
import com.ssafy.charger.electric.service.ElectricStationService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class fetchTest {

    @Autowired
    ElectricDataGoService electricDataGoService;

    @Autowired
    ElectricMapper electricMapper;

    @Autowired
    ElectricStationService electricStationService;

    @Test
    public void getTest() throws Exception{
        electricDataGoService.fetch("50"); // 46 47 48 50
    }

//    @Test
//    public void selectTest(){
//        ElectricStation station = new ElectricStation(electricMapper.selectChargers("ME178009"));
//        log.debug("station : {}", station);
//    }

    @Test
    public void URITEST(){
        String encoded = "dRn0wpwE0u%2BrLRUP27Ygd8kJQzVl3HMwKbRV0H%2Fid2eDRO1MT0u0S3kvlYg5f3%2B4%2BDgBwq6BtqTPe86e9dGDlg%3D%3D";
        String decoded = "dRn0wpwE0u+rLRUP27Ygd8kJQzVl3HMwKbRV0H/id2eDRO1MT0u0S3kvlYg5f3+4+DgBwq6BtqTPe86e9dGDlg==";

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("http://localhost/");
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        String value = factory.builder().queryParam("key", decoded).build().toString();

        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        String encode = factory.builder().queryParam("key", encoded).build().toString();

        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);
        String uri_comp = factory.builder().queryParam("key", decoded).build().toString();

        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);
        String tmp_val = factory.builder().queryParam("key", decoded).build().toString();

        log.debug("None : {}", encode);
        log.debug("value only : {}", value);
        log.debug("uri_comp : {}", uri_comp);
        log.debug("tmp_val : {}", tmp_val);
    }

    @Test
    public void getNearStNoTest(){
        ElectricNearQuery query = ElectricNearQuery.builder().meters(5000).attractionId(
                Arrays.asList(new Integer[]{130057, 125408})
        ).build();

        List<Integer> rst = electricMapper.selectNearStationNo(query);
        assertEquals(17, rst.size());

        List<ElectricStation> list = electricStationService.selectNearStations(ElectricNearQuery.builder().meters(5000).attractionId(Arrays.asList(new Integer[]{130057, 125408})).build());
        assertEquals(17, list.size());
        log.debug("list : {}", list);
    }
}
