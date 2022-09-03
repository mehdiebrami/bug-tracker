package com.ratepay.bugtracker.exception;

public class NotFoundProjectException extends Exception {

	public NotFoundProjectException(String id) {
		super("This project does not exist: %s".formatted(id));
	}

}
