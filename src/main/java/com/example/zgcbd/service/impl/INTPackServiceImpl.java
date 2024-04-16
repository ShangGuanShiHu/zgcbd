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
import com.example.zgcbd.util.Utils;

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

    public List<OriPack> getAriPackages(Long dpid, Long dataType, String traceId, String dataSrc, String dataDst, int dataSizeSort, int switchNumSort){

        Map<String, OriPackDecorator> map = new HashMap<>();

        // 获取所有的数据包，经过的交换机数量无法在数据库中进行排序，但是要自定义分页功能了
        List<INTPack> packs = intpackMapper.selectALLPackages(null, dataType, traceId, dataSrc, dataDst);

        OriPackDecorator oriPackDecorator;
        for(INTPack pack:packs){
            if(!map.containsKey(pack.getTraceId())){
               map.put(pack.getTraceId(), new OriPackDecorator(new OriPack(pack)));
            }
            oriPackDecorator = map.get(pack.getTraceId());
            oriPackDecorator.addContext(stationService.getStartTime(pack.getDpid())+pack.getTimebias(), pack.getDpid());
        }

        // 收集所有的原始数据包
        List<OriPack> result = new ArrayList<>();

        for(OriPackDecorator oriPackDecorator1:map.values()){
            oriPackDecorator1.updateOriPack();
            // 根据交换机进行选择
            if(Objects.isNull(dpid) || oriPackDecorator1.getOriPack().getRoute().contains(dpid)){
                result.add(oriPackDecorator1.getOriPack());
            }
        }


        // 根据datasize进行排序, 大于0升序，小于0降序，等于0乱序
        result.sort((OriPack o1, OriPack o2)-> (int)(o1.getDataSize()-o2.getDataSize()) * dataSizeSort);

        // 根据交换机数量进行排序
        result.sort((OriPack o1, OriPack o2)-> (o1.getRoute().size()-o2.getRoute().size()) * switchNumSort);

        return result;
    }

    public INTPack getINTPack(String traceId, Long dpid){
        return intpackMapper.getINTPack(traceId, dpid);
    }

    public List<Long> getAllDataTypes() {
        return intpackMapper.getAllDataTypes();
    }

    public List<String> getAllIPs() {
        Set<String> ips = new HashSet<>();
        ips.addAll(intpackMapper.getAllSrcIPs());
        ips.addAll(intpackMapper.getAllDstIPs());
        List<String> result = new ArrayList<>(ips.stream().toList());
        Collections.sort(result);
        return result;
    }

    private Map<Long, List<INTPack>> getOriPackageSizeByStationId(Long dpid){
        Map<Long, List<INTPack>> result = new HashMap<>();

        List<Long> dataTypes = intpackMapper.getAllDataTypes();
        for(Long dataType: dataTypes){
            result.put(dataType, new ArrayList<>());
        }

        for(INTPack pack: intpackMapper.selectByDpid(dpid)){
            result.get(pack.getDataType()).add(pack);
        }
        return result;
    }

    public Map<Long, Long> getOriPackagesNumByStationId(Long dpid) {
        Map<Long, List<INTPack>> oriPackageSizeDict = this.getOriPackageSizeByStationId(dpid);
        Map<Long, Long> result = new HashMap<>();
        for(Map.Entry<Long, List<INTPack>> entry: oriPackageSizeDict.entrySet()){
            result.put(entry.getKey(), (long) entry.getValue().size());
        }
        return result;
    }

    public Map<Long, Long> getOriPackagesBytesByStationId(Long dpid){
        Map<Long, List<INTPack>> oriPackageSizeDict = this.getOriPackageSizeByStationId(dpid);
        Map<Long, Long> result = new HashMap<>();
        for(Map.Entry<Long, List<INTPack>> entry: oriPackageSizeDict.entrySet()){
            result.put(entry.getKey(), entry.getValue().stream().mapToLong(INTPack::getDataSize).sum());
        }
        return result;
    }

    public Map<String, Double> getDurationStatisticByStationId(Long dpid){
        Map<String, Double> result = new HashMap<>();
        double[] durations = intpackMapper.selectByDpid(dpid).stream().mapToDouble(INTPack::getDataSize).sorted().toArray();

        // p99
        result.put("p99", Utils.percentile(durations, 99));

        // p90
        result.put("p90", Utils.percentile(durations, 90));

        // p50
        result.put("p50", Utils.percentile(durations, 50));

        return result;
    }

    public void addINTPackages(List<INTPack> intPacks){
        intpackMapper.insertPacks(intPacks);
    }
}
