package org.bean.converter.metadata.model;


import org.bean.converter.metadata.annotation.Align;

public class AnnotationMetadata {
    private final int position;
    private final int length;
    private char padding;
    private final Align align;
    private final boolean required;
    private BooleanPatternMetadata booleanPatternMetadata;
    private DatePatternMetadata datePatternMetadata;
    private DecimalPatternMetadata decimalPatternMetadata;

    public AnnotationMetadata(int position, int length, Align align, boolean required) {
        this.position = position;
        this.length = length;
        this.align = align;
        this.required = required;
    }

    public int getPosition() {
        return position;
    }

    public int getLength() {
        return length;
    }

    public char getPadding() {
        return padding;
    }

    public Align getAlign() {
        return align;
    }

    public void setPadding(char padding) {
        this.padding = padding;
    }

    public boolean isRequired() {
        return required;
    }

    public void setBooleanPatternMetadata(BooleanPatternMetadata booleanPatternMetadata) {
        this.booleanPatternMetadata = booleanPatternMetadata;
    }

    public BooleanPatternMetadata getBooleanPatternMetadata() {
        return booleanPatternMetadata;
    }

    public void setDatePatternMetadata(DatePatternMetadata datePatternMetadata) {
        this.datePatternMetadata = datePatternMetadata;
    }

    public DatePatternMetadata getDatePatternMetadata() {
        return datePatternMetadata;
    }

    public void setDecimalPatternMetadata(DecimalPatternMetadata decimalPatternMetadata) {
        this.decimalPatternMetadata = decimalPatternMetadata;
    }

    public DecimalPatternMetadata getDecimalPatternMetadata() {
        return decimalPatternMetadata;
    }

    @Override
    public String toString() {
        return "AnnotationMetadata: {" +
                "position=" + position +
                ", length=" + length +
                ", padding=" + padding +
                ", align=" + align +
//                ", delimiter='" + delimiter + '\'' +
                ", " + booleanPatternMetadata +
                ", " + datePatternMetadata +
                ", " + decimalPatternMetadata +
                '}';
    }
}
