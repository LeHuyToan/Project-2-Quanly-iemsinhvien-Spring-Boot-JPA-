package com.example.demo.controller;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ResponseDTO.ResponseDTOBuilder;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionController {
//	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler({NoResultException.class})
	public ResponseDTO<String> notFound(NoResultException e) {
		log.info("INFO", e);
		//C1
//		return ResponseDTO.<String>builder().status(404).msg("No data").build();
		
		//C2
		ResponseDTO<String> responseDTO = new ResponseDTO<>();
		responseDTO.setStatus(400);
		responseDTO.setMsg("No Data");
		return responseDTO;
	}
	
	@ExceptionHandler({BindException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)//HTTP STATUS CODE
	public ResponseDTO<String> badRequest(BindException e) {
		log.info("bad request");
		
		List<ObjectError> errors = e.getBindingResult().getAllErrors();
		
		String msg = "";
		for(ObjectError err : errors) {
			FieldError fieldError = (FieldError) err;
			
			msg += fieldError.getField() + ":" + err.getDefaultMessage() + ";";
		}
		
		return ResponseDTO.<String>builder().status(400).msg(msg).build();
	}
	
	@ExceptionHandler({SQLIntegrityConstraintViolationException.class})
	@ResponseStatus(code = HttpStatus.CONFLICT) 
	public ResponseDTO<String> duplicate(Exception e) {
		log.info("INFO", e);
		return ResponseDTO.<String>builder().status(409).msg("Duplicate data").build();
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ResponseDTO<String> accessDeny(Exception e){
		log.info("INFO",e);
		return ResponseDTO.<String>builder().status(403).msg("Access Deny").build();
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ResponseDTO<String> unauthrized(Exception e){
		log.info("INFO",e);
		return ResponseDTO.<String>builder().status(403).msg(e.getMessage()	).build();
	}
}
