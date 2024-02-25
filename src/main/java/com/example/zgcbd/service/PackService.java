package com.example.zgcbd.service;

import com.example.zgcbd.pojo.Pack;

import java.util.List;
import java.util.Map;

public interface PackService {
    List<Pack> selectPackagesByDpid(int dpid);

    List<Pack> selectPackages(Integer dpid, String startTime, String endTime, String ipv4Src, String ethSrc, String ethDst, String ipv4Dst, String ethertype);

    List<Pack> selectAllPackages();

    List<List<Integer>> selectStationTopo();

    List<Integer> selectPackageRoute(long seq);

    void insertPack(Map map);
}
