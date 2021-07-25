package com.sapient.rakesh.footballleague.globalExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sapient.rakesh.footballleague.exception.BadRequestException;
import com.sapient.rakesh.footballleague.exception.Error;

@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({BadRequestException.class})
	public ResponseEntity<Error> handleBadRequestException(Exception ex) {
		return new ResponseEntity<Error>(new Error(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({Exception.class, HttpClientErrorException.class})
	public ResponseEntity<Error> handleException(Exception ex) {
		return new ResponseEntity<>(new Error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
