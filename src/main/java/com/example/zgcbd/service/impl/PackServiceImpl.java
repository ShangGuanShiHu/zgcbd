package com.example.zgcbd.service.impl;

import com.example.zgcbd.mapper.PackMapper;
import com.example.zgcbd.pojo.Pack;
import com.example.zgcbd.service.PackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import com.example.zgcbd.util.Utils;

@Service
public class PackServiceImpl implements PackService {
    @Autowired
    private PackMapper packMapper;

    public List<Pack> selectPackagesByDpid(int dpid){
        return packMapper.selectByDpid(dpid);
    }

    public List<Pack> selectAllPackages(){
        return packMapper.selectALL();
    }


    public List<List<Integer>> selectStationTopo(){
        List<Pack> packs = packMapper.selectALL();
        
        // 查看每一个数据包的路径
        Map<Long, List<Integer>> map = new HashMap<>();
        for (Pack pack : packs) {
            if(map.containsKey(pack.getSeq())) {
                map.get(pack.getSeq()).add(pack.getDpid());
            }
            else {
                ArrayList<Integer> integers = new ArrayList<>();
                integers.add(pack.getDpid());
                map.put(pack.getSeq(), integers);
            }
        }
        
        // 根据每一个包的路径，拼接成拓扑结构
        Set<String> edges = new HashSet<>();

        int src = 0;
        int des = 1;
        for (List<Integer> integers : map.values()) {
            src = 0;
            des = 1;
            while (des < integers.size()){
                if(integers.get(src)!=integers.get(des)){
                    edges.add(String.join("_", String.valueOf(integers.get(src)), String.valueOf(integers.get(des))));
                }
                src ++;
                des ++;
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        // 将edge转化成
        for (String edge : edges) {
            result.add(Arrays.stream(edge.split("_")).map(Integer::valueOf).collect(Collectors.toList()));
        }

        return result;
    }

    public List<Integer> selectPackageRoute(long seq){

        List<Integer> route = new ArrayList<>();

        Set<Integer> reupload = new HashSet<>();

        List<Pack> packs = packMapper.selectInRoute(seq);

        for (Pack pack : packs) {
            if(!reupload.contains(pack.getDpid())){
                route.add(pack.getDpid());
                reupload.add(pack.getDpid());
            }
        }
        return route;
    }

    public void insertPack(Map map){
        Pack pack = Utils.decode(Pack.class, map);
        packMapper.insertPack(pack);
    }

    public List<Pack> selectPackages(Integer dpid, String startTime, String endTime, String ipv4Src, String ethSrc, String ethDst, String ipv4Dst, String ethertype){
        Timestamp startTimestamp=null;
        Timestamp endTimestamp=null;
        if(!Objects.isNull(startTime)){
            startTimestamp = Timestamp.valueOf(startTime);
        }
        if(!Objects.isNull(endTime)) {
            endTimestamp = Timestamp.valueOf(endTime);
        }
        return packMapper.selectPackages(dpid,startTimestamp, endTimestamp,ipv4Src,ethSrc,ethDst,ipv4Dst,ethertype);
    }
}
