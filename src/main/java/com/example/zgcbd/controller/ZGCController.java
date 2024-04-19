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
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public PageInfo<OriPack> getOriPackagesByStationId(@RequestParam int currentPage, @RequestParam int pageSize, Long dpid, Long dataType, String dataSrc, String dataDst, String traceId,@RequestParam int dataSizeSort, @RequestParam int switchNumSort){
        List<OriPack> result = packService.getAriPackages(dpid, dataType, traceId, dataSrc, dataDst, dataSizeSort,switchNumSort);
        List<OriPack> resultPage = new ArrayList<>();

        int maxPageNum = (result.size()+pageSize-1) / pageSize;
        PageInfo<OriPack> appsPageInfo;
        // 保证页号大于1，页的大小不超过总数
        if(currentPage>0 && currentPage<=maxPageNum) {
            for (int i = (currentPage - 1) * pageSize; i < result.size() && i < currentPage * pageSize; i++) {
                resultPage.add(result.get(i));
            }
            appsPageInfo = new PageInfo<>(resultPage);
            appsPageInfo.setPageNum(currentPage);
            appsPageInfo.setPageSize(pageSize);
            appsPageInfo.setSize(pageSize);
            appsPageInfo.setStartRow((currentPage - 1) * pageSize);
            appsPageInfo.setEndRow(currentPage * pageSize - 1);
            appsPageInfo.setPages((result.size() + pageSize - 1) / pageSize);
            appsPageInfo.setPrePage(currentPage - 1);
            appsPageInfo.setNextPage(currentPage + 1);
            appsPageInfo.setIsFirstPage(currentPage == 1);
            appsPageInfo.setIsLastPage(appsPageInfo.getPages() == appsPageInfo.getPageNum());
            appsPageInfo.setHasPreviousPage(currentPage > 1);
            appsPageInfo.setHasNextPage(currentPage < appsPageInfo.getPages());
            appsPageInfo.setTotal(result.size());
        }
        else {
            appsPageInfo = new PageInfo<>(result);
        }
        return appsPageInfo;
    }

    @GetMapping("/package/distribution")
    public Map<String, Float> getTypeDistribution(@RequestParam(required = false) Integer dpid) {
        return packService.getTypeDistribution(dpid);
    }

    @GetMapping("/station/statistic")
    public Map<String, Float> getStationStatistic(@RequestParam(required = false) Integer dpid) {
        return packService.getStationStatistic(dpid);
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
        return packService.getINTPack(traceId, dpid);
    }

    @GetMapping("/package/getAllDataType")
    public List<Long> getAllDataType(){
        return packService.getAllDataTypes();
    }

    @GetMapping("/package/getAllIP")
    public List<String> getAllIP(){
        return packService.getAllIPs();
    }

    @GetMapping("/station/getPackageNum")
    public Map<Long, Long> getOriPackagesNumByStationId(@RequestParam Long dpid){
        return packService.getOriPackagesNumByStationId(dpid);
    }

    @GetMapping("/station/getPackageBytes")
    public Map<Long, Long> getOriPackagesBytesByStationId(@RequestParam Long dpid){
        return packService.getOriPackagesBytesByStationId(dpid);
    }

    @GetMapping("/station/getDurationStatistic")
    public Map<String, Double> getDurationStatisticByStationId(@RequestParam Long dpid){
        return packService.getDurationStatisticByStationId(dpid);
    }

    @RequestMapping("/package/addINTPackages")
    public String addINTPackages(@RequestBody List<INTPack> intPacks){
        try{
            packService.addINTPackages(intPacks);
        }
        catch (Exception e){
            e.printStackTrace();
            return "Failure";
        }
        return "Success";
    }

    @GetMapping("/station/updateStartTime")
    public void updateStartTime(){
        stationService.updateStationStartTime();
    }

}
