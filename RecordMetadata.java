package org.bean.converter.metadata.model;

import java.util.Collections;
import java.util.List;

public class RecordMetadata<T> {

    private final String headerPrefix;
    private final String recordPrefix;
    private final String tailerPrefix;
    private final String delimiter;
    private final char padding;
    private final Class<T> type;
    private List<FieldMetadata> fieldMetadataList;

    public RecordMetadata(String headerPrefix,
                          String recordPrefix,
                          String tailerPrefix,
                          String delimiter,
                          char padding,
                          Class<T> type) {

        this.headerPrefix = headerPrefix;
        this.recordPrefix = recordPrefix;
        this.tailerPrefix = tailerPrefix;
        this.delimiter = delimiter;
        this.padding = padding;
        this.type = type;
        fieldMetadataList = Collections.emptyList();
    }

    public String getHeaderPrefix() {
        return headerPrefix;
    }

    public String getRecordPrefix() {
        return recordPrefix;
    }

    public String getTailerPrefix() {
        return tailerPrefix;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public char getPadding() {
        return padding;
    }

    public Class<T> getType() {
        return type;
    }

    public List<FieldMetadata> getFieldMetadataList() {
        return fieldMetadataList;
    }

    public void setFieldMetadataList(List<FieldMetadata> fieldMetadataList) {
        this.fieldMetadataList = fieldMetadataList;
    }

    @Override
    public String toString() {
        return "{" +
                ", recordPrefix='" + recordPrefix + '\'' +
                ", delimiter='" + delimiter + '\'' +
                ", padding=" + padding +
                ", type=" + type +
                ", " + fieldMetadataList +
                '}';
    }
}
