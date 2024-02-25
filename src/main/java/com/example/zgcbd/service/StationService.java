package com.example.zgcbd.service;


import com.example.zgcbd.pojo.Station;

import java.util.List;

public interface StationService {
    // 获取所有的站点
    List<Station> selectALLStations();
}
