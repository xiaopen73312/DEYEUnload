package com.deye.xl;

import com.deye.xl.manager.SetOnlineManager;
import com.deye.xl.tcp.server.NettyTcpServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class XlApplication implements CommandLineRunner {

    @Autowired
    NettyTcpServer nettyTcpServer;
    @Autowired
    SetOnlineManager setOnlineManager;
    public static void main(String[] args) {
        SpringApplication.run(XlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        setOnlineManager.setALLDownline();
        //启动服务端
        ChannelFuture start = nettyTcpServer.start();
        start.isDone();//是否完成
        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        start.channel().closeFuture().syncUninterruptibly();

    }
}
