package com.example.zgcbd.service;

import com.example.zgcbd.pojo.INTPack;
import com.example.zgcbd.pojo.Pack;

import java.util.List;
import java.util.Map;

public interface INTPackService {
    List<INTPack> selectPackagesByDpid(long dpid);

//    List<INTPack> selectPackages(Integer dpid, String startTime, String endTime, String ipv4Src, String ethSrc, String ethDst, String ipv4Dst, String ethertype);

    List<INTPack> selectAllPackages();

    List<List<Long>> selectStationTopo();

    List<Long> selectPackageRoute(String traceId);
}