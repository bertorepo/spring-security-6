package com.hubert.exception;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> exceptionHandler(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceDuplicateException.class)
    public ResponseEntity<ApiError> exceptionHandler(
            ResourceDuplicateException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                CONFLICT.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, CONFLICT);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiError> exceptionHandler(
            InsufficientAuthenticationException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                FORBIDDEN.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, FORBIDDEN);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiError> exceptionHandler(
            MalformedJwtException e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                new MalformedJwtException("Token Error").getMessage(),
                BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> exceptionHandler(

            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                new BadCredentialsException("Username/Password Incorrect!").getMessage(),
                UNAUTHORIZED.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, UNAUTHORIZED);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> exceptionHandler(
            Exception e,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }
}
