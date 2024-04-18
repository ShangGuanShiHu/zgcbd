package com.example.zgcbd.config;

import com.example.zgcbd.pojo.INTPack;
import com.example.zgcbd.pojo.OriPack;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Data
public class TopoConfig {
    private Map<String, Long[]> topoMap ;

    public TopoConfig(){
        topoMap = new HashMap<>();
        topoMap.put("10.0.1.2->10.0.1.3",new Long[]{1l});
        topoMap.put("10.0.1.3->10.0.1.2",new Long[]{1l});
        topoMap.put("10.0.2.2->10.0.2.3",new Long[]{2l});
        topoMap.put("10.0.2.3->10.0.2.2",new Long[]{2l});
        topoMap.put("10.0.3.2->10.0.3.3",new Long[]{3l});
        topoMap.put("10.0.3.3->10.0.3.2",new Long[]{3l});
        topoMap.put("10.0.4.2->10.0.4.3",new Long[]{4l});
        topoMap.put("10.0.4.3->10.0.4.2",new Long[]{4l});
        topoMap.put("10.0.1.2->10.0.2.2",new Long[]{1l,5l,2l});
        topoMap.put("10.0.1.2->10.0.2.3",new Long[]{1l,5l,2l});
        topoMap.put("10.0.1.2->10.0.3.2",new Long[]{1l,5l,7l,6l,3l});
        topoMap.put("10.0.1.2->10.0.3.3",new Long[]{1l,5l,7l,6l,3l});
        topoMap.put("10.0.1.2->10.0.4.2",new Long[]{1l,5l,7l,6l,4l});
        topoMap.put("10.0.1.2->10.0.4.3",new Long[]{1l,5l,7l,6l,4l});
        topoMap.put("10.0.1.3->10.0.2.2",new Long[]{1l,5l,2l});
        topoMap.put("10.0.1.3->10.0.2.3",new Long[]{1l,5l,2l});
        topoMap.put("10.0.1.3->10.0.3.2",new Long[]{1l,5l,7l,6l,3l});
        topoMap.put("10.0.1.3->10.0.3.3",new Long[]{1l,5l,7l,6l,3l});
        topoMap.put("10.0.1.3->10.0.4.2",new Long[]{1l,5l,7l,6l,4l});
        topoMap.put("10.0.1.3->10.0.4.3",new Long[]{1l,5l,7l,6l,4l});
        topoMap.put("10.0.2.2->10.0.1.2",new Long[]{2l,5l,1l});
        topoMap.put("10.0.2.2->10.0.1.3",new Long[]{2l,5l,1l});
        topoMap.put("10.0.2.2->10.0.3.2",new Long[]{2l,5l,7l,6l,3l});
        topoMap.put("10.0.2.2->10.0.3.3",new Long[]{2l,5l,7l,6l,3l});
        topoMap.put("10.0.2.2->10.0.4.2",new Long[]{2l,5l,7l,6l,4l});
        topoMap.put("10.0.2.2->10.0.4.3",new Long[]{2l,5l,7l,6l,4l});
        topoMap.put("10.0.2.3->10.0.1.2",new Long[]{2l,5l,1l});
        topoMap.put("10.0.2.3->10.0.1.3",new Long[]{2l,5l,1l});
        topoMap.put("10.0.2.3->10.0.3.2",new Long[]{2l,5l,7l,6l,3l});
        topoMap.put("10.0.2.3->10.0.3.3",new Long[]{2l,5l,7l,6l,3l});
        topoMap.put("10.0.2.3->10.0.4.2",new Long[]{2l,5l,7l,6l,4l});
        topoMap.put("10.0.2.3->10.0.4.3",new Long[]{2l,5l,7l,6l,4l});
        topoMap.put("10.0.3.2->10.0.1.2",new Long[]{3l,6l,7l,5l,1l});
        topoMap.put("10.0.3.2->10.0.1.3",new Long[]{3l,6l,7l,5l,1l});
        topoMap.put("10.0.3.2->10.0.2.2",new Long[]{3l,6l,7l,5l,2l});
        topoMap.put("10.0.3.2->10.0.2.3",new Long[]{3l,6l,7l,5l,2l});
        topoMap.put("10.0.3.2->10.0.4.2",new Long[]{3l,6l,4l});
        topoMap.put("10.0.3.2->10.0.4.3",new Long[]{3l,6l,4l});
        topoMap.put("10.0.3.3->10.0.1.2",new Long[]{3l,6l,7l,5l,1l});
        topoMap.put("10.0.3.3->10.0.1.3",new Long[]{3l,6l,7l,5l,1l});
        topoMap.put("10.0.3.3->10.0.2.2",new Long[]{3l,6l,7l,5l,2l});
        topoMap.put("10.0.3.3->10.0.2.3",new Long[]{3l,6l,7l,5l,2l});
        topoMap.put("10.0.3.3->10.0.4.2",new Long[]{3l,6l,4l});
        topoMap.put("10.0.3.3->10.0.4.3",new Long[]{3l,6l,4l});
        topoMap.put("10.0.4.2->10.0.1.2",new Long[]{4l,6l,7l,5l,1l});
        topoMap.put("10.0.4.2->10.0.1.3",new Long[]{4l,6l,7l,5l,1l});
        topoMap.put("10.0.4.2->10.0.2.2",new Long[]{4l,6l,7l,5l,2l});
        topoMap.put("10.0.4.2->10.0.2.3",new Long[]{4l,6l,7l,5l,2l});
        topoMap.put("10.0.4.2->10.0.3.2",new Long[]{4l,6l,3l});
        topoMap.put("10.0.4.2->10.0.3.3",new Long[]{4l,6l,3l});
        topoMap.put("10.0.4.3->10.0.1.2",new Long[]{4l,6l,7l,5l,1l});
        topoMap.put("10.0.4.3->10.0.1.3",new Long[]{4l,6l,7l,5l,1l});
        topoMap.put("10.0.4.3->10.0.2.2",new Long[]{4l,6l,7l,5l,2l});
        topoMap.put("10.0.4.3->10.0.2.3",new Long[]{4l,6l,7l,5l,2l});
        topoMap.put("10.0.4.3->10.0.3.2",new Long[]{4l,6l,3l});
        topoMap.put("10.0.4.3->10.0.3.3",new Long[]{4l,6l,3l});
    }

    public List<Long> getRoute(OriPack pack){
        String srcDst = String.format("%s->%s", pack.getDataSrc(), pack.getDataDst());
        return Arrays.asList(topoMap.get(srcDst));
    }

    public List<Long> getRoute(INTPack pack){
        String srcDst = String.format("%s->%s", pack.getDataSrc(), pack.getDataDst());
        return Arrays.asList(topoMap.get(srcDst));
    }

    public List<List<Long>> getTopo(){
        return topoMap.values().stream().map(Arrays::asList).toList();
    }
}
