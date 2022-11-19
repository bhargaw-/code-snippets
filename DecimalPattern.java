package org.bean.converter.metadata.annotation;

import java.math.RoundingMode;

public @interface DecimalPattern {

    int DECIMALS = 2;

    int PRECISION = 2;
    int SCALE = 2;

    String DECIMAL_DELIMITER = ".";

    int precision() default  PRECISION;

    int scale() default SCALE;

    String delimiter() default DECIMAL_DELIMITER;

    RoundingMode roundMode() default RoundingMode.HALF_UP;
}
