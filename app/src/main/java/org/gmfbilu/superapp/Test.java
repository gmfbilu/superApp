package org.gmfbilu.superapp;

import java.math.BigDecimal;

public class Test {

    public static void main(String[] arge) {
        Long a = 10l;
        Long b = 10l;
        System.out.println(a == b);

        String mount = "123.00000001";
        double v;
        try {
            v = Double.parseDouble(mount);
        } catch (Exception e) {
            System.out.println("提币数量格式错误");
            return;
        }
        System.out.println(v + "");

        BigDecimal bigDecimal = new BigDecimal(6903731992887.172330200000000000000);
        System.out.println(bigDecimal.toString());//6903731992887.171875
        System.out.println(bigDecimal.doubleValue());
        System.out.println(bigDecimal.stripTrailingZeros().toPlainString());

    }


}
