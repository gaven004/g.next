package com.g.commons.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 字符ID生成器，与{@link com.g.commons.utils.IDGenerator}类似的算法
 * <p>
 * 区别为字符ID生成器以日期时间开头，便于用户阅读，一般用于订单号之类的与人相关的唯一标识
 * <p>
 * ID组成规则：时间戳（精确到秒） + 计数器（每秒可生成Integer.MAX_VALUE个ID），
 * 如：201609202207098880062011f70，长度27位，目前只使用小写字母
 *
 * @version 1.0.0, 2020-12-12, Gaven
 */
@Component
public class HexIDGenerator {
    private String workerId;     //机器标识

    private int idLength; //

    private int sequence = 0; //序列号
    private long lastTimestamp = -1L;//上一次时间戳

    @Autowired
    public HexIDGenerator(@Value("${HexIDGenerator.workerId}") String workerId) {
    }

    private long getNextSecond() {
        long timestamp = System.currentTimeMillis() / 1000;
        while (timestamp <= lastTimestamp) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // igonre
            }
            timestamp = System.currentTimeMillis() / 1000;
        }
        return timestamp;
    }
}
