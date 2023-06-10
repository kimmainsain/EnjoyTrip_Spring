package com.ssafy.route.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class RouteDetail extends RouteBasic {
    private HashMap<Integer, RouteSubObject> data;

    public RouteDetail(RouteDetailBuilder<?, ?> b) {
        super(b);
        this.data = b.data;
    }

    public RouteDetail(List<RouteDetailEntity> list){
        if(list.size() > 0) {
            RouteDetailEntity entity = list.get(0);
            setId(entity.getId());
            setTitle(entity.getTitle());
            setMemo(entity.getMemo());
            setUserNo(entity.getUserNo());
            setNickname(entity.getNickname());
            setStar(entity.getStar());
        }
        data = new HashMap<>();
        for(RouteDetailEntity entity : list){
            RouteSubObject obj = new RouteSubObject();
            obj.setAttractionId(entity.getAttractionId());
            obj.setDetailId(entity.getDetailId());
            obj.setRouteSequence(entity.getRouteSequence());
            data.put(entity.getRouteSequence(), obj);
        }
    }

    /*
    static ArrayList<RouteDetailEntity> toEntity(RouteDetail detail){
        ArrayList<RouteDetailEntity> list = new ArrayList<>();

        if(detail.data.size() == 0)
            return null;

        for(Map.Entry entry :  detail.data.entrySet()){
            RouteDetailEntity entity = new RouteDetailEntity();
            entity.setId(detail.getId());
            entity.setTitle(detail.getTitle());
            entity.setMemo(detail.getMemo());
            entity.setUserNo(detail.getUserNo());
            entity.setNickname();
        }

        return list;
    }
     */
}
