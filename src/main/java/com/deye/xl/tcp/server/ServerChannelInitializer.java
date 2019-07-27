package com.deye.demo.tcp.server;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * description: 通道初始化，主要用于设置各种Handler author: date: 2018-11-28 14:55
 **/
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    ServerChannelHandler serverChannelHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //IdleStateHandler心跳机制,如果超时触发Handle中userEventTrigger()方法空闲管理器 3分钟没有任何操作时
        //0 则禁用事件
        pipeline.addLast("idleStateHandler",
                new IdleStateHandler(0, 0, 3, TimeUnit.MINUTES));

        ByteBuf delimiter = Unpooled.copiedBuffer("!".getBytes());
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, false, delimiter));
        pipeline.addLast(
                new StringEncoder(), //序列号
                new StringDecoder()//还原为字符串

        );

        //自定义Handler
        pipeline.addLast("serverChannelHandler", serverChannelHandler);
    }

}