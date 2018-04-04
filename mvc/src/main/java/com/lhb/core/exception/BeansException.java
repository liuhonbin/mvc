package com.lhb.core.exception;

public class BeansException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BeansException(String message) {
		super(message);
	}

	public BeansException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeansException(Throwable cause) {
		super(cause);
	}

	protected BeansException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
