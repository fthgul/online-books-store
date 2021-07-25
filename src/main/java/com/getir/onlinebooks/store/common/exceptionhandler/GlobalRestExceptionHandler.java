package com.getir.onlinebooks.store.common.exceptionhandler;

import com.getir.onlinebooks.store.common.ApiError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.List;

@ControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public <T> T handleEntityNotFoundException(EntityNotFoundException e) {
        ApiError apiError = createApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return (T) new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public <T> T handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ApiError apiError = createApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return (T) new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public <T> T handleNotSupportedMethodException(HttpRequestMethodNotSupportedException ex) {
        ApiError apiError = createApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return (T) new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public <T> T handleBaseException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ApiError apiError = processFieldErrors(fieldErrors);
        return (T) new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(value = ObjectOptimisticLockingFailureException.class)
    public <T> T handleOptimisticLockingFailureException(Exception e) {
        ApiError apiError = createApiError(HttpStatus.INTERNAL_SERVER_ERROR, "please try again!");
        apiError.addMessage(e.getMessage());
        return (T) new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }


    @ExceptionHandler(value = Exception.class)
    public <T> T handleBaseException(Exception e) {
        ApiError apiError = createApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return (T) new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(value = ValidationException.class)
    public <T> T handleValidationException(Exception e) {
        ApiError apiError = createApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return (T) new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(final AuthenticationException ex) {
        final ApiError apiError = createApiError(HttpStatus.UNAUTHORIZED);
        apiError.addMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }


    private ApiError processFieldErrors(List<FieldError> fieldErrors) {
        ApiError apiError = createApiError(HttpStatus.BAD_REQUEST);
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            apiError.addMessage(fieldError.getDefaultMessage());
        }
        return apiError;
    }

    private ApiError createApiError(HttpStatus httpStatus) {
        ApiError apiError = new ApiError();
        apiError.setError(httpStatus.getReasonPhrase());
        apiError.setHttpStatus(httpStatus);
        apiError.setStatus(httpStatus.value());
        return apiError;
    }

    private ApiError createApiError(HttpStatus httpStatus, String message) {
        ApiError apiError = new ApiError();
        apiError.setError(httpStatus.getReasonPhrase());
        apiError.addMessage(message);
        apiError.setHttpStatus(httpStatus);
        apiError.setStatus(httpStatus.value());
        return apiError;
    }
}
