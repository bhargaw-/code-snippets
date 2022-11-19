package org.bean.converter.metadata.annotation;

public @interface DatePattern {

    String PATTERN = "yyyy-MM-dd";

    String ZONE_ID = "UTC";

    String pattern() default PATTERN;

    String zoneId() default ZONE_ID;
}
