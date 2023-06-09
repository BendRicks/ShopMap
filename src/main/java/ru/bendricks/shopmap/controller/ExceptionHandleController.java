package ru.bendricks.shopmap.controller;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.bendricks.shopmap.dto.MessageResponse;
import ru.bendricks.shopmap.exception.BannedException;
import ru.bendricks.shopmap.exception.NotAuthorizedException;
import ru.bendricks.shopmap.exception.NotCreatedException;
import ru.bendricks.shopmap.exception.NotEnoughAuthoritiesException;
import ru.bendricks.shopmap.exception.NotFoundException;
import ru.bendricks.shopmap.exception.TooMuchShopsException;
import ru.bendricks.shopmap.util.constraints.GeneralConstants;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ExceptionHandleController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleUserNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        exception.getEntityName().concat(" not found"),
                        System.currentTimeMillis()
                ), HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleNotAuthorizedException(NotAuthorizedException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        exception.getMessage(),
                        System.currentTimeMillis()
                ), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleNotEnoughAuthoritiesException(NotEnoughAuthoritiesException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        exception.getMessage(),
                        System.currentTimeMillis()
                ), HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleNotEnoughAuthoritiesException(BannedException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        exception.getMessage(),
                        System.currentTimeMillis()
                ), HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleUserNotCreatedException(NotCreatedException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        exception.getMessage(),
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleNullPointerException(NullPointerException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        "Some params are missing or are null",
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleSQLException(PSQLException exception) {
        logger.error("SQL exception");
        switch (exception.getSQLState()) {
            case GeneralConstants.SQLStateConstants.UNIQUE_CONSTRAINT:
                return new ResponseEntity<>(
                        new MessageResponse(
                                "Such shop name already exists",
                                System.currentTimeMillis()
                        ), HttpStatus.BAD_REQUEST
                );
            default:
                return new ResponseEntity<>(
                        new MessageResponse(
                                "Illegal params values",
                                System.currentTimeMillis()
                        ), HttpStatus.BAD_REQUEST
                );
        }
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleNumberFormatException(NumberFormatException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        "Incorrect number format",
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleBadCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        "Bad credentials",
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        "Not authorized",
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleTooMuchShopsException(TooMuchShopsException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        "Maximum amount of shops is 3",
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

}

