package com.Kashish.secure_order_api.exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.Kashish.secure_order_api.dto.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex)
	{
		return new ResponseEntity<>(new ApiResponse(404,ex.getMessage(),null),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex)
	{
		String message=ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
		return new ResponseEntity<>(new ApiResponse(400,message,null),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ApiResponse> handleInvalidCredentials(InvalidCredentialsException ex)
	{
		return new ResponseEntity<>(new ApiResponse(401,ex.getMessage(),null),HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(PasswordResetException.class)
	public ResponseEntity<ApiResponse> handlePassworedResetException(PasswordResetException ex)
	{
		return new ResponseEntity<>(new ApiResponse(400,ex.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ApiResponse> handleResponseStatusException(ResponseStatusException ex)
	{
	    return new ResponseEntity<>(
	            new ApiResponse(
	                    ex.getStatusCode().value(),
	                    ex.getReason(),
	                    null
	            ),
	            ex.getStatusCode()
	    );
	}

}
