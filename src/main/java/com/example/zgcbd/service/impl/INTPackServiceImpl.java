package com.example.zgcbd.service.impl;

import com.example.zgcbd.mapper.INTPackMapper;
import com.example.zgcbd.pojo.INTPack;
import com.example.zgcbd.pojo.OriPack;
import com.example.zgcbd.service.INTPackService;
import com.example.zgcbd.service.StationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class INTPackServiceImpl implements INTPackService {
    @Autowired
    private INTPackMapper intpackMapper;

    @Autowired
    private StationService stationService;

    @Data
    @AllArgsConstructor
    public class Context implements Comparable<Context>{
        private Long time;
        private Long dpid;

        @Override
        public int compareTo(Context o) {
            return time.compareTo(o.getTime());
        }
    }

    @Data
    public class OriPackDecorator{
        private OriPack oriPack;

        private List<Context> contexts;

        public OriPack updateOriPack(){
            Collections.sort(contexts);
            oriPack.setRoute(contexts.stream().map(Context::getDpid).toList());
            long transTime= contexts.get(contexts.size() - 1).getTime() - contexts.get(0).getTime();
            oriPack.setTransTime(transTime);
            return oriPack;
        }

        public void addContext(Long time, Long dpid){
            contexts.add(new Context(time, dpid));
        }

        public OriPackDecorator(OriPack oriPack){
            this.oriPack = oriPack;
            this.contexts = new ArrayList<>();
        }
    }


    public List<INTPack> selectPackagesByDpid(long dpid){
        return intpackMapper.selectByDpid(dpid);
    }

    public List<INTPack> selectAllPackages(){
        return intpackMapper.selectALL();
    }

    public List<List<Long>> selectStationTopo(){
        List<INTPack> intPacks = intpackMapper.selectALL();

        Map<String, List<INTPack>> map = new HashMap<>();

        // 获取每一条trace中数据包的路径
        for (INTPack pack: intPacks) {
            String traceId = pack.getTraceId();
            if(map.containsKey(traceId)){
                map.get(traceId).add(pack);
            }
            else {
                ArrayList<INTPack> packs = new ArrayList<>();
                packs.add(pack);
                map.put(traceId, packs);
            }
        }

        // 边
        Set<String> edges = new HashSet<>();

        int src;
        int des;
        // 根据所有的路径解析出网络拓扑
        for (List<INTPack> packs : map.values()) {
            List<String> dpids = packs.stream().sorted(Comparator.comparing(INTPack::getTimebias)).map(intPack -> String.valueOf(intPack.getDpid())).toList();
            src = 0;
            des = 1;
            while (des < dpids.size()){
                edges.add(String.join("_", dpids.get(src), dpids.get(des)));
                src ++;
                des ++;
            }
        }

        List<List<Long>> result = new ArrayList<>();
        // 将edge转化成
        for (String edge : edges) {
            result.add(Arrays.stream(edge.split("_")).map(Long::valueOf).collect(Collectors.toList()));
        }

        return result;

    }

    public List<Long> selectPackageRoute(String traceId){
        List<INTPack> packs = intpackMapper.selectByTraceId(traceId);
        packs.forEach((pack)->{pack.setTimebias(pack.getTimebias()+stationService.getStartTime(pack.getDpid()));});
        Collections.sort(packs);
        return packs.stream().map(INTPack::getDpid).toList();
    }

    public List<OriPack> getAriPackages(Long dpid, Long dataType, String traceId, String dataSrc, String dataDst, Long dataSize){
        List<OriPack> result = intpackMapper.selectALLAriPackages(dpid, dataType, traceId, dataSrc, dataDst, dataSize);

        Map<String, OriPackDecorator> map = new HashMap<>();

        for(OriPack oriPack: result){
            map.put(oriPack.getTraceId(), new OriPackDecorator(oriPack));
        }

        List<INTPack> packs = intpackMapper.selectALLPackages(null, dataType, traceId, dataSrc, dataDst, dataSize);

        OriPackDecorator oriPackDecorator;
        for(INTPack pack:packs){
            if(!map.containsKey(pack.getTraceId())){
                continue;
            }
            oriPackDecorator = map.get(pack.getTraceId());
            oriPackDecorator.addContext(stationService.getStartTime(pack.getDpid())+pack.getTimebias(), pack.getDpid());
        }

        for(OriPackDecorator oriPackDecorator1:map.values()){
            oriPackDecorator1.updateOriPack();
        }

        return result;
    }

    public INTPack getINTPack(String traceId, Long dpid){
        return intpackMapper.getINTPack(traceId, dpid);
    }

}
