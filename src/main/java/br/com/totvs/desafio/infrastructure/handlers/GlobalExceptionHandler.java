package br.com.totvs.desafio.infrastructure.handlers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.com.totvs.desafio.application.excepetion.AccountNotFoundException;
import br.com.totvs.desafio.application.excepetion.InvalidJwtAuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseError> accountNotFoundException(AccountNotFoundException ex) {

        ResponseError responseError = new ResponseError(ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseError> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        
        ResponseError responseError = new ResponseError(ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseError> invalidJwtAuthenticationException(InvalidJwtAuthenticationException ex) {

        ResponseError responseError = new ResponseError(ex.getMessage(),
                HttpStatus.FORBIDDEN,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseError> fileUploadException(FileUploadException ex) {

        ResponseError responseError = new ResponseError(ex.getMessage(),
                HttpStatus.FORBIDDEN,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseError);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseError> genericException(Exception ex) {

        ResponseError responseError = new ResponseError(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
