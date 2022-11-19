package org.bean.converter.metadata.model;

public class FieldMetadata {
    private final String fieldName;
    private final String readMethod;
    private final String writeMethod;
    private final Class<?> fieldType;
    private AnnotationMetadata annotationMetadata;

    public FieldMetadata(String fieldName, String readMethod, String writeMethod, Class<?> fieldType) {
        this.fieldName = fieldName;
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getReadMethod() {
        return readMethod;
    }

    public String getWriteMethod() {
        return writeMethod;
    }

    public Class<?> getType() {
        return fieldType;
    }

    public AnnotationMetadata getAnnotationMetadata() {
        return annotationMetadata;
    }

    public void setAnnotationMetadata(AnnotationMetadata annotationMetadata) {
        this.annotationMetadata = annotationMetadata;
    }

    @Override
    public String toString() {
        return "FieldMetadata{" +
                "fieldName='" + fieldName + '\'' +
                ", readMethod='" + readMethod + '\'' +
                ", writeMethod='" + writeMethod + '\'' +
                ", fieldType=" + fieldType +
                ", " + annotationMetadata +
                '}';
    }
}
