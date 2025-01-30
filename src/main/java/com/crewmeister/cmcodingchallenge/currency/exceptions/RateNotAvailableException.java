package com.crewmeister.cmcodingchallenge.currency.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@Getter
@Setter
public class RateNotAvailableException extends ResponseStatusException {
    public RateNotAvailableException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}