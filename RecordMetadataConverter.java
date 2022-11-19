package org.bean.converter.metadata.service;

import org.bean.converter.exception.BeanConverterException;
import org.bean.converter.metadata.annotation.*;
import org.bean.converter.metadata.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bean.converter.util.CommonsUtil.validate;

public class RecordMetadataConverter<T> implements IRecordMetadataConverter<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordMetadataConverter.class);
    private final Class<T> fixedRecordClass;
    private final FixedRecord fixedRecord;

    public RecordMetadataConverter(Class<T> fixedRecordClass) {
        validate(fixedRecordClass, "fixedRecordClass should not be null");
        this.fixedRecordClass = fixedRecordClass;
        this.fixedRecord = getFixedRecord(fixedRecordClass);
    }

    @Override
    public RecordMetadata<T> getRecordMetadata() {
        List<FieldMetadata> fieldMetadataList = Stream.of(fixedRecordClass.getDeclaredFields())
                .map(this::extractFieldMetadata)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(o -> o.getAnnotationMetadata().getPosition()))
                .peek(fieldMetadata -> fieldMetadata.getAnnotationMetadata().setPadding(fixedRecord.padding()))
                .collect(Collectors.toList());
        validatePropertyOrder(fieldMetadataList);
        RecordMetadata<T> recordMetadata = new RecordMetadata<>(
                fixedRecord.headerPrefix(),
                fixedRecord.recordPrefix(),
                fixedRecord.tailerPrefix(),
                fixedRecord.delimiter(),
                fixedRecord.padding(),
                fixedRecordClass);
        recordMetadata.setFieldMetadataList(fieldMetadataList);
        LOGGER.info("Record Metadata : {}", recordMetadata);
        return recordMetadata;
    }

    private void validatePropertyOrder(List<FieldMetadata> fieldMetadataList) {

        for (int i = 1; i <= fieldMetadataList.size(); i++) {
            int count = 0;
            for (FieldMetadata fieldMetadata : fieldMetadataList) {
                int position = fieldMetadata.getAnnotationMetadata().getPosition();
                if (i == position) {
                    count = count + 1;
                }
            }

            String error = null;
            if (count == 0) {
                error = "There is no property found at position #no '" + i + "'";
            } else if (count > 1) {
                int position = fieldMetadataList.get(i - 1).getAnnotationMetadata().getPosition();
                error = "Invalid position. '" + position + "' is found at multiple places";
            }
            if (error != null) {
                throw new BeanConverterException(error);
            }
        }
    }

    private FixedRecord getFixedRecord(Class<T> fixedRecordClass) {
        FixedRecord recordClassAnnotation = fixedRecordClass.getAnnotation(FixedRecord.class);
        validate(recordClassAnnotation, String.format(
                "%s must be annotated with @FixedRecord.", fixedRecordClass.getName()));
        LOGGER.info("{} is valid FixedRecord class", fixedRecordClass.getName());
        return recordClassAnnotation;
    }

    private FieldMetadata extractFieldMetadata(Field field) {

        FixedField fixedField = field.getAnnotation(FixedField.class);
        if (fixedField == null) {
            return null;
        }

        FieldMetadata fieldMetadata = createFixedBeanPropertyMetadata(field.getName(), fixedRecordClass);
        if (fieldMetadata == null) {
            return null;
        }

        int position = fixedField.position();
        int length = fixedField.length();
        Align alignment = fixedField.align();
        boolean required = fixedField.required();
        AnnotationMetadata annotationMetadata = new AnnotationMetadata(
                position,
                length,
                alignment,
                required
        );
        setMetadata(field, annotationMetadata);
        fieldMetadata.setAnnotationMetadata(annotationMetadata);
        return fieldMetadata;
    }

    private FieldMetadata createFixedBeanPropertyMetadata(String propertyName, Class<T> tClass) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, tClass);
            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (readMethod == null || writeMethod == null) {
                return null;
            }
            return new FieldMetadata(propertyName,
                    readMethod.getName(),
                    writeMethod.getName(),
                    propertyDescriptor.getPropertyType());

        } catch (IntrospectionException e) {
            String errorMessage = String.format("set or get method is missed for a given property '%s' in '%s'. ",
                    propertyName, tClass.getName());
            throw new BeanConverterException(errorMessage, e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void setMetadata(Field field, AnnotationMetadata annotationMetadata) {
        FixedField fixedField = field.getAnnotation(FixedField.class);
        Class type = field.getType();
        if (isDateType(type)) {
            DatePatternMetadata datePatternMetadata =
                    getDatePatternMetadata(fixedField.datePattern());
            annotationMetadata.setDatePatternMetadata(datePatternMetadata);
        } else if (isFloatingPointType(type)) {
            DecimalPatternMetadata decimalPatternMetadata =
                    getDecimalPatternMetadata(fixedField.decimalPattern());
            annotationMetadata.setDecimalPatternMetadata(decimalPatternMetadata);
        } else if (isBooleanType(type)) {
            BooleanPatternMetadata booleanPatternMetadata =
                    getBooleanPatternMetadata(fixedField.booleanPattern());
            annotationMetadata.setBooleanPatternMetadata(booleanPatternMetadata);
        }
    }

    private boolean isDateType(Class<T> type) {
        return isUtilDateType(type) ||
                isLocalDateType(type) ||
                isLocalDateTimeType(type);
    }

    private boolean isUtilDateType(Class<T> type) {
        return type.isAssignableFrom(Date.class);
    }

    private boolean isLocalDateType(Class<T> type) {
        return type.isAssignableFrom(LocalDate.class);
    }

    private boolean isLocalDateTimeType(Class<T> type) {
        return type.isAssignableFrom(LocalDateTime.class);
    }

    private boolean isFloatingPointType(Class<T> type) {
        return isFloatType(type) ||
                isDoubleType(type) ||
                isBigDecimalType(type);
    }

    private boolean isFloatType(Class<T> type) {
        return type.isAssignableFrom(Float.class) || type.isAssignableFrom(float.class);
    }

    private boolean isDoubleType(Class<T> type) {
        return type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class);
    }

    private boolean isBigDecimalType(Class<T> type) {
        return type.isAssignableFrom(BigDecimal.class);
    }

    private boolean isBooleanType(Class<T> type) {
        return type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class);
    }

    private BooleanPatternMetadata getBooleanPatternMetadata(BooleanPattern booleanPattern) {
        return new BooleanPatternMetadata(booleanPattern.trueValue(), booleanPattern.falseValue());
    }

    private DatePatternMetadata getDatePatternMetadata(DatePattern datePattern) {
        return new DatePatternMetadata(datePattern.pattern(), datePattern.zoneId());
    }

    private DecimalPatternMetadata getDecimalPatternMetadata(DecimalPattern decimalPattern) {
        return new DecimalPatternMetadata(decimalPattern.precision(),
                decimalPattern.scale(),
                decimalPattern.delimiter(),
                decimalPattern.roundMode());
    }
}
