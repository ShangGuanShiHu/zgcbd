package com.example.zgcbd.mapper;

import com.example.zgcbd.pojo.Station;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface StationMapper{
    List<Station> selectALL();

    Station getStation(long dpid);
}
