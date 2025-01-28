package com.crewmeister.cmcodingchallenge.currency.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class utils {
    public static float withBigDecimal(float value, int places) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }
    public  static float roundToTwoDecimals(float value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).floatValue();
    }


}
