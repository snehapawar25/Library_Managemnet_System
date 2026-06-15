package edu.library_management_system.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

public class GlobalExceptionHandler {
	
	
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponseDto>handleNotFoundException(
		  ResourceNotFoundException ex, HttpServletRequest req
		  ){
	  ErrorResponseDto dto=ErrorResponseDto.builder()
			  .statusCode(HttpStatus.NOT_FOUND.value())
			  .message(ex.getMessage())
			  .error("NOT_FOUND")
			  .timeStamp(LocalDateTime.now())
			  .path(req.getRequestURI())
			  .build();
	  
	  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
  }
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleGeneralException(
		  Exception ex, HttpServletRequest req
		  ){
	  ErrorResponseDto dto=ErrorResponseDto.builder()
			  .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
			  .message(ex.getMessage())
			  .error("INTERNAL_SERVER_ERROR")
			  .timeStamp(LocalDateTime.now())
			  .path(req.getRequestURI())
			  .build();
	  
	  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
  }
}
