package org.bean.converter.exception;

public class BeanConverterException extends RuntimeException {

	private static final long serialVersionUID = -563419936854635659L;

	public BeanConverterException(String s) {
		super(s);
	}

	public BeanConverterException(String s, Throwable throwable) {
		super(s, throwable);
	}
}
