package org.gmfbilu.superapp.lib_base.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathUtils {

    private MathUtils(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;
    private static final DecimalFormat amountFormat = new DecimalFormat("###,###,###,##0.00");
    private static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后 10 位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由 scale 参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b   = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");

        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 金额格式化,保留两位小数
     *
     * @param value 数值
     * @return
     */
    public static String getAmountValue(double value) {
        return amountFormat.format(value);
    }
    /**
     * 金额格式化
     *
     * @param value 数值,保留两位小数
     * @return
     */
    public static String getAmountValue(String value) {
        return amountFormat.format(Double.parseDouble(value));
    }

    /**
     * 获取随机数  包含字母和数字
     *
     * @param length
     * @return
     */
    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }


    /**
     * 获取随机数  只包含数字
     *
     * @param length
     * @return
     */
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }


    /**
     * 获取随机数  只包含字母
     *
     * @param length
     * @return
     */
    public static String getRandomLetters(int length) {
        return getRandom(LETTERS, length);
    }


    /**
     * 获取随机数  只包含大写字母
     *
     * @param length
     * @return
     */
    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }


    /**
     * 获取随机数  只包含小写字母
     *
     * @param length
     * @return
     */
    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }


    /**
     * 从source中获取随机数
     *
     * @param source
     * @param length
     * @return
     */
    public static String getRandom(String source, int length) {
        return isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    private static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }
        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    /**
     * 得到从min到max之间的随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }


    private static boolean isEmpty(String chkStr) {
        if (chkStr == null) {
            return true;
        } else {
            return "".equals(chkStr.trim());
        }
    }


    /**
     * 字符串转16进制
     *
     * @param str
     * @return
     */
    public static String string2HexString(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }


    /***
     * 判断 String 是否是正整数
     *
     * @param string
     * @return
     */
    public static boolean isPureDigital(String string) {
        if (isBlank(string))
            return false;
        String regEx1 = "\\d+";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        return m.matches();
    }




    /**
     * 向上取整
     *
     * @param num
     * @return
     */
    public static int formatUp(double num) {
        double ceil = Math.ceil(num);
        String number = ceil + "";
        if (number.contains(".")) {
            int i = number.indexOf(".");
            number = number.substring(0, i);
        }
        return Integer.valueOf(number);
    }

    /**
     * 向下取整
     *
     * @param num
     * @return
     */
    public static int formatDown(double num) {
        double floor = Math.floor(num);
        String number = floor + "";
        if (number.contains(".")) {
            int i = number.indexOf(".");
            number = number.substring(0, i);
        }
        return Integer.valueOf(number);
    }

    private static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

}
