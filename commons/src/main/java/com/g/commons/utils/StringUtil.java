package com.g.commons.utils;

import java.util.Random;

public class StringUtil {
    private static final Random RANDOM = new Random();

    private static final char[] DICT_NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] DICT_LOWERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] DICT_UPPERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] DICT_OTHERS = {'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};

    public static String randomString(int count, boolean numbers, boolean lowers, boolean uppers, boolean others) {
        if (count <= 0) {
            throw new IllegalArgumentException("Length must greater than 0");
        }

        if (!(numbers || lowers || uppers || others)) {
            throw new IllegalArgumentException("At lease one dictionary be selected");
        }

        int length = 0;
        if (numbers) {
            length += DICT_NUMBERS.length;
        }
        if (lowers) {
            length += DICT_LOWERS.length;
        }
        if (uppers) {
            length += DICT_UPPERS.length;
        }
        if (others) {
            length += DICT_OTHERS.length;
        }

        char[] dict = new char[length];

        int i = 0;
        if (numbers) {
            System.arraycopy(DICT_NUMBERS, 0, dict, i, DICT_NUMBERS.length);
            i += DICT_NUMBERS.length;
        }
        if (lowers) {
            System.arraycopy(DICT_LOWERS, 0, dict, i, DICT_LOWERS.length);
            i += DICT_LOWERS.length;
        }
        if (uppers) {
            System.arraycopy(DICT_UPPERS, 0, dict, i, DICT_UPPERS.length);
            i += DICT_UPPERS.length;
        }
        if (others) {
            System.arraycopy(DICT_OTHERS, 0, dict, i, DICT_OTHERS.length);
        }

        char[] value = new char[count];

        while ((--count) >= 0) {
            value[count] = dict[RANDOM.nextInt(length)];
        }

        return new String(value);
    }

    public static String format(int intValue) {
        String formatted = Integer.toHexString(intValue);
        StringBuilder buf = new StringBuilder("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    public static String format(short shortValue) {
        String formatted = Integer.toHexString(shortValue);
        StringBuilder buf = new StringBuilder("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }
}
