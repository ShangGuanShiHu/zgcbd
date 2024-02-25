package com.example.zgcbd.controller;

import com.example.zgcbd.pojo.Pack;
import com.example.zgcbd.pojo.Station;
import com.example.zgcbd.service.PackService;
import com.example.zgcbd.service.StationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@MapperScan(value = "com.example.zgcbd.mapper")
public class ZGCController {
    @Autowired
    private StationService stationService;

    @Autowired
    private PackService packService;

    @GetMapping("/station/getALLStations")
    public List<Station> getALLStations(){
        return stationService.selectALLStations();
    }

    @GetMapping("/package/getPackagesByStationId")
    public List<Pack> getPackagesByStationId(@RequestParam Map<String, String> params){
        List<Pack> result;
        if(params.containsKey("dpid"))
            result = packService.selectPackagesByDpid(Integer.valueOf(params.get("dpid")));
        else {
            result = packService.selectAllPackages();
        }
        return result;
    }

    // 这里之前是通过dpid进行查询
    // 现在增加了 timestamp, ipv4_src, eth_src, eth_dst, ipv4_dst, ethertype
    @GetMapping("/package/getPackagesByStationIdPage")
    public PageInfo<Pack> getPackagesByStationIdPage(@RequestParam int currentPage, @RequestParam int pageSize, Integer dpid, String startTime, String endTime,
                                                     String ipv4Src, String ethSrc, String ethDst, String ipv4Dst, String ethertype){
        PageHelper.startPage(currentPage,pageSize);
        List<Pack> result = packService.selectPackages(dpid, startTime, endTime, ipv4Src, ethSrc, ethDst, ipv4Dst, ethertype);
        PageInfo<Pack> appsPageInfo = new PageInfo<>(result);
        return appsPageInfo;
    }


    @GetMapping("/station/getStationTopo")
    public List<List<Integer>> getStationTopo() {
        return packService.selectStationTopo();
    }

    @GetMapping("/package/getPackageRoute")
    public List<Integer> getPackageRoute(@RequestParam("seq") long seq){
        return packService.selectPackageRoute(seq);
    }

    @GetMapping("/package/insertPackage")
    public String inserPackage(@RequestParam Map<String, Object> params){
        try {
            packService.insertPack(params);
        }
        catch (Exception e){
            return "false";
        }
        return "success";
    }

}
