package com.ten.air.room.mocker;

import com.ten.air.protocol.ProtocolGenerator;
import com.ten.air.room.socket.TcpSocket;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * 模拟传感器
 */
public class AirSensor implements AirListener {
    private static final Logger logger = Logger.getLogger("Sensor");
    private static AtomicInteger count = new AtomicInteger(0);

    private TcpSocket tcpSocket = TcpSocket.getInstance();

    private Integer id;
    private String imei;

    AirSensor() {
        Integer id = count.addAndGet(1);
        this.id = id;
        this.imei = createImei();
        logger.info("NEW DEVICE - ID:" + id + ",IMEI:" + imei);
    }

    /**
     * 随机生成唯一IMEI序列号 长度为30
     */
    private static String createImei() {
        UUID uuid = UUID.randomUUID();
        String[] idd = uuid.toString().split("-");
        String result = idd[0] + idd[1] + idd[2] + idd[3] + idd[4];
        return result.substring(0, 30);
    }

    /**
     * FIXME 监听者 :生成模拟数据，发送TCP请求到服务器
     */
    @Override
    public void getAirInfo() {
        String protocol = ProtocolGenerator.genMockProtocol(imei).toUpperCase();
        logger.info("NEW RECORD - ID:" + id + "," + protocol);
        boolean result = tcpSocket.sendTcp(imei,protocol);
        if (result) {
            logger.info("TCP SUCCESS");
        } else {
            logger.warning("TCP FAILURE");
        }
    }

}
