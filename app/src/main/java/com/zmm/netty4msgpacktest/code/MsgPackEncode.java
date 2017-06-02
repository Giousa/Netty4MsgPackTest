package com.zmm.netty4msgpacktest.code;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Description:编码
 * Author:Giousa
 * Date:2017/2/10
 * Email:65489469@qq.com
 */
public class MsgPackEncode extends MessageToByteEncoder<Object> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack msgPack = new MessagePack();
        byte[] raw = msgPack.write(msg);
        out.writeBytes(raw);
    }
}