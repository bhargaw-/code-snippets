package org.bean.converter.metadata.model;

import java.math.RoundingMode;

public class DecimalPatternMetadata {
    private final int precision;
    private final int scale;
    private final String delimiter;
    private final RoundingMode roundingMode;

    public DecimalPatternMetadata(int precision, int scale, String delimiter, RoundingMode roundingMode) {

        this.precision = precision;
        this.scale = scale;
        this.delimiter = delimiter;
        this.roundingMode = roundingMode;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    @Override
    public String toString() {
        return "DecimalPatternMetadata : {" +
                "precision=" + precision +
                ", scale=" + scale +
                ", delimiter='" + delimiter + '\'' +
                ", roundingMode=" + roundingMode +
                '}';
    }
}
