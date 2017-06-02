package com.zmm.netty4msgpacktest.client;

import com.zmm.netty4msgpacktest.code.MsgPackDecode;
import com.zmm.netty4msgpacktest.code.MsgPackEncode;
import com.zmm.netty4msgpacktest.domain.DeviceValue;
import com.zmm.netty4msgpacktest.domain.TypeData;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/9
 * Email:65489469@qq.com
 */
public class Client {
    private NioEventLoopGroup workGroup = new NioEventLoopGroup(4);
    private Channel channel;
    private Bootstrap bootstrap;

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.start();
        client.sendData();
    }

    public void start() {
        try {
            bootstrap = new Bootstrap();
            bootstrap
                    .group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new IdleStateHandler(0, 0, 5));
//                            p.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, -4, 0));
                            p.addLast(new MsgPackDecode());
                            p.addLast(new MsgPackEncode());
                            p.addLast(new ClientHandler(Client.this));
                        }
                    });
            doConnect();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 重连机制,每隔2s重新连接一次服务器
     */
    protected void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }

        ChannelFuture future = bootstrap.connect("127.0.0.1", 12345);

        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                    System.out.println("Connect to server successfully!");
                } else {
                    System.out.println("Failed to connect to server, try connect after 2s");

                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect();
                        }
                    }, 2, TimeUnit.SECONDS);
                }
            }
        });
    }

    /**
     * 发送数据 每隔2秒发送一次
     * @throws Exception
     */
    public void sendData() throws Exception {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            if (channel != null && channel.isActive()) {

                DeviceValue deviceValue = new DeviceValue();
                deviceValue.setType(TypeData.CUSTOME);
                deviceValue.setAngle(i%15);
                deviceValue.setSeatId(i%30);
                deviceValue.setSpeed(i%120);

                System.out.println("client 发送数据:"+deviceValue.toString());

                channel.writeAndFlush(deviceValue);
            }

            Thread.sleep(random.nextInt(20000));
        }
    }


}
