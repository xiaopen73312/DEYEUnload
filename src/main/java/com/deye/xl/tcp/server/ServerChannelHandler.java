package com.deye.xl.tcp.server;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * description: author:jackie date: 2019.6.13
 **/
@Component
@ChannelHandler.Sharable

public class ServerChannelHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger log = LoggerFactory.getLogger(ServerChannelHandler.class);
    @Value("${netty.tcp.server.port}")
    int PORT;


    public static Map<String, Channel> HxzIdChannelmap = new ConcurrentHashMap<String, Channel>();

    /**
     * 拿到传过来的msg数据，开始处理
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    /**
     * 活跃的、有效的通道 第一次连接成功后进入的方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        //连接
        log.info("已建立新连接Established connection with the remote client.:" + getIPString(ctx) + ctx
                .channel());
        //往channel map中添加channel信息
        NettyTcpServer.map.put(getIPString(ctx), ctx.channel());
        //通道激活
        ctx.fireChannelActive();
    }

    /**
     * 不活动的通道 （客户端断电断网）连接丢失后执行的方法（client端可据此实现断线重连）
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //删除Channel Map中的失效Client

        NettyTcpServer.map.remove(getIPString(ctx));

        log.info("断开连接Disconnected with the remote client.:{}{}", getIPString(ctx), ctx
                .channel());
        ctx.fireChannelInactive();
        ctx.close();
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        //发生异常，关闭连接
        log.error("引擎 {} 的通道发生异常，即将断开连接", getRemoteAddress(ctx));

        NettyTcpServer.map.remove(getIPString(ctx));

        ctx.close();//再次建议close
        NettyTcpServer.map.remove(getIPString(ctx));
    }

    /**
     * 心跳机制，超时处理 客户端 一段时间内未做任何读写操作
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String socketString = ctx.channel().remoteAddress().toString();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("Client: " + socketString + " READER_IDLE 读超时");
                ctx.disconnect();//
                NettyTcpServer.map.remove(getIPString(ctx));

            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("Client: " + socketString + " WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("Client: " + socketString + " ALL_IDLE 总超时");

                NettyTcpServer.map.remove(getIPString(ctx));

                ctx.disconnect();
                NettyTcpServer.map.remove(getIPString(ctx));


            }
        }
    }


    /**
     * 获取client对象：ip+port
     */
    public String getRemoteAddress(ChannelHandlerContext ctx) {
        String socketString = "";
        socketString = ctx.channel().remoteAddress().toString();
        return socketString;
    }

    /**
     * 获取client的ip
     */
    public String getIPString(ChannelHandlerContext ctx) {
        String ipString = "";
        String socketString = ctx.channel().remoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1, colonAt);
        return ipString;
    }


}