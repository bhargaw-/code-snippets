package org.bean.converter.util;


import org.bean.converter.marshaller.BeanMarshaller;
import org.bean.converter.marshaller.PropertyMarshaller;
import org.bean.converter.metadata.model.RecordMetadata;
import org.bean.converter.metadata.service.IRecordMetadataConverter;
import org.bean.converter.metadata.service.RecordMetadataConverter;
import org.bean.converter.unmarshaller.BeanUnmarshaller;
import org.bean.converter.unmarshaller.ITokenizer;
import org.bean.converter.unmarshaller.PropertyUnmarshaller;

import static org.bean.converter.util.CommonsUtil.getTokenizerInstance;
import static org.bean.converter.util.CommonsUtil.validate;

public class BeanConverterUtil {

    public static final String CONVERTER_IMPLEMENTATION_FOUND_NULL = "IRecordMetadataConverter implementation is null";
    public static final String PROPERTY_MARSHALLER_FOUND_NULL = "PropertyMarshaller should not be null";
    public static final String PROPERTY_UNMARSHALLER_FOUND_NULL = "PropertyUnmarshaller should not be null";
    public static final String METADATA_IS_FOUND_NULL = "recordMetadata is found null in IRecordMetadataConverter implementation";
    public static final String TOKENIZER_IMPLEMENTATION_FOUND_NULL = "ITokenizer should not be null";

    private BeanConverterUtil() {
    }

    public static <T> BeanMarshaller<T> createMarshaller(Class<T> tClass) {

        IRecordMetadataConverter<T> recordMetadataConverter = new RecordMetadataConverter<>(tClass);
        PropertyMarshaller fieldMarshaller = new PropertyMarshaller();
        return createMarshaller(recordMetadataConverter, fieldMarshaller);
    }

    public static <T> BeanMarshaller<T> createMarshaller(IRecordMetadataConverter<T> recordMetadataConverter,
                                                         PropertyMarshaller fieldMarshaller) {
        validate(recordMetadataConverter, CONVERTER_IMPLEMENTATION_FOUND_NULL);
        validate(fieldMarshaller, PROPERTY_MARSHALLER_FOUND_NULL);
        RecordMetadata<T> recordMetadata = recordMetadataConverter.getRecordMetadata();
        validate(recordMetadata, METADATA_IS_FOUND_NULL);
        return new BeanMarshaller<>(recordMetadata, fieldMarshaller);
    }

    public static <T> BeanUnmarshaller<T> createUnmarshaller(Class<T> tClass) {
        IRecordMetadataConverter<T> recordMetadataConverter = new RecordMetadataConverter<>(tClass);
        PropertyUnmarshaller propertyUnmarshaller = new PropertyUnmarshaller();
        return createUnmarshaller(recordMetadataConverter, propertyUnmarshaller);
    }

    public static <T> BeanUnmarshaller<T> createUnmarshaller(IRecordMetadataConverter<T> recordMetadataConverter,
                                                             PropertyUnmarshaller propertyUnmarshaller) {
        return createUnmarshaller(recordMetadataConverter, propertyUnmarshaller, null);
    }

    public static <T> BeanUnmarshaller<T> createUnmarshaller(IRecordMetadataConverter<T> recordMetadataConverter,
                                                             PropertyUnmarshaller propertyUnmarshaller,
                                                             ITokenizer iTokenizer) {
        validate(recordMetadataConverter, CONVERTER_IMPLEMENTATION_FOUND_NULL);
        validate(propertyUnmarshaller, PROPERTY_UNMARSHALLER_FOUND_NULL);
        RecordMetadata<T> recordMetadata = recordMetadataConverter.getRecordMetadata();
        validate(recordMetadata, METADATA_IS_FOUND_NULL);
        if (iTokenizer == null) {
            iTokenizer = getTokenizerInstance(recordMetadata);
        }
        validate(iTokenizer, TOKENIZER_IMPLEMENTATION_FOUND_NULL);
        return new BeanUnmarshaller<>(recordMetadata, propertyUnmarshaller, iTokenizer);
    }
}
