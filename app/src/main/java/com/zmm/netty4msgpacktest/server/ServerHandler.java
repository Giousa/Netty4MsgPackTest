package com.zmm.netty4msgpacktest.server;

import com.zmm.netty4msgpacktest.common.CustomHeartbeatHandler;
import com.zmm.netty4msgpacktest.domain.DeviceValue;
import com.zmm.netty4msgpacktest.domain.TypeData;

import io.netty.channel.ChannelHandlerContext;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/9
 * Email:65489469@qq.com
 */
public class ServerHandler extends CustomHeartbeatHandler {

    public ServerHandler() {
        super("server");
    }

    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, Object msg) {
        DeviceValue deviceValue = (DeviceValue) msg;
        System.out.println("server 接收数据:"+deviceValue.toString());

        DeviceValue s = new DeviceValue();
        s.setType(TypeData.CUSTOME);
        s.setSpeed(0);
        s.setAngle(15);
        s.setSeatId(TypeData.SERVER_RESPONSE);
        channelHandlerContext.writeAndFlush(s);
        System.out.println("server 发送数据:"+s.toString());
    }

    @Override
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        super.handleReaderIdle(ctx);
        System.err.println("---client " + ctx.channel().remoteAddress().toString() + " reader timeout, close it---");
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(name+" exception"+cause.toString());
    }
}