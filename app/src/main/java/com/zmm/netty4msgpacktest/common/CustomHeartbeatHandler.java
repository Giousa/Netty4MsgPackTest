package com.zmm.netty4msgpacktest.common;

import com.zmm.netty4msgpacktest.domain.DeviceValue;
import com.zmm.netty4msgpacktest.domain.TypeData;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/9
 * Email:65489469@qq.com
 */
public abstract class CustomHeartbeatHandler extends ChannelInboundHandlerAdapter {


    protected String name;
    private int heartbeatCount = 0;

    public CustomHeartbeatHandler(String name) {
        this.name = name;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DeviceValue deviceValue = (DeviceValue) msg;
        int type = deviceValue.getType();
        System.out.println("CustomHeartbeatHandler type="+type);

        switch (type){
            case 1:
                sendPongMsg(ctx);
                break;

            case 2:
                System.out.println(name + " get pong msg from " + ctx.channel().remoteAddress());

                break;

            case 3:
                handleData(ctx, msg);

                break;
        }

    }

    protected void sendPingMsg(ChannelHandlerContext context) {
        DeviceValue deviceValue = new DeviceValue();
        deviceValue.setType(TypeData.PING);
        deviceValue.setSpeed(0);
        deviceValue.setAngle(15);
        deviceValue.setSeatId(TypeData.PING_SEAT);
        context.channel().writeAndFlush(deviceValue);
        heartbeatCount++;
        System.out.println(name + " sent ping msg to " + context.channel().remoteAddress() + ", count: " + heartbeatCount);
    }

    private void sendPongMsg(ChannelHandlerContext context) {
        DeviceValue deviceValue = new DeviceValue();
        deviceValue.setType(TypeData.PONG);
        deviceValue.setSpeed(0);
        deviceValue.setAngle(15);
        deviceValue.setSeatId(TypeData.PONG_SEAT);
        context.channel().writeAndFlush(deviceValue);
        heartbeatCount++;
        System.out.println(name + " sent pong msg to " + context.channel().remoteAddress() + ", count: " + heartbeatCount);
    }

    protected abstract void handleData(ChannelHandlerContext channelHandlerContext, Object msg);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("---" + ctx.channel().remoteAddress() + " is active---");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("---" + ctx.channel().remoteAddress() + " is inactive---");
    }

    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        System.err.println("---READER_IDLE---");
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        System.err.println("---WRITER_IDLE---");
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
        System.err.println("---ALL_IDLE---");
    }
}