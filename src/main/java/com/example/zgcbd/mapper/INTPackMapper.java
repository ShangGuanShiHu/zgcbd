
package com.example.zgcbd.mapper;

import com.example.zgcbd.pojo.INTPack;
import com.example.zgcbd.pojo.Pack;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface INTPackMapper{
    List<INTPack> selectByDpid(long dpid);

    List<INTPack> selectALL();

    List<INTPack> selectByTraceId(String traceId);
}
