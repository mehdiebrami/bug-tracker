package com.ratepay.bugtracker.exception;

public class NotFoundBugException extends Exception {

	public NotFoundBugException(String id) {
		super("This bug does not exist: %s".formatted(id));
	}

}
