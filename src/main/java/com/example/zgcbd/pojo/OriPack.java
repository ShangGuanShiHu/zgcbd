package com.example.zgcbd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;


@Data
@NoArgsConstructor
@ToString
public class OriPack {
    private long dataType;
    private String traceId;
    private long dataSize;
    private String dataSrc;
    private String dataDst;

    private List<Long> route = null;

    private long transTime = 0;

    public OriPack(INTPack intPack){
        this.dataType = intPack.getDataType();
        this.traceId = intPack.getTraceId();
        this.dataSize = intPack.getDataSize();
        this.dataSrc = intPack.getDataSrc();
        this.dataDst = intPack.getDataDst();
    }
}
