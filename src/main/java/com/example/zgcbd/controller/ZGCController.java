package com.example.zgcbd.controller;

import com.example.zgcbd.pojo.INTPack;
import com.example.zgcbd.pojo.OriPack;
import com.example.zgcbd.pojo.Station;
import com.example.zgcbd.service.INTPackService;
import com.example.zgcbd.service.StationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@MapperScan(value = "com.example.zgcbd.mapper")
public class ZGCController {


    @Autowired
    private StationService stationService;

    @Autowired
    private INTPackService packService;

    @GetMapping("/station/getALLStations")
    public List<Station> getALLStations(){
        return stationService.selectALLStations();
    }

    @GetMapping("/package/getPackagesByStationId")
    public List<INTPack> getPackagesByStationId(Long dpid){
        List<INTPack> result;
        if(!Objects.isNull(dpid))
            result = packService.selectPackagesByDpid(dpid);
        else {
            result = packService.selectAllPackages();
        }
        return result;
    }

//    // 这里之前是通过dpid进行查询
//    // 现在增加了 timestamp, ipv4_src, eth_src, eth_dst, ipv4_dst, ethertype
//    @GetMapping("/package/getPackagesByStationIdPage")
//    public PageInfo<INTPack> getPackagesByStationIdPage(@RequestParam int currentPage, @RequestParam int pageSize, Integer dpid, String startTime, String endTime,
//                                                     String ipv4Src, String ethSrc, String ethDst, String ipv4Dst, String ethertype){
//        PageHelper.startPage(currentPage,pageSize);
//        List<INTPack> result = packService.selectPackages(dpid, startTime, endTime, ipv4Src, ethSrc, ethDst, ipv4Dst, ethertype);
//        PageInfo<INTPack> appsPageInfo = new PageInfo<>(result);
//        return appsPageInfo;
//    }

    @GetMapping("/package/getOriPackages")
    public PageInfo<OriPack> getOriPackagesByStationId(@RequestParam int currentPage, @RequestParam int pageSize, Long dpid, Long dataType, String traceId, String dataSrc, String dataDst, Long dataSize){

        PageHelper.startPage(currentPage,pageSize);
        List<OriPack> result = packService.getAriPackages(dpid, dataType, traceId, dataSrc, dataDst, dataSize);
        PageInfo<OriPack> appsPageInfo = new PageInfo<>(result);
        return appsPageInfo;
    }


    @GetMapping("/station/getStationTopo")
    public List<List<Long>> getStationTopo() {
        return packService.selectStationTopo();
    }

    @GetMapping("/package/getPackageRoute")
    public List<Long> getPackageRoute(@RequestParam("traceId") String traceId){
        return packService.selectPackageRoute(traceId);
    }


    @GetMapping("/package/getINTPack")
    public INTPack getINTPack(@RequestParam String traceId, @RequestParam long dpid){
        INTPack intPack = packService.getINTPack(traceId, dpid);
        intPack.setTimebias(stationService.getStartTime(intPack.getDpid()) + intPack.getTimebias());
        return intPack;
    }

}
