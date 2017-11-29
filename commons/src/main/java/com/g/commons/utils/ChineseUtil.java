package com.g.commons.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 中文工具类
 *
 * @version 1.0.0, 2017年10月2日, Gaven
 */
public class ChineseUtil {
    // 大写数字
    private static final char RMB_NUMBER[] = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
    // 大写单位
    private static final char RMB_UNIT[] = { '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟' };

    private static final char RMB_UNIT_SP[] = { '元', '元', '元', ' ', '万', '万', '万', ' ', '亿', '亿', '亿', '亿' };

    private static final char ZHENG = '正';

    private static final char ZERO = '零';

    private static final char JIAO = '角';

    private static final char FEN = '分';

    private static final long MAX_VALUE = 1000000000000L;

    private ChineseUtil() {
    }

    /*
     * 人民币大写符号的正确写法还应注意以下几项：
     * 
     * 一、中文大写金额数字到“元”为止的，在“元”之后、应写“整”(或“正”)字;在“角”之后，可以不写“整”(或“正”)字;大写金额数字有“分”的，“
     * 分”后面不写“整”(或“正”)字。
     * 
     * 二、中文大写金额数字前应标明“人民币”字样，大写金额数字应紧接“人民币”字样填写，不得留有空白。大写金额数字前未印“人民币”字样的，应加填“人民币
     * ”三字，在票据和结算凭证大写金额栏内不得预印固定的“仟、佰、拾、万、仟、佰、拾、元、角、分”字样。
     * 
     * 三、阿拉伯数字小写金额数字中有“0”时，中文大写应按照汉语语言规律、金额数字构成和防止涂改的要求进行书写。举例如下：
     * 
     * 1、阿拉伯数字中间有“0”时，中文大写要写“零”字，如 ¥1409.50 应写成人民币壹仟肆佰零玖元伍角;
     * 
     * 2、阿拉伯数字中间连续有几个“0”时、中文大写金额中间可以只写一个“零”字，如￥6007.14应写成人民币陆仟零柒元壹角肆分。
     * 
     * 3、阿拉伯金额数字万位和元位是“0”，或者数字中间连续有几个“0”，万位、元位也是“0”但千位、角位不是“0”时，中文大写金额中可以只写一个零字，
     * 也可以不写“零”字，如 ￥1680.32 应写成人民币壹仟陆佰捌拾元零叁角贰分，或者写成人民币壹仟陆佰捌拾元叁角贰分。又如 ￥107000. 53
     * 应写成人民币壹拾万柒仟元零伍角叁分，或者写成人民币壹拾万零柒仟元伍角叁分。
     * 
     * 4、阿拉伯金额数字角位是“0”而分位不是“0”时，中文大写金额“元”后面应写“零”字，如 ￥16409.02
     * 应写成人民币壹万陆仟肆佰零玖元零贰分， 又如￥325.04应写成人民币叁佰贰拾伍元零肆分。
     */

    /**
     * 将金额转换为大写
     * 
     * 只接受两位小数
     */
    public static String getAmountInWords(BigDecimal d) {
        if (d == null) {
            return null;
        }

        long longValue = d.longValue();
        if (longValue >= MAX_VALUE) {
            throw new IllegalArgumentException("Exceeds the maximum");
        }

        int i, l, k = 0;
        boolean zf = false; // 0标志
        String s;
        StringBuffer buff = new StringBuffer();

        d = d.setScale(2, RoundingMode.HALF_UP);
        s = d.toPlainString();
        l = s.length() - 1;

        boolean isInt = (s.charAt(l) == '0' && s.charAt(l - 1) == '0'); // 是否是整数

        if (longValue > 0) {
            // 处理整数位
            for (i = 0, l = l - 3; i <= l; i++) {
                if (s.charAt(i) == '0') {
                    if (!zf) {
                        zf = true;
                        k = i;
                    }
                } else {
                    if (zf) {
                        // 处理0的情况
                        if (RMB_UNIT_SP[l - k] != RMB_UNIT_SP[l - i] && RMB_UNIT_SP[l - k] != ' ') {
                            buff.append(RMB_UNIT_SP[l - k]);
                        }

                        // 阿拉伯数字中间有“0”时，中文大写要写“零”字，如 ¥1409.50 应写成人民币壹仟肆佰零玖元伍角;

                        // 阿拉伯数字中间连续有几个“0”时、中文大写金额中间可以只写一个“零”字，
                        // 如￥6007.14应写成人民币陆仟零柒元壹角肆分
                        if (RMB_UNIT[l - i] != '仟') {
                            // 阿拉伯金额数字亿位和万位是“0”，但千万位、千位不是“0”时，中文大写金额中可以只写一个零字，也可以不写“零”字
                            // 如：￥107000.53，应写成人民币壹拾万柒仟元零伍角叁分，或者写成人民币壹拾万零柒仟元伍角叁分
                            buff.append(ZERO);
                        }

                        zf = false;
                    }

                    buff.append(RMB_NUMBER[s.charAt(i) - '0']).append(RMB_UNIT[l - i]);
                }
            }

            if (zf) {
                if (RMB_UNIT_SP[l - k] != ' ') {
                    buff.append(RMB_UNIT_SP[l - k]);
                }

                if (RMB_UNIT_SP[l - k] != RMB_UNIT[0]) {
                    buff.append(RMB_UNIT[0]);
                }
            }

            // 处理小数位
            if (isInt) {
                buff.append(ZHENG);
            } else {
                // 角
                l = s.length() - 2;
                if (s.charAt(l) == '0') {
                    // 阿拉伯金额数字角位是“0”而分位不是“0”时，中文大写金额“元”后面应写“零”字
                    // 如￥325.04应写成人民币叁佰贰拾伍元零肆分
                    buff.append(ZERO);
                } else {
                    buff.append(RMB_NUMBER[s.charAt(l) - '0']).append(JIAO);
                }

                // 分
                l++;
                if (s.charAt(l) != '0') {
                    buff.append(RMB_NUMBER[s.charAt(l) - '0']).append(FEN);
                }
            }
        } else {
            // 整数位为0
            if (isInt) {
                buff.append("零元正");
            } else {
                // 角
                l = s.length() - 2;
                if (s.charAt(l) != '0') {
                    buff.append(RMB_NUMBER[s.charAt(l) - '0']).append(JIAO);
                }

                // 分
                l++;
                if (s.charAt(l) != '0') {
                    buff.append(RMB_NUMBER[s.charAt(l) - '0']).append(FEN);
                }
            }
        }

        return buff.toString();
    }
    
    /**
     * 根据Unicode Block判断中文汉字和符号
     */

    /**
     * 是否中文汉字和符号
     * 
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 是否中文汉字
     * 
     * @param c
     * @return
     */
    public static boolean isChineseCharacter(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) {
            return true;
        }
        return false;
    }

    /**
     * 是否中文符号
     * 
     * @param c
     * @return
     */
    public static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

}
