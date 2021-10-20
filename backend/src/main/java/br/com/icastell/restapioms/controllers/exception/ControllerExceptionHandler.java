package br.com.icastell.restapioms.controllers.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;

import br.com.icastell.restapioms.services.exceptions.AuthorizationException;
import br.com.icastell.restapioms.services.exceptions.DataIntegrityException;
import br.com.icastell.restapioms.services.exceptions.FileException;
import br.com.icastell.restapioms.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value = {ObjectNotFoundException.class})
	public ResponseEntity<StandardError> ObjectNotFoundHandler(
			ObjectNotFoundException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.NOT_FOUND.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(value = {DataIntegrityException.class})
	public ResponseEntity<StandardError> DataIntegrityHandler(
			DataIntegrityException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), 
				                              e.getMessage(), 
				                              System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<StandardError> validationHandler(
			MethodArgumentNotValidException e, HttpServletRequest request) {
		
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), 
				                              "Validation Error", 
				                              System.currentTimeMillis());	
		
		List<FieldError> errors = e.getBindingResult().getFieldErrors(); 		
		errors.forEach(fe -> err.addError(fe.getField(), fe.getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(value = {AuthorizationException.class})
	public ResponseEntity<StandardError> authorization(
			AuthorizationException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.FORBIDDEN.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(value = {FileException.class})
	public ResponseEntity<StandardError> file(
			FileException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(value = {AmazonServiceException.class})
	public ResponseEntity<StandardError> amazonService(
			AmazonServiceException e, HttpServletRequest request) {
		
		HttpStatus statusCode = HttpStatus.valueOf(e.getErrorCode());
		
		StandardError err = new StandardError(
				statusCode.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(statusCode).body(err);
	}
	
	@ExceptionHandler(value = {AmazonClientException.class})
	public ResponseEntity<StandardError> amazonClient(
			AmazonClientException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(value = {AmazonS3Exception.class})
	public ResponseEntity<StandardError> amazonS3(
			AmazonS3Exception e, HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
