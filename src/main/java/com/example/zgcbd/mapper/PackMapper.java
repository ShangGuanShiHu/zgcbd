package com.example.zgcbd.mapper;

import com.example.zgcbd.pojo.Pack;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface PackMapper{
    List<Pack> selectByDpid(int dpid);

    List<Pack> selectALL();

    List<Pack> selectInRoute(long seq);

    List<Pack> selectPackages(Integer dpid, Timestamp startTimestamp, Timestamp endTimestamp, String ipv4Src, String ethSrc, String ethDst, String ipv4Dst, String ethertype);

    void insertPack(Pack pack);
}
