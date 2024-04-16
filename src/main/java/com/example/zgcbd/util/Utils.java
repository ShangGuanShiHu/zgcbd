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

    // 计算百分位数的方法
    public static double percentile(double[] dataset, double percentile) {
        if (percentile < 0 || percentile > 100) {
            throw new IllegalArgumentException("百分位数必须在0到100之间");
        }

        double index = percentile / 100.0 * (dataset.length - 1);
        if (index == Math.floor(index)) {
            return dataset[(int) index];
        } else {
            int lowerIndex = (int) Math.floor(index);
            int upperIndex = (int) Math.ceil(index);
            double lowerValue = dataset[lowerIndex];
            double upperValue = dataset[upperIndex];
            return lowerValue + (upperValue - lowerValue) * (index - lowerIndex);
        }
    }
}
