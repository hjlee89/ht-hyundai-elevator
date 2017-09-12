package com.ht.emulator.ht_hyundai_elevator;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ht.emulator.ht_hyundai_elevator.ByteToStringDecoder.HyundaiElevatorMessage_DecodeState;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;

public class ByteToStringDecoder extends ReplayingDecoder<HyundaiElevatorMessage_DecodeState> {

    public static enum HyundaiElevatorMessage_DecodeState {
        FIND_STX_SYMBOL, FIND_DATA, FIND_ETX_SYMBOL
    }

    private String[] mappingTable = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ":", ";",
            "<", "=", ">", "?" };

    private Set<Integer> convertCallListToIntegerSet(String callList) {
        Set<Integer> callSet = new HashSet<Integer>();

        for (int i = 0; i < callList.length(); i++) {
            String callInfo = callList.substring(i, i + 1);

            if (mappingTable[0].equals(callInfo)) {
                continue;
            } else if (mappingTable[1].equals(callInfo)) {
                callSet.add(i * 4 + 1);
            } else if (mappingTable[2].equals(callInfo)) {
                callSet.add(i * 4 + 2);
            } else if (mappingTable[3].equals(callInfo)) {
                callSet.add(i * 4 + 1);
                callSet.add(i * 4 + 2);
            } else if (mappingTable[4].equals(callInfo)) {
                callSet.add(i * 4 + 3);
            } else if (mappingTable[5].equals(callInfo)) {
                callSet.add(i * 4 + 1);
                callSet.add(i * 4 + 3);
            } else if (mappingTable[6].equals(callInfo)) {
                callSet.add(i * 4 + 2);
                callSet.add(i * 4 + 3);
            } else if (mappingTable[7].equals(callInfo)) {
                callSet.add(i * 4 + 1);
                callSet.add(i * 4 + 2);
                callSet.add(i * 4 + 3);
            } else if (mappingTable[8].equals(callInfo)) {
                callSet.add(i * 4 + 4);
            } else if (mappingTable[9].equals(callInfo)) {
                callSet.add(i * 4 + 1);
                callSet.add(i * 4 + 4);
            } else if (mappingTable[10].equals(callInfo)) {
                callSet.add(i * 4 + 2);
                callSet.add(i * 4 + 4);
            } else if (mappingTable[11].equals(callInfo)) {
                callSet.add(i * 4 + 1);
                callSet.add(i * 4 + 2);
                callSet.add(i * 4 + 4);
            } else if (mappingTable[12].equals(callInfo)) {
                callSet.add(i * 4 + 3);
                callSet.add(i * 4 + 4);
            } else if (mappingTable[13].equals(callInfo)) {
                callSet.add(i * 4 + 1);
                callSet.add(i * 4 + 3);
                callSet.add(i * 4 + 4);
            } else if (mappingTable[14].equals(callInfo)) {
                callSet.add(i * 4 + 2);
                callSet.add(i * 4 + 3);
                callSet.add(i * 4 + 4);
            } else if (mappingTable[15].equals(callInfo)) {
                callSet.add(i * 4 + 1);
                callSet.add(i * 4 + 2);
                callSet.add(i * 4 + 3);
                callSet.add(i * 4 + 4);
            } else {
            }
        }

        return callSet;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        switch (state()) {
        case FIND_STX_SYMBOL: {
            byte stxSymbol = in.readByte();

            if (stxSymbol == 'S') {
                stxSymbol = in.readByte();
                if (stxSymbol == 'T') {
                    stxSymbol = in.readByte();
                    if (stxSymbol == 'X') {
                        state(HyundaiElevatorMessage_DecodeState.FIND_DATA);
                    }
                }
            }
            break;
        }

        case FIND_DATA: {

            byte etxSymbol = in.readByte();

            if (etxSymbol == 'E') {
                state(HyundaiElevatorMessage_DecodeState.FIND_ETX_SYMBOL);
            } else {
                byte[] data = new byte[62];
                data[0] = etxSymbol;
                if (in.isReadable(61)) {

                    in.readBytes(data, 1, 61);
                    String str = new String(data, CharsetUtil.US_ASCII);
                    String elevatorServerIp = ((InetSocketAddress) ctx.channel().remoteAddress())
                            .getAddress().getHostAddress();
                    String elevatorDong = str.substring(0, 4);
                    Integer elevatorNumber = Integer.parseInt(str.substring(4, 6));
                    String elevatorState = str.substring(6, 8);
                    Integer elevatorCurrentFloor = Integer.parseInt(str.substring(8, 10));
                    String elevatorMoveDirection = str.substring(10, 11);
                    if (elevatorMoveDirection.equals("0")) {
                        elevatorMoveDirection = "stop";
                    } else if (elevatorMoveDirection.equals("1")) {
                        elevatorMoveDirection = "up";
                    } else {
                        elevatorMoveDirection = "down";
                    }
                    String elevatorDoor = str.substring(11, 12);
                    String elevatorTargetFloor = str.substring(12, 14);
                    String elevatorCarCallList = str.substring(14, 30);
                    String elevatorUpCallList = str.substring(30, 46);
                    String elevatorDownCallList = str.substring(46, 62);
                    checkpoint();
                }
            }

            break;
        }

        case FIND_ETX_SYMBOL: {
            byte etxSymbol = in.readByte();
            if (etxSymbol == 'T') {
                checkpoint();
                etxSymbol = in.readByte();
                if (etxSymbol == 'X') {
                    checkpoint();
                }
            }

            state(HyundaiElevatorMessage_DecodeState.FIND_STX_SYMBOL);
            break;
        }
        }

    }

}
