package com.ratepay.bugtracker.api.aspect;

import com.ratepay.bugtracker.exception.NotFoundBugException;
import com.ratepay.bugtracker.exception.NotFoundProjectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class BugResourceResponseExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(NotFoundBugException.class)
	public final ResponseEntity<Object> handleBusinessException(NotFoundBugException ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotFoundProjectException.class)
	public final ResponseEntity<Object> handleBusinessException(NotFoundProjectException ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("validation exception {}", ex);
		return new ResponseEntity<>(ex.getAllErrors(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleGeneralException(Exception ex) {
		logger.error("unexpected error ", ex);
		return new ResponseEntity<>("FAILURE: contact Admin", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
