package com.footballteam.managefootballteam.exception;

import java.text.MessageFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	public enum CodeErrorException {
		ERROR_CREATING_EQUIPE(1, "Error creating équipe {0}"),
		ERROR_RETRIEVING_EQUIPES(2, "Error retrieving équipes{0}"), NOT_FOUND_ELEMENT(3, "{0} with id {1} not found");

		private Integer codeNumber;
		private String stringError;

		CodeErrorException(Integer codeNumber, String stringError) {
			this.codeNumber = codeNumber;
			this.stringError = stringError;
		}
	}

	private final String[] args;
	private CodeErrorException codeErrorException;

	public EquipeException(final CodeErrorException codeError) {
		this.codeErrorException = codeError;
		this.args = null;
	}

	public EquipeException(final CodeErrorException codeError, final String... args) {
		this.codeErrorException = codeError;
		this.args = args;
	}

	@Override
	public String getMessage() {
		if (this.args != null && this.getArgs().length > 0) {
			return MessageFormat.format(codeErrorException.stringError, args);
		}
		return codeErrorException.stringError;
	}

}
