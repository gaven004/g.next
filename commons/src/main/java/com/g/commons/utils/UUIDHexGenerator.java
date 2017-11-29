package com.g.commons.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ID生成器
 * 
 * ID组成规则：时间戳（精确到毫秒） + 计数器（每毫秒可生成Short.MAX_VALUE个ID） + IP（主机标识） + JVM（JVM标识），
 * 如：201609202207098880062011f70，长度27位
 * <p>
 * 保证ID遵守时间序，并在集群环境中不重复
 * <p>
 * 用法：UUIDHexGenerator.generate()
 *
 * @version 1.0.0, 2016年9月20日, Gaven
 *
 */
public class UUIDHexGenerator {
    private static final String IP = getIP();

    private static final String JVM = getProcessId();

    private static final String SUFFIX = IP + JVM;

    private static short counter = (short) 0;

    private static long ts = System.currentTimeMillis();

    private static String dt;
    static {
        dt = formatDate(ts);
    }

    private UUIDHexGenerator() {
    }

    /**
     * Unique in a millisecond for this JVM instance
     */
    public static String generate() {
        synchronized (UUIDHexGenerator.class) {
            if (ts == System.currentTimeMillis()) {
                counter++;

                if (counter < 0) {
                    while (ts == System.currentTimeMillis()) {
                        try {
                            Thread.sleep(1L);
                        } catch (InterruptedException e) {
                        }
                    }

                    ts = System.currentTimeMillis();
                    dt = formatDate(ts);
                    counter = 0;
                }
            } else {
                ts = System.currentTimeMillis();
                dt = formatDate(ts);
                counter = 0;
            }

            return new StringBuffer(30).append(dt).append(format(counter)).append(SUFFIX).toString();
        }
    }

    /**
     * 获取主机的IP，作为主机的标识，暂使用IP的最后一段，若有重复可修改算法
     */
    static String getIP() {
        final String fallback = "00";

        try {
            InetAddress ip = InetAddress.getLocalHost();
            return format(ip.hashCode()).substring(6);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return fallback;
    }

    /**
     * 获取JVM的运行进程号，作为JVM的标识，以区别系统中同一主机多个JVM运行实例的， 使用short型，在进程号小于30000的情况可用
     */
    static String getProcessId() {
        // Note: may fail in some JVM implementations
        // therefore fallback has to be provided
        final String fallback = "0000";

        // something like '<pid>@<hostname>', at least in SUN / Oracle JVMs
        final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        final int index = jvmName.indexOf('@');

        if (index < 1) {
            // part before '@' empty (index = 0) / '@' not found (index = -1)
            return fallback;
        }

        try {
            return format((short) Integer.parseInt(jvmName.substring(0, index)));
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    static String format(int intValue) {
        String formatted = Integer.toHexString(intValue);
        StringBuilder buf = new StringBuilder("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    static String format(short shortValue) {
        String formatted = Integer.toHexString(shortValue);
        StringBuilder buf = new StringBuilder("0000");
        return buf.append(formatted).substring(buf.length() - 4);
    }

    static String formatDate(long ts) {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return df.format(new Date(ts));
    }
}
