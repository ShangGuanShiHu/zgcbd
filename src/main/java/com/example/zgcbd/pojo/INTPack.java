package com.example.zgcbd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.logging.log4j.util.PropertySource;

import java.util.Comparator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class INTPack implements Comparator<INTPack> {
  private String content;
  private String copyFlag;
  private String optclass;
  private String option;
  private long length;
  private long dpid;
  private long dataType;
  private String traceId;
  private long dataSize;
  private String dataSrc;
  private String dataDst;
  private long timebias;
  private String undefined;


  @Override
  public int compare(INTPack o1, INTPack o2) {
      return o1.getTraceId().compareTo(o2.getTraceId());
  }
}
