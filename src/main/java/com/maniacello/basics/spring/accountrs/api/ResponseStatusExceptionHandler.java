package com.maniacello.basics.spring.accountrs.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception handler that generates response body in JSON format.
 *
 * @author maniacello
 */
@ControllerAdvice
public class ResponseStatusExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleException(RuntimeException e, WebRequest request) {
        if (e instanceof ResponseStatusException) {
            ResponseStatusException rse = (ResponseStatusException) e;
            return new ResponseEntity<>(
                    new ExceptionResponse(
                            rse.getReason(),
                            Instant.now().toString(),
                            rse.getStatus().value(),
                            rse.getStatus().getReasonPhrase()),
                    rse.getStatus());
        }
        return new ResponseEntity<>(new ExceptionResponse(
                e.getMessage(),
                Instant.now().toString(),
                HttpStatus.BAD_GATEWAY.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase()),
                HttpStatus.BAD_GATEWAY);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class ExceptionResponse {

        private String message;
        private String timestamp;
        private Integer statusCode;
        private String statusDescription;

        public ExceptionResponse() {
        }

        public ExceptionResponse(String message, String timestamp, Integer statusCode, String statusDescription) {
            this.message = message;
            this.timestamp = timestamp;
            this.statusCode = statusCode;
            this.statusDescription = statusDescription;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getStatusDescription() {
            return statusDescription;
        }

        public void setStatusDescription(String statusDescription) {
            this.statusDescription = statusDescription;
        }

    }

}
