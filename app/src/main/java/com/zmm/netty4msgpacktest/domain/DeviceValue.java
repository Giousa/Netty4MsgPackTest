package com.zmm.netty4msgpacktest.domain;

import org.msgpack.annotation.Message;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/6/1
 * Time:下午5:42
 */

@Message
public class DeviceValue {

    private int type;

    private int seatId;

    private int speed;

    private int angle;

    public int getType() {
        return type;
    }

    public DeviceValue() {
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }


    @Override
    public String toString() {
        return "DeviceValue{" +
                "type=" + type +
                ", seatId=" + seatId +
                ", speed=" + speed +
                ", angle=" + angle +
                '}';
    }
}
