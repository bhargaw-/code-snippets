package org.bean.converter.metadata.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedField {

    int position();

    int length();

    Align align() default Align.LEFT;

    boolean required() default true;

    DecimalPattern decimalPattern() default @DecimalPattern;

    DatePattern datePattern() default @DatePattern;

    BooleanPattern booleanPattern() default @BooleanPattern;
}
