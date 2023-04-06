package com.caerdydd.taf.security;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends Exception{

    public CustomRuntimeException(String message) {
        super(message);
    }
    
}
