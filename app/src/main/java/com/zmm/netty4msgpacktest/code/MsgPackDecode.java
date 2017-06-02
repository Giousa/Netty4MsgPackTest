package com.zmm.netty4msgpacktest.code;

import com.zmm.netty4msgpacktest.domain.DeviceValue;

import org.msgpack.MessagePack;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * Description:解码
 * Author:Giousa
 * Date:2017/2/10
 * Email:65489469@qq.com
 */
public class MsgPackDecode extends MessageToMessageDecoder<ByteBuf> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final byte[] array;
        final int length=msg.readableBytes();
        array=new byte[length];
        msg.getBytes(msg.readerIndex(), array,0,length);
        MessagePack msgpack=new MessagePack();
        out.add(msgpack.read(array, DeviceValue.class));
    }

}