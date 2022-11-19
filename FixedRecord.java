@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedRecord {
    String DEFAULT_RECORD_PREFIX_VALUE = "";

    String DEFAULT_DELIMITER_VALUE = "";

    char DEFAULT_CHAR_VALUE = ' ';

    String headerPrefix() default DEFAULT_RECORD_PREFIX_VALUE;

    String recordPrefix() default DEFAULT_RECORD_PREFIX_VALUE;

    String tailerPrefix() default DEFAULT_RECORD_PREFIX_VALUE;

    String delimiter() default DEFAULT_DELIMITER_VALUE;

    char padding() default DEFAULT_CHAR_VALUE;
}
