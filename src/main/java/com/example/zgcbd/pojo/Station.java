package com.example.zgcbd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Station implements Serializable {
    private Integer dpid;
    
    private Double longitude;
    
    private Double latitude;
    
    private String label;
}

