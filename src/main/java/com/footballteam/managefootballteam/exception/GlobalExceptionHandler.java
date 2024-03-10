package com.footballteam.managefootballteam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.footballteam.managefootballteam.error.ErrorDTO;

import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EquipeException.class)
	public ResponseEntity<ErrorDTO> handleException(EquipeException ex) {

		final ErrorDTO error = new ErrorDTO(ex.getCodeErrorException().name(), ex.getMessage());

		return switch (ex.getCodeErrorException()) {
		case NOT_FOUND_ELEMENT -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		case ERROR_CREATING_EQUIPE, ERROR_RETRIEVING_EQUIPES ->
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		default -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		};

	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<ErrorDTO> handleException(HttpServletResponse response, InvalidFormatException ex) {
		final ErrorDTO error = new ErrorDTO(ex.getOriginalMessage(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}