package com.example.zgcbd;

import com.example.zgcbd.mapper.PackMapper;
import com.example.zgcbd.mapper.StationMapper;
import com.example.zgcbd.pojo.Pack;
import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
class ZgcbdApplicationTests {
//    @Autowired
//    private PackMapper packMapper;
//
//    @Autowired
//    private StationMapper stationMapper;
//
//    @Test
//    void testGetALL() {
//        List<Pack> packs = packMapper.selectPackages(1, Timestamp.valueOf("2023-11-15 17:18:59"),
//                Timestamp.valueOf("2023-11-15 17:21:00"),null,null,null,null,null);
//        System.out.println(packs);
//    }
}
