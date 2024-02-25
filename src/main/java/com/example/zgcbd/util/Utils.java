package com.example.zgcbd.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;
import java.util.Set;

public class Utils {
    public static <T> T decode(Class<T> clazz , Map<String,Object> msg){
        try {
            T cls = clazz.newInstance();
            Set<Map.Entry<String, Object>> entrySet = msg.entrySet();
            for(Map.Entry<String,Object> entry : entrySet){
                BeanUtils.setProperty(cls,entry.getKey(),entry.getValue());
            }
            return cls;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
