package com.example.zgcbd.listener;


import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;

//@Component
public class FileApplication implements ApplicationRunner {

    //读取配置文件中了文件路径 拿到要监听的文件夹packFile
    @Value("${packFile.dir}")
    private String fileDir;

    @Value("${packFile.names}")
    private String[] fileNames;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 构造观察类主要提供要观察的文件或目录，当然还有详细信息的filter
        FileAlterationObserver observer = new FileAlterationObserver(ResourceUtils.getFile(fileDir));
        // 构造收听类
        PackageFileListener listener = new PackageFileListener(fileDir, fileNames);
        // 为观察对象添加收听对象
        observer.addListener(listener);
        // 配置Monitor，第一个参数单位是毫秒，是监听间隔；第二个参数就是绑定我们之前的观察对象。
        FileAlterationMonitor fileMonitor = new FileAlterationMonitor(1000, observer);
        // 启动监听
        fileMonitor.start();
    }
}
