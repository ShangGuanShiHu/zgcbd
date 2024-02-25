package com.example.zgcbd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * (Pack)实体类
 *
 * @author makejava
 * @since 2023-11-21 15:55:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pack implements Serializable {
    private Integer dpid;
    
    private Timestamp timestamp;
    
    private Long seq;

    private Long ack;
    
    private String ipv4Src;
    
    private String ipv4Dst;
    
    private String ethSrc;
    
    private String ethDst;
    
    private String ethertype;
}

