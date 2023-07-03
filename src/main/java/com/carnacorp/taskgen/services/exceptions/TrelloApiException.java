package com.carnacorp.taskgen.services.exceptions;

@SuppressWarnings("serial")
public class TrelloApiException extends RuntimeException {

    public TrelloApiException(String msg) {
        super(msg);
    }
}
