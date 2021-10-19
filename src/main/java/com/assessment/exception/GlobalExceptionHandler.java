package com.assessment.exception;

import java.net.BindException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assessment.ErrorCodes;
import com.assessment.exception.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	
	private Map<String,String> fieldErrors = new HashMap<String, String>();
	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);	
	
	@Override
	protected ResponseEntity<Object> handleBindException(org.springframework.validation.BindException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		super.handleBindException(ex, headers, status, request);
		for(FieldError error : ex.getFieldErrors()){
			fieldErrors.put(error.getField(), error.getDefaultMessage());
		}
		String jsonError = "{"+fieldErrors.entrySet().stream()
			    .map(e -> "\""+ e.getKey() + "\":\"" + String.valueOf(e.getValue()) + "\"")
			    .collect(Collectors.joining(", "))+"}";
		final ErrorResponse error = new ErrorResponse(ErrorCodes.BAD_USER_INPUT_REQUEST,jsonError,
        		DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(LocalDateTime.now()));    
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	
	@ExceptionHandler({ UserAlreadyExistException.class })
    public ResponseEntity<Object> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
        
        final ErrorResponse error = new ErrorResponse(ErrorCodes.USER_ALREADY_EXISTS,"User already exists",
        		DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(LocalDateTime.now()));        		
        		
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
		
	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(
		      MissingServletRequestParameterException ex, HttpHeaders headers,
		      HttpStatus status, WebRequest request){
		final ErrorResponse error = new ErrorResponse(ErrorCodes.BAD_USER_INPUT_REQUEST,ex.getMessage(),
        		DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(LocalDateTime.now()));    
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	@ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleGenericException(final RuntimeException ex, final WebRequest request) {
		logger.info("Inside exception error");
        final ErrorResponse error = new ErrorResponse(ErrorCodes.GENERIC_EXCEPTION,ex.getMessage(),
        		DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(LocalDateTime.now()));        		
        		
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<Object> handleXXException(HttpClientErrorException ex,final WebRequest request) {
		
		final ErrorResponse error = new ErrorResponse(ErrorCodes.HTTP_CLIENT_ERROR,ex.getMessage(),
        		DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(LocalDateTime.now()));       
		logger.info("Inside client error");
        		
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(HttpServerErrorException.class)
	public ResponseEntity<Object> handleXXException(HttpServerErrorException ex,final WebRequest request) {
		
		logger.info("Inside server error");
		final ErrorResponse error = new ErrorResponse(ErrorCodes.HTTP_SERVER_ERROR,ex.getMessage(),
        		DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(LocalDateTime.now()));        		
        		
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	

}
