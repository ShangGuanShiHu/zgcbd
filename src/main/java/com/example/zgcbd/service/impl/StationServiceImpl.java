package com.example.zgcbd.service.impl;

import com.example.zgcbd.mapper.StationMapper;
import com.example.zgcbd.pojo.Station;
import com.example.zgcbd.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationMapper stationMapper;

    private Map<Long, Long> station_start_time = new HashMap<>();

    public List<Station> selectALLStations() {
        List<Station> stations = stationMapper.selectALL();
        if(station_start_time.size() == 0){
            for(Station station:stations){
                station_start_time.put(station.getDpid(), station.getStartTime());
            }
        }
        return stations;
    }

    public Station getStationById(long dpid){
        return stationMapper.getStation(dpid);
    }

    public Long getStartTime(long dpid) {
        if(!station_start_time.containsKey(dpid)) {
            station_start_time.put(dpid, stationMapper.getStation(dpid).getStartTime());
        }
        return  station_start_time.get(dpid);
    }

    public void updateStationStartTime(){
        List<Station> stations = stationMapper.selectALL();
        station_start_time.clear();
        for(Station station:stations){
            station_start_time.put(station.getDpid(), station.getStartTime());
        }
    }
}
