package br.com.icastell.restapioms.controllers.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;	
	
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String message, Long timeStamp) {
		super(status, message, timeStamp);		
	}
	
	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}

}
