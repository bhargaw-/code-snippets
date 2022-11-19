package org.bean.converter.metadata.model;

public class BooleanPatternMetadata {
    private final String trueValue;
    private final String falseValue;

    public BooleanPatternMetadata(String trueValue, String falseValue) {
        this.trueValue = trueValue;
        this.falseValue = falseValue;
    }

    public String getTrueValue() {
        return trueValue;
    }

    public String getFalseValue() {
        return falseValue;
    }

    @Override
    public String toString() {
        return "BooleanPatternMetadata : {" +
                "trueValue='" + trueValue + '\'' +
                ", falseValue='" + falseValue + '\'' +
                '}';
    }
}
