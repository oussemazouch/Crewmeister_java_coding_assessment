package com.crewmeister.cmcodingchallenge.currency.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class utils {
    public  static float roundToTwoDecimals(float value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).floatValue();
    }


}
