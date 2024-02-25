package com.example.zgcbd.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.example.zgcbd.mapper.PackMapper;
import com.example.zgcbd.service.PackService;
import com.example.zgcbd.util.SpringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.util.ResourceUtils;


public class PackageFileListener extends FileAlterationListenerAdaptor {

    //设置变量 记录上次读取位置
    private Map<String, Long> place = new HashMap<>();

    private PackService packService;


    public static String[] columns = {"dpid", "timestamp", "seq", "ack", "ipv4Src", "ipv4Dst", "ethSrc", "ethDst", "ethertype"};



    public PackageFileListener(String fileDir, String[] fileNames) throws Exception{
        super();
        for (String fileName : fileNames) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(ResourceUtils.getFile(fileDir+File.separator+fileName),"r");
            place.put(fileName, randomAccessFile.length());
        }
        packService = SpringUtil.getBean(PackService.class);
    }

    private void insert(File file) throws IOException {
        String filename = file.getName();
        if(!place.containsKey(file.getName())){
            return;
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
        //将文件定位到偏移量所指位置，在该位置发生下一个读取或写入操作
        randomAccessFile.seek(place.get(filename)+1);
        //获取按行读取的数据并落库
        String s = randomAccessFile.readLine();

        int i;
        for(;s!= null;s = randomAccessFile.readLine()){
            Map<String, Object> map = new HashMap<>();
            String[] splits = s.split(" ");
            if (columns.length != splits.length){
                continue;
            }
            i=0;
            while (i<splits.length){
                if(columns[i]!="timestamp"){
                    map.put(columns[i], splits[i]);
                }else {
                    double timestampSeconds = Double.parseDouble(splits[i]);

                    // 将秒级时间戳转换为微秒级
                    long timestampMS = (long) (timestampSeconds * 1e3);

                    // 创建Timestamp对象
                    Timestamp timestamp = new Timestamp(timestampMS);  // 转换为纳秒级
                    // 设置纳秒部分
                    timestamp.setNanos((int) ((timestampSeconds  % 1) * 1e9));
                    map.put(columns[i], timestamp);
                }

                i++;
            }
            packService.insertPack(map);
        }
        //重新计算偏移量，做下一次读取时的初始偏移量
        place.replace(filename, randomAccessFile.length());
    }


    /**
     * 文件创建修改
     */
    public void onFileChange(File file) {
        try {
            insert(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}