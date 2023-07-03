package com.carnacorp.taskgen.services.exceptions;

@SuppressWarnings("serial")
public class OpenAiApiException extends RuntimeException {

    public OpenAiApiException(String msg) {
        super(msg);
    }
}
