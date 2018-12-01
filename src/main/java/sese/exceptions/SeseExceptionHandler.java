package sese.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class SeseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SeseException.class)
    public ResponseEntity<Object> handleSeseException(SeseException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ex.getSeseError().getCode());
        errorResponse.setMessage(ex.getSeseError().getMessage());
        errorResponse.setTimestamp(OffsetDateTime.now());
        errorResponse.setDescription(request.getDescription(false));

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), ex.getSeseError().getHttpStatus(), request);
    }
}
