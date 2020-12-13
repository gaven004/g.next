package com.g.commons.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 字符ID生成器，与{@link com.g.commons.utils.IDGenerator}类似的算法
 * <p>
 * 区别为字符ID生成器以日期时间开头，便于阅读，一般用于订单号之类的与人相关的唯一标识
 * <p>
 * ID组成规则：时间戳（精确到秒）+ 机器标识 + 计数器（每秒可生成ID数，由sequence的最大值所定），
 * 如：2020121315401800000f，长度20位（机器标识2位），目前只使用小写字母
 * <p>
 * 性能：在一台双核I5机器上跑，10000000 ID，约2秒
 *
 * @version 1.0.0, 2020-12-12, Gaven
 */
@Component
public class HexIDGenerator {
    private int idLength; // ID长度
    private String workerId; // 机器标识
    private short sequence = 0; // 序列号
    private long lastTimestamp = -1L; // 上一次时间戳
    private String lastTimestampString = null;

    ZoneOffset zoneOffset = OffsetDateTime.now().getOffset();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Autowired
    public HexIDGenerator(@Value("${HexIDGenerator.workerId}") String workerId) {
        if (!StringUtils.hasText(workerId)) {
            throw new IllegalArgumentException("Worker id must be set");
        }

        this.workerId = workerId;
        this.idLength = 14 + workerId.length() + 4; // Length of datetime string + workerId.length + length of integer
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized String nextId() {
        long timestamp = System.currentTimeMillis() / 1000;
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d seconds",
                    (lastTimestamp - timestamp)));
        }

        if (timestamp == lastTimestamp) {
            // 相同秒内，序列号自增
            sequence++;

            // 同一秒的序列数已经达到最大
            if (sequence == 0L) {
                timestamp = getNextSecond();
                setValue(timestamp);
            }
        } else {
            // 不同秒内，序列号置为0
            sequence = 0;
            setValue(timestamp);
        }

        StringBuilder buff = new StringBuilder(idLength)
                .append(lastTimestampString) // 时间戳部分
                .append(workerId) // 机器标识部分
                .append(StringUtil.format((short) sequence)); // 序列号部分

        return buff.toString();
    }

    private void setValue(long timestamp) {
        lastTimestamp = timestamp;
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestamp, 0, zoneOffset);
        lastTimestampString = localDateTime.format(formatter);
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
