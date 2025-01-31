package org.zkpk.cs.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

public class IncorrectCaptchaException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2388389696082850201L;
	
	public IncorrectCaptchaException() {
		super();
	}

	public IncorrectCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectCaptchaException(String message) {
		super(message);
	}

	public IncorrectCaptchaException(Throwable cause) {
		super(cause);
	}

}
