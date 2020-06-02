package org.gmfbilu.superapp.module_java.basic.设计模式.工厂.工厂方法;

public class Test {

    public static void main(String[] args) {
      /*  Factory bmwFactory = new BMWFactory();
        System.out.println(bmwFactory.getCar().getName());
        Factory benzFactory = new BenzFactory();
        System.out.println(benzFactory.getCar().getName());*/


        double a = -2378909876.9087;
        double b = -12323.789;
        double c = -12.97889;
        double e = -1;
        double f = 0;
        double g = 0.0978887;
        double h = 1.08766;
        double i = 1212.677888;
        double j = 7890987.7777777888;
        double k = 9999.98988;
        double m = 10000.0988;
        double l = 67865456.9876;
        double x = 100000000.988777;
        double y = 232327898767.076;
        formatBigNumber(a);
        formatBigNumber(b);
        formatBigNumber(c);
        formatBigNumber(e);
        formatBigNumber(f);
        formatBigNumber(g);
        formatBigNumber(h);
        formatBigNumber(i);
        formatBigNumber(j);
        formatBigNumber(k);
        formatBigNumber(m);
        formatBigNumber(l);
        formatBigNumber(x);
        formatBigNumber(y);
        formatBigNumber(10000);
        formatBigNumber(100000000);
    }


    public static String formatBigNumber(double number) {
        String format = "";
        double abs = Math.abs(number);
        if (abs >= 10000 && abs < 100000000) {
            double v = number / 10000;
            format = fuzhu(v + "", "万");
        } else if (abs >= 100000000) {
            double v = number / 100000000;
            format = fuzhu(v + "", "亿");
        } else {
            //小于一万
            format = fuzhu(number + "", "");
        }
        System.out.println(format);
        return format;
    }

    public static String fuzhu(String number, String unit) {
        String format = "";
        if (number.contains(".")) {
            int length = number.length();
            int i = number.indexOf(".");
            if (i + 1 == length) {
                //不包含小数位，不应该出现的情况
                format = number + "00" + unit;
            } else if (i + 2 == length) {
                //只包含一位小数，末尾加上一位0
                format = number + "0" + unit;
            } else if (i + 3 == length) {
                //只包含两位小数，什么都不加
                format = number + unit;
            } else {
                //包含多为小数
                format = number.substring(0, i + 3) + unit;
            }
        } else {
            //加上.00
            format = number + ".00" + unit;
        }
        return format;
    }
}
