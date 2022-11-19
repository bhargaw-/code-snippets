package org.bean.converter.util;

import org.bean.converter.exception.BeanConverterException;
import org.bean.converter.metadata.annotation.FixedRecord;
import org.bean.converter.metadata.model.RecordMetadata;
import org.bean.converter.unmarshaller.DelimiterStringTokenizer;
import org.bean.converter.unmarshaller.FixedLengthStringTokenizer;
import org.bean.converter.unmarshaller.ITokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class CommonsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonsUtil.class);

    private CommonsUtil() {
    }

    public static void validate(Object object, String errorMessage) {
        if (Objects.isNull(object)) {
            throw new BeanConverterException(errorMessage);
        }
    }

    public static  <T> ITokenizer getTokenizerInstance(RecordMetadata<T> recordMetadata){
        if (FixedRecord.DEFAULT_DELIMITER_VALUE.equals(recordMetadata.getDelimiter())) {
            LOGGER.info("FixedLengthStringTokenizer implementation is found");
            return new FixedLengthStringTokenizer<>(recordMetadata);
        }else{
            LOGGER.info("DelimiterStringTokenizer implementation is found");
            return new DelimiterStringTokenizer<>(recordMetadata);
        }
    }
}
