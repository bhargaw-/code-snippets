package org.bean.converter.metadata.annotation;


import org.bean.converter.util.StringUtil;

public enum Align {

    RIGHT {
        public String apply(String data, int length, char paddingChar) {
            String result;
            if (data == null) {
                data = "";
            }
            int dataLength = data.length();
            if (dataLength > length) {
                result = StringUtil.substring(data, dataLength - length, dataLength);
            } else {
                result = StringUtil.leftPad(data, length, paddingChar);
            }
            return result;
        }

        public String remove(String data, char paddingChar) {
            String result = data;
            if (data == null) {
                result = "";
            }
            while (result.startsWith("" + paddingChar)) {
                result = result.substring(1, result.length());
            }
            return result;
        }
    },

    LEFT {
        public String apply(String data, int length, char paddingChar) {
            String result;
            if (data == null) {
                data = "";
            }
            int dataLength = data.length();
            if (dataLength > length) {
                result = StringUtil.substring(data, 0, length);
            } else {
                result = StringUtil.rightPad(data, length, paddingChar);
            }
            return result;
        }

        public String remove(String data, char paddingChar) {
            String result = data;
            if (data == null) {
                result = "";
            }
            while (result.endsWith("" + paddingChar)) {
                result = result.substring(0, result.length() - 1);
            }
            return result;
        }
    };

    public abstract String apply(String data, int length, char paddingChar);

    public abstract String remove(String data, char paddingChar);
}
