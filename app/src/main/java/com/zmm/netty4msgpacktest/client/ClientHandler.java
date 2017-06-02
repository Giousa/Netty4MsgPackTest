package com.zmm.netty4msgpacktest.client;

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
public class ClientHandler extends CustomHeartbeatHandler {
    private Client client;
    public ClientHandler(Client client) {
        super("client");
        this.client = client;
    }


    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, Object msg) {
        DeviceValue deviceValue = (DeviceValue) msg;
        System.out.println("client 接收数据:"+deviceValue.toString());

//        DeviceValue s = new DeviceValue();
//        s.setType(TypeData.CUSTOME);
//        s.setSpeed(0);
//        s.setAngle(15);
//        s.setSeatId(TypeData.SERVER_RESPONSE);
//        channelHandlerContext.writeAndFlush(s);
    }

    @Override
    protected void handleAllIdle(ChannelHandlerContext ctx) {
        super.handleAllIdle(ctx);
        sendPingMsg(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        client.doConnect();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(name + " exception"+cause.toString());

    }
}