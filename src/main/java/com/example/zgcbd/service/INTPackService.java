package com.example.zgcbd.service;

import com.example.zgcbd.pojo.INTPack;
import com.example.zgcbd.pojo.OriPack;
import com.example.zgcbd.pojo.Pack;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface INTPackService {
    List<INTPack> selectPackagesByDpid(long dpid);

//    List<INTPack> selectPackages(Integer dpid, String startTime, String endTime, String ipv4Src, String ethSrc, String ethDst, String ipv4Dst, String ethertype);

    List<INTPack> selectAllPackages();

    List<List<Long>> selectStationTopo();

    List<Long> selectPackageRoute(String traceId);

    List<OriPack> getAriPackages(Long dpid, Long dataType, String traceId, String dataSrc, String dataDst, int dataSizeSort, int switchNumSort);

    INTPack getINTPack(String traceId, Long dpid);

    List<Long> getAllDataTypes();

    List<String> getAllIPs();

    Map<Long, Long> getOriPackagesNumByStationId(Long dpid);

    Map<Long, Long> getOriPackagesBytesByStationId(Long dpid);

    Map<String, Double> getDurationStatisticByStationId(Long dpid);

    void addINTPackages(List<INTPack> intPacks);

    Map<String, Float> getTypeDistribution(Integer dpid);

    Map<String, Float> getStationStatistic(Integer dpid);
}
