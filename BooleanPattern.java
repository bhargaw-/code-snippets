public @interface BooleanPattern {

    String TRUE_VALUE = "T";

    String FALSE_VALUE = "F";

    String trueValue() default TRUE_VALUE;

    String falseValue() default FALSE_VALUE;
}
