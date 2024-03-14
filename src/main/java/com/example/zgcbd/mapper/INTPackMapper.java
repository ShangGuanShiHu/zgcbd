
package com.example.zgcbd.mapper;

import com.example.zgcbd.pojo.INTPack;
import com.example.zgcbd.pojo.Pack;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface INTPackMapper{
    List<INTPack> selectByDpid(long dpid);

    List<INTPack> selectALL();

    List<INTPack> selectByTraceId(String traceId);

    List<Map> selectALLAriPackages(Long dpid, Long dataType, String traceId, String dataSrc, String dataDst, Long dataSize);

    INTPack getINTPack(String traceId, Long dpid);
}
