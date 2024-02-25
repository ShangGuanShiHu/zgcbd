package com.example.zgcbd.service.impl;

import com.example.zgcbd.mapper.StationMapper;
import com.example.zgcbd.pojo.Station;
import com.example.zgcbd.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationMapper stationMapper;

    public List<Station> selectALLStations() {
        List<Station> stations = stationMapper.selectALL();
        return stations;
    }
}
