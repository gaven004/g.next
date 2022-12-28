package com.g.commons.utils;

import com.g.config.SystemConfig;

/**
 * ID生成器，Twitter的分布式雪花算法SnowFlake的JAVA实现
 * <p>
 * 分布式系统中，有一些需要使用全局唯一ID的场景，这种时候为了防止ID冲突可以使用36位的UUID，但是UUID有一些缺点，首先他相对比较长，另外UUID一般是无序的。
 * 有些时候我们希望能使用一种简单一些的ID，并且希望ID能够按照时间有序生成。
 * 而twitter的SnowFlake解决了这种需求，最初Twitter把存储系统从MySQL迁移到Cassandra，因为Cassandra没有顺序ID生成机制，所以开发了这样一套全局唯一ID生成服务。
 * <p>
 * 原理
 * SnowFlake算法产生的ID是一个64位的整型，结构如下（每一部分用“-”符号分隔）：
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * 1位标识部分，在java中由于long的最高位是符号位，正数是0，负数是1，一般生成的ID为正数，所以为0；
 * 41位时间戳部分，这个是毫秒级的时间，一般实现上不会存储当前的时间戳，而是时间戳的差值（当前时间-固定的开始时间），这样可以使产生的ID从更小值开始；41位的时间戳可以使用69年，(1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69年；
 * 10位节点部分，Twitter实现中使用前5位作为数据中心标识，后5位作为机器标识，可以部署1024个节点；
 * 12位序列号部分，支持同一毫秒内同一个节点可以生成4096个ID；
 * <p>
 * SnowFlake算法生成的ID大致上是按照时间递增的，用在分布式系统中时，需要注意数据中心标识和机器标识必须唯一，这样就能保证每个节点生成的ID都是唯一的。或许我们不一定都需要像上面那样使用5位作为数据中心标识，5位作为机器标识，可以根据我们业务的需要，灵活分配节点部分，如：若不需要数据中心，完全可以使用全部10位作为机器标识；若数据中心不多，也可以只使用3位作为数据中心，7位作为机器标识。
 * snowflake生成的ID整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞（由datacenter和workerId作区分），并且效率较高。据说：snowflake每秒能够产生26万个ID。
 */

public class IDGenerator {
    private static volatile IDGenerator instance;

    /**
     * 起始的时间戳
     */
    private final static long START_TIMESTAMP = 1577808000000L; // 2020-01-01

    /**
     * 每一部分占用的位数
     */
    private final static long DATACENTER_ID_BITS = 5; // 数据中心占用的位数
    private final static long WORKER_ID_BITS = 5; // 机器标识占用的位数
    private final static long SEQUENCE_BITS = 12; // 序列号占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS);
    private final static long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);

    /**
     * 每一部分向左的位移
     */
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private final static long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    private final static long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);


    private long datacenterId; // 数据中心

    private long workerId; // 机器标识

    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上一次时间戳

    private IDGenerator() {
        long candidateDatacenterId;
        long candidateWorkerId;

        try {
            candidateDatacenterId = Long.parseLong(SystemConfig.getProperty("commons.IDGenerator.datacenterId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("datacenter id must be set");
        }
        try {
            candidateWorkerId = Long.parseLong(SystemConfig.getProperty("commons.IDGenerator.workerId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("worker id must be set");
        }

        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATACENTER_ID));
        }
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }

        this.datacenterId = candidateDatacenterId;
        this.workerId = candidateWorkerId;
    }

    public static IDGenerator getInstance() {
        IDGenerator result = instance;
        if (result != null) {
            return result;
        }
        synchronized (IDGenerator.class) {
            if (instance == null) {
                instance = new IDGenerator();
            }
            return instance;
        }
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    (lastTimestamp - timestamp)));
        }

        if (timestamp == lastTimestamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & SEQUENCE_MASK;

            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                timestamp = getNextMillis();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return (timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT // 时间戳部分
                | datacenterId << DATACENTER_ID_SHIFT // 数据中心部分
                | workerId << WORKER_ID_SHIFT // 机器标识部分
                | sequence; // 序列号部分
    }

    private long getNextMillis() {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
