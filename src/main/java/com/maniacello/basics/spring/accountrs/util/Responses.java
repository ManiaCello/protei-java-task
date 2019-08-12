package com.maniacello.basics.spring.accountrs.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class includes short static methods to create ResponseEntity objects or throw
 * ResponseStatusException with given status code and body.
 *
 * @author maniacello
 */
public class Responses {

    public static ResponseEntity<Object> OK(Object body) {
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public static ResponseEntity<Object> OK() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static ResponseEntity<Object> CREATED(Object body) {
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    public static ResponseEntity<Object> CREATED() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public static ResponseEntity<Object> BAD_REQUEST() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Object> BAD_REQUEST(String message) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }

    public static ResponseEntity<Object> NOT_FOUND() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<Object> NOT_FOUND(String message) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }

    public static ResponseEntity<Object> BAD_GATEWAY() {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
    }

    public static ResponseEntity<Object> BAD_GATEWAY(String message) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, message);
    }

}
