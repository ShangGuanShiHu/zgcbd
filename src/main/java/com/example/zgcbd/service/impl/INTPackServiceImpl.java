package com.example.zgcbd.service.impl;

import com.example.zgcbd.mapper.INTPackMapper;
import com.example.zgcbd.mapper.PackMapper;
import com.example.zgcbd.pojo.INTPack;
import com.example.zgcbd.pojo.Pack;
import com.example.zgcbd.service.INTPackService;
import com.example.zgcbd.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class INTPackServiceImpl implements INTPackService {
    @Autowired
    private INTPackMapper intpackMapper;

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
        return intpackMapper.selectByTraceId(traceId).stream().map(INTPack::getDpid).collect(Collectors.toList());
    }

    public List<Map> getALLAriPackages(String dpid){
        return intpackMapper.selectALLAriPackages(dpid);
    }

    public INTPack getINTPack(String traceId, String dpid){
        return intpackMapper.getINTPack(traceId, dpid);
    }

}
