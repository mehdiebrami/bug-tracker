package com.ratepay.bugtracker.exception;

import java.io.Serial;

public class NotFoundBugException extends Exception {
	private static final long serialVersionUID = -3738473450763573666L;

	public NotFoundBugException(String id) {
		super("This bug does not exist: %s".formatted(id));
	}

}
