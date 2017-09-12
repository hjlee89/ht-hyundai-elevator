package com.ht.emulator.ht_hyundai_elevator;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    // 도착
    private static final String HYUNDAI_ELEVATOR_STX = "STX";
    private static final String HYUNDAI_ELEVATOR_DONG = "0101";
    private static final String HYUNDAI_ELEVATOR_NUMBER = "01";
    private static final String HYUNDAI_ELEVATOR_NUMBER2 = "02";
    private static final String HYUNDAI_ELEVATOR_STATE = "01";
    private static final String HYUNDAI_ELEVATOR_FLOOR = "04";
    private static final String HYUNDAI_ELEVATOR_DIRECTION = "2";
    private static final String HYUNDAI_ELEVATOR_DOOR = "0";
    private static final String HYUNDAI_ELEVATOR_RESERVED = "00";
    private static final String HYUNDAI_ELEVATOR_CARCALL = "0000000000000000";
    private static final String HYUNDAI_ELEVATOR_UPCALL = "0000000000000000";
    private static final String HYUNDAI_ELEVATOR_DOWNCALL = "0000000000000000";
    private static final String HYUNDAI_ELEVATOR_ETX = "ETX";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        StringBuffer frame = new StringBuffer();
        frame.append(HYUNDAI_ELEVATOR_STX).append(HYUNDAI_ELEVATOR_DONG)
                .append(HYUNDAI_ELEVATOR_NUMBER).append(HYUNDAI_ELEVATOR_STATE)
                .append(HYUNDAI_ELEVATOR_FLOOR).append(HYUNDAI_ELEVATOR_DIRECTION)
                .append(HYUNDAI_ELEVATOR_DOOR).append(HYUNDAI_ELEVATOR_RESERVED)
                .append(HYUNDAI_ELEVATOR_CARCALL).append(HYUNDAI_ELEVATOR_UPCALL)
                .append(HYUNDAI_ELEVATOR_DOWNCALL).append(HYUNDAI_ELEVATOR_DONG)
                .append(HYUNDAI_ELEVATOR_NUMBER2).append(HYUNDAI_ELEVATOR_STATE)
                .append(HYUNDAI_ELEVATOR_FLOOR).append(HYUNDAI_ELEVATOR_DIRECTION)
                .append(HYUNDAI_ELEVATOR_DOOR).append(HYUNDAI_ELEVATOR_RESERVED)
                .append(HYUNDAI_ELEVATOR_CARCALL).append(HYUNDAI_ELEVATOR_UPCALL)
                .append(HYUNDAI_ELEVATOR_DOWNCALL).append(HYUNDAI_ELEVATOR_DONG)
                .append(HYUNDAI_ELEVATOR_NUMBER2).append(HYUNDAI_ELEVATOR_STATE)
                .append(HYUNDAI_ELEVATOR_FLOOR).append(HYUNDAI_ELEVATOR_DIRECTION)
                .append(HYUNDAI_ELEVATOR_DOOR).append(HYUNDAI_ELEVATOR_RESERVED)
                .append(HYUNDAI_ELEVATOR_CARCALL).append(HYUNDAI_ELEVATOR_UPCALL)
                .append(HYUNDAI_ELEVATOR_DOWNCALL).append(HYUNDAI_ELEVATOR_ETX);

        String response = frame.toString();
        ctx.writeAndFlush(response);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
